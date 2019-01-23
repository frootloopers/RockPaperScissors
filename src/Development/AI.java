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
 * Implement this to make your ai... Not really useful since right now all teams have the same number and types of units.
 * 
 * @author Jia Jia Chen
 */
public interface AI {
    
    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2);

    public void act();
    
    public String getName();
}
