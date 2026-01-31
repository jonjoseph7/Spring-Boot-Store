package com.codewithmosh.store.users;

import com.codewithmosh.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name required!")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;

    @NotBlank(message = "Email required!")
    @Email(message = "Must be valid email!")
    @Lowercase(message = "Must be lowercase")
    private String email;

    @NotBlank(message = "Password required!")
    @Size(min = 6, max = 25, message = "Must be between 6 and 25 characters")
    private String password;
}
