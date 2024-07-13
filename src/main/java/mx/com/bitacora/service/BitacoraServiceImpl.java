package mx.com.bitacora.service;

import mx.com.bitacora.model.Login;
import mx.com.bitacora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class BitacoraServiceImpl implements IBitacoraService{

    private final UserRepository repository;

    @Autowired
    public BitacoraServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Object> login(Login login) {
//        Optional<UserDTO> email = this.repository.findUserByEmail(login.getEmail());
//        Optional<UserDTO> password = this.repository.findByPassword(login.getPassword());
//
//        if (email.isPresent() && password.isPresent()){
//            MultiValueMap<String, String> response = new LinkedMultiValueMap<>();
//            response.add("nombre", email.get().);
//            return new ResponseEntity<>()
//        }

        return null;
    }
}
