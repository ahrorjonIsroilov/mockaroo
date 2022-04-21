package noname.mapper;

import java.util.List;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 14.03.2022 10:33
 * Project : zakovat
 */

public interface BaseMapper<E, D, CD, UD> extends Mapper {
    D toDto(E e);

    List<D> toDto(List<E> e);

    E fromCreateDto(CD cd);

    E fromUpdateDto(UD d);

}
