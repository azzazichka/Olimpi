package com.example.server.service;

import com.example.server.repository.subject.Subject;
import com.example.server.repository.subject.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public void createSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteSubjects(Long contest_id) {
        subjectRepository.deleteAllByContestId(contest_id);
    }
}
