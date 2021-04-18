package ch.rohner.demo.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender sex;
}
