/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors12two;

import java.util.Scanner;

/**
 *
 * @author redti
 */
public class RockPaperScissors12Two {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner keyboard = new Scanner(System.in);
        String input;
        int player1 = 0;
        int player2 = 0;
        int tie = 0;
        input = keyboard.nextLine();
        for (int x = 1; x < input.length() && player1 < 2 && player2 < 2; x = x + 3) {
            if (input.substring(x - 1, x + 1).equals("PR") || input.substring(x - 1, x + 1).equals("SP") || input.substring(x - 1, x + 1).equals("RS")) {
                player1++;
            } else if (input.substring(x - 1, x + 1).equals("RP") || input.substring(x - 1, x + 1).equals("PS") || input.substring(x - 1, x + 1).equals("SR")) {
                player2++;
            } else {
                tie++;
            }
        }
        if (player1 > 1 && player1 > player2) {
            System.out.print("PLAYER ONE ");
        } else if (player2 > 1 && player2 > player1) {
            System.out.print("PLAYER TWO ");
        } else {
            
        }
        System.out.println(tie);
    }
}
