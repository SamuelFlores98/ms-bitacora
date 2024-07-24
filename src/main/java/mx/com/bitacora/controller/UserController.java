package mx.com.bitacora.controller;

import jakarta.validation.Valid;
import mx.com.bitacora.exception.UserNotFoundException;
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
}
