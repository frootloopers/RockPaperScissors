/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Blocks.*;
import Entities.*;

/**
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
     * 90, inclusive
     * @return boolean value true if facing the position within the tolerance
     * range. false value if not. Special case return true if target position is
     * the position of the entity
     * @throws IllegalArgumentException for a value of tolerance out of bounds
     */
    public static boolean turnToFace(Controllable c, Pos pos, double tolerance) throws IllegalArgumentException {
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
                || (currentAngle <= targetAngle + tolerance - 180 && currentAngle >= targetAngle - tolerance - 180)
                || (currentAngle <= targetAngle + tolerance + 180 && currentAngle >= targetAngle - tolerance + 180)) {
            c.setThrustRotR(0);
            c.setThrustRotL(0);
            return true;
        }

        //if facing too far right, use right thruster else use left thruster
        if ((currentAngle > targetAngle && currentAngle < targetAngle + 180)
                || (currentAngle < targetAngle && currentAngle < targetAngle - 180)) {
            c.setThrustRotR(100);
            //slow down thruster as approach target angle
            if (currentAngle - targetAngle <= 2.0) {
                c.setThrustRotR((int) ((currentAngle - targetAngle) * 50));
            }
        } else {
            c.setThrustRotL(100);
            //slow down thruster as approach target angle
            if (targetAngle - currentAngle <= 2.0) {
                c.setThrustRotL((int) ((currentAngle - targetAngle) * 50));
            }
        }
        return false;

    }

}


//getTo (position)
//isFacing (with tollerance)
//isAt (with tollerance)

