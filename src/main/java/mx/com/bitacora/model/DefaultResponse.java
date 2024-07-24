package mx.com.bitacora.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultResponse<T> {
    private ResponseEntity<GenericResponse<T>> response (GenericResponse<T> genericResponse){
        return  new ResponseEntity<>(genericResponse, genericResponse != null ? HttpStatus.OK:  HttpStatus.CONFLICT);
    }
}
