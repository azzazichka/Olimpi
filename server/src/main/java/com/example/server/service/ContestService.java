package com.example.server.service;

import com.example.server.repository.contest.Contest;
import com.example.server.repository.contest.ContestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContestService {
    private final ContestRepository contestRepository;

    public ContestService(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }


    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    public void createContest(Contest contest) {
        contestRepository.save(contest);
    }



    public void deleteContest(Long id) {
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
        if (changes.getSubjects() != null) contest.setSubjects(changes.getSubjects());

        contestRepository.save(contest);
    }

    public Contest getContest(Long id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isEmpty()) {
            throw new IllegalStateException("Контеста с id: " + id + " не существует");
        }
        return optionalContest.get();
    }
}
