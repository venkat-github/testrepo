package com.test.app.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.health.app.domain.Languages;
import com.test.app.domain.Authority;
import com.test.app.domain.MedUser;
import com.test.app.domain.TypeOfCommunication;
import com.test.app.domain.enumeration.BloodGroup;
import com.test.app.domain.enumeration.Sex;
import com.test.app.domain.enumeration.Speciality;
import com.test.app.domain.util.CustomDateTimeDeserializer;
import com.test.app.domain.util.CustomDateTimeSerializer;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;

public class MedUserDto implements Serializable {

    private String id;
    
    private String name;

    private String fullname;

    private String firstName;

    private String lastName;

    private String email;

    private String langKey;

    private List<String> roles;
    
    private String uuid;

    private String mobileno;
    
    private String emailId;
    
    private String password;

    private Sex sex;

    private Integer age;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate lastPasswordUpdatedDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate lastLoginDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate lastBloodDonationDate;

    private BloodGroup bloodGroup;

    private boolean	willingnessToDonateBlood;
    
    private TypeOfCommunication communication;

    private boolean	shareIdentity;

    private String street;

    private String landmark;

    private String houseNo;
    
    private String location;

    private String city;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    private Set<String> degrees;

    private Set<Speciality> specialities;

    private Set<Languages> languages;

    private Integer experience;

    private Float rating;

    private Set<String> hospitalIds;

    private Set<String> labIds;

    private Set<String> doctorIds;

    private Set<String> adminHospitalIds;

    private Set<String> phoneNos;

    private boolean activated = false;

    private boolean doctorActivated = false;


    boolean isHospitalAdmin;
    boolean isDoctor;
    
    String login;
    

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isHospitalAdmin() {
		return isHospitalAdmin;
	}

	public void setHospitalAdmin(boolean isHospitalAdmin) {
		this.isHospitalAdmin = isHospitalAdmin;
	}

	public boolean isDoctor() {
		return isDoctor;
	}

	public void setDoctor(boolean isDoctor) {
		this.isDoctor = isDoctor;
	}

	
    @JsonIgnore
    private String activationKey;

    private String resetKey;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime resetDate = null;

    String photoId;
    
    String doctorRegistrationId;
    
    public MedUserDto() {
    	
    }
    
    public MedUserDto(MedUser user) {
		name = user.getName();
		id = user.getId();
	}

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedUserDto userDTO = (MedUserDto) o;

        if ( ! Objects.equals(id, userDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", mobileno='" + mobileno + "'" +
                ", emailId='" + emailId + "'" +
                ", password='" + password + "'" +
                ", sex='" + sex + "'" +
                ", age='" + age + "'" +
                ", dateOfBirth='" + dateOfBirth + "'" +
                ", bloodGroup='" + bloodGroup + "'" +
                ", location='" + location + "'" +
                ", city='" + city + "'" +
                '}';
    }
}
