/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author John Popovici
 */
public class FileIO {

    /**
     * Writes to a text file
     *
     * @param fileName the file name being written to
     * @param s the string being written to the file
     * @param append the boolean of append
     */
    public static void writeTo(String fileName, String s, Boolean append) {
        //creates file object
        File f = new File("res/" + fileName);
        try {
            //create and use printwriter
            PrintWriter p = new PrintWriter(new FileWriter(f, append));
            p.println(s);
            p.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
