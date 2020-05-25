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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
    //client.socketConnect(ip, port);
 
    // escreve e recebe mensagem 
    //String message = "mensagem123";
 
    //System.out.println("Enviando: " + message);
    //String retorno = client.echo(message);
    //System.out.println("Recebendo: " + retorno);
    //client.socket.close();
    Crypt crypt = new Crypt();
    if("rsa".equals(args[0])){
        try {
            long stTime = System.currentTimeMillis();
            crypt.GenerateKeysRSA(Integer.parseInt(args[1]));
            byte[] test = crypt.encrypt("test");
            long useMemoryCrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            long usedTimeCrypt = (System.currentTimeMillis() - stTime);
            String txt = crypt.decrypt(test);
            long useMemoryDecrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            long usedTimeDecrypt = (System.currentTimeMillis() - stTime);
            WriteRead.Write("rsa-useMemoryCrypt-" + args[2], Long.toString(useMemoryCrypt));
            WriteRead.Write("rsa-useMemoryDecrypt-" + args[2], Long.toString(useMemoryDecrypt));
            WriteRead.Write("rsa-usedTimeCrypt-" + args[2], Long.toString(usedTimeCrypt));
            WriteRead.Write("rsa-usedTimeDecrypt-" + args[2], Long.toString(usedTimeDecrypt));
            System.out.println(useMemoryCrypt / 1024);
            System.out.println(useMemoryDecrypt / 1024);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
