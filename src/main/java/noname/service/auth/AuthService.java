package noname.service.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import noname.config.encyriptions.PasswordEncoderConfigurer;
import noname.dto.auth.AuthUserDto;
import noname.dto.auth.RegisterDto;
import noname.dto.auth.SessionDto;
import noname.entity.auth.AuthUser;
import noname.properties.ServerProperties;
import noname.repo.auth.AuthUserRepository;
import noname.service.BaseService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements BaseService, UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findByUsernameAndDeletedFalse(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
        return User.builder().username(user.getUsername()).password(user.getPassword()).authorities(user.getAuthority()).accountLocked(false).accountExpired(false).disabled(false).credentialsExpired(false).build();
    }

    public AuthUser findById(Long id) {
        if (authUserRepository.findById(id).isPresent()) return authUserRepository.findById(id).get();
        return null;
    }

    public Boolean register(RegisterDto dto) {
        AuthUser user = AuthUser.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastName())
                .username(dto.getUsername())
                .password(new PasswordEncoderConfigurer().passwordEncoder().encode(dto.getPassword()))
                .build();
        authUserRepository.save(user);
        return true;
    }

    public Boolean delete(Long id) {
        Optional<AuthUser> byId = authUserRepository.findById(id);
        if (byId.isPresent()) {
            authUserRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    public ResponseEntity<SessionDto> getToken(AuthUserDto dto) {

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));

            HttpResponse response = httpclient.execute(httppost);

            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));

            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDto sessionDto = objectMapper.readValue(node.toString(), SessionDto.class);
                return new ResponseEntity<>(sessionDto, HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return null;
    }


}
