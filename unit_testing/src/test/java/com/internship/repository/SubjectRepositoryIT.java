package com.internship.repository;

import com.internship.model.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryIT {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SubjectRepository subjectRepository;

    @Before
    public void setUp() {
        Subject subject = new Subject().withName("mare").withMentor("1");
        Subject subject1 = new Subject().withName("shangi").withMentor("2");
        Subject subject2 = new Subject().withName("mare").withMentor("3");

        subjectRepository.saveAll(Arrays.asList(subject, subject1, subject2));
    }

    @Test
    public void shouldReturnSubjectsByName() {
        List<Subject> subjects = subjectRepository.findByName("mare");
        assertEquals(2, subjects.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        List<Subject> subjects = subjectRepository.findByName("asdf");
        assertEquals(0, subjects.size());
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
//        subjectRepository.deleteAll();
    }
}
