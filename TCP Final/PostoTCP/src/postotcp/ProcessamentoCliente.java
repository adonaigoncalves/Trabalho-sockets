package postotcp;

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
public class ProcessamentoCliente implements Runnable {

    private final Socket socket;
    private final Pilha pilha;

    public ProcessamentoCliente(Socket cliente, Pilha pilha) {
        this.socket = cliente;
        this.pilha = pilha;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Tratando...");
            String mensagem = input.readUTF();
            String resposta = null;

            if (mensagem.contains("c")) {
                mensagem = mensagem.substring(1);
                int c = pilha.pop(Integer.parseInt(mensagem));
                if (c == 1) {
                    System.out.println("Combustivel consumido com sucesso.");
                } else {
                    System.out.println("Combustivel consumido sem sucesso.");
                }
                resposta = mensagem + " litros enviados.";
                int valor_retpop = pilha.ret_pop();
                System.out.println(valor_retpop + " litros consumidos.");
            } else if (mensagem.contains("p")) {
                mensagem = mensagem.substring(1);
                int p = pilha.push(Integer.parseInt(mensagem));
                System.out.println("Adicionados " + p + " litros no tanque.");
                resposta = p + " litros recebidos.";
            } else {
                mensagem = "ERRO";
            }
            output.writeUTF(resposta);
            output.flush();

            input.close();
            output.close();
            System.out.println("Cliente finalizado...\n");
        } catch (SocketException ex) {
            System.out.println("Falha ao abrir o socket.\n");
            Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            System.out.println("Problema no tratamento de conexão com cliente " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Falha ao fechar o pacote.\n");
            }
        }
    }

}
