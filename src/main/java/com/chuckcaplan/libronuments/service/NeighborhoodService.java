package com.chuckcaplan.libronuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chuckcaplan.libronuments.dao.NeighborhoodDAO;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Various operations that can be performed on a Neighborhood entity.
 * 
 * @author Chuck Caplan
 *
 */
@Service
public class NeighborhoodService implements BaseService<Neighborhood> {

	@Autowired
	private NeighborhoodDAO neighborhoodDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getAll()
	 */
	@Override
	public List<Neighborhood> getAll() {
		return neighborhoodDAO.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#getById(int)
	 */
	@Override
	public Neighborhood getById(int neighborhoodId) {
		return neighborhoodDAO.getById(neighborhoodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public boolean add(Neighborhood neighborhood) {
		if (neighborhoodDAO.exists(neighborhood.getNeighborhood())) {
			return false;
		} else {
			neighborhoodDAO.add(neighborhood);
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
	public void update(Neighborhood neighborhood) {
		neighborhoodDAO.update(neighborhood);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chuckcaplan.libronuments.service.BaseService#delete(int)
	 */
	@Override
	public void delete(int neighborhoodId) {
		neighborhoodDAO.delete(neighborhoodId);
	}

}
