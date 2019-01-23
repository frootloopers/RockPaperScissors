/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Entities.*;

/**
 *
 * @author Jia Jia Chen
 */
public class DummyAI implements AI {
    
    public final String name = "Dummy";
    private Planet planet;
    private Ship ship;
    private Drone drone1;
    private Drone drone2;
    
    public DummyAI() {
    }
    
    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2){
        this.planet = planet;
        this.ship = ship;
        this.drone1 = drone1;
        this.drone2 = drone2;
    }
    
    public void act() {
        ship.setThrustF(100);
    }

    @Override
    public String getName() {
        return name;
    }
    
}
