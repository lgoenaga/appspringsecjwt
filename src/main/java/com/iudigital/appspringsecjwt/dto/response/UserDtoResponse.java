package com.iudigital.appspringsecjwt.dto.response;


import com.iudigital.appspringsecjwt.model.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private LocalDate dateBirth;
    private boolean enabled;

    private List<Role> roles;
    private String image;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Override
    public String toString() {
        return "\nUser {" + '\n' +
                "\t\t\tid = " + id + '\n' +
                "\t\t\tfirstName = " + firstName + '\n' +
                "\t\t\tlastName = " + lastName + '\n' +
                "\t\t\tusername = " + username + '\n' +
                "\t\t\temail = " + email + '\n' +
                "\t\t\tdateBirth = " + dateBirth + '\n' +
                "\t\t\tenabled = " + enabled + '\n' +
                "\t\t\troles=" + roles + '\n' +
                "\t\t\timage='" + image + '\n' +
                "\t\t\tcreatedAt=" + createdAt + '\n' +
                "\t\t\tupdatedAt=" + updatedAt + '\n' +
                "\t\t\t}";
    }

}
