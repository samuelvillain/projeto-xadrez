package application;

import xadrez.Cor;
import xadrez.JogoXadrez;
import xadrez.PecaXadrezz;
import xadrez.PosicaoXadrez;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static PosicaoXadrez escrevaPosicoXadrez(Scanner sc){
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha =  Integer.parseInt(s.substring(1));
            return new PosicaoXadrez(coluna, linha);
        } catch (RuntimeException e){
          throw new InputMismatchException("Erro em ler a posição de xadrez: Insira um valor valido que vai de a1 até h8.");
        }
    }

    public static void imprimirJogo(JogoXadrez jogoXadrez, List<PecaXadrezz> capturada){
        imprimaTabuleiro(jogoXadrez.getPecas());
        System.out.println();
        imprimirCapturaPecas(capturada);
        System.out.println();
        System.out.println("Vez: " + jogoXadrez.getVez());
        System.out.println("Esperando o jogador: " + jogoXadrez.getJogadorAtual());
        if (jogoXadrez.getChecar()){
            System.out.println("VOCÊ ESTÁ EM XEQUE!");
        }

    }

    public static void imprimaTabuleiro(PecaXadrezz[][] pecas) {

        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], false);
            }
            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }

    public static void imprimaTabuleiro(PecaXadrezz[][] pecas, boolean[][] possiveisMovimentos) {

        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]);
            }
            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }

    private static void imprimirPeca(PecaXadrezz PeçaXadrez, boolean background) {
        if (background){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (PeçaXadrez == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (PeçaXadrez.getCor() == Cor.WHITE) {
                System.out.print(ANSI_WHITE + PeçaXadrez + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + PeçaXadrez + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void imprimirCapturaPecas (java.util.List<PecaXadrezz> capturada){
        java.util.List<PecaXadrezz> white = capturada.stream().filter(x -> x.getCor() == Cor.WHITE).collect(Collectors.toList());
        java.util.List<PecaXadrezz> black = capturada.stream().filter(x -> x.getCor() == Cor.BLACK).collect(Collectors.toList());
        System.out.println("Peça capturada:");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);



    }
}
