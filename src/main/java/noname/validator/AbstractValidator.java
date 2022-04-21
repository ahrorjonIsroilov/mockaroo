package noname.validator;

import noname.dto.Dto;
import noname.dto.GenericDto;

import javax.xml.bind.ValidationException;
import java.io.Serializable;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 14.03.2022 10:45
 * Project : zakovat
 */
public abstract class AbstractValidator<
        CD extends Dto,
        UD extends GenericDto,
        K extends Serializable> implements Validator {

    public abstract void validateKey(K id) throws ValidationException;

    public abstract void validateOnCreate(CD cd) throws ValidationException;

    public abstract void validateOnUpdate(UD ud) throws ValidationException;

}
