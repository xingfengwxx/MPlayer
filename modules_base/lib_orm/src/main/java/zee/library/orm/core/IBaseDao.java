package zee.library.orm.core;

import java.util.List;

public interface IBaseDao<T> {

    // CREATE
    void insert(T entity);

    // READ
    List<T> query(T where);

    // DELETE
    void delete(T where);

    // UPDATE
    void update(T entity, T where);

}