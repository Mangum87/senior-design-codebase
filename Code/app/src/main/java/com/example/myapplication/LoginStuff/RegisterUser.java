package com.example.myapplication.LoginStuff;


public class RegisterUser {
    private String address, bio = "this is a test", city, email, fname, gender ="male", lname, password, phone , state, username,zipcode;
    private boolean is_admin = false, is_email_verified = false, profile_pic = true, check_entry = false;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUname(String username) {
        this.username = username;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public void setCheck_entry(boolean check_entry) { this.check_entry = check_entry; }

    public boolean get_check_entry() { return check_entry; }
}
