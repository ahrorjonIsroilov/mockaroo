package noname.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 14.03.2022 10:29
 * Project : zakovat
 */
@Getter
@Setter
@NoArgsConstructor
public class GenericDto implements Dto{

    private Long id;

    public GenericDto(Long id) {
        this.id = id;
    }

}
