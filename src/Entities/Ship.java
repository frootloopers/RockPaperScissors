/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Foundation.Map;
import java.util.ArrayList;

/**
 *
 * @author John Popovici
 */
public class Ship extends Controllable {

    /**
     * Creates a ship entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Ship(double x, double y, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_SHIP, faceAngle, teamID, map);
    }

    //bullet 
    private final double FIREPOWER = 1;
    private final double FIRECOST = 1;
    private final double PULSERANGE = 10;
    private final double PULSEDMG = 100;
    private final double PULSECOST = 1;

    /**
     * By Jia Jia: This spawns a bullet in the map.
     */
    public void fireBullet() {
        if (hasAct) {
            hasAct = false;
            map.addBullet(new Bullet(pos.x, pos.y, faceAngle, FIREPOWER, teamID, map));
            storage-=FIRECOST;
        }
    }

    /**
     * By Jia Jia: Fire a pulse lowering the score of enemies within PULSERANGE
     * by PULSEDMG.
     */
    public void pulse() {
        if (hasAct) {
            hasAct = false;
            //get the enemies within range
            ArrayList<Entity> temp = map.aoe(pos, PULSERANGE);
            for (Entity e : temp) {
                int sc = e.getTeamID();
                //damage the team if an enemy team
                if (sc != this.getTeamID()) {
                    map.Teams[sc].subScore(100);
                }
            }
            storage-=PULSECOST;
        }
    }

    @Override
    protected void move() {
        //add acceleration
        vel.x += Math.sin(Math.toRadians(faceAngle)) * (thrustF / 100.0 * SHIP_STERN_STRENGTH);
        vel.y -= Math.cos(Math.toRadians(faceAngle)) * (thrustF / 100.0 * SHIP_STERN_STRENGTH);
        //add rotation
        faceAngle -= thrustRotR * SHIP_ROT_STRENGTH;
        faceAngle += thrustRotL * SHIP_ROT_STRENGTH;
        super.move();
    }

}
