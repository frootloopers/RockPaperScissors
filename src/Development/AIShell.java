/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Entities.Drone;
import Entities.Planet;
import Entities.Ship;

/**
 * Extend this to make an AI.
 *
 * @author Jia Jia Chen
 */
public class AIShell implements AI {

    protected String name = "";
    protected String author = "";
    protected String desc = "";
    protected Planet planet;
    protected Ship ship;
    protected Drone drone1;
    protected Drone drone2;

    public AIShell() {
        //give the AI a name here.
        name = "NO AI";
        author = "NO AUTHOR";
        desc = "NO DESC";
    }

    /**
     * Tells the AI what units it has under its control.
     *
     * @param planet
     * @param ship
     * @param drone1
     * @param drone2
     */
    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2) {
        this.planet = planet;
        this.ship = ship;
        this.drone1 = drone1;
        this.drone2 = drone2;
    }

    /**
     * The commands the AI will give its entities. This is called for each team
     * for each game loop.
     */
    public void act() {
    }

    /**
     * Gets the name of this AI.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the author of this AI.
     *
     * @return
     */
    public String getAuthor() {
        return name;
    }

    /**
     * Gets the description of this AI.
     *
     * @return
     */
    public String getDesc() {
        return name;
    }
}
