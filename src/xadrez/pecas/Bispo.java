package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrezz;

public class Bispo extends PecaXadrezz {


    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "B";
    }
    @Override
    public boolean[][] possiveisMovimentos() {
        boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // movimento para noroeste
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.recebendoValores(p.getLinha() - 1, p.getColuna() - 1 );
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para nordeste
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.recebendoValores(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para a sudoeste
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.recebendoValores(p.getLinha() + 1, p.getColuna() -1 );
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para sudeste
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.recebendoValores(p.getLinha() + 1, p.getColuna() + 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
