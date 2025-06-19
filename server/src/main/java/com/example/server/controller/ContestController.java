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

    @DeleteMapping(path = "{id}")
    public void deleteContest(@PathVariable Long id) {
        contestService.deleteContest(id);
    }

    @PutMapping
    public void updateContest(@RequestBody Contest changes) {
        contestService.updateContest(changes);
    }


}
// TODO: удаление всех ивентов, где user_id == id;
// без подключения к интернету, взаимодействие с Contest запрещено(можно только приватные создавать)