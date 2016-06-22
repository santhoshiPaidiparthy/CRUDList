package crudexample.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crudexample.dao.CRUDDao;

 
@Service
public class CRUDServiceImpl implements CRUDService  {


	@Autowired
	private CRUDDao CRUDDao;

	@Transactional(readOnly = true)
	public <T> List<T> getAll(Class<T> klass) {
		return CRUDDao.getAll(klass);
	}


	@Transactional
	public <T> T save(T t)  {
		T newRecord = null;
		newRecord = CRUDDao.save(t);
		return newRecord;
	}

	@Transactional
	public <T> void delete(T t) {
		CRUDDao.delete(t);
	}
}
