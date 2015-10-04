package com.test.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.health.app.domain.Languages;
import com.test.app.domain.enumeration.BloodGroup;
import com.test.app.domain.enumeration.Sex;
import com.test.app.domain.enumeration.Speciality;
import com.test.app.domain.util.CustomDateTimeDeserializer;
import com.test.app.domain.util.CustomDateTimeSerializer;
import com.test.app.domain.util.CustomLocalDateSerializer;
import com.test.app.domain.util.ISO8601LocalDateDeserializer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * A user.
 */
@Document(collection = "JHI_USER")
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    private String login;//email alias

    //@JsonIgnore
    @NotNull
    private String password;

    @Size(max = 50)
    @Field("first_name")
    private String firstName;

    @Size(max = 50)
    @Field("last_name")
    private String lastName;

	@Field("lang_key")
    private String langKey;

    @Field("fullname")
    private String name;

    @Field("uuid")
    private String uuid;

    @Field("mobileno")
    private String mobileno;
    
    @Field("email_id")
    private String email;

    List<String> roles;
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    @Field("sex")
    private Sex sex;

    @Field("age")
    private Integer age;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("password_updated_date")
    private LocalDate lastPasswordUpdatedDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("last_login_date")
    private LocalDate lastLoginDate;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("last_blood_date")
    private LocalDate lastBloodDonationDate;

    @Field("blood_group")
    private BloodGroup bloodGroup;

    @Field("willing_blood")
    private boolean	willingnessToDonateBlood;
    
    @Field("communication_mode")
    private TypeOfCommunication communication;

    @Field("share_identity")
    private boolean	shareIdentity;

    @Field("street")
    private String street;

    @Field("landmark")
    private String landmark;

    @Field("houseno")
    private String houseNo;
    
    @Field("location")
    private String location;

    @Field("city")
    private String city;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    @Field("degrees")
    private Set<String> degrees;

    @Field("specialities")
    private Set<Speciality> specialities;

    @Field("languages")
    private Set<Languages> languages;

    @Field("experience")
    private Integer experience;

    @Field("rating")
    private Float rating;

    @Field("hospitalIds")
    private Set<String> hospitalIds;

    @Field("labIds")
    private Set<String> labIds;

    @Field("permittedDoctorIds")
    private Set<String> doctorIds;

    @Field("adminHospitalIds")
    private Set<String> adminHospitalIds;

    @Field("emergencyPhoneNos")
    private Set<String> phoneNos;

    private boolean activated = false;

    private boolean doctorActivated = false;

    boolean isHospitalAdmin;
    
    boolean isDoctor;
    
    public String getLogin() {
		return email;
	}

	public void setLogin(String login) {
		this.email = login;
		this.login = login;
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

	@Size(max = 20)
    @Field("activation_key")
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Field("reset_key")
    private String resetKey;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("reset_date")
    private DateTime resetDate = null;

    String photoId;
    
    String doctorRegistrationId;
    
    
    public User() {
    }
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

        User userDTO = (User) o;

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
                ", emailId='" + email + "'" +
                ", password='" + password + "'" +
                ", sex='" + sex + "'" +
                ", age='" + age + "'" +
                ", dateOfBirth='" + dateOfBirth + "'" +
                ", bloodGroup='" + bloodGroup + "'" +
                ", location='" + location + "'" +
                ", city='" + city + "'" +
                '}';
    }

	public String getFullname() {
		return name;
	}

	public void setFullname(String fullname) {
		this.name = fullname;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDate getLastPasswordUpdatedDate() {
		return lastPasswordUpdatedDate;
	}

	public void setLastPasswordUpdatedDate(LocalDate lastPasswordUpdatedDate) {
		this.lastPasswordUpdatedDate = lastPasswordUpdatedDate;
	}

	public LocalDate getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDate lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDate getLastBloodDonationDate() {
		return lastBloodDonationDate;
	}

	public void setLastBloodDonationDate(LocalDate lastBloodDonationDate) {
		this.lastBloodDonationDate = lastBloodDonationDate;
	}

	public boolean isWillingnessToDonateBlood() {
		return willingnessToDonateBlood;
	}

	public void setWillingnessToDonateBlood(boolean willingnessToDonateBlood) {
		this.willingnessToDonateBlood = willingnessToDonateBlood;
	}

	public TypeOfCommunication getCommunication() {
		return communication;
	}

	public void setCommunication(TypeOfCommunication communication) {
		this.communication = communication;
	}

	public boolean isShareIdentity() {
		return shareIdentity;
	}

	public void setShareIdentity(boolean shareIdentity) {
		this.shareIdentity = shareIdentity;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<String> getDegrees() {
		return degrees;
	}

	public void setDegrees(Set<String> degrees) {
		this.degrees = degrees;
	}

	public Set<Speciality> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(Set<Speciality> specialities) {
		this.specialities = specialities;
	}

	public Set<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Languages> languages) {
		this.languages = languages;
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

	public Set<String> getHospitalIds() {
		return hospitalIds;
	}

	public void setHospitalIds(Set<String> hospitalIds) {
		this.hospitalIds = hospitalIds;
	}

	public Set<String> getLabIds() {
		return labIds;
	}

	public void setLabIds(Set<String> labIds) {
		this.labIds = labIds;
	}

	public Set<String> getDoctorIds() {
		return doctorIds;
	}

	public void setDoctorIds(Set<String> doctorIds) {
		this.doctorIds = doctorIds;
	}

	public Set<String> getAdminHospitalIds() {
		return adminHospitalIds;
	}

	public void setAdminHospitalIds(Set<String> adminHospitalIds) {
		this.adminHospitalIds = adminHospitalIds;
	}

	public Set<String> getPhoneNos() {
		return phoneNos;
	}

	public void setPhoneNos(Set<String> phoneNos) {
		this.phoneNos = phoneNos;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isDoctorActivated() {
		return doctorActivated;
	}

	public void setDoctorActivated(boolean doctorActivated) {
		this.doctorActivated = doctorActivated;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public DateTime getResetDate() {
		return resetDate;
	}

	public void setResetDate(DateTime resetDate) {
		this.resetDate = resetDate;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getDoctorRegistrationId() {
		return doctorRegistrationId;
	}

	public void setDoctorRegistrationId(String doctorRegistrationId) {
		this.doctorRegistrationId = doctorRegistrationId;
	}

	public boolean getActivated() {
		return activated;
	}

	public String getLangKey() {
		return "en";
	}

	public void setLangKey(String langKey2) {
		
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

}
