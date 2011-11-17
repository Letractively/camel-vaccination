/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userManagement;

import com.mysql.jdbc.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author administrator
 */
public class Paziente {

    public Paziente(ResultSet userData) {
        try {
            this.id = userData.getInt("id");
            this.username = userData.getString("username");
            this.name = userData.getString("name");
            this.surname = userData.getString("surname");
            this.vaccination_date = userData.getDate("vaccination_date");
            this.doctor_id = userData.getString("doctor_id");
            this.gender = userData.getString("gender");
            this.birthdate = userData.getDate("birthdate");
            this.picture = userData.getString("picture");

        } catch (SQLException ex) {
        }
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
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

    public Date getVaccination_date() {
        return vaccination_date;
    }

    public void setVaccination_date(Date vaccination_date) {
        this.vaccination_date = vaccination_date;
    }
    //User Parameters
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String gender;
    private Date birthdate;
    private String picture;
    private Date vaccination_date;
    private String doctor_id;
}
