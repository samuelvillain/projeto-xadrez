package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.JogoXadrez;
import xadrez.PecaXadrezz;

public class Rei extends PecaXadrezz {
    private JogoXadrez jogoXadrez;


    public Rei(Tabuleiro tabuleiro, Cor cor, JogoXadrez jogoXadrez) {
        super(tabuleiro, cor);
        this.jogoXadrez = jogoXadrez;
    }
    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrezz p = (PecaXadrezz)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    private boolean testeTorreRock (Posicao posicao){
        PecaXadrezz p = (PecaXadrezz)getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimentos() == 0;
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // se movendo para cima
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para baixo
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para esquerda
        p.recebendoValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para direita
        p.recebendoValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para noroeste
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para o nordeste
        p.recebendoValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para o sudoeste
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // se movendo para o sudeste
        p.recebendoValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().recebendoUmaPosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // Movimento especial rock
        if (getContagemMovimentos() == 0 && !jogoXadrez.getChecar()) {
            // movimento especial rock pequeno
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeTorreRock(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            // movimento especial rock grande
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeTorreRock(posT2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
        }
        return mat;
    }
}

