/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author John Popovici
 */
public class Ship extends Controllable {

    /**
     * Creates a ship entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     */
    public Ship(double x, double y, double faceAngle, int  teamID) {
        super(x, y, RADIUS_SHIP, faceAngle, teamID);  
    }
    
    /**
     * Jia
     */
    public void fireBullet(){
        map.addBullet();
    }
}
