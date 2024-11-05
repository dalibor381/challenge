package com.ama.agencybooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name="books")
@Table(name="books")
@Data
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue()
    @Column(name="id")
    private Long id;

    @Column(unique=true)
    @NotNull
    private String title;

    @Column
    @NotNull
    private String author;

    @Column
    @NotNull
    private SecurityLevel classification;

}