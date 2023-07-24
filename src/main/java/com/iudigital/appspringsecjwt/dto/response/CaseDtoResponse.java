package com.iudigital.appspringsecjwt.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class CaseDtoResponse {

    private Long id;
    private String description;
    private LocalDate dateCase;
    private Float latitude;
    private Float longitude;
    private Float altitude;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long user_id;
    private Long crime_id;

    @Override
    public String toString() {
        return "\nCase {" + '\n' +
                "\t\t\tid = " + id + '\n' +
                "\t\t\tdescription = " + description + '\n' +
                "\t\t\tdateCase = " + dateCase + '\n' +
                "\t\t\tlatitude = " + latitude + '\n' +
                "\t\t\tlongitude = " + longitude + '\n' +
                "\t\t\taltitude = " + altitude + '\n' +
                "\t\t\tcreatedAt = " + createdAt + '\n' +
                "\t\t\tupdatedAt = " + updatedAt + '\n' +
                "\t\t\tuser_id = " + user_id + '\n' +
                "\t\t\tcrime_id = " + crime_id + '\n' +
                "\t\t\t}";
    }
}
