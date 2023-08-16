package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrezz;

public class Cavalo extends PecaXadrezz {
    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }
    @Override
    public String toString() {
        return "C";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrezz p = (PecaXadrezz)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0,0);


        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }



        return mat;
    }
}
