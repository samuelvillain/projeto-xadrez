package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrezz;

public class Peao extends PecaXadrezz {


    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (getCor() == Cor.WHITE){
            p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p) && getTabuleiro().existePeca(p2) && !getTabuleiro().recebendoUmaPosicao(p2) && getContagemMovimentos() == 0); {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePeca(p) ){
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        return null;
    }
}
