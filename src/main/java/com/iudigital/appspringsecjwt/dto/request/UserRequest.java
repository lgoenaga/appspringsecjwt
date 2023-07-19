package com.iudigital.appspringsecjwt.dto.request;

import com.iudigital.appspringsecjwt.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest implements UserDetails {

    @Size(max = 30, message = "Firstname must be max 60 characters")
    private String firstName;

    @Size(max = 30, message = "Lastname must be max 60 characters")
    private String lastName;

    @NotNull(message = "Username is required DTO")
    @NotEmpty(message = "Username is required DTO")
    private String username;

    @NotNull(message = "Email is required")
    @Size(min=6, max = 60, message = "Email must be between 6 and 60 characters")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String image;
    private LocalDate dateBirth;

    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
