package org.example.task15.models;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class Error {
    private Integer status;
    private String error;
    private String message;
    private String path;
    private ZonedDateTime timestamp;
}
