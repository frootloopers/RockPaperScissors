/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Foundation.Map;

/**
 *
 * @author John Popovici
 */
public class Harvestable extends Entity {

    /**
     * Creates a harvestable entity
     *
     * @author John Popovici
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param map the map the entity is in
     */
    public Harvestable(double x, double y, Map map) {
        //harvestable is not a team entity
        super(x, y, RADIUS_HARVESTABLE, 0, map);
    }
    
    
}
