package com.chuckcaplan.libronuments.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.chuckcaplan.libronuments.entity.Monument;

/**
 * @author Chuck Caplan
 *
 *         Database access object for a Monument entity.
 */
@Transactional
@Repository
public class MonumentDAO extends BaseEntityDAO<Monument> {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Constructor to tell the BaseEntityDAO which entity we are working with.
	 */
	public MonumentDAO() {
		super(new Monument());
	}

	/**
	 * @param name
	 * @param zipCode
	 * @return whether or not the monument exists
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(String name, String zipCode) {
		String hql = "FROM Monument as m WHERE m.name = :name and m.zipCode = :zipCode";
		List<Monument> monuments = (List<Monument>) hibernateTemplate.findByNamedParam(hql,
				new String[] { "name", "zipCode" }, new String[] { name, zipCode });
		return monuments.size() > 0 ? true : false;
	}

	/**
	 * Updates an existing monument in the DB
	 * 
	 * @param monument
	 */
	public void update(Monument monument) {
		Monument m = getById(monument.getId());
		m.setLocation(monument.getLocation());
		m.setName(monument.getName());
		m.setNeighborhood(monument.getNeighborhood());
		m.setZipCode(monument.getZipCode());
		hibernateTemplate.update(m);
	}
}
