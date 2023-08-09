package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class JogoXadrez {

    private Tabuleiro tabuleiro;

    public JogoXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        inicioPartida();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    public PecaXadrez executarPosicaoXadrez (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.toposicao();
        Posicao destino = posicaoDestino.toposicao();
        validandoOrigemPosition(origem);
        Peca capturaPeca = fazerMover(origem, destino);
        return (PecaXadrez) capturaPeca;
    }

    private Peca fazerMover(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removePeca(origem);
        Peca capturaPeca = tabuleiro.removePeca(destino);
        tabuleiro.recebendoPeca(p, destino);
        return capturaPeca;
    }

    private void validandoOrigemPosition (Posicao posicao){
        if (!tabuleiro.existePeca(posicao)){
            throw new ExcecaoXadrez("Não Existe peça na posição de origem.");
        }

    }

    private void novaPosicaoPeca(char coluna, int linha, Torre peca) {
        tabuleiro.recebendoPeca(peca, new PosicaoXadrez(coluna, linha).toposicao());
    }

    private void inicioPartida() {
        novaPosicaoPeca('c', 1, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('c', 2, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('d', 2, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('e', 2, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('e', 1, new Torre(tabuleiro, Cor.WHITE));

        novaPosicaoPeca('c', 7, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('c', 8, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('d', 7, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('e', 7, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('e', 8, new Torre(tabuleiro, Cor.BLACK));

    }




}
