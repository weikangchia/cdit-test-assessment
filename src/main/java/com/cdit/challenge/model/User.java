package com.cdit.challenge.model;

import com.cdit.challenge.externalDto.CurrencyDoubleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    private String name;
    @Getter(AccessLevel.NONE)
    private int salary;

    public User(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    @JsonSerialize(using = CurrencyDoubleSerializer.class)
    public double getSalary() {
        return salary / 100.00;
    }
}
