package com.spring.course.resource.exception;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 5910717180355584502L;

    private int code;
    private String msg;
    private Date date;

}
