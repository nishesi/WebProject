package ru.itis.MyTube.model.dto.forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationForm {

    private String username;

    private String password;

}
