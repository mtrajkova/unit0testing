package com.internship.controllers;

import com.google.gson.Gson;
import com.internship.model.Subject;
import com.internship.repository.SubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerIT {

    private static final String URL_SUBJECT_SAVE = "/demo/saveSubject";

    protected MockMvc mockMvc;

    private Gson gson;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();
    }

    @Test
    public void shouldSaveSubject() throws Exception {
        Subject subject = new Subject()
                .withName("Math")
                .withMentor("Marence");

        String jsonString = gson.toJson(subject);
        mockMvc.perform(post(URL_SUBJECT_SAVE).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(jsonString).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Subject> subjects = subjectRepository.findAll();
        Subject foundSubject = subjects.get(0);

        assertEquals(subject.getName(), foundSubject.getName());
        assertEquals(subject.getMentor(), foundSubject.getMentor());
    }
}
