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
public class Bullet extends Movable {

    /**
     * Creates a bullet entity
     *
     * @param x the position value of the entity on the x-axis
     * @param y the position value of the entity on the y-axis
     * @param speed the speed the entity starts with
     * @param faceAngle the angle the entity faces when created
     * @param teamID the ID of the team this entity belongs to
     * @param map the map the entity is in
     */
    public Bullet(double x, double y, double speed, double faceAngle, int teamID, Map map) {
        super(x, y, RADIUS_PROJECTILE, speed, faceAngle, teamID, map);
    }
}
