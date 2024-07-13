package mx.com.bitacora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BT_ROLES")
public class Rol {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;
    private String nombreRol;
}
