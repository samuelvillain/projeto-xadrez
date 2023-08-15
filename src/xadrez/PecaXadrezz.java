package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrezz extends Peca {

    private Cor cor;

    public PecaXadrezz(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.dePosicao(posicao);

    }

    protected boolean recebendoUmaPosicao (Posicao posicao){
        PecaXadrezz p = (PecaXadrezz)getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }

}
