package mx.com.bitacora.service;

import mx.com.bitacora.model.GenericResponse;
import mx.com.bitacora.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    GenericResponse<Usuario> insertUser(Usuario input);

    List<Usuario> listUsers();

    GenericResponse<List<Usuario>> getListUsers();

    GenericResponse<Usuario> updateUser(Long id, Usuario input);

    GenericResponse<Usuario> deleteUser(Long id);

    GenericResponse<Usuario> getUserById(Long id);

    GenericResponse<Usuario> login(String email);
}
