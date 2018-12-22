/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Blocks.Pos;

/**
 *
 * @author John Popovici
 */
public abstract class Entity {

    protected final double VEL_DECAY = 0.05;
    protected final double MOVABLE_ROT_STRENGTH = 0.5;
    protected final double SHIP_STERN_STRENGTH = 0.2;
    protected final double DRONE_STERN_STRENGTH = 0.5;
    protected final double DRONE_SIDE_STRENGTH = 0.5;
    
    
    /**
     * the position object of this entity
     */
    private Pos pos;

    /**
     * Gets the position object of the entity
     *
     * @return the position object of the entity
     */
    public Pos getPos() {
        return new Pos(this.pos.getX(), this.pos.getY());
    }
}
