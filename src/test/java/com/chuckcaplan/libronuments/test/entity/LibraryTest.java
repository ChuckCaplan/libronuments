package com.chuckcaplan.libronuments.test.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Library;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Tests Library.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class LibraryTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Test
	public void testLibrary() {
		Library l = new Library();

		// check initial state
		Assert.assertEquals(l.getId(), 0);
		Assert.assertNull(l.getLocation());
		Assert.assertNull(l.getName());
		Assert.assertNull(l.getZipCode());
		Assert.assertNull(l.getNeighborhood());

		// check values
		Neighborhood n = new Neighborhood();
		l.setId(12);
		l.setLocation("Ellicott City");
		l.setName("Testing");
		l.setNeighborhood(n);
		l.setZipCode("12345");

		Assert.assertEquals(l.getId(), 12);
		Assert.assertEquals(l.getLocation(), "Ellicott City");
		Assert.assertEquals(l.getName(), "Testing");
		Assert.assertEquals(l.getZipCode(), "12345");
		Assert.assertEquals(l.getNeighborhood(), n);
	}
}
