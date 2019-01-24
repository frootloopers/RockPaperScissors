/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RazeSource;

import Blocks.Vel;
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
    public final double BARRELVELOCITY = 2;
    //firing resource cost
    public final double FIRECOST = 1;

    //aoe attack range
    public final double PULSERANGE = 5;
    //aoe attack resource cost
    public final int PULSECOST = 10;

    //aoe attack range
    public final double SHIELDRANGE = 15;
    //aoe attack resource cost
    public final int SHIELDCOST = 5;

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
     * By Jia Jia: Damages nearby enemies.
     */
    public void pulse() {
        if (hasAct == false && storage >= PULSECOST) {
            hasAct = true;
            //get the controllables within range and put them in an arraylist
            ArrayList<Controllable> temp = map.aoeControllable(pos, PULSERANGE + radius);
            //damage enemies in range
            for (Controllable c : temp) {
                //verify they are enemies
                if (c.getTeamID() != teamID) {
                    //uncomment line below if you want pulse to steal points
//                    map.getTeams()[e.teamID].addScore(c.storage);
                    c.storage = 0;
                    //uncomment line below if you want to give points per enemy hit
//                    map.getTeams()[e.teamID].addScore(1);
                }
            }
            storage -= PULSECOST;
        }
    }

    /**
     * By Jia Jia: Erases nearby bullets.
     */
    public void shield() {
        if (hasAct == false && storage >= SHIELDCOST) {
            hasAct = true;
            ArrayList<Bullet> temp = map.aoeBullet(pos, SHIELDRANGE + radius);
            //erase the enemy bullets found in the arrayList
            for (Bullet b : temp) {
                if (b.getTeamID() != teamID) {
                    map.getBullets().remove(b);
                    //uncomment line below if you want to give points per bullet erased
//                    map.getTeams()[e.teamID].addScore(1);
                }
            }
            storage -= SHIELDCOST;
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
     * transfers the storage of the drone to the ship (Carl)
     *
     * @param other the drone that's transferring
     * @param input the number of resources being transfered
     */
    protected void collideDrone(Drone other, int input) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            storage += input;
        }
    }

    /**
     * transfers the storage of the ship to the planet (Carl)
     *
     * @param other the planet the ship is transferring to
     */
    protected void collidePlanet(Planet other) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            other.collideShip(this, storage);
            storage = 0;
        }
    }

    //copying to allow AI devs to get info without getting the pointer, to prevent unwanted access to editing entities.
    //Jia Jia does all the copying methods
    public Ship copy() {
        Ship temp = new Ship(pos.x, pos.y, faceAngle, teamID, map);
        temp.vel = new Vel(vel.x, vel.y);
        temp.storage = storage;
        return temp;
    }
}
