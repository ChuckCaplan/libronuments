package com.chuckcaplan.libronuments.test.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Tests Monument.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class MonumentTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Test
	public void test() {
		Monument m = new Monument();

		// check initial state
		Assert.assertEquals(m.getId(), 0);
		Assert.assertNull(m.getLocation());
		Assert.assertNull(m.getName());
		Assert.assertNull(m.getZipCode());
		Assert.assertNull(m.getNeighborhood());

		// check values
		Neighborhood n = new Neighborhood();
		m.setId(12);
		m.setLocation("Ellicott City");
		m.setName("Testing");
		m.setNeighborhood(n);
		m.setZipCode("12345");

		Assert.assertEquals(m.getId(), 12);
		Assert.assertEquals(m.getLocation(), "Ellicott City");
		Assert.assertEquals(m.getName(), "Testing");
		Assert.assertEquals(m.getZipCode(), "12345");
		Assert.assertEquals(m.getNeighborhood(), n);
	}
}
