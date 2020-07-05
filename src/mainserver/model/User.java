/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.model;

import java.io.Serializable;

/**
 *
 * @author harshit
 */
public class User implements Serializable{

    private String name;
    private String email;
    private String phone;
    private String workplace;
    private String userid;
    private String status;
    public User() {
        
    }
    
    public User(String userid, String name, String email, String phone, String workplace) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.userid = userid;
        this.workplace = workplace;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setStatus(String status)
    {
        this.status=status;
    }
    public String getStatus()
    {
        return this.status;
    }
    

}
