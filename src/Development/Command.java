/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Blocks.*;
import Entities.*;

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
     * Turn the entity to be facing towards the position
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
            return true;
        } else if (difx >= 0.0 && dify <= 0.0) { //top right, x positive, y negative
            targetAngle = (Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        } else if (difx >= 0.0 && dify >= 0.0) { //bottom right, x positive, y positive
            targetAngle = (180 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
        } else if (difx <= 0.0 && dify >= 0.0) { //bottom left, x negative, y positive
            targetAngle = (270 - Math.toDegrees(Math.atan(Math.abs(difx) / Math.abs(dify))));
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
        } else {
            c.setThrustRotL(100);
            //slow down thruster as approach target angle
            if (Math.abs(targetAngle - currentAngle) <= 2.0) {
                c.setThrustRotL((int) (Math.abs(currentAngle - targetAngle) * 50));
            }
        }
        return false;

    }

    /**
     * Move the entity to a specific position
     *
     * @param c the controllable entity
     * @param pos the position to be moved to
     * @param tolerance the tolerance value in pixels away. Must be between 2.5
     * and 300, inclusive. Larger values will make the entity count as moved if
     * farther away
     * @return boolean value true if at the position within the tolerance range.
     * false value if not at the position
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static boolean getTo(Controllable c, Pos pos, double tolerance) throws IllegalArgumentException {
        if (tolerance < 2.5 || tolerance > 300) {
            throw new IllegalArgumentException();
        }

        //calculate distance from entity to destination
        double dist = Math.sqrt(Math.pow(Math.abs(c.getPos().x - pos.x), 2) + Math.pow(Math.abs(c.getPos().y - pos.y), 2));

        //return if already at position within tollerance
        if (tolerance >= dist) {
            return true;
        }

        //if facing at a difference of 60 degrees, start accelerating
        //else if facing, accelerate towards target depending on distance
        //final turnTo method will turn entity towards target
        if (turnTo(c, pos, 60) && !turnTo(c, pos, 0.5)) {
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

}

//getTo (position)
//isFacing (with tollerance)
//isAt (with tollerance)

