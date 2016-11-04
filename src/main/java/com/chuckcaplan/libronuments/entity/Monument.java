package com.chuckcaplan.libronuments.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for a monument
 * 
 * @author Chuck Caplan
 */
@Entity
@Table(name = "monument")
public class Monument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "monument_id")
	private int id;

	@Column(name = "name_txt")
	private String name;

	@Column(name = "zipcode_txt")
	private String zipCode;

	@Column(name = "location_txt")
	private String location;

	@ManyToOne
	@JoinColumn(name = "neighborhood_id")
	private Neighborhood neighborhood;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the neighborhood
	 */
	public Neighborhood getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood
	 *            the neighborhood to set
	 */
	public void setNeighborhood(Neighborhood neighborhood) {
		this.neighborhood = neighborhood;
	}

}