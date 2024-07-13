package mx.com.bitacora.controller;

import mx.com.bitacora.model.Usuario;
import mx.com.bitacora.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bitacora/v1/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("getUsers")
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        List<Usuario> listUsers = userService.getUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @PostMapping("createUser")
    public ResponseEntity<Object> insertarUsuario(@RequestBody Usuario user){
        return new ResponseEntity<>(this.userService.insertUser(user), HttpStatus.OK);
    }

    @PutMapping("updateUser")
    public ResponseEntity<Object> actualizarUsuario(@RequestBody Usuario user) {
        return new ResponseEntity<>(this.userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping("getUser/{id}")
    public ResponseEntity<Object> consultaUsuarioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }
}
