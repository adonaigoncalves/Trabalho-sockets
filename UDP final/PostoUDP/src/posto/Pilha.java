package posto;

/**
 *
 * @author William
 */
import java.util.ArrayList;

public class Pilha {

    private final int capacidade = 100000;
    private ArrayList<Integer> array = null;
    private int valorpop;

    public Pilha() {
        array = new ArrayList();
    }

    public boolean vazia() {
        return sum() == 0;
    }

    public int sum() {
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        return sum;
    }

    public synchronized int push(int valor) {
        if (sum() == capacidade) {
            System.out.println("Tanque cheio!");
            return 0;
        } else if (sum() + valor <= capacidade) {
            array.add(valor);
            return valor;
        } else {
            int recebido = capacidade - sum();
            System.out.println("Tanque cheio! Somente " + recebido + " litros puderam ser inseridos.");
            array.add(recebido);
            return recebido;
        }
    }

    public synchronized int pop(int valor) {
        int i = array.size() - 1;
        if (valor <= sum()) {
            valorpop = valor;
            while (valor > 0) {
                if (valor < array.get(i)) {
                    array.set(i, (array.get(i) - valor));
                    valor = 0;
                } else if (valor == array.get(i)) {
                    array.remove(i);
                    valor = 0;
                } else {
                    valor -= array.get(i);
                    array.remove(i);
                }
                i--;
            }
            return 1;
        } else {
            System.out.println("Posto vazio! " + (valor-sum()) + " litros não puderam ser removidos.");
            valor = sum();
            valorpop = valor;
            while (valor > 0) {
                if (valor < array.get(i)) {
                    array.set(i, (array.get(i) - valor));
                    valor = 0;
                } else if (valor == array.get(i)) {
                    array.remove(i);
                    valor = 0;
                } else {
                    valor -= array.get(i);
                    array.remove(i);
                }
                i--;
            }
            return 0;
        }
    }

    public synchronized int ret_pop() {
        return valorpop;
    }
}
