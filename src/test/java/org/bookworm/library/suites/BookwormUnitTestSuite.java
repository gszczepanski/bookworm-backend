package org.bookworm.library.suites;

import org.bookworm.library.services.AuthorServiceTest;
import org.bookworm.library.utils.UnitTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

import static org.junit.experimental.categories.Categories.IncludeCategory;
import static org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory({
        UnitTest.class
})
@SuiteClasses(AuthorServiceTest.class)
@Ignore
public class BookwormUnitTestSuite {
}
