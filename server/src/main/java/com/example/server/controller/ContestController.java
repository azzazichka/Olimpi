package com.example.server.controller;

import com.example.server.repository.contest.Contest;
import com.example.server.service.ContestService;
import com.example.server.service.UserKeyService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/contests")
public class ContestController {
    private final ContestService contestService;
    private final UserKeyService userKeyService;
    public ContestController(ContestService contestService, UserKeyService userKeyService) {
        this.contestService = contestService;
        this.userKeyService = userKeyService;
    }

    @GetMapping
    public List<Contest> getContestsBySubjects(@RequestParam List<String> subjects_names) {
        return contestService.getContestBySubjects(subjects_names);
    }

    @PostMapping("/admin")
    public void createContests(@RequestBody List<Contest> contests) {
        for (Contest contest : contests) {
            contestService.createContest(contest);
        }
    }

    @PostMapping
    public void createContest(@RequestBody Contest contest, @RequestHeader("x-api-key") String key) throws AuthException {
        userKeyService.checkAccessRights(key, 1);
        contestService.createContest(contest);
    }

    @DeleteMapping
    public void deleteContest(@RequestParam Long id, @RequestHeader("x-api-key") String key) throws AuthException {
        userKeyService.checkAccessRights(key, 1);
        contestService.deleteContest(id);
    }

    @PutMapping
    public void updateContest(@RequestBody Contest changes, @RequestHeader("x-api-key") String key) throws AuthException {
        userKeyService.checkAccessRights(key, 1);
        contestService.updateContest(changes);
    }
}
