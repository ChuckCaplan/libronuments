package com.chuckcaplan.libronuments.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuckcaplan.libronuments.entity.Library;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;
import com.chuckcaplan.libronuments.service.LibraryService;
import com.chuckcaplan.libronuments.service.MonumentService;
import com.chuckcaplan.libronuments.service.NeighborhoodService;

/**
 * @author Chuck Caplan
 * 
 *         Server-side controller class and RESTful web services. Allows the UI
 *         to call various services on the library, monument, and neighborhood
 *         service classes
 *
 */
@Controller
// the services will be under this URL mapping
@RequestMapping("/libronuments")
public class LibronumentsController {

	private final static Logger logger = LoggerFactory.getLogger(LibronumentsController.class);

	@Autowired
	private LibraryService libraryService;

	@Autowired
	private MonumentService monumentService;

	@Autowired
	private NeighborhoodService neighborhoodService;

	/**
	 * @return a URL to home.jsp
	 */
	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	/**
	 * @param id
	 * @return a library by the libraryId
	 */
	@RequestMapping(value = "/library/{id}", method = RequestMethod.GET)
	public ResponseEntity<Library> getLibraryById(@PathVariable("id") Integer id) {
		Library library = libraryService.getById(id);
		return new ResponseEntity<Library>(library, HttpStatus.OK);
	}

	/**
	 * @return all the libraries in a list
	 */
	@RequestMapping(value = "/library", method = RequestMethod.GET)
	public ResponseEntity<List<Library>> getAllLibraries() {
		List<Library> list = libraryService.getAll();
		return new ResponseEntity<List<Library>>(list, HttpStatus.OK);
	}

	/**
	 * Adds a new library
	 * 
	 * @param library
	 * @param builder
	 * @return a status saying whether or not the library was created
	 */
	@RequestMapping(value = "/library", method = RequestMethod.POST)
	public ResponseEntity<Library> addLibrary(@RequestBody Library library, UriComponentsBuilder builder) {
		logger.debug("adding library");
		boolean flag = libraryService.add(library);
		logger.debug("done adding library");
		if (flag == false) {
			return new ResponseEntity<Library>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Library>(library, HttpStatus.CREATED);
	}

	/**
	 * Updates a library
	 * 
	 * @param library
	 * @return a status saying whether or not the library was updated
	 */
	@RequestMapping(value = "/library/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Library> updateLibrary(@RequestBody Library library) {
		logger.debug("updating library");
		libraryService.update(library);
		logger.debug("done updating library");
		return new ResponseEntity<Library>(library, HttpStatus.OK);
	}

	/**
	 * Deletes a library
	 * 
	 * @param id
	 * @return a status saying whether or not the library was deleted
	 */
	@RequestMapping(value = "/library/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteLibrary(@PathVariable("id") Integer id) {
		logger.debug("about to delete library with id " + id);
		libraryService.delete(id);
		logger.debug("deleted library with id " + id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @param id
	 * @return a monument by monumentId
	 */
	@RequestMapping(value = "/monument/{id}", method = RequestMethod.GET)
	public ResponseEntity<Monument> getMonumentById(@PathVariable("id") Integer id) {
		Monument monument = monumentService.getById(id);
		return new ResponseEntity<Monument>(monument, HttpStatus.OK);
	}

	/**
	 * @return a list of all monuments
	 */
	@RequestMapping(value = "/monument", method = RequestMethod.GET)
	public ResponseEntity<List<Monument>> getAllMonuments() {
		List<Monument> list = monumentService.getAll();
		return new ResponseEntity<List<Monument>>(list, HttpStatus.OK);
	}

	/**
	 * Adds a new monument
	 * 
	 * @param monument
	 * @param builder
	 * @return a status saying whether or not the monument was created
	 */
	@RequestMapping(value = "/monument", method = RequestMethod.POST)
	public ResponseEntity<Monument> addMonument(@RequestBody Monument monument, UriComponentsBuilder builder) {
		logger.debug("adding monument");
		boolean flag = monumentService.add(monument);
		logger.debug("done adding monument");
		if (flag == false) {
			return new ResponseEntity<Monument>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Monument>(monument, HttpStatus.CREATED);
	}

	/**
	 * Updates a monument
	 * 
	 * @param monument
	 * @return a status saying whether or not the monument was updated
	 */
	@RequestMapping(value = "/monument/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Monument> updateMonument(@RequestBody Monument monument) {
		monumentService.update(monument);
		return new ResponseEntity<Monument>(monument, HttpStatus.OK);
	}

	/**
	 * Deletes a monument with the specified monumentId
	 * 
	 * @param id
	 * @return a status saying whether or not the monument was deleted
	 */
	@RequestMapping(value = "/monument/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMonument(@PathVariable("id") Integer id) {
		logger.debug("about to delete monument with id " + id);
		monumentService.delete(id);
		logger.debug("deleted monument with id " + id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @param id
	 * @return a neighborhood based on the specified neighborhoodId
	 */
	@RequestMapping(value = "/neighborhood/{id}", method = RequestMethod.GET)
	public ResponseEntity<Neighborhood> getNeighborhoodById(@PathVariable("id") Integer id) {
		Neighborhood neighborhood = neighborhoodService.getById(id);
		return new ResponseEntity<Neighborhood>(neighborhood, HttpStatus.OK);
	}

	/**
	 * @return a list of all neighborhoods
	 */
	@RequestMapping(value = "/neighborhood", method = RequestMethod.GET)
	public ResponseEntity<List<Neighborhood>> getAllNeighborhoods() {
		List<Neighborhood> list = neighborhoodService.getAll();
		return new ResponseEntity<List<Neighborhood>>(list, HttpStatus.OK);
	}

	/**
	 * @param key
	 * @return the api key associated with the property key passed in, e.g.
	 *         "google.maps.api.key"
	 */
	public static String getApiKey(String key) {
		// NOTE - I am purposely loading the properties on demand each time this
		// is called instead of loading them once and caching them. This is
		// because I don't want the user to have to bounce the server each time
		// a property is changed. This should be changed to be cached if this
		// ever goes into a production environment.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// load properties from classpath
		InputStream input = classLoader.getResourceAsStream("api.properties");
		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			logger.error("Error loading api properties", e);
		}
		return properties.getProperty(key);
	}

}