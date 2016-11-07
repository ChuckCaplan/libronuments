package com.chuckcaplan.libronuments.test.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chuckcaplan.libronuments.config.AppConfig;
import com.chuckcaplan.libronuments.controller.LibronumentsController;
import com.chuckcaplan.libronuments.entity.Library;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * Tests LibronumentsController.java
 * 
 * @author Chuck Caplan
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class LibronumentsControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private LibronumentsController controller;

	@Test
	public void testHome() {
		Assert.assertEquals(controller.home(), "home");
	}

	@Test
	public void testGetLibraryById() {
		// test a good result
		ResponseEntity<Library> responseEntity = controller.getLibraryById(1);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Library library = responseEntity.getBody();
		Assert.assertEquals(library.getName(), "Central");
		Assert.assertEquals(library.getZipCode(), "21201");
		Assert.assertEquals(library.getNeighborhood().getId(), 1);
		Assert.assertEquals(library.getNeighborhood().getNeighborhood(), "Downtown");
		Assert.assertEquals(library.getLocation(), "400 Cathedral St" + System.getProperty("line.separator")
				+ "Baltimore, MD" + System.getProperty("line.separator"));

		// Test a null result
		responseEntity = controller.getLibraryById(-1);
		library = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assert.assertNull(library);
	}

	@Test
	public void testGetAllLibraries() {
		ResponseEntity<List<Library>> responseEntity = controller.getAllLibraries();
		List<Library> libraries = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
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
	public void testAddLibrary() {
		Library library = controller.getLibraryById(2).getBody();
		Neighborhood n = library.getNeighborhood();
		library = new Library();
		library.setLocation("Ellicott City");
		library.setName("My Library");
		library.setNeighborhood(n);
		library.setZipCode("21043");
		ResponseEntity<Library> responseEntity = controller.addLibrary(library, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
		Library addedLibrary = responseEntity.getBody();
		Assert.assertNotNull(addedLibrary);
		// confirm it was added
		Library l2 = controller.getLibraryById(addedLibrary.getId()).getBody();
		Assert.assertEquals(l2.getName(), "My Library");
		Assert.assertEquals(l2.getZipCode(), "21043");
		Assert.assertEquals(l2.getNeighborhood().getNeighborhood(), n.getNeighborhood());
		Assert.assertEquals(l2.getLocation(), "Ellicott City");

		// confirm existing libraries cannot be added
		responseEntity = controller.addLibrary(library, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
		Assert.assertNull(responseEntity.getBody());

		responseEntity = controller.addLibrary(l2, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
		Assert.assertNull(responseEntity.getBody());
	}

	@Test
	public void testUpdateLibrary() {
		Library library = controller.getLibraryById(1).getBody();
		library.setName("Testing");
		ResponseEntity<Library> responseEntity = controller.updateLibrary(library);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Library returnedLibrary = responseEntity.getBody();
		Assert.assertEquals(library.getId(), returnedLibrary.getId());
		Assert.assertEquals(library.getLocation(), returnedLibrary.getLocation());
		Assert.assertEquals(library.getName(), returnedLibrary.getName());
		Assert.assertEquals(library.getZipCode(), returnedLibrary.getZipCode());
		Assert.assertEquals(library.getNeighborhood().getNeighborhood(),
				returnedLibrary.getNeighborhood().getNeighborhood());

		// confirm the name was updated in the DB
		Library l2 = controller.getLibraryById(1).getBody();
		Assert.assertTrue(l2.getName().equals("Testing"));
	}

	@Test
	public void testDeleteLibrary() {
		ResponseEntity<Void> responseEntity = controller.deleteLibrary(1);
		Void v = responseEntity.getBody();
		Assert.assertNull(v);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
		Library l = controller.getLibraryById(1).getBody();
		Assert.assertNull(l);

		// check if deleting something that doesn't exist
		try {
			responseEntity = controller.deleteLibrary(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// do nothing, this is expected if there is nothing to delete
		}
	}

	@Test
	public void testGetMonumentById() {
		// test a good result
		ResponseEntity<Monument> responseEntity = controller.getMonumentById(1);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Monument monument = responseEntity.getBody();
		Assert.assertEquals(monument.getName(), "James Cardinal Gibbons");
		Assert.assertEquals(monument.getZipCode(), "21201");
		Assert.assertEquals(monument.getNeighborhood().getId(), 1);
		Assert.assertEquals(monument.getNeighborhood().getNeighborhood(), "Downtown");
		Assert.assertEquals(monument.getLocation(), "408 CHARLES ST" + System.getProperty("line.separator")
				+ "Baltimore, MD" + System.getProperty("line.separator"));

		// Test a null result
		responseEntity = controller.getMonumentById(-1);
		monument = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assert.assertNull(monument);
	}

	@Test
	public void testGetAllMonuments() {
		ResponseEntity<List<Monument>> responseEntity = controller.getAllMonuments();
		List<Monument> monuments = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
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
	public void testAddMonument() {
		Monument monument = controller.getMonumentById(2).getBody();
		Neighborhood n = monument.getNeighborhood();
		monument = new Monument();
		monument.setLocation("Ellicott City");
		monument.setName("My Monument");
		monument.setNeighborhood(n);
		monument.setZipCode("21043");
		ResponseEntity<Monument> responseEntity = controller.addMonument(monument, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
		Monument addedMonument = responseEntity.getBody();
		Assert.assertNotNull(addedMonument);
		// confirm it was added
		Monument m2 = controller.getMonumentById(addedMonument.getId()).getBody();
		Assert.assertEquals(m2.getName(), "My Monument");
		Assert.assertEquals(m2.getZipCode(), "21043");
		Assert.assertEquals(m2.getNeighborhood().getNeighborhood(), n.getNeighborhood());
		Assert.assertEquals(m2.getLocation(), "Ellicott City");

		// confirm existing libraries cannot be added
		responseEntity = controller.addMonument(monument, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
		Assert.assertNull(responseEntity.getBody());

		responseEntity = controller.addMonument(m2, null);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
		Assert.assertNull(responseEntity.getBody());
	}

	@Test
	public void testUpdateMonument() {
		Monument monument = controller.getMonumentById(1).getBody();
		monument.setName("Testing");
		ResponseEntity<Monument> responseEntity = controller.updateMonument(monument);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Monument returnedMonument = responseEntity.getBody();
		Assert.assertEquals(monument.getId(), returnedMonument.getId());
		Assert.assertEquals(monument.getLocation(), returnedMonument.getLocation());
		Assert.assertEquals(monument.getName(), returnedMonument.getName());
		Assert.assertEquals(monument.getZipCode(), returnedMonument.getZipCode());
		Assert.assertEquals(monument.getNeighborhood().getNeighborhood(),
				returnedMonument.getNeighborhood().getNeighborhood());

		// confirm the name was updated in the DB
		Monument m2 = controller.getMonumentById(1).getBody();
		Assert.assertTrue(m2.getName().equals("Testing"));
	}

	@Test
	public void testDeleteMonument() {
		ResponseEntity<Void> responseEntity = controller.deleteMonument(1);
		Void v = responseEntity.getBody();
		Assert.assertNull(v);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
		Monument m = controller.getMonumentById(1).getBody();
		Assert.assertNull(m);

		// check if deleting something that doesn't exist
		try {
			responseEntity = controller.deleteMonument(-1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// do nothing, this is expected if there is nothing to delete
		}
	}

	@Test
	public void testGetNeighborhoodById() {
		// test a good result
		ResponseEntity<Neighborhood> responseEntity = controller.getNeighborhoodById(1);
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Neighborhood neighborhood = responseEntity.getBody();
		Assert.assertEquals(neighborhood.getNeighborhood(), "Downtown");
		Assert.assertEquals(neighborhood.getId(), 1);

		// Test a null result
		responseEntity = controller.getNeighborhoodById(-1);
		neighborhood = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assert.assertNull(neighborhood);
	}

	@Test
	public void testGetAllNeighborhoods() {
		ResponseEntity<List<Neighborhood>> responseEntity = controller.getAllNeighborhoods();
		List<Neighborhood> neighborhoods = responseEntity.getBody();
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
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
}
