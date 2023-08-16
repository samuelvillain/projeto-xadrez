package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.JogoXadrez;
import xadrez.PecaXadrezz;

public class Peao extends PecaXadrezz {
    private JogoXadrez jogoXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor, JogoXadrez jogoxadrez) {
        super(tabuleiro, cor);
        this.jogoXadrez = jogoxadrez;
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (getCor() == Cor.WHITE) {
            p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p) && getTabuleiro().existePeca(p2) && !getTabuleiro().recebendoUmaPosicao(p2) && getContagemMovimentos() == 0)
                ;
            {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            // movimento especial do peao branca
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().existePeca(esquerda) && possivelPosicao(esquerda) && getTabuleiro().peca(esquerda) == jogoXadrez.getDePAssagemVuneravel()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().existePeca(direita) && possivelPosicao(direita) && getTabuleiro().peca(direita) == jogoXadrez.getDePAssagemVuneravel()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }

        } else {
            p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePeca(p) && !getTabuleiro().recebendoUmaPosicao(p) && getTabuleiro().existePeca(p2) && !getTabuleiro().recebendoUmaPosicao(p2) && getContagemMovimentos() == 0)
                ;
            {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            // movimento especial do peao preta
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().existePeca(esquerda) && possivelPosicao(esquerda) && getTabuleiro().peca(esquerda) == jogoXadrez.getDePAssagemVuneravel()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().existePeca(direita) && possivelPosicao(direita) && getTabuleiro().peca(direita) == jogoXadrez.getDePAssagemVuneravel()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
