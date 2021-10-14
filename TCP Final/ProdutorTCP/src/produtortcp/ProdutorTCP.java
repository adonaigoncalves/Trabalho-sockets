package produtortcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class ProdutorTCP {

    private static final int combustivel = 10000;

    public static void conversaComServidor() {
        try {
            Socket socket = new Socket("localhost", 5555);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");

            output.writeUTF("p" + combustivel);
            output.flush();

            System.out.println("Mensagem enviada: " + combustivel + " litros");
            String resposta = input.readUTF();
            System.out.println("Resposta: " + resposta);

            input.close();
            output.close();
            socket.close();
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(ProdutorTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Falha ao enviar o pacote.\n");
            Logger.getLogger(ProdutorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println(combustivel + " litros produzidos");
                Thread.sleep(10000);//Valor em milisegundos
                System.out.println("Estabelecendo conexão...");
                conversaComServidor();
            }
        } catch (InterruptedException e) {
            System.out.println("Erro no Servidor: " + e.getMessage());
        }
    }
}
