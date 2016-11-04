package com.chuckcaplan.libronuments.test.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Master class to run all DAO tests
 * 
 * @author Chuck Caplan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ LibraryDAOTest.class, MonumentDAOTest.class, NeighborhoodDAOTest.class })
public class AllDAOTests {

}
