package com.chuckcaplan.libronuments.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Chuck Caplan
 * 
 *         Generic abstract class that can perform CRUD database operations on
 *         an entity.
 *
 * @param <E>
 */
@Transactional
@Repository
public abstract class BaseEntityDAO<E> {
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	private final E entity;

	/**
	 * Call this constructor so the class knows which entity is being used.
	 * 
	 * @param entity
	 */
	public BaseEntityDAO(E entity) {
		this.entity = entity;
	}

	/**
	 * @return the entity in question
	 */
	public E getEntity() {
		return this.entity;
	}

	/**
	 * @return all items in the DB for that entity in ascending order by the
	 *         primary key
	 */
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return (List<E>) hibernateTemplate
				.findByCriteria(DetachedCriteria.forClass(entity.getClass()).addOrder(Order.asc("id")));
	}

	/**
	 * @param id
	 * @return an item from the db based on the primary key
	 */
	@SuppressWarnings("unchecked")
	public E getById(int id) {
		return (E) hibernateTemplate.get(entity.getClass(), id);
	}

	/**
	 * Adds an entity to the DB
	 * 
	 * @param entity
	 * @return whether or not the entity was added
	 */
	public void add(E entity) {
		hibernateTemplate.save(entity);
	}

	/**
	 * Deletes an entity from the DB
	 * 
	 * @param id
	 */
	public void delete(int id) {
		hibernateTemplate.delete(getById(id));
	}

}
