package util;

import java.io.Serializable;

/**
 *
 * @author William
 */
public class Mensagem implements Serializable {
    
    private final String operacao;
    private Status status; 
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public Mensagem(String operacao){
        this.operacao = operacao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
    
    @Override
    public String toString(){     
        return "< Operacao: "+ operacao +" | Status: " + status + " >";
    }
    
}
