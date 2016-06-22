package crudexample.dao;

import java.util.List;

public interface CRUDDao {

	<T> List<T> getAll(Class<T> klass);

	<T> T save(T t);

	<T> void delete(T t);
}
