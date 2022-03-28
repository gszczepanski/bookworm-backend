package org.bookworm.library.suites;

import org.bookworm.library.controllers.AuthorControllerTest;
import org.bookworm.library.controllers.BookControllerTest;
import org.bookworm.library.controllers.PersonControllerTest;
import org.bookworm.library.controllers.PublisherControllerTest;
import org.bookworm.library.utils.IntegrationTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

import static org.junit.experimental.categories.Categories.IncludeCategory;
import static org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory({
        IntegrationTest.class
})
@SuiteClasses({
        AuthorControllerTest.class,
        BookControllerTest.class,
        PersonControllerTest.class,
        PublisherControllerTest.class
})
@Ignore
public class BookwormIntegrationTestSuite {
}
