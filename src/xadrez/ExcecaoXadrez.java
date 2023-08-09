package xadrez;

public class ExcecaoXadrez extends RuntimeException {
    private static final long serialversionUID = 1L;

    public ExcecaoXadrez (String msg){
        super(msg);
    }
}
