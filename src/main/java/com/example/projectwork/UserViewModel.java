package com.example.projectwork;

import java.sql.Timestamp;
import java.util.Date;

public class UserViewModel {
    Integer userId;
    String firstname;
    String lastname;
    Integer age;
    String gender;
    Date dob;
    Integer phonenumber;
    Timestamp joinedon;
    String email;

    public UserViewModel(Integer userId, String firstname, String lastname, Integer age, String gender, Date dob, Integer phonenumber, Timestamp joinedon, String email) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.phonenumber = phonenumber;
        this.joinedon = joinedon;
        this.email = email;
    }

    public UserViewModel() {

    }


    public Integer getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Date getDob() {
        return dob;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public Timestamp getJoinedon() {
        return joinedon;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setJoinedon(Timestamp joinedon) {
        this.joinedon = joinedon;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
