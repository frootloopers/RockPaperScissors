/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RazeSource;

import Development.AI;
import RazeSource.Entity;
import java.awt.Color;

/**
 *
 * @author Jia Jia Chen
 */
public class Team implements Comparable {

    private int score;
    private String aiName;
    private Entity[] entities;
    private Map map;
    private AI ai;

    /**
     * For sorting game pieces into teams, keeping score
     *
     * @param planet Team planet
     * @param ship Team mothership
     * @param drone1 Team drones
     * @param drone2
     * @param aiName Name of the team
     */
    public Team(Planet planet, Ship ship, Drone drone1, Drone drone2, String aiName, Map map, AI ai) {
        score = 0;
        entities = new Entity[4];
        entities[0] = planet;
        entities[1] = ship;
        entities[2] = drone1;
        entities[3] = drone2;
        this.ai = ai;
        this.ai.setUnits((Planet) entities[0], (Ship) entities[1], (Drone) entities[2], (Drone) entities[3]);
        this.aiName = ai.getName();
        this.map = map;
    }

    public void useAI() {
        ai.act();
    }

    /**
     * For recording score purposes
     *
     * @param score
     * @param aiName
     */
    protected Team(int score, String aiName) {
        this.score = score;
        this.aiName = aiName;
    }

    /**
     * Subtracts score from a team.
     *
     * @param score
     */
    protected void subScore(int score) {
        this.score -= score;
    }

    /**
     * Adds score to a team.
     *
     * @param score
     */
    protected void addScore(int score) {
        this.score += score;
    }

    /**
     * Gets the score of a team.
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the name of a team.
     *
     * @return
     */
    public String getName() {
        return aiName;
    }

    /**
     * Gets the list of entities belonging to a team.
     *
     * @return
     */
    protected Entity[] getEntities() {
        return entities;
    }

    /**
     * Gets the map this team belongs to.
     *
     * @return
     */
    protected Map getMap() {
        return map;
    }

    /**
     * Gets the map this team belongs to.
     *
     * @return
     */
    protected AI getAI() {
        return ai;
    }

    @Override
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
        return (((Team) o).getScore() - score);
    }
}
