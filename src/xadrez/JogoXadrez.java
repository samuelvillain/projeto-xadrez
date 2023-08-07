package xadrez;

import tabuleiro.Tabuleiro;

public class JogoXadrez {

    private Tabuleiro tabuleiro;

    public JogoXadrez(){
        tabuleiro = new Tabuleiro(8,8);
    }

    public PecaXadrez[][] getPecas(){
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i=0; i<tabuleiro.getLinhas(); i++){
            for (int j=0; j< tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

}
