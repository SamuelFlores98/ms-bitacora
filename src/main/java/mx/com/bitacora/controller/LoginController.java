package mx.com.bitacora.controller;

import mx.com.bitacora.model.GenericResponse;
import mx.com.bitacora.model.Login;
import mx.com.bitacora.model.Usuario;
import mx.com.bitacora.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bitacora/v1/")
public class LoginController {

    private final IUserService service;

    @Autowired
    public LoginController(IUserService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse<Usuario>> login (@RequestBody Login input){
        return new ResponseEntity<>(this.service.login(input.getEmail()), HttpStatus.OK);
    }
}
