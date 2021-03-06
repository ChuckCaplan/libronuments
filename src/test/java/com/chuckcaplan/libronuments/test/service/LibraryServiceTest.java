package com.chuckcaplan.libronuments.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.entity.Library;
import com.chuckcaplan.libronuments.entity.Neighborhood;
import com.chuckcaplan.libronuments.service.LibraryService;

/**
 * Tests LibraryService.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class LibraryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private LibraryService libraryService;

	@Test
	public void testUpdate() {
		Library l = libraryService.getById(1);
		l.setName("Testing");
		libraryService.update(l);
		// confirm the name was updated in the DB
		Library l2 = libraryService.getById(1);
		Assert.assertTrue(l2.getName().equals("Testing"));
	}

	@Test
	public void testGetAll() {
		List<Library> libraries = libraryService.getAll();
		Assert.assertEquals(libraries.size(), 22);
		boolean foundId1 = false;
		for (Library library : libraries) {
			Assert.assertNotEquals(library.getId(), 0);
			Assert.assertNotNull(library.getLocation());
			Assert.assertNotNull(library.getName());
			Assert.assertNotNull(library.getZipCode());
			Assert.assertNotNull(library.getNeighborhood());
			Assert.assertNotNull(library.getNeighborhood().getId());
			Assert.assertNotNull(library.getNeighborhood().getNeighborhood());
			// make sure there is only 1 with id 1
			if (library.getId() == 1) {
				if (foundId1) {
					Assert.fail();
				}
				foundId1 = true;
				Assert.assertEquals(library.getName(), "Central");
				Assert.assertEquals(library.getZipCode(), "21201");
				Assert.assertEquals(library.getNeighborhood().getId(), 1);
				Assert.assertEquals(library.getNeighborhood().getNeighborhood(), "Downtown");
				Assert.assertEquals(library.getLocation(), "400 Cathedral St" + System.getProperty("line.separator")
						+ "Baltimore, MD" + System.getProperty("line.separator"));
			}
		}
	}

	@Test
	public void testGetById() {
		Library library = libraryService.getById(1);
		Assert.assertEquals(library.getName(), "Central");
		Assert.assertEquals(library.getZipCode(), "21201");
		Assert.assertEquals(library.getNeighborhood().getId(), 1);
		Assert.assertEquals(library.getNeighborhood().getNeighborhood(), "Downtown");
		Assert.assertEquals(library.getLocation(), "400 Cathedral St" + System.getProperty("line.separator")
				+ "Baltimore, MD" + System.getProperty("line.separator"));
		library = libraryService.getById(-1);
		Assert.assertNull(library);
	}

	@Test
	public void testAdd() {
		Library library = libraryService.getById(2);
		Neighborhood n = library.getNeighborhood();
		library = new Library();
		library.setLocation("Ellicott City");
		library.setName("My Library");
		library.setNeighborhood(n);
		library.setZipCode("21043");
		Assert.assertTrue(libraryService.add(library));
		Library l2 = libraryService.getById(library.getId());
		Assert.assertEquals(l2.getName(), "My Library");
		Assert.assertEquals(l2.getZipCode(), "21043");
		Assert.assertEquals(l2.getNeighborhood().getNeighborhood(), n.getNeighborhood());
		Assert.assertEquals(l2.getLocation(), "Ellicott City");
		Assert.assertFalse(libraryService.add(library));
		Assert.assertFalse(libraryService.add(l2));
	}

	@Test
	public void testDelete() {
		libraryService.delete(1);
		Library l = libraryService.getById(1);
		Assert.assertNull(l);
	}
}
