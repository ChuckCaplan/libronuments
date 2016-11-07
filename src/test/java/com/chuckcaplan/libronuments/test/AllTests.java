package com.chuckcaplan.libronuments.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.chuckcaplan.libronuments.test.controller.AllControllerTests;
import com.chuckcaplan.libronuments.test.dao.AllDAOTests;
import com.chuckcaplan.libronuments.test.entity.AllEntityTests;
import com.chuckcaplan.libronuments.test.service.AllServiceTests;

/**
 * Master class to run all JUnit test suites
 * 
 * @author Chuck Caplan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AllDAOTests.class, AllEntityTests.class, AllServiceTests.class, AllControllerTests.class })
public class AllTests {

}
