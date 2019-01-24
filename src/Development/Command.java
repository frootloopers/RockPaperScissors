/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Game.Movable;
import Game.Controllable;
import Blocks.*;

/**
 * The methods in the Command class are used to assist with the creation of AI
 * algorithms. Users are encouraged to create their own base methods using the
 * thruster controls of the movable entities they control to better fit their
 * program and strategies. All methods available use only whatever methods any
 * user would have access to as well and are therefore allowed to be used as a
 * foundation to custom algorithms.
 *
 * @author John Popovici
 */
public class Command {

    /**
     * Turn a controllable entity to be facing towards a specific position
     *
     * @param c the controllable entity
     * @param pos the position to be faced
     * @param tolerance the tolerance value in degrees. Must be between 0.5 and
     * 90, inclusive. Larger values will make the entity count as facing if
     * farther away
     * @return boolean value true if facing the position within the tolerance
     * range. false value if not facing the position. Special case return true
     * if target position is the position of the entity
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static boolean turnTo(Controllable c, Pos pos, double tolerance) throws IllegalArgumentException {
        if (tolerance < 0.5 || tolerance > 90) {
            throw new IllegalArgumentException();
        }

        //calculate the difference in position axis values
        double difx = pos.x - c.getPos().x;
        double dify = pos.y - c.getPos().y;
        //measure both the current angle and the target angle
        double currentAngle = c.getFaceAngle();
        double targetAngle;

        //determines the target angle
        if (difx == 0.0 && dify == 0.0) { //special case, target is current position
            c.setThrustRotR(0);
            c.setThrustRotL(0);
            return true;
        } else if (difx >= 0.0 && dify <= 0.0) { //top right, x positive, y negative
            targetAngle = (Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        } else if (difx >= 0.0 && dify >= 0.0) { //bottom right, x positive, y positive
            targetAngle = (180 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        } else if (difx <= 0.0 && dify >= 0.0) { //bottom left, x negative, y positive
            targetAngle = (180 + Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        } else { //if (x <= 0.0 && y <= 0.0) { //top left, x negative, y negative
            targetAngle = (360 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        }

        //returns if already facing
        //accounts for the facing angle being a whole rotation away from the target angle
        if ((currentAngle <= targetAngle + tolerance && currentAngle >= targetAngle - tolerance)
                || (currentAngle <= targetAngle + tolerance - 360 && currentAngle >= targetAngle - tolerance - 360)
                || (currentAngle <= targetAngle + tolerance + 360 && currentAngle >= targetAngle - tolerance + 360)) {
            c.setThrustRotR(0);
            c.setThrustRotL(0);
            return true;
        }

        //if facing too far right, use right thruster else use left thruster
        if ((currentAngle > targetAngle && currentAngle <= targetAngle + 180)
                || (currentAngle < targetAngle && currentAngle <= targetAngle - 180)) {
            c.setThrustRotR(100);
            //slow down thruster as approach target angle
            if (Math.abs(targetAngle - currentAngle) <= 2.0) {
                c.setThrustRotR((int) (Math.abs(currentAngle - targetAngle) * 50));
            }
            c.setThrustRotL(0);
        } else {
            c.setThrustRotL(100);
            //slow down thruster as approach target angle
            if (Math.abs(targetAngle - currentAngle) <= 2.0) {
                c.setThrustRotL((int) (Math.abs(currentAngle - targetAngle) * 50));
            }
            c.setThrustRotR(0);
        }
        return false;
    }

    /**
     * Move a controllable entity to a specific position
     *
     * @param c the controllable entity
     * @param pos the position to be moved to
     * @param tolerance the tolerance value in pixels away. Must be between 0.5
     * and 300, inclusive. Larger values will make the entity count as moved if
     * farther away
     * @return boolean value true if at the position within the tolerance range.
     * false value if not at the position
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static boolean getTo(Controllable c, Pos pos, double tolerance) throws IllegalArgumentException {
        if (tolerance < 0.5 || tolerance > 300) {
            throw new IllegalArgumentException();
        }

        //calculate distance from entity to destination
        double dist = distance(c.getPos(), pos);

        //return if already at position within tollerance
        if (tolerance >= dist) {
            c.setThrustF(0);
            return true;
        }

        //if facing at a difference of 60 degrees, start accelerating
        //else if facing, accelerate towards target depending on distance
        //final turnTo method will turn entity towards target
        if (turnTo(c, pos, 30) && !turnTo(c, pos, 0.5)) {
            c.setThrustF(50);
        } else if (turnTo(c, pos, 0.5)) {
            if (dist <= 50.0) {
                c.setThrustF((int) (dist * 2));
            } else {
                c.setThrustF(100);
            }
        }
        return false;
    }

    /**
     * Calculates the position the movable entity will be at in a certain amount
     * of frames based off of the current velocity and direction. Cannot account
     * for change in direction of velocity
     *
     * @param m the movable entity
     * @param frames the amount of frames in which the position is estimated.
     * The value must be greater than or equal to 0
     * @return the position the entity is predicted to be at
     * @throws IllegalArgumentException for a value of frames out of bounds
     */
    public static Pos willBe(Movable m, int frames) throws IllegalArgumentException {
        if (frames < 0) {
            throw new IllegalArgumentException();
        }
        return new Pos(m.getPos().x + (m.getVel().x * frames),
                m.getPos().y + (m.getVel().y * frames));
    }

    /**
     * Calculates the distance between two positions
     *
     * @param p1 the first position
     * @param p2 the second position
     * @return the distance between the positions as a double
     */
    public static double distance(Pos p1, Pos p2) {
        return Math.sqrt(Math.pow(Math.abs(p1.x - p2.x), 2) + Math.pow(Math.abs(p1.y - p2.y), 2));
    }

    /**
     * Check if a controllable entity will collide with a movable entity. Cannot
     * account for change in direction of velocities
     *
     * @param c the controllable entity
     * @param m the movable entity
     * @param tolerance the tolerance value in pixels away. Must be between 0.5
     * and 20, inclusive. Larger values will make the entities count as collided
     * even if further away
     * @return boolean value if the entities are calculated to collide
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static boolean willCollide(Controllable c, Movable m, double tolerance) throws IllegalArgumentException {
        double dist = c.getRadius() + m.getRadius() + tolerance;
        for (int i = 5; i <= 150; i += 5) {
            if (distance(willBe(c, i), willBe(m, i)) <= dist) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move a controllable entity to intercept the future position of a movable
     * entity in a certain amount of frames
     *
     * @param c the controllable entity
     * @param m the movable entity
     * @param frames the amount of frames in which the position of the movable.
     * The value must be greater than or equal to 0
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static void chase(Controllable c, Movable m, int frames) throws IllegalArgumentException {
        Pos pos = willBe(m, frames);

        //if facing at a difference of 60 degrees, accelerating towards target
        //continue turning towards target
        if (turnTo(c, pos, 60)) {
            c.setThrustF(100);
        }
        turnTo(c, pos, 0.5);
    }

    /**
     * Controls the controllable entity to circle around
     *
     * @param c the controllable entity
     * @param turnThrust the thrust of the rotation. The value must be between 0
     * and 100, inclusive
     * @param clockwise the direction of the rotation. A true value indicates a
     * clockwise rotation while a false indicates a counterclockwise rotation
     * @throws IllegalArgumentException for a value of turnThrust out of bounds
     */
    public static void circle(Controllable c, int turnThrust, boolean clockwise) throws IllegalArgumentException {
        c.setThrustF(100);
        if (clockwise) {
            c.setThrustRotL(turnThrust);
            c.setThrustRotR(0);
        } else {
            c.setThrustRotL(0);
            c.setThrustRotR(turnThrust);
        }
    }

}
