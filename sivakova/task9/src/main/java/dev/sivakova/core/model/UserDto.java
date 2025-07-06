package dev.sivakova.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private long daysSinceRegistration;
    private ZonedDateTime registeredAt;
    private Set<String> roles = new HashSet<>();

}
