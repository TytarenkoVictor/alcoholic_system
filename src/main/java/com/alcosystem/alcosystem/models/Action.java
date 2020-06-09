package com.alcosystem.alcosystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Action {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate actionDate;
    private String action;

}
