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
     */
    public Asteroid(double x, double y) {
        //asteroid hit-box radius of 1
        //asteroid is not a team entity
        super(x, y, 1, 0);
    }
}
