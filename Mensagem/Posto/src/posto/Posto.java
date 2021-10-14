package posto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class Posto {

    private ServerSocket serverSocket;
    private static final Pilha pilha = new Pilha();
    private static long count = 1;

    private void criarServerSocket(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    private Socket esperaConexao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void fechaSocket(Socket socket) throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Posto posto = new Posto();
            System.out.println("Servidor iniciado.\n");
            posto.criarServerSocket(5555);
            while (true) {
                System.out.println("Combustível no tanque: " + pilha.sum() + " litros.");
                System.out.println("Aguardando conexão...");
                Socket socket = posto.esperaConexao();
                System.out.println("Cliente conectado.");
                
                System.out.println("Processando pacote... Requisicao " + count);
                ProcessamentoCliente cliente = new ProcessamentoCliente(socket, pilha);
                Thread t = new Thread(cliente);
                t.start();
                System.out.println("Cliente finalizado...\n");
                count++;
            }
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(Posto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Falha ao enviar o pacote.\n" + ex.getMessage());
            Logger.getLogger(Posto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
