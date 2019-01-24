/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RazeSource;

/**
 *
 * @author John Popovici
 */
public class Planet extends Entity {

    /**
     * Creates a planet entity
     *
     * @author John Popovici
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Planet(double x, double y, int teamID, Map map) {
        super(x, y, RADIUS_PLANET, teamID, map);
    }

    /**
     * Adds points to the team when the ship transfers the resources (Carl)
     *
     * @param other the Ship that's transfers the resources
     * @param input the number of resources transfered
     */
    protected void collideShip(Ship other, int input) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            map.getTeams()[teamID].addScore(input); // add a way to increase security?
        }
    }

    //copying to allow AI devs to get info without getting the pointer, to prevent unwanted access to editing entities.
    //Jia Jia does all the copying methods
    public Planet copy() {
        return new Planet(pos.x, pos.y, teamID, map);
    }
}
