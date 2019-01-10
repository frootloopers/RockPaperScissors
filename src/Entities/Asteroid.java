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
public class Asteroid extends Movable {

    /**
     * Creates an asteroid entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param speed the speed the entity starts with
     * @param faceAngle the angle the entity faces when created
     */
    public Asteroid(double x, double y, double speed, double faceAngle) {
        //asteroid is not a team entity
        super(x, y, RADIUS_PROJECTILE, speed, faceAngle, 0);
    }
}
