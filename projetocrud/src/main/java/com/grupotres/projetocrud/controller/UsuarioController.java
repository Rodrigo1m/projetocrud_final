package com.grupotres.projetocrud.controller;

import com.grupotres.projetocrud.model.User;
import com.grupotres.projetocrud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // LOGIN
    public Optional<User> realizarLogin(String email, String senha) {
        return usuarioService.login(email, senha);
    }

    // CADASTRAR
    public User cadastrarUsuario(String nome, String email, String senha, String perfil) {
        return usuarioService.cadastrar(nome, email, senha, perfil);
    }

    // LISTAR
    public List<User> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    // EDITAR — passa a nova senha em texto puro, o service faz o hash
    public void editarUsuario(User user, String novaSenhaTexto) {
        usuarioService.editar(user, novaSenhaTexto);
    }

    // EXCLUIR
    public void excluirUsuario(Long id) {
        usuarioService.excluir(id);
    }
}
