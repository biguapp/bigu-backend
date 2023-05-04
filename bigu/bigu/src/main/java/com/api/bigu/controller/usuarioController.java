package com.api.bigu.controller;

import com.api.bigu.dto.UsuarioDTO;
import com.api.bigu.model.Usuario;
import com.api.bigu.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class usuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> buscarTodos() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Usuario> buscarPorMatricula(@PathVariable Long matricula) {
        Optional<Usuario> usuario = usuarioService.buscarPorMatricula(matricula);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getMatricula())).body(usuario);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> excluirPorMatricula(@PathVariable Long matricula) {
        usuarioService.excluirPorMatricula(matricula);
        return ResponseEntity.noContent().build();
    }

}
