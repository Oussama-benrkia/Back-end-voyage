package org.backend.voyage.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestUp {
    private String firstname;
    private String lastname;
    private String Role;
    private String password;
    @Email
    private String email;
    private MultipartFile image;
}

