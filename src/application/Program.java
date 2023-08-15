package application;

import xadrez.ExcecaoXadrez;
import xadrez.JogoXadrez;
import xadrez.PecaXadrezz;
import xadrez.PosicaoXadrez;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        JogoXadrez jogoXadrez = new JogoXadrez();
        List<PecaXadrezz> capturada = new ArrayList<>();

        while (!jogoXadrez.getXequeMate()){
            try {
                UI.clearScreen();
                UI.imprimirJogo(jogoXadrez, capturada);
                System.out.println();
                System.out.print("Posição de Origem: ");
                PosicaoXadrez origem = UI.escrevaPosicoXadrez(sc);

                boolean[][] possiveisPosicoes = jogoXadrez.possiveisMovimentos(origem);
                UI.clearScreen();
                UI.imprimaTabuleiro(jogoXadrez.getPecas(), possiveisPosicoes);
                
                System.out.println();
                System.out.print("Posição de Destino da Peça: ");
                PosicaoXadrez destino = UI.escrevaPosicoXadrez(sc);

                PecaXadrezz capturaPeca = jogoXadrez.executarPosicaoXadrez(origem, destino);

                if (capturaPeca != null){
                    capturada.add(capturaPeca);
                }

            } catch (ExcecaoXadrez e){
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.imprimirJogo(jogoXadrez, capturada);
    }
}