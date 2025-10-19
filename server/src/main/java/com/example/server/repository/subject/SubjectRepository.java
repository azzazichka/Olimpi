package com.example.server.repository.subject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Modifying
    @Query(value = "DELETE FROM subjects WHERE contest_id = :contestId", nativeQuery = true)
    void deleteAllByContestId(Long contestId);

    @Query(value = "SELECT * FROM subjects WHERE subject IN :subjectsNames", nativeQuery = true)
    List<Subject> findBySubjectsNames(List<String> subjectsNames);
}
