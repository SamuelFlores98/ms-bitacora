package mx.com.bitacora.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import mx.com.bitacora.model.Usuario;
import mx.com.bitacora.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.ICsvBeanWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bitacora/v1/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("getUsers")
    public ResponseEntity<List<Usuario>> getUsers(){
        List<Usuario> listUsers = userService.listUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("listUsers")
    public ResponseEntity<Object> listUsuarios(){
        return new ResponseEntity<>(this.userService.getListUsers(), HttpStatus.OK);
    }

    @PostMapping("createUser")
    public ResponseEntity<Object> insertarUsuario(@Valid @RequestBody Usuario user){
        return new ResponseEntity<>(this.userService.insertUser(user), HttpStatus.OK);
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario user) {
        return new ResponseEntity<>(this.userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping("getUser/{id}")
    public ResponseEntity<Object> consultaUsuarioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("exportUsers")
    public ResponseEntity<Map<String, Object>> exportarUsuarios() throws IOException {
        Map<String, Object> response = new HashMap<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ICsvBeanWriter writer = this.userService.exportCsv(new OutputStreamWriter(outputStream));
        writer.close();

        response.put("name", "persona_".concat(String.valueOf(System.currentTimeMillis())));
        response.put("file", new String(Base64.getEncoder().encode(outputStream.toByteArray())));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("exportUsersXls")
    public void exportarUsuariosCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment: filename=users.csv");
        ICsvBeanWriter writer = this.userService.exportCsv(response.getWriter());
        writer.close();
    }
}
