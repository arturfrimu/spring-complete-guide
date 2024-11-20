package com.arturfrimu.elasticsearch.entity;

import com.arturfrimu.elasticsearch.entity.dataclass.Address;
import com.arturfrimu.elasticsearch.entity.dataclass.Company;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "users")
public class User {
    @Id
    private Integer id;
    private String name;
    private String username;
    private String email;
    @Field(type = FieldType.Nested)
    private Address address;
    private String phone;
    private String website;
    @Field(type = FieldType.Nested)
    private Company company;
}