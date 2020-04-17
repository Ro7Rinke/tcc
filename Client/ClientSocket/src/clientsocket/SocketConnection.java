/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author Ro7Rinke
 */
public class SocketConnection {
    private Socket socket = null;
 
  public static void main(String[] args) throws UnknownHostException, 
  IOException, ClassNotFoundException {
    // instancia classe
    SocketConnection client = new SocketConnection();
 
    // conexão socket tcp
    String ip = "192.168.0.35";
    int port = 3000;
    client.socketConnect(ip, port);
 
    // escreve e recebe mensagem 
    String message = "mensagem123";
 
    System.out.println("Enviando: " + message);
    String retorno = client.echo(message);
    System.out.println("Recebendo: " + retorno);
    client.socket.close();
  }
 
  // realiza a conexão com o socket
  private void socketConnect(String ip, int port) throws UnknownHostException, IOException {
    System.out.println("[Conectando socket...]");
    this.socket = new Socket(ip, port);
  }           
               
  // escreve e recebe mensagem full no socket (String)
  public String echo(String message) {
    try {
      // out & in 
      PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
      BufferedReader in = new BufferedReader
      (new InputStreamReader(getSocket().getInputStream()));
 
      // escreve str no socket e lêr
      out.println(message);
      String retorno = in.readLine();
      return retorno;
                
      } catch (IOException e) {
      e.printStackTrace();
    }
               
    return null;    
  }
 
  // obtem instância do socket
  private Socket getSocket() {
              return socket;
  }
}
