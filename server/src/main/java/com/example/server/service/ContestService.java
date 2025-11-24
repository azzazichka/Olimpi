package com.example.server.service;

import com.example.server.repository.contest.Contest;
import com.example.server.repository.contest.ContestRepository;
import com.example.server.repository.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContestService {
    private final ContestRepository contestRepository;
    private final SubjectService subjectService;

    public ContestService(ContestRepository contestRepository, SubjectService subjectService) {
        this.contestRepository = contestRepository;
        this.subjectService = subjectService;
    }


    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    public void createContest(Contest contest) {
        contestRepository.save(contest);
        for (String subject : contest.getSubjects()) {
            subjectService.createSubject(new Subject(null, subject, contest.getId()));
        }
    }


    public void deleteContest(Long id) {
        getContest(id);
        subjectService.deleteSubjects(id);
        contestRepository.deleteById(id);
    }

    public void updateContest(Contest changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id контеста при обновлении");
        }
        Contest contest = getContest(changes.getId());

        if (changes.getTitle() != null) contest.setTitle(changes.getTitle());
        if (changes.getLvl() != null) contest.setLvl(changes.getLvl());
        if (changes.getDate_start() != null) contest.setDate_start(changes.getDate_start());
        if (changes.getDate_end() != null) contest.setDate_end(changes.getDate_end());
        if (changes.getLink() != null) contest.setLink(changes.getLink());
        if (changes.getLow_grade() != null) contest.setLow_grade(changes.getLow_grade());
        if (changes.getUp_grade() != null) contest.setUp_grade(changes.getUp_grade());

        contestRepository.save(contest);
    }

    public Contest getContest(Long id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isEmpty()) {
            throw new IllegalStateException("Контеста с id: " + id + " не существует");
        }
        return optionalContest.get();
    }

    public List<Contest> getContestBySubjects(List<String> subjects_names) {
        List<Subject> subjects = subjectService.getSubjects(subjects_names);
        List<Contest> contests = new ArrayList<>();
        HashMap<Long, Contest> contest = new HashMap<>();

        for (Subject subject : subjects) {
            Contest tmp_contest = getContest(subject.getContest_id());
            if (!contest.containsKey(tmp_contest.getId())) {
                tmp_contest.setSubjects(new ArrayList<>());
                contest.put(tmp_contest.getId(), tmp_contest);
            }
            tmp_contest = contest.get(tmp_contest.getId());
            tmp_contest.addSubject(subject.getSubject());
            contest.put(tmp_contest.getId(), tmp_contest);
        }
        for (Map.Entry<Long, Contest> entry : contest.entrySet()) {
            contests.add(entry.getValue());
        }
        return contests;
    }

    public List<Contest> getContestsByIds(List<Long> contestIds) {
        return contestRepository.findAllById(contestIds);

    }
}
