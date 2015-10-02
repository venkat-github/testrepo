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


/**
 * A CheckEntity.
 */
@Document(collection = "CHECKENTITY")
public class CheckEntity implements Serializable {

    @Id
    private String id;

    @NotNull
    @Size(min = 2)    

    
    @Field("name")
    private String name;


    
    @Field("age")
    private Integer age;

    @NotNull
    @Min(value = 2)
    @Max(value = 2)    

    
    @Field("age2")
    private Integer age2;

    @NotNull    

    
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Field("check1")
    private LocalDate check1;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge2() {
        return age2;
    }

    public void setAge2(Integer age2) {
        this.age2 = age2;
    }

    public LocalDate getCheck1() {
        return check1;
    }

    public void setCheck1(LocalDate check1) {
        this.check1 = check1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckEntity checkEntity = (CheckEntity) o;

        if ( ! Objects.equals(id, checkEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CheckEntity{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age='" + age + "'" +
                ", age2='" + age2 + "'" +
                ", check1='" + check1 + "'" +
                '}';
    }
}
