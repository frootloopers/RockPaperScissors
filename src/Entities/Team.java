/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.Entity;
import java.awt.Color;

/**
 *
 * @author Jia Jia Chen
 */
public class Team implements Comparable {

    private int score;
    private String aiName;
    private Entity[] Entities;

    /**
     * For sorting game pieces into teams and keeping score
     *
     * @param Entities
     */
    public Team(Entity[] Entities, String aiName) {
        score = 0;
        this.Entities = Entities;
        this.aiName = aiName;
    }

    /**
     * For recording score purposes
     *
     * @param score
     * @param aiName
     */
    public Team(int score, String aiName) {
        this.score = score;
        this.aiName = aiName;
    }

    public void subScore(int score) {
        this.score -= score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return aiName;
    }

    public Entity[] getEntities() {
        return Entities;
    }

    public String toString() {
        return aiName + "," + Integer.toString(score);
    }

    /**
     * Get the associated color of a team.
     * 
     * @param teamID The ID of the team.
     * @return The official team Color.
     */
    public static Color getColor(int teamID) {
        switch (teamID) {
            case (0):
                return Color.LIGHT_GRAY;
            case (1):
                return Color.RED;
            case (2):
                return Color.BLUE;
            case (3):
                return Color.GREEN;
            case (4):
                return Color.YELLOW;
            default:
                return Color.GRAY;
        }
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
