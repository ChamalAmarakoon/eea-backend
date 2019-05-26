package com.example.easynotes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)


public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Setter @Getter private String username;

    @Setter @Getter private String password;

    @Setter @Getter private String email;

    @OneToMany
    @Getter @Setter private List<Orders> orders;

    public User() {

    }


}
