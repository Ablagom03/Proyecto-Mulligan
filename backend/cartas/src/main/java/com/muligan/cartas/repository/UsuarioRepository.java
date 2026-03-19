package com.muligan.cartas.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.muligan.cartas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    Usuario findByUsrId(long usrId);

    
}