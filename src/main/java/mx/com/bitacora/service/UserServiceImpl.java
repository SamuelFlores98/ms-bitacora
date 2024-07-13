package mx.com.bitacora.service;

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
    public ResponseEntity<Object> insertUser(Usuario input) {
        Optional<Usuario> email = this.repository.findUserByEmail(input.getEmail());
        if (email.isEmpty()){
            input.setCreatedAt(String.valueOf(LocalDate.now()));
            return new ResponseEntity<>(this.repository.save(input), HttpStatus.CREATED);
        }
        List<String> errors = new ArrayList<>();
        errors.add(Constants.EMAIL_DUPLICADO);

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @Override
    public List<Usuario> getUsers() {
        return this.repository.findAll();
    }

    @Override
    public ResponseEntity<Object> updateUser(Usuario input) {
        Optional<Usuario> userExist = this.repository.findById(input.getIdUsuario());
        if (userExist.isPresent()) {
            if (!input.getEmail().equals(userExist.get().getEmail())) {
                Optional<Usuario> email = this.repository.findUserByEmail(input.getEmail());
                if (email.isPresent()){
                    Object[] error = {"Email existente"};
                    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
                }
            }

            Usuario user = setUserUpdate(userExist.get(), input);

            return new ResponseEntity<>(this.repository.save(user), HttpStatus.OK);
        }
        Object[] error = {"Usuario no existe"};
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        boolean userExist = this.repository.existsById(id);
        if (!userExist) {
            Object[] error = {"Usuario no existe"};
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        this.repository.deleteById(id);
        Object[] response = {"Usuario eliminado"};
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getUserById(Long id) {
        Optional<Usuario> user = this.repository.findById(id);
        if (user.isEmpty()) {
            Object[] error = {"Usuario no existe"};
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }


    public Usuario setUserUpdate (Usuario userExist, Usuario input) {
        Usuario user = new Usuario();
        user.setIdUsuario(userExist.getIdUsuario());
        user.setNombre(null == input.getNombre() ? userExist.getNombre() : input.getNombre());
        user.setApellidoPaterno(null == input.getApellidoPaterno() ? userExist.getApellidoPaterno() : input.getApellidoPaterno());
        user.setApellidoMaterno(null == input.getApellidoMaterno() ? userExist.getApellidoMaterno() : input.getApellidoMaterno());
        user.setEmail(null == input.getEmail() ? userExist.getEmail() : input.getEmail());
        user.setCreatedAt(userExist.getCreatedAt());
        user.setUpdatedAt(String.valueOf(LocalDate.now()));
        user.setIdRol(userExist.getIdRol());
        return user;
    }
}
