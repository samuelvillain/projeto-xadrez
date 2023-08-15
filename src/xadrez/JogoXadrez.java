package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JogoXadrez {

    private int vez;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean checar;
    private boolean xequeMate;
    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();


    public JogoXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        vez = 1;
        jogadorAtual = Cor.WHITE;
        inicioPartida();
    }
    public int getVez() {
        return vez;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getChecar() {
        return checar;
    }

    public boolean getXequeMate() {
        return xequeMate;
    }

    public PecaXadrezz[][] getPecas() {
        PecaXadrezz[][] mat = new PecaXadrezz[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (PecaXadrezz) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possiveisMovimentos (PosicaoXadrez posicaoOrigem) {
        Posicao posicao = posicaoOrigem.toposicao();
        validandoOrigemPosition(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public PecaXadrezz executarPosicaoXadrez (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.toposicao();
        Posicao destino = posicaoDestino.toposicao();
        validandoOrigemPosition(origem);
        validandoDestinoPosicao(origem, destino);
        Peca capturaPeca = fazerMover(origem, destino);

        if (testeParaChecar(jogadorAtual)){
            desfazerMovimento(origem, destino, capturaPeca);
            throw new ExcecaoXadrez("Você não pode se colocar em xeque.");
        }

        checar = (testeParaChecar(oponente(jogadorAtual))) ? true : false;

        if (testeXequeMate(oponente(jogadorAtual))){
            xequeMate = true;
        } else {
            proximaVez();
        }
        return (PecaXadrezz) capturaPeca;
    }

    private Peca fazerMover(Posicao origem, Posicao destino){
        PecaXadrezz p = (PecaXadrezz) tabuleiro.removePeca(origem);
        p.aumentarContagemMovimentos();
        Peca capturaPeca = tabuleiro.removePeca(destino);
        tabuleiro.recebendoPeca(p, destino);

        if (capturaPeca != null){
            pecasNoTabuleiro.remove(capturaPeca);
            pecasCapturadas.add(capturaPeca);
        }
        return capturaPeca;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        PecaXadrezz p = (PecaXadrezz)tabuleiro.removePeca(destino);
        p.diminuirContagemMovimentos();
        tabuleiro.recebendoPeca(p, origem);

        if (pecaCapturada != null){
            tabuleiro.recebendoPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    private void validandoOrigemPosition (Posicao posicao){
        if (!tabuleiro.existePeca(posicao)){
            throw new ExcecaoXadrez("Não Existe peça na posição de origem.");
        }
        if (jogadorAtual != ((PecaXadrezz) tabuleiro.peca(posicao)).getCor()){
            throw new ExcecaoXadrez("A peça escolhida não é a sua.");
        }
        if (!tabuleiro.peca(posicao).existeAlgumMovimentoPosssivel()){
            throw new ExcecaoXadrez("Não existe movimentos possiveis para peça escolhida");
        }
    }

    private void validandoDestinoPosicao (Posicao origem, Posicao destino){
        if (!tabuleiro.peca(origem).possivelPosicao(destino)){
            throw new ExcecaoXadrez("A peça escolhida não pode se mover para posição de destino.");
        }
    }

    private void proximaVez (){
        vez++;
        jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;

    }
    private Cor oponente(Cor cor){
        return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
    }

    private PecaXadrezz rei(Cor cor){
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrezz)x).getCor() == cor ).collect(Collectors.toList());
        for (Peca p : list){
            if (p instanceof Rei){
                return (PecaXadrezz)p;
            }
        }
        throw new IllegalStateException("Não existe o rei " + cor + "no tabuleiro.");
    }

    private boolean testeParaChecar (Cor cor){
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().toposicao();
        List<Peca> pecasOponenete = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrezz)x).getCor() == oponente(cor) ).collect(Collectors.toList());
        for (Peca p : pecasOponenete){
            boolean[][] mat = p.possiveisMovimentos();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;
    }

    private boolean testeXequeMate (Cor cor){
        if (!testeParaChecar(cor)){
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrezz)x).getCor() == cor ).collect(Collectors.toList());
        for (Peca p: list){
            boolean[][] mat = p.possiveisMovimentos();
            for (int i = 0; i < tabuleiro.getLinhas(); i++){
                for (int j = 0; j < tabuleiro.getColunas(); j++){
                    if (mat[i][j]){
                        Posicao origem = ((PecaXadrezz)p).getPosicaoXadrez().toposicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMover(origem, destino);
                        boolean testeXeque = testeParaChecar(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if (!testeXeque ){
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }


    private void novaPosicaoPeca(char coluna, int linha, Torre peca) {
        tabuleiro.recebendoPeca(peca, new PosicaoXadrez(coluna, linha).toposicao());
        pecasNoTabuleiro.add(peca);
    }

    private void inicioPartida() {

    }




}
