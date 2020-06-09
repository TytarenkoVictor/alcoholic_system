package com.alcosystem.alcosystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Alcoholic {

    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    private Integer takenQuantity;

}
