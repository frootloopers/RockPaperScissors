/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Vel;

/**
 *
 * @author John Popovici
 */
public abstract class Movable extends Entity {
    /**
     * the velocity object of this entity
     */
    private Vel vel;

    /**
     * Gets the velocity object of the entity
     *
     * @return the velocity object of the entity
     */
    public Vel getVel() {
        return new Vel(this.vel.getX(), this.vel.getY());
    }
}
