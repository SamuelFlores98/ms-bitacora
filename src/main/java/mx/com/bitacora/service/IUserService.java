package mx.com.bitacora.service;

import mx.com.bitacora.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    ResponseEntity<Object> insertUser(Usuario input);

    List<Usuario> getUsers();

    ResponseEntity<Object> updateUser(Usuario input);

    ResponseEntity<Object> deleteUser(Long id);

    ResponseEntity<Object> getUserById(Long id);
}
