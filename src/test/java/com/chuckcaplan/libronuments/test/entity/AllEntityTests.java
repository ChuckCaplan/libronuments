package com.chuckcaplan.libronuments.test.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Master class to run all entity tests
 * 
 * @author Chuck Caplan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ LibraryTest.class, MonumentTest.class, NeighborhoodTest.class })
public class AllEntityTests {

}
