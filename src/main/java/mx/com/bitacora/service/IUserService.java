package mx.com.bitacora.service;

import jakarta.servlet.http.HttpServletResponse;
import mx.com.bitacora.model.GenericResponse;
import mx.com.bitacora.model.Usuario;
import org.supercsv.io.ICsvBeanWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IUserService {

    GenericResponse<Usuario> insertUser(Usuario input);

    List<Usuario> listUsers();

    GenericResponse<List<Usuario>> getListUsers();

    GenericResponse<Usuario> updateUser(Long id, Usuario input);

    GenericResponse<Usuario> deleteUser(Long id);

    GenericResponse<Usuario> getUserById(Long id);

    GenericResponse<Usuario> login(String email);

    GenericResponse<Map<String, Object>> exportUsers();

    ByteArrayInputStream exportAllUsers();

    ICsvBeanWriter exportCsv(Writer writer) throws IOException;
}
