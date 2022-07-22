package org.bookworm.library.services;

import org.bookworm.library.dto.PersonMapper;
import org.bookworm.library.dto.PersonMapperImpl;
import org.bookworm.library.entities.Person;
import org.bookworm.library.repositories.PersonRepository;
import org.bookworm.library.utils.UnitTest;
import org.junit.Before;
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

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PersonMapperImpl.class})
@Category(UnitTest.class)
public class PersonServiceTest {

    @Autowired
    PersonMapper personMapper;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    public Page<Person> personPage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(personService, "personMapper", personMapper);
        ReflectionTestUtils.setField(personService, "personRepository", personRepository);

        personPage = mock(Page.class);
    }
}
