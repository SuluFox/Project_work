package com.example.projectwork;


public class TrainerModel {
    Integer trainerId;
    String firstname;
    String lastname;
    Integer phonenumber;
    String email;


    public TrainerModel(Integer trainerId, String firstname, String lastname, Integer phonenumber,String email) {
        this.trainerId = trainerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.email = email;
    }



    public void TrainerModel(){

    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }


    public String getEmail() {
        return email;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
