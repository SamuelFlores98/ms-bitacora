package mx.com.bitacora.service;

import mx.com.bitacora.exception.UserNotFoundException;
import mx.com.bitacora.model.GenericResponse;
import mx.com.bitacora.model.Usuario;
import mx.com.bitacora.repository.UserRepository;
import mx.com.bitacora.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public GenericResponse<Usuario> insertUser(Usuario input) {
        Optional<Usuario> email = this.repository.findUserByEmail(input.getEmail());
        if (email.isEmpty()){
            input.setCreatedAt(String.valueOf(LocalDate.now()));
            return new GenericResponse<>(HttpStatus.CREATED, this.repository.save(input),
                    true, "Usuario Creado");
            //return new ResponseEntity<>(this.repository.save(input), HttpStatus.CREATED);
        }
        List<String> errors = new ArrayList<>();
        errors.add(Constants.EMAIL_DUPLICADO);

        return new GenericResponse<>(HttpStatus.CONFLICT, null, false, Constants.EMAIL_DUPLICADO);
        //return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @Override
    public List<Usuario> listUsers() {
        return this.repository.findAll();
    }

    @Override
    public GenericResponse<List<Usuario>> getListUsers() {
        List<Usuario> usuarios = this.repository.findAll();
        return new GenericResponse<>(HttpStatus.OK, usuarios, true, "Listado Exitoso");
    }

    @Override
    public GenericResponse<Usuario> updateUser(Long id, Usuario input) {
        Optional<Usuario> userExist = this.repository.findById(id);
        if (userExist.isPresent()) {
            userExist.get().setNombre(input.getNombre());
            userExist.get().setApellidoPaterno(input.getApellidoPaterno());
            userExist.get().setApellidoMaterno(input.getApellidoMaterno());
            userExist.get().setUpdatedAt(String.valueOf(LocalDate.now()));
            return new GenericResponse<>(HttpStatus.OK, this.repository.save(userExist.get()), true, "Actualizacion Exitosa");
        }
        return new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE);
    }

    @Override
    public GenericResponse<Usuario> deleteUser(Long id){
        boolean userExist = this.repository.existsById(id);
        if (!userExist) {
            return new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE);
        }
        this.repository.deleteById(id);
        return new GenericResponse<>(HttpStatus.OK, null, true, "Usuario Eliminado");
    }

    @Override
    public GenericResponse<Usuario> getUserById(Long id){
        Optional<Usuario> user = this.repository.findById(id);
        return user.map(usuario -> new GenericResponse<>(HttpStatus.OK, usuario, true, "Consulta Exitosa"))
                .orElseGet(() -> new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE));
    }

    @Override
    public GenericResponse<Usuario> login(String email) {
        Optional<Usuario> user = this.repository.findUserByEmail(email);
        return user.map(usuario -> new GenericResponse<>(HttpStatus.OK, usuario, true, "Login Exitoso"))
                .orElseGet(() ->
                        new GenericResponse<>(HttpStatus.NOT_FOUND, null, false, Constants.USUARIO_INEXISTENTE));
    }
}
