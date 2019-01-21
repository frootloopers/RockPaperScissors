/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Random;

/**
 *
 * @author John Popovici
 */
public class Drone extends Controllable {

    /**
     * Creates a drone entity
     *
     * @author John Popovici
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Drone(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_DRONE, faceAngle, 0.5, teamID,map);
    }

    private Random rand = new Random();
    private final static int HarvestableValue = 5;
    
    @Override
    public void move() {
        //add acceleration
        vel.x += Math.sin(Math.toRadians(faceAngle)) * (thrustF / 100.0 * DRONE_STERN_STRENGTH);
        vel.y -= Math.cos(Math.toRadians(faceAngle)) * (thrustF / 100.0 * DRONE_STERN_STRENGTH);
        //add rotation
        faceAngle -= thrustRotR * DRONE_ROT_STRENGTH / 100.0;
        faceAngle += thrustRotL * DRONE_ROT_STRENGTH / 100.0;
        super.move();
    }

    public void collideHarvestable(Harvestable other) {
        if (this.checkCollision(other)) {
            storage+=HarvestableValue;            
        }
    }

    public void collideShip(Ship other) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            other.collideDrone(this, storage);
            storage = 0;
        }
    }
}
