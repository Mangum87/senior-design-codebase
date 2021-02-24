package com.example.myapplication.LoginStuff;

/**
Create a User class to store the User information that is received upon successfull
login. These are all the User attributes used during costumer registration.
Check documentation with the heading POSTMAN RESPONSE to view the response.
 */
public class User {
//Creating constructor, getter and setter
    private String address, bio, city, email, fname, gender, lname, password, phone, state, uname,zipcode;
    private boolean is_admin, is_email_verified, profile_pic;
    private int user_id;

    public User(String address, String bio, String city, String email, String fname, String gender, String lname, String password, String phone, String state, String uname, String zipcode, boolean is_admin, boolean is_email_verified, boolean profile_pic, int user_id) {
        this.address = address;
        this.bio = bio;
        this.city = city;
        this.email = email;
        this.fname = fname;
        this.gender = gender;
        this.lname = lname;
        this.password = password;
        this.phone = phone;
        this.state = state;
        this.uname = uname;
        this.zipcode = zipcode;
        this.is_admin = is_admin;
        this.is_email_verified = is_email_verified;
        this.profile_pic = profile_pic;
        this.user_id = user_id;
    }


    public String getAddress() {
        return address;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getGender() {
        return gender;
    }

    public String getLname() {
        return lname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getState() {
        return state;
    }

    public String getUname() {
        return uname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public boolean isIs_email_verified() {
        return is_email_verified;
    }

    public boolean isProfile_pic() {
        return profile_pic;
    }

    public int getUser_id() {
        return user_id;
    }
}
