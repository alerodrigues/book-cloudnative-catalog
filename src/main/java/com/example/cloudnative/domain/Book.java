package com.example.cloudnative.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book (
	
    @Id
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn,

    @Column
    @NotBlank(message = "The book title must be defined.")
    String title,

    @Column
    @NotBlank(message = "The book author must be defined.")
    String author,

    @Column
    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    Double price

) {}