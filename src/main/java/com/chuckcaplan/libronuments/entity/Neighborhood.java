package com.chuckcaplan.libronuments.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for a neighborhood
 * 
 * @author Chuck Caplan
 */
@Entity
@Table(name = "mstr_neighborhood")
public class Neighborhood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "neighborhood_id")
	private int id;

	@Column(name = "neighborhood_txt")
	private String neighborhood;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood
	 *            the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

}