package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;


/**
 * A RegistrationOtp.
 */
@Document(collection = "REGISTRATIONOTP")
public class RegistrationOtp implements Serializable {

    @Id
    private String id;


    
    @Field("mobileno")
    private String mobileno;


    
    @Field("userid")
    private String userid;


    
    @Field("otp")
    private String otp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegistrationOtp registrationOtp = (RegistrationOtp) o;

        if ( ! Objects.equals(id, registrationOtp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegistrationOtp{" +
                "id=" + id +
                ", mobileno='" + mobileno + "'" +
                ", userid='" + userid + "'" +
                ", otp='" + otp + "'" +
                '}';
    }
}
