package com.iudigital.appspringsecjwt.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CrimeDtoResponse {

    private Long id;
    private String name;
    private String description;
    private Long userId;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Override
    public String toString() {
        return "\nCrime {" + '\n' +
                "\t\t\tid = " + id + '\n' +
                "\t\t\tname = " + name + '\n' +
                "\t\t\tdescription = " + description + '\n' +
                "\t\t\tuserId = " + userId + '\n' +
                "\t\t\tcreatedAt = " + createdAt + '\n' +
                "\t\t\tupdatedAt = " + updatedAt + '\n' +
                "\t\t\t}";
    }

}
