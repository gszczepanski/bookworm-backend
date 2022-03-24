package org.bookworm.library.services;

import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.dto.AuthorMapper;
import org.bookworm.library.dto.AuthorMapperImpl;
import org.bookworm.library.entities.Author;
import org.bookworm.library.repositories.AuthorRepository;
import org.bookworm.library.services.builders.AuthorDtoTestBuilder;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AuthorMapperImpl.class})
@Category(UnitTest.class)
public class AuthorServiceTest {

    @Autowired
    AuthorMapper authorMapper;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService;

    private Page<Author> authorPage;


    public AuthorServiceTest() {

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(authorService, "authorMapper", authorMapper);
        ReflectionTestUtils.setField(authorService, "authorRepository", authorRepository);

        authorPage = mock(Page.class);
    }

    @Test
    public void save_author_and_return_author() {
        AuthorDto authorDto = AuthorDtoTestBuilder.standardItem();

        when(authorRepository.saveAndFlush(any(Author.class))).thenReturn(authorMapper.toAuthor(authorDto));

        AuthorDto authorDtoCreated = authorService.save(authorDto);

        assertThat(authorDtoCreated).isEqualTo(authorDto);
    }

    @Test
    public void find_all_authors_and_return_authors_list() {
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl(Arrays.asList(mock(Author.class), mock(Author.class))));

        Page<AuthorDto> authorDtos = authorService.findAll(PageRequest.of(0, 10));

        assertThat(authorDtos).isInstanceOf(Page.class);
        assertThat(authorDtos.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void find_authors_by_book_id_and_return_authors_list() {
        when(authorRepository.findAllByBookId(any(UUID.class))).thenReturn(
                Arrays.asList(mock(Author.class), mock(Author.class)));

        List<AuthorDto> authorDtos = authorService.findAllByBookId(AuthorDtoTestBuilder.standardKey());

        assertThat(authorDtos.size()).isEqualTo(2);
        assertThat(authorDtos.stream().allMatch(a -> a instanceof AuthorDto)).isTrue();
    }

    @Test
    public void find_one_author_by_id_and_return_author() {
        when(authorRepository.findById(any(UUID.class))).thenReturn(Optional.of(mock(Author.class)));

        Optional<AuthorDto> authorDtoOptional = authorService.findById(AuthorDtoTestBuilder.standardKey());

        assertThat(authorDtoOptional.get()).isInstanceOf(AuthorDto.class);
    }

    @Test
    public void delete_one_author_by_id_and_return_ok() {
        doNothing().when(authorRepository).deleteById(any(UUID.class));

        authorService.deleteById(AuthorDtoTestBuilder.standardKey());

        verify(authorRepository).deleteById(AuthorDtoTestBuilder.standardKey());
    }
}
