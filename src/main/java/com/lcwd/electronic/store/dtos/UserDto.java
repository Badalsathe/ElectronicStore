package com.lcwd.electronic.store.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;

    @Size(min = 3, max = 25, message = "Name is invalid ......")
    private String name;

  //  @Email(message = "Invalid User Email....")

    @Pattern
            (regexp ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                    , message ="Invalid User Email...." )
    @NotBlank(message = "E-mail is required..")
    private String email;

    @NotBlank(message = "Password is required....")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender")
    private String gender;

    @NotBlank(message = "Write something about urself")
    private String about;

    private String imageName;

}
