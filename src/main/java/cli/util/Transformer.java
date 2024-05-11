package cli.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transformer {
    public static String transformToString(String orig) {
        return orig;
    }

    public static Integer transformToInt(String orig) {
        return Integer.parseInt(orig);
    }

    public static Double transformToDouble(String orig) {
        return Double.parseDouble(orig);
    }

    public static LocalDate transformToLocalDate(String orig) {
        return LocalDate.parse(orig, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static LocalDateTime transformToLocalDateTime(String orig) {
        return LocalDateTime.parse(orig, DateTimeFormatter.ofPattern("[dd/MM/yyyy HH:mm:ss][dd/MM/yyyy]"));
    }
}
