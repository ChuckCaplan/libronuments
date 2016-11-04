package com.chuckcaplan.libronuments.test.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Tests Neighborhood.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class NeighborhoodTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Test
	public void test() {
		Neighborhood n = new Neighborhood();

		// check initial state
		Assert.assertEquals(n.getId(), 0);
		Assert.assertNull(n.getNeighborhood());

		// check values
		n.setId(12);
		n.setNeighborhood("Brookville");

		Assert.assertEquals(n.getId(), 12);
		Assert.assertEquals(n.getNeighborhood(), "Brookville");
	}
}
