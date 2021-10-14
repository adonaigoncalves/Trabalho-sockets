package consumidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class ConsumidorUDP {

    private static final String nome_servidor = "localhost";
    private static final int porta_servidor = 5555;
    private static InetAddress ip;

    public static void conversaComServidor(int combustivel) {
        // Configuracoes iniciais do cliente
        int valor_recebido = 0;
        byte[] dado = new byte[8];

        // Tenta pegar o inetAddress do servidor pelo nome
        try {
            ip = InetAddress.getByName(nome_servidor);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConsumidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            dado = String.valueOf("c" + combustivel).getBytes(); // Opcode de request
            DatagramPacket pacote_envio = new DatagramPacket(dado, dado.length, ip, porta_servidor);

            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(0);
            socket.send(pacote_envio);

            dado = String.valueOf("        ").getBytes(); // Limpa o buffer
            DatagramPacket pacote_recebido = new DatagramPacket(dado, dado.length);
            socket.setSoTimeout(1500);      // Timeout da requisicao
            socket.receive(pacote_recebido);
            String valor = new String(pacote_recebido.getData());
            valor_recebido = Integer.parseInt(valor.trim());
            if (valor_recebido == 1) {
                System.out.println("Combustivel consumido com sucesso.");
            } else {
                System.out.println("Combustivel consumido sem sucesso.");
            }
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(ConsumidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Falha ao enviar o pacote.\n");
            Logger.getLogger(ConsumidorUDP.class.getName()).log(Level.SEVERE, null, ex);
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
