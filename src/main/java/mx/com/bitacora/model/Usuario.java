package mx.com.bitacora.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BT_USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idUsuario;

    @Column
    private String nombre;

    @Column
    private String apellidoPaterno;

    private String apellidoMaterno;

    @Column(unique = true)
    private String email;

    @Column
    @Nullable
    private String createdAt;

    @Column
    @Nullable
    private String updatedAt;

    @Column
    private Integer idRol;
}
