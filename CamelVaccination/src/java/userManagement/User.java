/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userManagement;

import com.mysql.jdbc.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import logManagement.Log4k;

/**
 *
 * @author Lorenzo
 */
public class User {
    public User(ResultSet userData, Boolean isDoctor){
        try {
            this.id = userData.getInt("id");
            this.username = userData.getString("username");
            this.name = userData.getString("name");
            this.surname = userData.getString("surname");
            this.isDoctor = isDoctor;
            this.lastLogin = null;
            if (!isDoctor){
                this.gender = userData.getString("gender");
                this.birthdate = userData.getDate("birthdate");
                this.picture = userData.getString("picture");
            }
        } catch (SQLException ex) {
            Log4k.error(User.class.getName(), ex.getMessage());
        }
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public Date getBirthdate() {
        return birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Boolean getIsDoctor() {
        return isDoctor;
    }
    
    public void setIsDoctor(Boolean isDoctor) {
        this.isDoctor = isDoctor;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    //User Parameters
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String gender;
    private Date birthdate;
    private String picture;
    private Boolean isDoctor;
    private String lastLogin;
}
