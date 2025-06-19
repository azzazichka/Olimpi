package com.example.server.repository.contest;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "contests")
@Data
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer lvl;
    private Date date_start;
    private Date date_end;
    private String link;
    private Integer low_grade;
    private Integer up_grade;

}
