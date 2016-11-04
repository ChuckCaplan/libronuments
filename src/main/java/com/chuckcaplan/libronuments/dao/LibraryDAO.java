package com.chuckcaplan.libronuments.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.chuckcaplan.libronuments.entity.Library;

/**
 * @author Chuck Caplan
 *
 *         Database access object for a Library entity.
 */
@Transactional
@Repository
public class LibraryDAO extends BaseEntityDAO<Library> {

	/**
	 * Constructor to tell the BaseEntityDAO which entity we are working with.
	 */
	public LibraryDAO() {
		super(new Library());
	}

	/**
	 * @param name
	 * @param zipCode
	 * @return whether or not the library exists
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(String name, String zipCode) {
		String hql = "FROM Library as l WHERE l.name = :name and l.zipCode = :zipCode";
		List<Library> libraries = (List<Library>) hibernateTemplate.findByNamedParam(hql,
				new String[] { "name", "zipCode" }, new String[] { name, zipCode });
		return libraries.size() > 0 ? true : false;
	}

	/**
	 * Updates an existing library in the DB
	 * 
	 * @param library
	 */
	public void update(Library library) {
		Library l = getById(library.getId());
		l.setLocation(library.getLocation());
		l.setName(library.getName());
		l.setNeighborhood(library.getNeighborhood());
		l.setZipCode(library.getZipCode());
		hibernateTemplate.update(l);
	}

}
