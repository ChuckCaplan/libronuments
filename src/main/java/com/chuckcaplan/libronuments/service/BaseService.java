package com.chuckcaplan.libronuments.service;

import java.util.List;

/**
 * Base interface that has all methods a service should support. Uses generics
 * so each entity can be processed.
 * 
 * @author Chuck Caplan
 *
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * @return a list of all the entities
	 */
	List<T> getAll();

	/**
	 * @param id
	 * @return a specific entity based on the primary key
	 */
	T getById(int id);

	/**
	 * @param t
	 * @return whether or not the entity was added
	 */
	boolean add(T t);

	/**
	 * Updates the entity in the DB
	 * 
	 * @param t
	 */
	void update(T t);

	/**
	 * Deletes an entity from the DB
	 * 
	 * @param id
	 */
	void delete(int id);
}
