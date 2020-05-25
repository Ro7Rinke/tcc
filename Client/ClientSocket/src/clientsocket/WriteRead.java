/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ro7Rinke
 */
public class WriteRead {
    
    public static void Write(String fileName, String data){
        try {
            try (FileWriter myWriter = new FileWriter(fileName)) {
                myWriter.write(data);
                myWriter.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(WriteRead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String Read(String fileName){
        try {
            Scanner myReader = new Scanner(new File(fileName));
            String data = myReader.nextLine();
            return data;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
