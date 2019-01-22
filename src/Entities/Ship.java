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
    private final double PULSERANGE = 15;
    //aoe attack resource cost
    private final double PULSECOST = 2;

    /**
     * By Jia Jia: This spawns a bullet in the map in the direction of the ship.
     */
    public void fireBullet() {
        //prevent a ship from firing infinite times in a single loop, requires resources and the ship to have not fired before
        if (hasAct == false && storage >= FIRECOST) {
            hasAct = true;
            //add bullet to the arraylist
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
            //erase the enemy bullets found in the arrayList
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
     *  transfers the storage of the drone to the ship
     * (Carl)
     * @param other the drone that's transferring
     * @param input the number of resources being transfered 
     */
    protected void collideDrone(Drone other, int input) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            storage += input;
        }
    }

    /**
     * transfers the storage of the ship to the planet
     *(Carl)
     * @param other the planet the ship is transferring to
     */
    public void collidePlanet(Planet other) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            other.collideShip(this, storage);
            storage = 0;
        }
    }
}
