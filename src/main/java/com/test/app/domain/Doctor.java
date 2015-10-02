package com.test.app.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.test.app.domain.enumeration.Sex;
import com.test.app.domain.enumeration.Speciality;

/**
 * A DoctorDTO.
 */
@Document(collection = "DOCTORDTO")
public class Doctor implements Serializable {

    @Id
    private String id;

    @NotNull    
    @Field("name")
    private String name;

    @Field("mobileno")
    private String mobileno;
    
    @Field("email_id")
    private String emailId;


    
    @Field("password")
    private String password;


    
    @Field("age")
    private Integer age;


    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("date_of_birth")
    private LocalDate dateOfBirth;


    
    @Field("sex")
    private Sex sex;


    
    @Field("degree")
    private String degree;


    
    @Field("speciality")
    private Speciality speciality;


    
    @Field("experience")
    private Integer experience;


    
    @Field("rating")
    private Float rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Doctor doctorDTO = (Doctor) o;

        if ( ! Objects.equals(id, doctorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", mobileno='" + mobileno + "'" +
                ", emailId='" + emailId + "'" +
                ", password='" + password + "'" +
                ", age='" + age + "'" +
                ", dateOfBirth='" + dateOfBirth + "'" +
                ", sex='" + sex + "'" +
                ", degree='" + degree + "'" +
                ", speciality='" + speciality + "'" +
                ", experience='" + experience + "'" +
                ", rating='" + rating + "'" +
                '}';
    }
}
