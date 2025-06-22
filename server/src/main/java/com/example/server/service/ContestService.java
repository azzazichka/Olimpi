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

    public void createContests(List<Contest> contests) {
        contestRepository.saveAll(contests);
    }



    public void deleteContests(List<Long> ids) {
        contestRepository.deleteAllById(ids);
    }

    public void updateContests(List<Contest> changes) {
        List<Contest> contests = new ArrayList<>();
        for (Contest change : changes) {
            if (change.getId() == null) {
                throw new IllegalArgumentException("Не указан id контеста при обновлении");
            }
            Contest contest = getContest(change.getId());

            if (change.getTitle() != null) contest.setTitle(change.getTitle());
            if (change.getLvl() != null) contest.setLvl(change.getLvl());
            if (change.getDate_start() != null) contest.setDate_start(change.getDate_start());
            if (change.getDate_end() != null) contest.setDate_end(change.getDate_end());
            if (change.getLink() != null) contest.setLink(change.getLink());
            if (change.getLow_grade() != null) contest.setLow_grade(change.getLow_grade());
            if (change.getUp_grade() != null) contest.setUp_grade(change.getUp_grade());
            if (change.getSubjects() != null) contest.setSubjects(change.getSubjects());

            contests.add(contest);
        }
        contestRepository.saveAll(contests);
    }

    public Contest getContest(Long id) {
        Optional<Contest> optionalContest = contestRepository.findById(id);
        if (optionalContest.isEmpty()) {
            throw new IllegalStateException("Контеста с id: " + id + " не существует");
        }
        return optionalContest.get();
    }
}
