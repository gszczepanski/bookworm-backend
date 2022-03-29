package org.bookworm.library.suites;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        BookwormUnitTestSuite.class,
        BookwormIntegrationTestSuite.class
})
@Ignore
public class BookwormFullTestSuite {
}
