package com.chuckcaplan.libronuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chuckcaplan.libronuments.dao.MonumentDAO;
import com.chuckcaplan.libronuments.entity.Monument;

/**
 * 
 * Various operations that can be performed on a Monument entity.
 * 
 * @author Chuck Caplan
 *
 */
@Service
public class MonumentService implements BaseService<Monument> {

	@Autowired
	private MonumentDAO monumentDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getAll()
	 */
	@Override
	public List<Monument> getAll() {
		return monumentDAO.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getById(int)
	 */
	@Override
	public Monument getById(int monumentId) {
		return monumentDAO.getById(monumentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public boolean add(Monument monument) {
		if (monumentDAO.exists(monument.getName(), monument.getZipCode())) {
			return false;
		} else {
			monumentDAO.add(monument);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chuckcaplan.libronuments.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public void update(Monument monument) {
		monumentDAO.update(monument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#delete(int)
	 */
	@Override
	public void delete(int monumentId) {
		monumentDAO.delete(monumentId);
	}

}
