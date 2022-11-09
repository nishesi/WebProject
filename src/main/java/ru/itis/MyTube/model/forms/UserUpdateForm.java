package ru.itis.MyTube.model.forms;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.Part;

@Data
@Builder
public class UserUpdateForm {
    private Part iconPart;
    private String password;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String country;
}
