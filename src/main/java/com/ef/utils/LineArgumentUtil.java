package com.ef.utils;

import com.ef.dto.LineArgumentDTO;
import com.ef.enumeration.DurationEnum;
import com.ef.exception.GeneralRuntimeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LineArgumentUtil {
    private static final String DATE_FORMAT;
    private static final DateTimeFormatter FORMATTER;

    private static final String ARGUMENT_DELIMITER = "--";

    private static final String DURATION_NAME = "duration";
    private static final String START_DATE_NAME = "startDate";
    private static final String THRESHOLD_NAME = "threshold";
    private static final String PATH_TO_LOG_NAME = "accesslog";

    static {
        DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";
        FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    public static LineArgumentDTO getArguments(String[] args) {
        return new LineArgumentDTO(
                getStartDate(args), DurationEnum.of(getLineArgumentValue(args, DURATION_NAME, false)),
                getThreshold(args), getLineArgumentValue(args, PATH_TO_LOG_NAME, true)
        );
    }

    private static int getThreshold(String[] args) {
        String thresholdStr = getLineArgumentValue(args, THRESHOLD_NAME, false);
        try {
            return Integer.parseInt(thresholdStr);
        } catch (NumberFormatException e) {
            throw new GeneralRuntimeException("Bad threshold argument. Should be integer");
        }
    }

    private static LocalDateTime getStartDate(String[] args) {
        String startDateStr = getLineArgumentValue(args, START_DATE_NAME, false);
        try {
            return LocalDateTime.parse(startDateStr, FORMATTER);
        } catch (Exception e) {
            throw new GeneralRuntimeException(
                    String.format("Bad start date format %s instead of %s", startDateStr, DATE_FORMAT)
            );
        }
    }

    private static String getLineArgumentValue(String[] args, String argumentName, boolean softy) {
        for (String arg : args) {
            if (arg.startsWith(ARGUMENT_DELIMITER + argumentName)) {
                String[] arr = arg.split("=");
                if (arr.length != 2 && !softy) {
                    throw new GeneralRuntimeException("Bad command line argument " + arg);
                }
                return arr[1].trim();
            }
        }

        if (softy) {
            return null;
        }

        throw new GeneralRuntimeException("Could not find argument name in the command line: " + argumentName);
    }

}
