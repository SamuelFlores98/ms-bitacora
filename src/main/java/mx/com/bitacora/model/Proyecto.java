package mx.com.bitacora.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BT_PROYECTOS")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idProyecto;

    @Column
    private String nombreProyecto;

    @Column
    private String claveProyecto;
}
