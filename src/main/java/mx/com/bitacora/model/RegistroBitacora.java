package mx.com.bitacora.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "REGISTRO_BITACORA")
public class RegistroBitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idRegistro;

    private Integer idUsuario;

    private Integer idProyecto;

    private Integer horas;

    private Date fecha;
}
