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
public class PostoUDP {

    public static Pilha pilha = new Pilha();
    private static DatagramSocket socket;
    private static long count = 1;

    public static void main(String[] args) {
        // Loop de requisicoes
        while (true) {
            try {
                // Abre o socket
                System.out.println("Abrindo o socket");
                socket = new DatagramSocket(5555);

                // Aguarda por uma requiscao
                byte[] dado = new byte[8];
                System.out.println("Combustível no tanque: " + pilha.sum() + " litros.");
                System.out.println("Aguardando requisicao...");
                DatagramPacket pacote_recebido = new DatagramPacket(dado, dado.length);
                socket.receive(pacote_recebido);

                // Lanca uma thread para responder a requisicao
                System.out.println("Processando pacote... Requisicao " + count);
                ProcessamentoCliente cliente = new ProcessamentoCliente(PostoUDP.pilha, pacote_recebido);
                Thread t = new Thread(cliente);
                t.start();

                socket.close();
                dado = null;
                count++;
            } catch (SocketException ex) {
                System.out.println("Falha ao abrir o socket.\n");
                Logger.getLogger(PostoUDP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("Falha ao enviar o pacote.\n");
                Logger.getLogger(PostoUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
