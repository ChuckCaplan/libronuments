package com.chuckcaplan.libronuments.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.dao.MonumentDAO;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Tests MonumentDAO.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class MonumentDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private MonumentDAO monumentDAO;

	@Test
	public void testExists() {
		Assert.assertTrue(monumentDAO.exists("Flame at the Holocaust Monument", "21202"));
		Assert.assertFalse(monumentDAO.exists("Flame at the Holocaust Monument", "12345"));
		Assert.assertFalse(monumentDAO.exists("Testing", "21202"));
	}

	@Test
	public void testUpdate() {
		Monument m = monumentDAO.getById(1);
		m.setName("Testing");
		monumentDAO.update(m);
		// confirm the name was updated in the DB
		Monument m2 = monumentDAO.getById(1);
		Assert.assertTrue(m2.getName().equals("Testing"));
	}

	@Test
	public void testGetAll() {
		List<Monument> monuments = monumentDAO.getAll();
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
		Monument monument = monumentDAO.getById(1);
		Assert.assertEquals(monument.getName(), "James Cardinal Gibbons");
		Assert.assertEquals(monument.getZipCode(), "21201");
		Assert.assertEquals(monument.getNeighborhood().getId(), 1);
		Assert.assertEquals(monument.getNeighborhood().getNeighborhood(), "Downtown");
		Assert.assertEquals(monument.getLocation(), "408 CHARLES ST" + System.getProperty("line.separator")
				+ "Baltimore, MD" + System.getProperty("line.separator"));
		monument = monumentDAO.getById(-1);
		Assert.assertNull(monument);
	}

	@Test
	public void testAdd() {
		Monument monument = monumentDAO.getById(2);
		Neighborhood n = monument.getNeighborhood();
		monument = new Monument();
		monument.setLocation("Ellicott City");
		monument.setName("My Monument");
		monument.setNeighborhood(n);
		monument.setZipCode("21043");
		monumentDAO.add(monument);
		Monument m2 = monumentDAO.getById(monument.getId());
		Assert.assertEquals(m2.getName(), "My Monument");
		Assert.assertEquals(m2.getZipCode(), "21043");
		Assert.assertEquals(m2.getNeighborhood().getNeighborhood(), n.getNeighborhood());
		Assert.assertEquals(m2.getLocation(), "Ellicott City");
	}

	@Test
	public void testDelete() {
		monumentDAO.delete(1);
		Monument m = monumentDAO.getById(1);
		Assert.assertNull(m);
	}

}
