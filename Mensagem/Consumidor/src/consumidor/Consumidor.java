package consumidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Mensagem;
import util.Status;

/**
 *
 * @author William
 */
public class Consumidor {

    public static void conversaComServidor(int combustivel) {
        try {
            Socket socket = new Socket("localhost", 5555);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");

            Mensagem m = new Mensagem("POP");
            m.setStatus(Status.POP);
            m.setQuantidade(combustivel);

            output.writeObject(m);
            output.flush();

            System.out.println("Mensagem enviada: " + m);
            m = (Mensagem) input.readObject();
            if (m.getStatus() == Status.OK) {
                int resposta = (int) m.getQuantidade();
                System.out.println("Resposta: " + m);
                System.out.println("Recebido: " + resposta + " litros\n");
            } else {
                System.out.println("Erro: " + m.getStatus() + "\n");
            }

            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro no cast: " + ex.getMessage());
            Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
