package com.crud.app.rest.Models;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String profession;
    @Column
    private int age;

    public User() {}

//    public User(String email, String fName, String lName, String profession, int age) {
//        this.email = email;
//        this.first_name = fName;
//        this.last_name = lName;
//        this.profession = profession;
//        this.age = age;
//    }

    public User(long id, String fName, String lName, String profession, int age) {
        this.id = id;
        this.first_name = fName;
        this.last_name = lName;
        this.profession = profession;
        this.age = age;
    }

    public User(long id, String email, String fName, String lName, String profession, int age) {
        this.id = id;
        this.email = email;
        this.first_name = fName;
        this.last_name = lName;
        this.profession = profession;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
