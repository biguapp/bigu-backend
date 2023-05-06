package com.api.bigu.controllers;

import com.api.bigu.dto.UsuarioDTO;
import com.api.bigu.model.Usuario;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Usuario> getAllUsers() {
        return userService.buscarTodos();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Usuario> buscarPorMatricula(@PathVariable Long matricula) {
        Optional<Usuario> usuario = userService.buscarPorMatricula(matricula);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = userService.criarUsuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getMatricula())).body(usuario);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> excluirPorMatricula(@PathVariable Long matricula) {
        userService.excluirPorMatricula(matricula);
        return ResponseEntity.noContent().build();
    }
}
