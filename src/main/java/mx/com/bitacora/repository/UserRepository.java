package mx.com.bitacora.repository;

import mx.com.bitacora.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUserByEmail(String email);
}
