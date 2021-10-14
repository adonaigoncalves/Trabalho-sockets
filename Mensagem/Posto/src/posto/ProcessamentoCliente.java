package posto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Mensagem;
import util.Status;

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
            Mensagem m = (Mensagem) input.readObject();
            String operacao = m.getOperacao();
            Mensagem reply = null;
            if (operacao.equals("PUSH")) {
                int quantidade = (int) m.getQuantidade();
                int teste = pilha.push(quantidade);

                if (quantidade != 10000) {
                    reply = new Mensagem("Rejeitado!");
                    reply.setStatus(Status.ERRO_QUANTIDADE);
                } else {
                    if (teste == 0) {
                        reply = new Mensagem("Rejeitado!");
                        reply.setStatus(Status.TANQUE_CHEIO);
                    } else {
                        reply = new Mensagem("PUSH_RESPOSTA");
                        reply.setStatus(Status.OK);
                        reply.setQuantidade(teste);
                    }
                }
            }

            if (operacao.equals("POP")) {
                int quantidade = (int) m.getQuantidade();
                int teste = pilha.pop(quantidade);
                if (teste == 0) {
                    reply = new Mensagem("Tanque vazio!");
                    reply.setStatus(Status.TANQUE_VAZIO);
                } else if (teste == 1) {
                    reply = new Mensagem("PUSH_RESPOSTA");
                    reply.setStatus(Status.OK);
                    reply.setQuantidade(quantidade);
                }
                teste = pilha.ret_pop();
                System.out.println(teste + " litros consumidos.");

            }

            output.writeObject(reply);
            output.flush();

            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println("Problema no tratamento de conexão com cliente " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ProcessamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
