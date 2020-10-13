/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

/**
 *
 * @author Ro7Rinke
 */
public class Result {
    long cryptTime;
    long decryptTime;
    long cryptSize;
    long cryptMemory;
    long decryptMemory;
    long keyTime;
    String name;

    @Override
    public String toString() {
        return "Result{" + "name=" + name + "cryptTime=" + cryptTime + ", decryptTime=" + decryptTime + ", cryptSize=" + cryptSize + ", cryptMemory=" + cryptMemory + ", decryptMemory=" + decryptMemory + ", keyTime=" + keyTime + '}';
    }
    
    public long getValue(String alias){
        switch(alias){
            case "cryptTime":
                return cryptTime;
            case "decryptTime":
                return decryptTime;
            case "cryptSize":
                return cryptSize;
            case "cryptMemory":
                return cryptMemory;
            case "decryptMemory":
                return decryptMemory;
            case "keyTime":
                return keyTime;
            default:
                return 0;
        }
    }
}
