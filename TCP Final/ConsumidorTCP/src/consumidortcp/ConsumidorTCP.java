package consumidortcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class ConsumidorTCP {

    public static void conversaComServidor(int combustivel) {
        try {
            Socket socket = new Socket("localhost", 5555);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem de pop...");
            output.writeUTF("c" + combustivel);
            output.flush();
            String resposta = input.readUTF();
            System.out.println("Resposta: " + resposta);
           
            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(ConsumidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        int combustivel;
        Random r = new Random();
        try {
            while (true) {
                combustivel = r.nextInt(41) + 10;
                System.out.println(combustivel + " litros requisitados");
                System.out.println("Estabelecendo conexão...");
                Thread.sleep(500);//Valor em milisegundos
                conversaComServidor(combustivel);
            }
        } catch (InterruptedException e) {
            System.out.println("Erro no Servidor: " + e.getMessage());
        }
    }
}
