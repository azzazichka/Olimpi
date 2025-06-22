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

    @PostMapping()
    public void createContests(@RequestBody List<Contest> contests) {
        contestService.createContests(contests);
    }

    @DeleteMapping
    public void deleteContests(@RequestBody List<Long> ids) {
        contestService.deleteContests(ids);
    }

    @PutMapping
    public void updateContests(@RequestBody List<Contest> changes) {
        contestService.updateContests(changes);
    }


}
