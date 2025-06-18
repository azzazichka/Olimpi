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
    Long id;
    String title;
    Integer lvl;
    Date date_start;
    Date date_end;
    String link;
    Integer low_grade;
    Integer up_grade;

}
