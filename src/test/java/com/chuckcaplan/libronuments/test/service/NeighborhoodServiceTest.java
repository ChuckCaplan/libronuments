package com.chuckcaplan.libronuments.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Neighborhood;
import com.chuckcaplan.libronuments.service.NeighborhoodService;

/**
 * Tests NeighborhoodService.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class NeighborhoodServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private NeighborhoodService neighborhoodService;

	@Test
	public void testUpdate() {
		Neighborhood n = neighborhoodService.getById(1);
		n.setNeighborhood("Testing");
		neighborhoodService.update(n);
		// confirm the name was updated in the DB
		Neighborhood n2 = neighborhoodService.getById(1);
		Assert.assertTrue(n2.getNeighborhood().equals("Testing"));
	}

	@Test
	public void testGetAll() {
		List<Neighborhood> neighborhoods = neighborhoodService.getAll();
		Assert.assertEquals(neighborhoods.size(), 48);
		boolean foundId1 = false;
		for (Neighborhood neighborhood : neighborhoods) {
			Assert.assertNotEquals(neighborhood.getId(), 0);
			Assert.assertNotNull(neighborhood.getNeighborhood());
			// make sure there is only 1 with id 1
			if (neighborhood.getId() == 1) {
				if (foundId1) {
					Assert.fail();
				}
				foundId1 = true;
				Assert.assertEquals(neighborhood.getNeighborhood(), "Downtown");
			}
		}
	}

	@Test
	public void testGetById() {
		Neighborhood neighborhood = neighborhoodService.getById(1);
		Assert.assertEquals(neighborhood.getNeighborhood(), "Downtown");
		neighborhood = neighborhoodService.getById(-1);
		Assert.assertNull(neighborhood);
	}

	@Test
	public void testAdd() {
		Neighborhood neighborhood = new Neighborhood();
		neighborhood.setNeighborhood("Ellicott City");
		Assert.assertTrue(neighborhoodService.add(neighborhood));
		Neighborhood n2 = neighborhoodService.getById(neighborhood.getId());
		Assert.assertEquals(n2.getNeighborhood(), "Ellicott City");
		Assert.assertFalse(neighborhoodService.add(neighborhood));
		Assert.assertFalse(neighborhoodService.add(n2));
	}

	@Test
	public void testDelete() {
		neighborhoodService.delete(1);
		Neighborhood n = neighborhoodService.getById(1);
		Assert.assertNull(n);
	}

}
