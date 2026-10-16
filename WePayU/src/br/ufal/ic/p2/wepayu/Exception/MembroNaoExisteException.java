package br.ufal.ic.p2.wepayu.Exception;

@SuppressWarnings("serial")
public class MembroNaoExisteException extends Exception{
    public MembroNaoExisteException(){
        super("Membro nao existe.");
    }
}
