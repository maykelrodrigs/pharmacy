package io.github.maykelrodrigs.pharmacy.service.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(Class<?> clazz, String id) {
        this(String.format("'%s' entity with ID: %s not found.", clazz.getSimpleName(), id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
