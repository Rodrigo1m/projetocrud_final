package com.grupotres.projetocrud.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.grupotres.projetocrud.controller.UsuarioController;
import com.grupotres.projetocrud.model.User;

import java.util.List;
import java.util.Optional;

public class TelaUsuario {

    private final UsuarioController controller;
    private final User usuarioLogado;

    // cor do badge por perfil
    private String corBadge(String perfil) {
        if (perfil == null) return "#e8e8e0|#444440";
        return switch (perfil.toUpperCase()) {
            case "ADMIN MORHAR"                             -> "#eeedfe|#3c3489";
            case "CLIENTE"                                  -> "#e1f5ee|#0f6e56";
            case "TRABALHADOR INTERNO MORHAR"               -> "#e6f1fb|#185fa5";
            case "EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)" -> "#faeeda|#854f0b";
            default                                         -> "#e8e8e0|#444440";
        };
    }

    public TelaUsuario(UsuarioController controller, User usuarioLogado) {
        this.controller    = controller;
        this.usuarioLogado = usuarioLogado;
    }

    public void abrir() {

        Stage stage = new Stage();

        // =========================
        // ROOT
        // =========================
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f5f5f0;");
        root.setPrefWidth(440);

        // =========================
        // CABEÇALHO
        // =========================
        HBox header = new HBox();
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setSpacing(12);
        header.setStyle("-fx-background-color: white; -fx-border-color: #e0e0d8; -fx-border-width: 0 0 1 0;");

        // avatar com iniciais
        String iniciais = iniciais(usuarioLogado.getNome());
        Label avatar = new Label(iniciais);
        avatar.setMinSize(40, 40);
        avatar.setMaxSize(40, 40);
        avatar.setAlignment(javafx.geometry.Pos.CENTER);
        avatar.setStyle("-fx-background-color: #eeedfe; -fx-text-fill: #3c3489; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20;");

        VBox info = new VBox(2);
        Label nomeLabel = new Label(usuarioLogado.getNome());
        nomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1a1a18;");

        String[] cores = corBadge(usuarioLogado.getPerfil()).split("\\|");
        Label perfilLabel = new Label(usuarioLogado.getPerfil());
        perfilLabel.setStyle("-fx-background-color: " + cores[0] + "; -fx-text-fill: " + cores[1] + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 999; -fx-padding: 2 8;");

        info.getChildren().addAll(nomeLabel, perfilLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: transparent; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-text-fill: #666660; -fx-font-size: 12px; -fx-cursor: hand; -fx-pref-height: 30;");

        header.getChildren().addAll(avatar, info, spacer, btnLogout);

        // =========================
        // BOTÃO LISTAR
        // =========================
        HBox barraAcoes = new HBox(8);
        barraAcoes.setPadding(new Insets(16, 20, 8, 20));

        Button btnListar = new Button("Listar usuários");
        btnListar.setStyle("-fx-background-color: #2c2c2a; -fx-text-fill: #f1efe8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-height: 34;");

        barraAcoes.getChildren().add(btnListar);

        // =========================
        // MENSAGEM
        // =========================
        Label mensagem = new Label();
        mensagem.setWrapText(true);
        mensagem.setVisible(false);
        mensagem.setManaged(false);
        mensagem.setPadding(new Insets(0, 20, 0, 20));

        // =========================
        // LISTA DE USUÁRIOS (scroll)
        // =========================
        VBox listaContainer = new VBox(8);
        listaContainer.setPadding(new Insets(8, 20, 20, 20));

        ScrollPane scroll = new ScrollPane(listaContainer);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(380);
        scroll.setStyle("-fx-background: #f5f5f0; -fx-background-color: #f5f5f0; -fx-border-color: transparent;");

        // =========================
        // AÇÃO LISTAR
        // =========================
        btnListar.setOnAction(e -> {
            mensagem.setVisible(false);
            mensagem.setManaged(false);
            listaContainer.getChildren().clear();

            List<User> usuarios = controller.listarUsuarios();

            if (usuarios.isEmpty()) {
                Label vazio = new Label("Nenhum usuário cadastrado.");
                vazio.setStyle("-fx-font-size: 13px; -fx-text-fill: #888880;");
                listaContainer.getChildren().add(vazio);
                return;
            }

            for (User u : usuarios) {
                listaContainer.getChildren().add(
                    criarCartaoUsuario(u, stage, mensagem, listaContainer, btnListar)
                );
            }
        });

        // =========================
        // LOGOUT
        // =========================
        btnLogout.setOnAction(e -> {
            stage.close();
            TelaInicial telaInicial = new TelaInicial();
            telaInicial.start(new Stage());
        });

        // =========================
        // MONTAR
        // =========================
        root.getChildren().addAll(header, barraAcoes, mensagem, scroll);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema Morhar");
        stage.setResizable(false);
        stage.show();
    }

    // =========================
    // CARTÃO DE USUÁRIO
    // =========================
    private VBox criarCartaoUsuario(User u, Stage owner, Label mensagemPrincipal,
                                    VBox listaContainer, Button btnListar) {

        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0d8; -fx-border-radius: 8; -fx-background-radius: 8;");

        // linha nome + badge
        HBox linhaTop = new HBox(8);
        linhaTop.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label nomeCard = new Label(u.getNome());
        nomeCard.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1a1a18;");

        String[] coresBadge = corBadge(u.getPerfil()).split("\\|");
        Label badgePerfil = new Label(u.getPerfil());
        badgePerfil.setStyle("-fx-background-color: " + coresBadge[0] + "; -fx-text-fill: " + coresBadge[1] + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 999; -fx-padding: 2 7;");

        linhaTop.getChildren().addAll(nomeCard, badgePerfil);

        // linha email + status
        Label metaCard = new Label(u.getEmail() + "  ·  " + u.getStatus() + "  ·  " + u.getDataCadastro());
        metaCard.setStyle("-fx-font-size: 12px; -fx-text-fill: #888880;");

        card.getChildren().addAll(linhaTop, metaCard);

        // botões admin
        if (usuarioLogado.isPodeEditar()) {
            HBox acoes = new HBox(6);
            acoes.setPadding(new Insets(4, 0, 0, 0));

            Button btnEditar  = new Button("Editar");
            btnEditar.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-size: 12px; -fx-cursor: hand; -fx-pref-height: 28;");

            Button btnExcluir = new Button("Excluir");
            btnExcluir.setStyle("-fx-background-color: #fcebeb; -fx-text-fill: #a32d2d; -fx-border-color: #f09595; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-size: 12px; -fx-cursor: hand; -fx-pref-height: 28;");

            btnEditar.setOnAction(e -> abrirModalEditar(u, owner, mensagemPrincipal, listaContainer, btnListar));

            btnExcluir.setOnAction(e -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmar exclusão");
                confirm.setHeaderText("Excluir " + u.getNome() + "?");
                confirm.setContentText("Essa ação não pode ser desfeita.");

                Optional<ButtonType> resultado = confirm.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    controller.excluirUsuario(u.getId());
                    mostrarMensagem(mensagemPrincipal, "Usuário excluído com sucesso!", true);
                    // recarrega a lista
                    btnListar.fire();
                }
            });

            acoes.getChildren().addAll(btnEditar, btnExcluir);
            card.getChildren().add(acoes);
        }

        return card;
    }

    // =========================
    // MODAL EDITAR
    // =========================
    private void abrirModalEditar(User u, Stage owner, Label mensagemPrincipal,
                                   VBox listaContainer, Button btnListar) {

        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Editar usuário");
        dialog.setResizable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");
        layout.setPrefWidth(320);

        Label titulo = new Label("Editar usuário");
        titulo.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a18;");

        String estiloInput = "-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 13px;";

        Label lblNome = new Label("Nome");
        lblNome.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        TextField campoNome = new TextField(u.getNome());
        campoNome.setStyle(estiloInput);

        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        TextField campoEmail = new TextField(u.getEmail());
        campoEmail.setStyle(estiloInput);

        Label lblSenha = new Label("Senha");
        lblSenha.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Nova senha");
        campoSenha.setStyle(estiloInput);

        Label lblPerfil = new Label("Perfil");
        lblPerfil.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        ComboBox<String> comboPerfil = new ComboBox<>();
        comboPerfil.getItems().addAll(
                "ADMIN MORHAR",
                "CLIENTE",
                "TRABALHADOR INTERNO MORHAR",
                "EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)"
        );
        comboPerfil.setValue(u.getPerfil());
        comboPerfil.setMaxWidth(Double.MAX_VALUE);
        comboPerfil.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 13px;");

        Label mensagemDialog = new Label();
        mensagemDialog.setVisible(false);
        mensagemDialog.setManaged(false);
        mensagemDialog.setWrapText(true);

        HBox botoes = new HBox(8);
        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnSalvar.setStyle("-fx-background-color: #2c2c2a; -fx-text-fill: #f1efe8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-height: 36;");
        HBox.setHgrow(btnSalvar, Priority.ALWAYS);

        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-text-fill: #666660; -fx-font-size: 13px; -fx-cursor: hand; -fx-pref-height: 36;");
        HBox.setHgrow(btnCancelar, Priority.ALWAYS);

        botoes.getChildren().addAll(btnSalvar, btnCancelar);

        btnSalvar.setOnAction(e -> {
            String novoNome   = campoNome.getText().trim();
            String novoEmail  = campoEmail.getText().trim();
            String novaSenha  = campoSenha.getText();
            String novoPerfil = comboPerfil.getValue();

            if (novoNome.isEmpty() || novoEmail.isEmpty() || novaSenha.isEmpty()) {
                mostrarMensagemDialog(mensagemDialog, "Preencha todos os campos.", false);
                dialog.sizeToScene();
                return;
            }
            if (novoPerfil == null) {
                mostrarMensagemDialog(mensagemDialog, "Selecione um perfil.", false);
                dialog.sizeToScene();
                return;
            }

            u.setNome(novoNome);
            u.setEmail(novoEmail);
            u.definirPerfil(novoPerfil);
            // passa a senha em texto puro — o service faz o hash com BCrypt
            controller.editarUsuario(u, novaSenha);

            mostrarMensagem(mensagemPrincipal, "Usuário atualizado com sucesso!", true);
            btnListar.fire();
            dialog.close();
        });

        btnCancelar.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(
                titulo,
                lblNome, campoNome,
                lblEmail, campoEmail,
                lblSenha, campoSenha,
                lblPerfil, comboPerfil,
                mensagemDialog,
                botoes
        );

        Scene scene = new Scene(layout);
        dialog.setScene(scene);
        dialog.show();
    }

    // =========================
    // HELPERS
    // =========================
    private String iniciais(String nome) {
        if (nome == null || nome.isBlank()) return "?";
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 1) return partes[0].substring(0, Math.min(2, partes[0].length())).toUpperCase();
        return (partes[0].charAt(0) + "" + partes[partes.length - 1].charAt(0)).toUpperCase();
    }

    private void mostrarMensagem(Label label, String texto, boolean sucesso) {
        label.setText(texto);
        label.setVisible(true);
        label.setManaged(true);
        if (sucesso) {
            label.setStyle("-fx-font-size: 13px; -fx-padding: 8 12; -fx-background-radius: 6; -fx-background-color: #eaf3de; -fx-text-fill: #3b6d11; -fx-border-color: #c0dd97; -fx-border-radius: 6;");
        } else {
            label.setStyle("-fx-font-size: 13px; -fx-padding: 8 12; -fx-background-radius: 6; -fx-background-color: #fcebeb; -fx-text-fill: #a32d2d; -fx-border-color: #f09595; -fx-border-radius: 6;");
        }
    }

    private void mostrarMensagemDialog(Label label, String texto, boolean sucesso) {
        mostrarMensagem(label, texto, sucesso);
    }
}
