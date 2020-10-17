/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import com.google.gson.Gson;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
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
//        args = new String[4];
//        args[0] = "500000";
//        args[1] = "aes";
//        args[2] = "192";
//        args[3] = "0";

        if ("chart".equals(args[0])) {
            generateCharts(args[1].split(","));
        }else{
            String data = WriteRead.Read("../strings/" + args[0] + ".dat");

            // instancia classe
            SocketConnection client = new SocketConnection();
            // conexão socket tcp
            String ip = "192.168.0.35";
            int port = 3000;
//            client.socketConnect(ip, port);

            // escreve e recebe mensagem 
            //String message = "mensagem123";
            //System.out.println("Enviando: " + message);
            //String retorno = client.echo(message);
            //System.out.println("Recebendo: " + retorno);
            //client.socket.close();
            long startUseMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            Crypt crypt = new Crypt();
            try {
                client.socketConnect(ip, port);
                long stTime = System.currentTimeMillis();
                String retorno = client.echo(data);
                long usedTimeSendDecrypt = (System.currentTimeMillis() - stTime);
                client.socket.close();
                stTime = System.currentTimeMillis();
                crypt.startKeys(args[1], Integer.parseInt(args[2]));
                long usedTimeKey = (System.currentTimeMillis() - stTime);
                stTime = System.currentTimeMillis();
                byte[] cryptData = crypt.selectEncrypt(args[1], data);
                long useMemoryCrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - startUseMemory;
                long usedTimeCrypt = (System.currentTimeMillis() - stTime);
                client.socketConnect(ip, port);
                stTime = System.currentTimeMillis();
                retorno = client.echo(new String(cryptData));
                long usedTimeSendCrypt = (System.currentTimeMillis() - stTime);
                client.socket.close();
                stTime = System.currentTimeMillis();
                String txt = crypt.selectDecrypt(args[1], cryptData);
//                System.out.println(txt);
                long useMemoryDecrypt = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - startUseMemory;
                long usedTimeDecrypt = (System.currentTimeMillis() - stTime);
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useTotalMemory-" + args[3] + ".dat", Long.toString(startUseMemory));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeKey-" + args[3] + ".dat", Long.toString(usedTimeKey));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-sizeCrypt-" + args[3] + ".dat", Integer.toString(cryptData.length));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useMemoryCrypt-" + args[3] + ".dat", Long.toString(useMemoryCrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-useMemoryDecrypt-" + args[3] + ".dat", Long.toString(useMemoryDecrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeCrypt-" + args[3] + ".dat", Long.toString(usedTimeCrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeDecrypt-" + args[3] + ".dat", Long.toString(usedTimeDecrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeSendCrypt-" + args[3] + ".dat", Long.toString(usedTimeSendCrypt));
                WriteRead.Write("./singleresults/" + args[0] + "-" + args[1] + "-" + args[2] + "-usedTimeSendDecrypt-" + args[3] + ".dat", Long.toString(usedTimeSendDecrypt));
            } catch (NumberFormatException ex) {
                Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void generateCharts(String[] resultFiles) {
        ArrayList<ChartType> chartTypes = new ArrayList<ChartType>();
        chartTypes.add(new ChartType("cryptTime", "Tempo para criptografar", "cryptTime", "milisigundos"));
        chartTypes.add(new ChartType("decryptTime", "Tempo para descriptografar", "decryptTime", "milisigundos"));
        chartTypes.add(new ChartType("cryptSize", "Tamanho do dado criptografado", "cryptSize", "bytes"));
        chartTypes.add(new ChartType("cryptMemory", "Memória usada para criptografar", "cryptMemory", "bytes"));
        chartTypes.add(new ChartType("decryptMemory", "Memória usada para descriptografar", "decryptMemory", "bytes"));
        chartTypes.add(new ChartType("keyTime", "Tempo para gerar a chave", "keyTime", "milisigundos"));
        chartTypes.add(new ChartType("sendTime", "Tempo para enviar e receber a mensagem do servidor", "sendTime", "milisigundos"));

        ArrayList<Result> results = new ArrayList<Result>();

        Gson gson = new Gson();

        for (String resultFile : resultFiles) {
            Result result = gson.fromJson(WriteRead.Read("./" + resultFile + ".dat"), Result.class);
            result.name = resultFile;
            results.add(result);
        }
        
        for( ChartType chartType : chartTypes){
            EventQueue.invokeLater(() -> {

            BarChartEx ex = new BarChartEx(chartType, results);
            ex.setVisible(true);
//            ex.dispose();
            
        });
        }

    }

// realiza a conexão com o socket
    private void socketConnect(String ip, int port) throws UnknownHostException, IOException {
        System.out.println("[Conectando socket...]");
        this.socket = new Socket(ip, port);
//        this.socket.setTcpNoDelay(true);
//        this.socket.setReceiveBufferSize(500000);
//        this.socket.setSendBufferSize(500000);
    }

    // escreve e recebe mensagem full no socket (String)
    public String echo(String message) {
        try {
//            getSocket().setTcpNoDelay(true);
            // out & in 
            
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

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
