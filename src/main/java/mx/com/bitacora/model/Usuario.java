package mx.com.bitacora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BT_USUARIOS")
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idUsuario;

    @Column
    @NotBlank(message = "Please add nombre")
    private String nombre;

    @Column
    @NotBlank(message = "Please add apellidoPaterno")
    private String apellidoPaterno;

    @Column
    private String apellidoMaterno;

    @Column(unique = true)
    private String email;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @Column
    private Integer idRol;
}
