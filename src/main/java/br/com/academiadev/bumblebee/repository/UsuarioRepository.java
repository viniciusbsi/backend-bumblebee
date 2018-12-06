package br.com.academiadev.bumblebee.repository;

import br.com.academiadev.bumblebee.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    Optional<Usuario> findByResetToken(String resetToken);

    Optional<Usuario> findByConfirmToken(String token);
}
