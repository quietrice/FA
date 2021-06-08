package com.mark.pr0j3c7.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String login;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String hashedPassword;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    @OneToMany(mappedBy = "devs")
    private Set<Task> developerTask;

    @OneToMany(mappedBy = "anals")
    private Set<Task> analystTask;

    @OneToMany(mappedBy = "testers")
    private Set<Task> testerTask;

    @Column
    private Set<Role> roles;

}
