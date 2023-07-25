package com.sentimentanalysis.pojo;

public class User
{
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String gender;
    private String mobile;
    private String addr;
    private String role;
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getFname() {
        return this.fname;
    }
    
    public void setFname(final String fname) {
        this.fname = fname;
    }
    
    public String getLname() {
        return this.lname;
    }
    
    public void setLname(final String lname) {
        this.lname = lname;
    }
    
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(final String gender) {
        this.gender = gender;
    }
    
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }
    
    public String getAddr() {
        return this.addr;
    }
    
    public void setAddr(final String addr) {
        this.addr = addr;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public void setRole(final String role) {
        this.role = role;
    }
}