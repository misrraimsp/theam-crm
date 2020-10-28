package misrraimsp.theam.crm.util;

public class EntityNotFoundByIdException extends IllegalArgumentException {

    public EntityNotFoundByIdException(String entityId, String className) {
        super("Entity of class " + className + " not found by id=" + entityId);
    }

}
