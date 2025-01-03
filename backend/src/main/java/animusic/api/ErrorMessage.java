package animusic.api;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private Class<? extends Exception> clazz;
    private String stackTrace;
    private String description;

    public ErrorMessage(
            int statusCode,
            Date timestamp,
            Exception ex,
            String description
    ) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = ex.getMessage();
        this.clazz = ex.getClass();
        this.stackTrace = Arrays.stream(ex.getStackTrace())
                .limit(2)  // Ограничиваем вывод 5 строчками для примера
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        this.description = description;
    }
}
