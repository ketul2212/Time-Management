package com.ketul.module;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "user")
public class User {

    // section Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "enabled" , nullable = false)
    private boolean isEnabled;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TimeTable.class)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ToString.Exclude
    private List<TimeTable> timeTables = new ArrayList<>();

}