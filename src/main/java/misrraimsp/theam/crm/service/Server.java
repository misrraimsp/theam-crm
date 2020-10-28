package misrraimsp.theam.crm.service;


public interface Server<T> {
    T[] findAll();
    T findById(String id);
    T create(T object);
    T edit(T object);
    void delete(String id);
}
