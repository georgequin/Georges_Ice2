package com.example.georgesice;

public class User {


        //POJO for Users

        private String PhoneNum;
        private String fullname;
        private String email;


    public User( String phoneNum, String fullName, String email) {
        this.PhoneNum = phoneNum;
        this.fullname = fullName;
        this.email = email;
    }

    public User() {
        }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
}
