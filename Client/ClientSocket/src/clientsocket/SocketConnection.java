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
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ro7Rinke
 */
public class SocketConnection {
    private Socket socket = null;
 
  public static void main(String[] args) throws UnknownHostException, 
  IOException, ClassNotFoundException {
    args = new String[4];
//    args[0] = "500";
//    args[1] = "rsa";
//    args[2] = "1024";
//    args[3] = "0";
      
    String data = WriteRead.Read("../strings/" + args[0] + ".dat");
      
    // instancia classe
    //SocketConnection client = new SocketConnection();
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
    long startUseMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    Crypt crypt = new Crypt();
//    switch(args[1]){
//        case "rsa":
            try {
                long stTime = System.currentTimeMillis();
                crypt.startKeys(args[1], Integer.parseInt(args[2]));
                long usedTimeKey = (System.currentTimeMillis() - stTime);
                byte[] cryptData = crypt.selectEncrypt(args[1], data);
                long useMemoryCrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - startUseMemory;
                long usedTimeCrypt = (System.currentTimeMillis() - stTime);
                String txt = crypt.selectDecrypt(args[1], cryptData);
                System.out.println(txt);
                long useMemoryDecrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - startUseMemory;
                long usedTimeDecrypt = (System.currentTimeMillis() - stTime);
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useTotalMemory-" + args[3] + ".dat", Long.toString( startUseMemory ));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeKey-" + args[3] + ".dat", Long.toString( usedTimeKey ));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-sizeCrypt-" + args[3] + ".dat", Integer.toString(cryptData.length));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useMemoryCrypt-" + args[3] + ".dat", Long.toString(useMemoryCrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useMemoryDecrypt-" + args[3] + ".dat", Long.toString(useMemoryDecrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeCrypt-" + args[3] + ".dat", Long.toString(usedTimeCrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeDecrypt-" + args[3] + ".dat", Long.toString(usedTimeDecrypt));
            } catch (NumberFormatException ex) {
                Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
//            break;
//    }
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
