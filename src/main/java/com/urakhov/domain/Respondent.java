package com.urakhov.domain;

import java.time.LocalDate;

public class Respondent {
    private int id;
    private String surname;
    private LocalDate birth;
    private String gender;
    private String email;
    private String phone;

    public Respondent() {}

    public Respondent(int id, String surname, LocalDate birth, String gender, String email, String phone) {
        this.id = id;
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public Respondent(String surname, LocalDate birth, String gender, String email, String phone) {
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
