package tabuleiro;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1 ){
            throw new TabuleiroExecao("Erro Criando Tbuleiro: Necessario que haja 1 linha e 1 coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca (int linha, int coluna){
        if (!existepeca(linha, coluna)){
            throw new TabuleiroExecao("Posição não existe.");
        }
        return pecas[linha][coluna];
    }
    public Peca peca (Posicao posicao){
        if (!existePeca(posicao)){
            throw new TabuleiroExecao("Posição não existe.");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void recebendoPeca (Peca peca, Posicao posicao){
        if (existePeca(posicao)){
            throw new TabuleiroExecao("Essa posição ja tem uma peça " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removePeca(Posicao posicao){
        if (!existePeca(posicao)){
            throw new TabuleiroExecao("Posição não existe.");
        }
        if (peca(posicao) == null){
            return null;
        }
        Peca aux = peca(posicao);
        aux.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return aux;
    }

    private boolean existepeca (int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean existePeca (Posicao posicao) {
        return existepeca(posicao.getLinha(), posicao.getColuna());
    }

    public boolean recebendoUmaPosicao (Posicao posicao) {
        if (existePeca(posicao)){
            throw new TabuleiroExecao("Essa posição ja tem uma peça " + posicao);
        }
        return peca(posicao) != null;
    }
}
