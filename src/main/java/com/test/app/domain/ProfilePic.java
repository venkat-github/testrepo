package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;


public class ProfilePic implements Serializable {

    @Id
    private String userId;
    
    @Field("image")
    private byte[] image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfilePic testBlob = (ProfilePic) o;

        if ( ! Objects.equals(userId, testBlob.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "TestBlob{" +
                "id=" + userId +
                ", image='" + image + "'" +
                '}';
    }
}
