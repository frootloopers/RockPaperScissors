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
    public Ship(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_SHIP, faceAngle, teamID, map);
    }

    //bullet velocity
    private final double FIREPOWER = 2;
    //firing resource cost
    private final double FIRECOST = 10;
    //aoe attack range
    private final double PULSERANGE = 10;
    //aoe damage
    private final double PULSEDMG = 100;
    //aoe attack resource cost
    private final double PULSECOST = 1;

    /**
     * By Jia Jia: This spawns a bullet in the map.
     */
    public void fireBullet() {
        if (hasAct == false && storage >= FIRECOST) {
            hasAct = true;
            map.getBullets().add(new Bullet(pos.x, pos.y, FIREPOWER, faceAngle, teamID, map));
            storage -= FIRECOST;
        }
    }

    /**
     * By Jia Jia: Fire a pulse lowering the score of enemies within PULSERANGE
     * by PULSEDMG.
     */
    public void pulse() {
        if (hasAct == false) {
            hasAct = true;
            //get the enemies within range
            ArrayList<Entity> temp = map.aoe(pos, PULSERANGE);
            for (Entity e : temp) {
                int sc = e.getTeamID();
                //damage the team if an enemy team
                if (sc != this.getTeamID()) {
                    map.getTeams()[sc].subScore(100);
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

    //Carl is in charge of collisions
    protected void collideDrone(Drone other, int input) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            storage += input;
        }
    }

    public void collidePlanet(Planet other) {
        if (this.checkCollision(other) && teamID == other.teamID) {
            other.collideShip(this, storage);
            storage = 0;
        }
    }
}
