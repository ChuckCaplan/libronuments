package com.chuckcaplan.libronuments.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;
import com.chuckcaplan.libronuments.service.MonumentService;

/**
 * Tests MonumentService.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class MonumentServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private MonumentService monumentService;

	@Test
	public void testUpdate() {
		Monument m = monumentService.getById(1);
		m.setName("Testing");
		monumentService.update(m);
		// confirm the name was updated in the DB
		Monument m2 = monumentService.getById(1);
		Assert.assertTrue(m2.getName().equals("Testing"));
	}

	@Test
	public void testGetAll() {
		List<Monument> monuments = monumentService.getAll();
		Assert.assertEquals(monuments.size(), 84);
		boolean foundId1 = false;
		for (Monument monument : monuments) {
			Assert.assertNotEquals(monument.getId(), 0);
			Assert.assertNotNull(monument.getLocation());
			Assert.assertNotNull(monument.getName());
			Assert.assertNotNull(monument.getZipCode());
			Assert.assertNotNull(monument.getNeighborhood());
			Assert.assertNotNull(monument.getNeighborhood().getId());
			Assert.assertNotNull(monument.getNeighborhood().getNeighborhood());
			// make sure there is only 1 with id 1
			if (monument.getId() == 1) {
				if (foundId1) {
					Assert.fail();
				}
				foundId1 = true;
				Assert.assertEquals(monument.getName(), "James Cardinal Gibbons");
				Assert.assertEquals(monument.getZipCode(), "21201");
				Assert.assertEquals(monument.getNeighborhood().getId(), 1);
				Assert.assertEquals(monument.getNeighborhood().getNeighborhood(), "Downtown");
				Assert.assertEquals(monument.getLocation(), "408 CHARLES ST" + System.getProperty("line.separator")
						+ "Baltimore, MD" + System.getProperty("line.separator"));
			}
		}
	}

	@Test
	public void testGetById() {
		Monument monument = monumentService.getById(1);
		Assert.assertEquals(monument.getName(), "James Cardinal Gibbons");
		Assert.assertEquals(monument.getZipCode(), "21201");
		Assert.assertEquals(monument.getNeighborhood().getId(), 1);
		Assert.assertEquals(monument.getNeighborhood().getNeighborhood(), "Downtown");
		Assert.assertEquals(monument.getLocation(), "408 CHARLES ST" + System.getProperty("line.separator")
				+ "Baltimore, MD" + System.getProperty("line.separator"));
		monument = monumentService.getById(-1);
		Assert.assertNull(monument);
	}

	@Test
	public void testAdd() {
		Monument monument = monumentService.getById(2);
		Neighborhood n = monument.getNeighborhood();
		monument = new Monument();
		monument.setLocation("Ellicott City");
		monument.setName("My Monument");
		monument.setNeighborhood(n);
		monument.setZipCode("21043");
		Assert.assertTrue(monumentService.add(monument));
		Monument m2 = monumentService.getById(monument.getId());
		Assert.assertEquals(m2.getName(), "My Monument");
		Assert.assertEquals(m2.getZipCode(), "21043");
		Assert.assertEquals(m2.getNeighborhood().getNeighborhood(), n.getNeighborhood());
		Assert.assertEquals(m2.getLocation(), "Ellicott City");
		Assert.assertFalse(monumentService.add(monument));
		Assert.assertFalse(monumentService.add(m2));
	}

	@Test
	public void testDelete() {
		monumentService.delete(1);
		Monument m = monumentService.getById(1);
		Assert.assertNull(m);
	}

}
