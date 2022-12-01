package ru.itis.MyTube.model.dto.forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String passwordRepeat;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String country;
    private String agreement;
}




