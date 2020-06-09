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
public class Inspector {

    private Long id;
    private String inspectorName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate hireDate;
    private Long departmentId;
    private Integer takenQuantity;
    private Integer alcoholicTaken;

}
