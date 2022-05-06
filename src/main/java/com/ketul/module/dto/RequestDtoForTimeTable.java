package com.ketul.module.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RequestDtoForTimeTable extends Login {

    private String title;
    private String description;
    private LocalDateTime endTask;

}
