package com.grupotres.projetocrud.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;

import com.grupotres.projetocrud.controller.UsuarioController;
import com.grupotres.projetocrud.model.User;

public class TelaInicial extends Application {

    public static ApplicationContext springContext;

    private UsuarioController controller;

    @Override
    public void start(Stage stage) {

        controller = springContext.getBean(UsuarioController.class);

        // aba selecionada: "login" ou "cadastro"
        final String[] abaAtiva = {"login"};

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f5f5f0;");
        root.setPrefWidth(360);

        // =========================
        // CABEÇALHO
        // =========================
        VBox header = new VBox(4);
        header.setPadding(new Insets(24, 24, 16, 24));
        header.setStyle("-fx-background-color: white; -fx-border-color: #e0e0d8; -fx-border-width: 0 0 1 0;");

        Label titulo = new Label("Sistema Morhar");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1a1a18;");

        Label subtitulo = new Label("Faça login para continuar");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #888880;");

        header.getChildren().addAll(titulo, subtitulo);

        // =========================
        // ABAS
        // =========================
        HBox abas = new HBox(6);
        abas.setPadding(new Insets(16, 24, 0, 24));

        Button btnAbaLogin    = new Button("Login");
        Button btnAbaCadastro = new Button("Cadastrar");

        String estiloAbaAtiva   = "-fx-background-color: #2c2c2a; -fx-text-fill: #f1efe8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-width: 130;";
        String estiloAbaInativa = "-fx-background-color: #e8e8e0; -fx-text-fill: #666660; -fx-font-size: 13px; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-width: 130;";

        btnAbaLogin.setStyle(estiloAbaAtiva);
        btnAbaCadastro.setStyle(estiloAbaInativa);

        abas.getChildren().addAll(btnAbaLogin, btnAbaCadastro);

        // =========================
        // FORMULÁRIO LOGIN
        // =========================
        VBox formLogin = new VBox(10);
        formLogin.setPadding(new Insets(20, 24, 0, 24));

        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        emailField.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Label lblSenha = new Label("Senha");
        lblSenha.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("••••••••");
        senhaField.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setStyle("-fx-background-color: #2c2c2a; -fx-text-fill: #f1efe8; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-height: 38;");

        formLogin.getChildren().addAll(lblEmail, emailField, lblSenha, senhaField, btnEntrar);

        // =========================
        // FORMULÁRIO CADASTRO
        // =========================
        VBox formCadastro = new VBox(10);
        formCadastro.setPadding(new Insets(20, 24, 0, 24));
        formCadastro.setVisible(false);
        formCadastro.setManaged(false);

        Label lblNome = new Label("Nome");
        lblNome.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome completo");
        nomeField.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Label lblEmailC = new Label("Email");
        lblEmailC.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        TextField emailCadastro = new TextField();
        emailCadastro.setPromptText("seu@email.com");
        emailCadastro.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Label lblSenhaC = new Label("Senha");
        lblSenhaC.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        PasswordField senhaCadastro = new PasswordField();
        senhaCadastro.setPromptText("••••••••");
        senhaCadastro.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Label lblPerfil = new Label("Perfil");
        lblPerfil.setStyle("-fx-font-size: 12px; -fx-text-fill: #666660;");
        ComboBox<String> perfilCombo = new ComboBox<>();
        perfilCombo.getItems().addAll(
                "ADMIN MORHAR",
                "CLIENTE",
                "TRABALHADOR INTERNO MORHAR",
                "EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)"
        );
        perfilCombo.setPromptText("Selecione o perfil");
        perfilCombo.setMaxWidth(Double.MAX_VALUE);
        perfilCombo.setStyle("-fx-background-color: #f0f0e8; -fx-border-color: #d0d0c8; -fx-background-radius: 6; -fx-pref-height: 36; -fx-font-size: 14px;");

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);
        btnCadastrar.setStyle("-fx-background-color: #2c2c2a; -fx-text-fill: #f1efe8; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-pref-height: 38;");

        formCadastro.getChildren().addAll(lblNome, nomeField, lblEmailC, emailCadastro, lblSenhaC, senhaCadastro, lblPerfil, perfilCombo, btnCadastrar);

        // =========================
        // MENSAGEM + ENCERRAR
        // =========================
        VBox rodape = new VBox(10);
        rodape.setPadding(new Insets(16, 24, 24, 24));

        Label mensagem = new Label();
        mensagem.setWrapText(true);
        mensagem.setVisible(false);
        mensagem.setManaged(false);
        mensagem.setStyle("-fx-font-size: 13px; -fx-padding: 8 12; -fx-background-radius: 6;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e0e0d8;");

        Button btnSair = new Button("Encerrar programa");
        btnSair.setMaxWidth(Double.MAX_VALUE);
        btnSair.setStyle("-fx-background-color: transparent; -fx-border-color: #d0d0c8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-text-fill: #666660; -fx-font-size: 13px; -fx-cursor: hand; -fx-pref-height: 36;");

        rodape.getChildren().addAll(mensagem, sep, btnSair);

        // =========================
        // MONTAR TELA
        // =========================
        root.getChildren().addAll(header, abas, formLogin, formCadastro, rodape);

        // =========================
        // LÓGICA DAS ABAS
        // =========================
        btnAbaLogin.setOnAction(e -> {
            abaAtiva[0] = "login";
            btnAbaLogin.setStyle(estiloAbaAtiva);
            btnAbaCadastro.setStyle(estiloAbaInativa);
            formLogin.setVisible(true);   formLogin.setManaged(true);
            formCadastro.setVisible(false); formCadastro.setManaged(false);
            mensagem.setVisible(false);   mensagem.setManaged(false);
            subtitulo.setText("Faça login para continuar");
            stage.sizeToScene();
        });

        btnAbaCadastro.setOnAction(e -> {
            abaAtiva[0] = "cadastro";
            btnAbaCadastro.setStyle(estiloAbaAtiva);
            btnAbaLogin.setStyle(estiloAbaInativa);
            formCadastro.setVisible(true);  formCadastro.setManaged(true);
            formLogin.setVisible(false);    formLogin.setManaged(false);
            mensagem.setVisible(false);     mensagem.setManaged(false);
            subtitulo.setText("Crie sua conta");
            stage.sizeToScene();
        });

        // =========================
        // AÇÃO ENTRAR
        // =========================
        btnEntrar.setOnAction(e -> {
            String email = emailField.getText().trim();
            String senha = senhaField.getText();

            if (email.isEmpty() || senha.isEmpty()) {
                mostrarMensagem(mensagem, "Preencha todos os campos!", false);
                stage.sizeToScene();
                return;
            }

            var userOpt = controller.realizarLogin(email, senha);

            if (userOpt.isPresent()) {
                mostrarMensagem(mensagem, "Login realizado com sucesso!", true);
                stage.sizeToScene();

                TelaUsuario tela = new TelaUsuario(controller, userOpt.get());
                tela.abrir();
                stage.close();
            } else {
                mostrarMensagem(mensagem, "Email ou senha inválidos!", false);
                stage.sizeToScene();
            }
        });

        // =========================
        // AÇÃO CADASTRAR
        // =========================
        btnCadastrar.setOnAction(e -> {
            String nome   = nomeField.getText().trim();
            String email  = emailCadastro.getText().trim();
            String senha  = senhaCadastro.getText();
            String perfil = perfilCombo.getValue();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                mostrarMensagem(mensagem, "Preencha todos os campos!", false);
                stage.sizeToScene();
                return;
            }
            if (perfil == null) {
                mostrarMensagem(mensagem, "Selecione um perfil!", false);
                stage.sizeToScene();
                return;
            }

            try {
                controller.cadastrarUsuario(nome, email, senha, perfil);
                mostrarMensagem(mensagem, "Usuário cadastrado com sucesso!", true);
                nomeField.clear();
                emailCadastro.clear();
                senhaCadastro.clear();
                perfilCombo.setValue(null);
            } catch (Exception ex) {
                mostrarMensagem(mensagem, ex.getMessage(), false);
            }
            stage.sizeToScene();
        });

        // =========================
        // ENCERRAR
        // =========================
        btnSair.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        Scene scene = new Scene(root);
        stage.setTitle("Sistema Morhar");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}
