package org.bookworm.library.services;

import org.bookworm.library.dto.AuthorMapperImpl;
import org.bookworm.library.dto.BookForModificationDto;
import org.bookworm.library.dto.BookMapper;
import org.bookworm.library.entities.Book;
import org.bookworm.library.repositories.BookRepository;
import org.bookworm.library.services.builders.BookDtoEasyTestBuilder;
import org.bookworm.library.utils.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AuthorMapperImpl.class})
@Category(UnitTest.class)
public class BookServiceTest {

    @Autowired
    BookMapper bookMapper;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    public Page<Book> bookPage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(bookService, "bookMapper", bookMapper);
        ReflectionTestUtils.setField(bookService, "bookRepository", bookRepository);

        bookPage = mock(Page.class);
    }

    @Test
    public void save_book_and_return_book() {
        BookForModificationDto bookDto = make(a(BookDtoEasyTestBuilder.BookDtoStandardItem));

        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(bookMapper.toBook(bookDto));

        Optional<BookForModificationDto> authorDtoCreated = bookService.update(bookDto);

        assertThat(authorDtoCreated).isEqualTo(bookDto);
    }
}
