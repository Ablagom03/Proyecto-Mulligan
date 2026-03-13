@Entity
@Table(name = "usuario")
public class Usuario {
   @Id
   @Column(name = "usrId")
   @JsonProperty("usrId")
    private Integer UsrId;

    @Column(name = "nombre_usr")
    @JsonProperty("nombre_usr")
    private String nombreUsr;
    private String email;
    private String passwd;
}