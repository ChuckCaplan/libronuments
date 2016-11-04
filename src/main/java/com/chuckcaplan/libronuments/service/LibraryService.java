package com.chuckcaplan.libronuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chuckcaplan.libronuments.dao.LibraryDAO;
import com.chuckcaplan.libronuments.entity.Library;

/**
 * Various operations that can be performed on a Library entity.
 * 
 * @author Chuck Caplan
 *
 */
@Service
public class LibraryService implements BaseService<Library> {

	@Autowired
	private LibraryDAO libraryDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getAll()
	 */
	@Override
	public List<Library> getAll() {
		return libraryDAO.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getById(int)
	 */
	@Override
	public Library getById(int libraryId) {
		return libraryDAO.getById(libraryId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public boolean add(Library library) {
		if (libraryDAO.exists(library.getName(), library.getZipCode())) {
			return false;
		} else {
			libraryDAO.add(library);
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
	public void update(Library library) {
		libraryDAO.update(library);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#delete(int)
	 */
	@Override
	public void delete(int libraryId) {
		libraryDAO.delete(libraryId);
	}

}
