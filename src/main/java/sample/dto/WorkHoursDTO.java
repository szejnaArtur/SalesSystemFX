package sample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkHoursDTO {

    private Long idWorkHours;
    private LocalDateTime startWork;
    private LocalDateTime endWork;
    
}
