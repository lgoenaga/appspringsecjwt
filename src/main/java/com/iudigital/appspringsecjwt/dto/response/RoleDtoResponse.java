package com.iudigital.appspringsecjwt.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDtoResponse {

    private Long id;
    private String rol;

    @Override
    public String toString() {
        return "\nRole {" + '\n' +
                "\t\t\tid = " + id + '\n' +
                "\t\t\trol = " + rol + '\n' +
                "\t\t\t}";
    }

}
