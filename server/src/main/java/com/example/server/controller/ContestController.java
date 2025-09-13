package com.example.server.controller;

import com.example.server.repository.contest.Contest;
import com.example.server.service.ContestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/contests")
public class ContestController {
    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }

    @PostMapping
    public void createContest(@RequestBody Contest contest) {
        contestService.createContest(contest);
    }

    @DeleteMapping
    public void deleteContest(@RequestParam Long contest_id) {
        contestService.deleteContest(contest_id);
    }

    @PutMapping
    public void updateContest(@RequestBody Contest changes) {
        contestService.updateContest(changes);
    }
}
