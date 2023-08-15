package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrezz;

public class Torre extends PecaXadrezz {


    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "T";
    }
    @Override
    public boolean[][] possiveisMovimentos() {
        boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // movimento para cima
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para esquerda
        p.recebendoValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para a direita
        p.recebendoValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento para baixo
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().recebendoUmaPosicao(p) && !getTabuleiro().existePeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().recebendoUmaPosicao(p) && possivelPosicao(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        return mat;
    }
}
