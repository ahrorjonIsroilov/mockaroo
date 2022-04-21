package noname.service;

import noname.mapper.Mapper;
import noname.repo.AbstractRepository;
import noname.validator.Validator;


public abstract class AbstractService<R extends AbstractRepository, M extends Mapper, V extends Validator> implements BaseService {
    protected final R repository;
    protected final M mapper;
    protected final V validator;

    protected AbstractService(R repository, M mapper, V validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }
}
