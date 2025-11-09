package com.example.server.repository.contest;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contests")
@Data
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer lvl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date_start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date_end;

    private String link;
    private Integer low_grade;
    private Integer up_grade;
    private boolean archived;

    @Transient
    List<String> subjects;

    public void addSubject(String subject) {
        subjects.add(subject);
    }
}


