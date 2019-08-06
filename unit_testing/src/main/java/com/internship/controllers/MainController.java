package com.internship.controllers;

import com.internship.controllers.exceptions.IncorrectSubjectEntryException;
import com.internship.controllers.exceptions.NoMentorNameException;
import com.internship.controllers.exceptions.SubjectNotFoundException;
import com.internship.model.Subject;
import com.internship.services.SubjectService;
import com.internship.services.impl.SubjectServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hnastevska on 7/18/2017.
 */
@RestController
@RequestMapping("/demo")
public class MainController {

    public static final String SUBJECT_MUST_HAVE_NAME = "Subject Must Have Name";
    public static final String SUBJECT_NOT_FOUND = "Subject Not Found";
    public static final String NO_SUCH_SUBJECT = "No Such Subject";
    public static final String NO_MENTOR_NAME_SPECIFIED = "No mentor name specified";
    private SubjectService subjectService;

    @Autowired
    public MainController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @RequestMapping(value = "/saveSubject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveSubject(@RequestBody Subject subject) throws IncorrectSubjectEntryException {
        if (subject.getName().equals("") || subject.getName() == null) {
            throw new IncorrectSubjectEntryException(SUBJECT_MUST_HAVE_NAME);
        }

        subjectService.save(subject);
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Subject> getAllSubject() {
        return subjectService.listAll();
    }

    @RequestMapping(value = "/subject/{name}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getMentorForSpecificSubject(@PathVariable String name) throws SubjectNotFoundException {
        if (name.isEmpty()) {
            throw new SubjectNotFoundException(SUBJECT_NOT_FOUND);
        }

        List<Subject> subjects = subjectService.listAll();

        Subject subject = subjects.stream()
                .filter(x -> name.equals(x.getName()))
                .findAny()
                .orElse(null);

        if (subject == null) {
            throw new SubjectNotFoundException(NO_SUCH_SUBJECT);
        }

        return subject.getMentor();
    }

    @RequestMapping(value = "/subjectsBy/{mentor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Subject> getAllSubjectsByMentor(@PathVariable String mentor) throws NoMentorNameException {

        if (mentor == null) {
            throw new NoMentorNameException(NO_MENTOR_NAME_SPECIFIED);
        }
        String mentorNoSpaces = mentor.replace(" ", "");

        List<Subject> subjects = subjectService.listAll();
        List<Subject> subjectsByMentor = subjects.stream()
                .filter(x -> mentorNoSpaces.equalsIgnoreCase(x.getMentor().replace(" ", "")))
                .collect(Collectors.toList());

        if (subjectsByMentor == null) {
            return new ArrayList<>();
        }

        return subjectsByMentor;
    }

}
