package com.grupotres.projetocrud.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")
public class User {

    // @Id vai identificar chave primária p usuário
    @Id 
    //@GeneratedValue vai gerar o valor automaticamente
    // Vai ser do tipo autoincremento 
    @GeneratedValue(strategy = GenerationType.IDENTITY)

// DECLARAÇÃO DE VARIÁVEIS
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String perfil;
    private boolean equipeMorhar;
    private boolean podeEditar;    
    private String status;
    private String dataCadastro;


// CRIAÇÃO DO CONSTRUTOR
// Ele vai inicializar o usuário
// Exemplo "User user = new User();" vai automaticamente chamar ele
    public User() {
    }

    public User(String nome, String email, String senha, String perfil) {

// Por padrão, THIS vai setar o nome passado
//  como sendo o nome a ser utilizado em determinada classe
        this.nome = nome;
        this.email = email;
        this.senha = senha;
// Data criada automaticamente:
        this.dataCadastro = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
// Status de um usuário novo sempre vai ser ativo
        this.status = "Ativo";
        this.perfil = perfil;
        aplicarRegras(perfil);
    }


// GETTER --> Pega um valor
// SETTER --> Altera um valor

//Vamos usar setter nos casos em que o valor pode ser alterado

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getStatus() {
        return status;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public boolean isEquipeMorhar() {
        return equipeMorhar;
    }

    public boolean isPodeEditar() {
        return podeEditar;
    }

// Regras de Negócio, definindo PERFIL, se pode editar e se é Morhar

    public void definirPerfil(String perfil) {

        this.perfil = perfil;

        aplicarRegras(perfil);
    }

    private void aplicarRegras(String perfil) {

        if (perfil.equalsIgnoreCase("CLIENTE") ||
            perfil.equalsIgnoreCase("EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)")) {

            this.equipeMorhar = false;

        } else {
            this.equipeMorhar = true;
        }

        if (perfil.equalsIgnoreCase("ADMIN MORHAR")) {
            this.podeEditar = true;
        } else {
            this.podeEditar = false;
        }
    }

// Métodos adicionais para mudar status
    public void ativarUsuario() {
        this.status = "Ativo";
    }

    public void desativarUsuario() {
        this.status = "Inativo";
    }

// Método para imprmir info do usuário
    public String exibirInformacoes() {

        String pertenceaMorhar = this.equipeMorhar
            ? "Sim"
            : "Não";

        String podeEditarTexto = this.podeEditar
            ? "SIM (ADMIN MORHAR)"
            : "NÃO";

        String informacoes = """
            ===== DADOS DO USUÁRIO =====

            ID: %d
            Nome: %s
            Email: %s
            Perfil: %s
            Status: %s
            Pertence à Equipe Morhar: %s
            Pode editar usuário: %s
            Data de Cadastro: %s
            """
            .formatted(
                id,
                nome,
                email,
                perfil,
                status,
                pertenceaMorhar,
                podeEditarTexto,
                dataCadastro
            );

 //       System.out.println("\n===== DADOS DO USUÁRIO =====");
 //       System.out.println("ID: " + id);
//        System.out.println("Nome: " + nome);
//        System.out.println("Email: " + email);
 //       System.out.println("Perfil: " + perfil);
 //       System.out.println("Status: " + status);

// A edição e pertencimento a equipe Morhar é do tipo BOOLEAN, varia
//        if (equipeMorhar) {
 //           System.out.println("Pertence à Equipe Morhar: Sim");
 //       } else {
 //           System.out.println("Pertence à Equipe Morhar: Não");
 //       }

  //      if (podeEditar) {
 //           System.out.println("Pode editar usuário: SIM ( ADMIN MORHAR )");
  //      } else {
 //           System.out.println("Pode editar usuário: NÃO");
 //       }
 //       System.out.println("Data de Cadastro: " + dataCadastro);        
//
  //      System.out.println("============================\n");
        return informacoes;
    }

}