package com.grupotres.projetocrud.util;

import java.util.Scanner;

public class PerfilUtil {

    public static String escolherPerfil() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Escolha o perfil:");
            System.out.println("1 - ADMIN MORHAR");
            System.out.println("2 - CLIENTE");
            System.out.println("3 - TRABALHADOR INTERNO MORHAR");
            System.out.println("4 - EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)");

            String input = scanner.nextLine();

            try {
                int opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        return "ADMIN MORHAR";
                    case 2:
                        return "CLIENTE";
                    case 3:
                        return "TRABALHADOR INTERNO MORHAR";
                    case 4:
                        return "EMPRESA TERCERIZADA (PRESTADOR DE SERVIÇO)";
                    default:
                        System.out.println("❌ Escreva um valor válido de perfil!");
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Digite apenas números!");
            }
        }
    }
}