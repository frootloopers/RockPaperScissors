/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Foundation.Team;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jia Jia Chen
 */
public abstract class Saviour {

    protected static ArrayList<Team> loadScore() throws FileNotFoundException {
        ArrayList<Team> scores = new ArrayList<>();
        Scanner input = new Scanner(new FileReader("High_Scores.txt"));
        while (input.hasNextLine()) {
            String[] temp = input.nextLine().split(",");
            scores.add(new Team(Integer.parseInt(temp[1]), temp[0]));
        }
        input.close();
        return scores;
    }

    /**
     *
     * @param teams
     * @throws IOException
     */
    protected static void saveScore(Team[] teams) throws IOException {
        for (Team t : teams) {
            saveTo(t.getName() + "," + Integer.toString(t.getScore()) + "\n");
        }
    }

    private static void saveTo(String toSave) throws IOException {
        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("High_Scores.txt", true)));
        output.append(toSave);
        output.close();
    }
}
