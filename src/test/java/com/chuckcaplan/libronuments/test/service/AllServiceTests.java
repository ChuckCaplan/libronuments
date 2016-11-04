package com.chuckcaplan.libronuments.test.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Master class to run all service tests
 * 
 * @author Chuck Caplan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ LibraryServiceTest.class, MonumentServiceTest.class, NeighborhoodServiceTest.class })
public class AllServiceTests {

}
