package posto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class ProcessamentoCliente implements Runnable {

    private final Pilha pilha;
    private final DatagramPacket pacote_recebido;

    public ProcessamentoCliente(Pilha p, DatagramPacket dp) {
        pilha = p;
        pacote_recebido = dp;
    }

    @Override
    public void run() {
        try {
            byte[] dado_recebido = "        ".getBytes(); // Limpa o buffer

            dado_recebido = pacote_recebido.getData();
            String msg_recebida = new String(dado_recebido);

            if (msg_recebida.contains("c")) {
                msg_recebida = msg_recebida.substring(1);
                System.out.println("Requisicao do consumidor!");
                String valor_resposta = String.valueOf(pilha.pop(Integer.parseInt(msg_recebida.trim())));
                byte[] array = valor_resposta.getBytes();
                int valor_retpop = pilha.ret_pop();
                System.out.println(valor_retpop + " litros consumidos.");
                DatagramSocket resp = new DatagramSocket();
                DatagramPacket pacote_resposta = new DatagramPacket(array, array.length, pacote_recebido.getAddress(), pacote_recebido.getPort());
                resp.send(pacote_resposta);
            } else {
                System.out.println("Requisicao do produtor!");
                int valor_push = Integer.parseInt(msg_recebida.trim());
                System.out.println("Valor: " + valor_push);
                pilha.push(valor_push);
            }
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Falha ao enviar o pacote.\n");
            Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n");
    }
}
