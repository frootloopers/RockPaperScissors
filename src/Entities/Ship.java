/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;

/**
 *
 * @author John Popovici
 */
public class Ship extends Controllable {

    /**
     * Creates a ship entity
     *
     * @author John Popovici
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    protected Ship(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_SHIP, faceAngle, 1.0, teamID, map);
    }

    //bullet velocity
    private final double BARRELVELOCITY = 2;
    //firing resource cost
    private final double FIRECOST = 1;
    //aoe attack range
    private final double PULSERANGE = 10;
    //aoe attack resource cost
    private final double PULSECOST = 2;

    /**
     * By Jia Jia: This spawns a bullet in the map.
     */
    public void fireBullet() {
        if (hasAct == false && storage >= FIRECOST) {
            hasAct = true;
            map.getBullets().add(new Bullet(pos.x, pos.y, BARRELVELOCITY, faceAngle, teamID, map));
            storage -= FIRECOST;
        }
    }

    /**
     * By Jia Jia: Fire a pulse that erases nearby bullets.
     */
    public void pulse() {
        if (hasAct == false && storage >= PULSECOST) {
            hasAct = true;
            //get the enemies within range and put them in an arraylist
            ArrayList<Bullet> temp = map.aoe(pos, PULSERANGE + radius);
            //erase all enemy bullets
            for (Bullet e : temp) {
                if (e.getTeamID() != teamID) {
                    map.getBullets().remove(e);
                    //uncomment line below if you want to give points for bullet erasure
//                    map.getTeams()[e.teamID].addScore(1);
                }
            }
            storage -= PULSECOST;
        }
    }

    @Override
    public void move() {
        hasAct = false;
        //add acceleration
        vel.x += Math.sin(Math.toRadians(faceAngle)) * (thrustF / 100.0 * SHIP_STERN_STRENGTH);
        vel.y -= Math.cos(Math.toRadians(faceAngle)) * (thrustF / 100.0 * SHIP_STERN_STRENGTH);
        //add rotation
        faceAngle -= thrustRotR * SHIP_ROT_STRENGTH / 100.0;
        faceAngle += thrustRotL * SHIP_ROT_STRENGTH / 100.0;
        super.move();
    }

    /**
     *
     *
     * @param other
     * @param input
     */
    protected void collideDrone(Drone other, int input) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            storage += input;
        }
    }

    /**
     * transfers the storage of the ship to the planet
     *
     * @param other
     */
    public void collidePlanet(Planet other) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            other.collideShip(this, storage);
            storage = 0;
        }
    }
}
