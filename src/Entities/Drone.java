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
public class Drone extends Controllable {

    /**
     * Creates a drone entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Drone(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_DRONE, faceAngle, teamID, map);
    }

    @Override
    protected void move() {
        //add acceleration
        vel.x += Math.sin(Math.toRadians(faceAngle)) * (thrustF / 100.0 * DRONE_STERN_STRENGTH);
        vel.y -= Math.cos(Math.toRadians(faceAngle)) * (thrustF / 100.0 * DRONE_STERN_STRENGTH);
        //add rotation
        faceAngle -= thrustRotR * DRONE_ROT_STRENGTH;
        faceAngle += thrustRotL * DRONE_ROT_STRENGTH;
        super.move();
    }

}
