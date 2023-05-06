package com.api.bigu.services;

import com.api.bigu.model.Usuario;
import com.api.bigu.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Long matricula, String nome, String email, String senha){
        Usuario usuario = new Usuario();
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setName(nome);
        usuario.setSenha(senha);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorMatricula(Long matricula) {
        return usuarioRepository.findById(matricula);
    }

    public void excluirPorMatricula(Long matricula) {
        usuarioRepository.deleteById(matricula);
    }

}
