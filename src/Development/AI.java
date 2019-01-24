/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import RazeSource.Planet;
import RazeSource.Ship;
import RazeSource.Drone;

/**
 *
 * @author Jia Jia Chen
 */
public interface AI {

    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2);

    public void act();

    public String getName();
}
