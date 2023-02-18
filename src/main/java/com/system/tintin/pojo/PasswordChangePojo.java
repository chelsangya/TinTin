package com.system.tintin.pojo;

import com.system.tintin.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangePojo {
    private String email;
    @NotEmpty(message = "Old Password can't be empty")
    private String oldPassword;
    @NotEmpty(message = "New Password can't be empty")
    private String newPassword;
    @NotEmpty(message = "Repeat Password can't be empty")
    private String repeatPassword;

}
