package mx.com.bitacora.service;

import mx.com.bitacora.model.Login;
import org.springframework.http.ResponseEntity;

public interface IBitacoraService {
    ResponseEntity<Object> login(Login login);
}
