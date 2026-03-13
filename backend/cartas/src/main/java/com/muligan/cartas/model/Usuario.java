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

    public Usuario(){

    }

    public Usuario(Integer usrId, String nombreUsr, String email, String passwd){
        UsrId = usrId;
        this.nombreUsr = nombreUsr;
        this.email = email;
        this.passwd = passwd;
    }

    public Integer UsrId(){
        return this.UsrId;
    }
    
    public void setUsrId(Integer UsrId){
        this.Cardid = Cardid;
    }

    public String getNombreUsr(){
        return this.nombreUsr;
    }

    public void setNombreUsr(String nombreUsr){
        this.nombreUsr = nombreUsr;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPasswd(){
        return this.passwd;
    }

    public String setPasswd(String passwd){
        this.passwd = passwd;
    }
}