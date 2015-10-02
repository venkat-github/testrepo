package com.test.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;


/**
 * A TestBlob.
 */
@Document(collection = "TESTBLOB")
public class TestBlob implements Serializable {

    @Id
    private String id;


    
    @Field("file")
    private byte[] file;


    
    @Field("image")
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
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

        TestBlob testBlob = (TestBlob) o;

        if ( ! Objects.equals(id, testBlob.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestBlob{" +
                "id=" + id +
                ", file='" + file + "'" +
                ", image='" + image + "'" +
                '}';
    }
}
