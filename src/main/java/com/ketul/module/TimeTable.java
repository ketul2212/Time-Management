package com.ketul.module;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "time_table")
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "end_task")
    private LocalDateTime endTask;

    @Column(name = "complete")
    private boolean complete = false;

//    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;



}