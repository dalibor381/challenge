package com.ama.agencybooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name="users")
@Table(name="users")
@Data
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue()
    @Column(name="id")
    private Long id;

    @Column(unique=true)
    @NotNull
    private String userName;

    @Column(unique=true)
    @NotNull
    private String fullName;

    @Column
    @NotNull
    private SecurityLevel clearance;

}
