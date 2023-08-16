package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JogoXadrez {

    private int vez;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean checar;
    private boolean xequeMate;
    private PecaXadrezz  dePAssagemVuneravel;
    private PecaXadrezz promocao;
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

    public PecaXadrezz getDePAssagemVuneravel() {
        return dePAssagemVuneravel;
    }

    public PecaXadrezz getPromocao() {
        return promocao;
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

        PecaXadrezz pecaMove = (PecaXadrezz)tabuleiro.peca(destino);

        // movimento especial promoção
        promocao = null;
        if (pecaMove instanceof Peao){
            if ((pecaMove.getCor() == Cor.WHITE && destino.getLinha() == 0) || (pecaMove.getCor() == Cor.BLACK && destino.getLinha() == 7)){
                promocao = (PecaXadrezz)tabuleiro.peca(destino);
                promocao = subestituirPecaPromovida("RA");
            }
        }

        checar = (testeParaChecar(oponente(jogadorAtual))) ? true : false;

        if (testeXequeMate(oponente(jogadorAtual))){
            xequeMate = true;
        } else {
            proximaVez();
        }
        // movimento especial peão
        if (pecaMove instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)){
            dePAssagemVuneravel = pecaMove;
        } else{
            dePAssagemVuneravel = null;
        }

        return (PecaXadrezz) capturaPeca;
    }

    public PecaXadrezz subestituirPecaPromovida (String type) {
        if (promocao == null){
            throw new IllegalStateException("Não a peça para ser promovida");
        }
        if (!type.equals("B") && !type.equals("T") && !type.equals("C") && !type.equals("RA")){
            return promocao;
        }
        Posicao pos = promocao.getPosicaoXadrez().toposicao();
        Peca p = tabuleiro.removePeca(pos);
        pecasNoTabuleiro.remove(p);

        PecaXadrezz novaPeca = novaPeca(type, promocao.getCor());
        tabuleiro.recebendoPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private PecaXadrezz novaPeca (String type, Cor cor){
        if (type.equals("B")) return new Bispo(tabuleiro, cor);
        if (type.equals("C")) return new Cavalo(tabuleiro, cor);
        if (type.equals("Ra")) return new Rainha(tabuleiro, cor);
        return new Torre(tabuleiro, cor);
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
        // rock pequeno movimento do rei
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrezz torre = (PecaXadrezz)tabuleiro.removePeca(origemT);
            tabuleiro.recebendoPeca(torre, destinoT);
            torre.aumentarContagemMovimentos();
        }
        // rock grande movimento do rei
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrezz torre = (PecaXadrezz)tabuleiro.removePeca(origemT);
            tabuleiro.recebendoPeca(torre, destinoT);
            torre.aumentarContagemMovimentos();
        }
        // movimento especial do peão
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && capturaPeca == null){
                Posicao posicaoPeao;
                if (p.getCor() == Cor.WHITE){
                    posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else {
                    posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                capturaPeca = tabuleiro.removePeca(posicaoPeao);
                pecasCapturadas.add(capturaPeca);
                pecasNoTabuleiro.remove(capturaPeca);
            }
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
        // rock pequeno movimento do rei
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrezz torre = (PecaXadrezz)tabuleiro.removePeca(destinoT);
            tabuleiro.recebendoPeca(torre, origemT);
            torre.diminuirContagemMovimentos();
        }
        // rock grande movimento do rei
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrezz torre = (PecaXadrezz)tabuleiro.removePeca(destinoT);
            tabuleiro.recebendoPeca(torre, origemT);
            torre.diminuirContagemMovimentos();
        }
        // movimento especial do peão
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && pecaCapturada  == dePAssagemVuneravel){
                PecaXadrezz peao = (PecaXadrezz)tabuleiro.removePeca(destino);
                Posicao posicaoPeao;
                if (p.getCor() == Cor.WHITE){
                    posicaoPeao = new Posicao(3, destino.getColuna());
                } else {
                    posicaoPeao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.recebendoPeca(peao, posicaoPeao);
            }
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


    private void novaPosicaoPeca(char coluna, int linha, Peca peca) {
        tabuleiro.recebendoPeca(peca, new PosicaoXadrez(coluna, linha).toposicao());
        pecasNoTabuleiro.add(peca);
    }

    private void inicioPartida() {
        novaPosicaoPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('d', 1, new Rainha(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
        novaPosicaoPeca('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));


        novaPosicaoPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('d', 8, new Rainha(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
        novaPosicaoPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
        novaPosicaoPeca('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
        novaPosicaoPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));

    }




}
