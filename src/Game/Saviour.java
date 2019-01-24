/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.Team;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * For loading from and saving to files
 *
 * @author Jia Jia Chen
 */
public abstract class Saviour {

    /**
     * Loads scores from a text file
     *
     * @return Arraylist of Team
     * @throws FileNotFoundException
     */
    public static ArrayList<Team> loadScore() throws FileNotFoundException {
        ArrayList<Team> scores = new ArrayList<>();
        Scanner input = new Scanner(new FileReader("src/High_Scores.txt"));
        while (input.hasNextLine()) {
            String[] temp = input.nextLine().split(",");
            scores.add(new Team(Integer.parseInt(temp[1]), temp[0]));
        }
        input.close();
        return scores;
    }

    /**
     * Saves scores in the format: NAME,SCORE\n
     *
     * @param teams
     * @throws IOException
     */
    public static void saveScore(Team[] teams) throws IOException {
        for (int x = 1; x < teams.length; x++) {
            saveTo(teams[x].getName() + "," + Integer.toString(teams[x].getScore()) + "\n");
        }
    }

    /**
     * Writes scores to the end of a text file
     *
     * @param toSave
     * @throws IOException
     */
    private static void saveTo(String toSave) throws IOException {
        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("src/High_Scores.txt", true)));
        output.append(toSave);
        output.close();
    }
}
