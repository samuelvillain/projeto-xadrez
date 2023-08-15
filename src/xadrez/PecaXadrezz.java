package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrezz extends Peca {

    private Cor cor;
    private int contagemMovimentos;

    public PecaXadrezz(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContagemMovimentos() {
        return contagemMovimentos;
    }

    public void aumentarContagemMovimentos () {
        contagemMovimentos++;
    }

    public void diminuirContagemMovimentos () {
        contagemMovimentos--;
    }


    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.dePosicao(posicao);

    }

    protected boolean recebendoUmaPosicao (Posicao posicao){
        PecaXadrezz p = (PecaXadrezz)getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }

}
