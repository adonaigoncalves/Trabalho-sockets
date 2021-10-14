package produtor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class ProdutorUDP {

    private static final String nome_servidor = "localhost";
    private static final int porta_servidor = 5555;
    private static InetAddress ip;
    private static final int combustivel = 10000;

    public static void conversaComServidor() {
        try {
            ip = InetAddress.getByName(nome_servidor);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ProdutorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] dado;

        try {
            dado = String.valueOf(combustivel).getBytes();
            DatagramPacket pacote_envio = new DatagramPacket(dado, dado.length, ip, porta_servidor);

            DatagramSocket socket = new DatagramSocket();
            socket.send(pacote_envio);
            socket.close();
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(ProdutorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Falha ao enviar o pacote.\n");
            Logger.getLogger(ProdutorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println(combustivel + " litros produzidos.");
                System.out.println("Estabelecendo conexão...");
                Thread.sleep(10000);//Valor em milisegundos
                conversaComServidor();
            }
        } catch (InterruptedException e) {
            System.out.println("Erro no Servidor: " + e.getMessage());
        }
    }
}
