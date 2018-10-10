package com.ef.dto;

import com.ef.enumeration.DurationEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LineArgumentDTO {

    private LocalDateTime startDate;
    private DurationEnum duration;
    private int threshold;
    private String pathToLogs;

    public LineArgumentDTO(LocalDateTime startDate, DurationEnum duration, int threshold, String pathToLogs) {
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        this.pathToLogs = pathToLogs;
    }
}
