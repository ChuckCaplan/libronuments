package com.chuckcaplan.libronuments.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * @author Chuck Caplan Database access object for a Neighborhood entity.
 */
@Transactional
@Repository
public class NeighborhoodDAO extends BaseEntityDAO<Neighborhood> {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Constructor to tell the BaseEntityDAO which entity we are working with.
	 */
	public NeighborhoodDAO() {
		super(new Neighborhood());
	}

	/**
	 * @param neighborhood
	 * @return whether or not the neighborhood exists
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(String neighborhood) {
		String hql = "FROM Neighborhood as n WHERE n.neighborhood = :neighborhood";
		List<Neighborhood> neighborhoods = (List<Neighborhood>) hibernateTemplate.findByNamedParam(hql, "neighborhood",
				neighborhood);
		return neighborhoods.size() > 0 ? true : false;
	}

	/**
	 * Updates an existing neighborhood in the DB
	 * 
	 * @param neighborhood
	 */
	public void update(Neighborhood neighborhood) {
		Neighborhood n = getById(neighborhood.getId());
		n.setNeighborhood(neighborhood.getNeighborhood());
		hibernateTemplate.update(n);
	}
}
