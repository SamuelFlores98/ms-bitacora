package mx.com.bitacora.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private HttpStatus status;
    private T data;
    private boolean success;
    private String detail;
}
