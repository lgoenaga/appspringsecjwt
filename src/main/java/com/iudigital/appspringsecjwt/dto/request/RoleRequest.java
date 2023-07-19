package com.iudigital.appspringsecjwt.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false, length = 20)
    private String rol;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
