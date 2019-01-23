/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Blocks.Pos;
import Development.AIShell;
import static Development.Command.*;
import Entities.*;
import java.util.Random;

/**
 * A demo AI Algorithm created by Foresight Software
 *
 * @author John Popovici
 */
public class ForesightAI extends AIShell {

    public ForesightAI() {
        name = "ForesightAI";
    }

    //name entities
    private final Drone r = drone1;
    private final Drone l = drone2;
    private final Ship s = ship;
    private final Controllable[] cs = {s, r, l};
    private final Planet p = planet;
    private final Map m = drone1.getMap();
    //dictates if drones are going to ship
    private boolean[] toShip = {true, false, false}; //burn, r, l
    //dictates if ship is going to planet
    private boolean toPlanet = false;
    //dictates if drones are harvesting
    private boolean[] harvesting = {true, false, false}; //burn, r, l
    //changeable variables
    private final int END_TIME = 285000; //Map.getTime();
    private final int COLLIDE_TOLERANCE = 10;
    private final int SHIP_MAX = 50;
    private final int DRONE_MAX = 25;

    @Override
    public void act() {
        //commands each entity
        for (int i = 0; i < 3; i++) {
            //calculates if enetity will be hit
            //special commands for end of game
            if (willBeHit(cs[i], i)) {
                avoidBeHit(cs[i]);
            } else if (m.getTime() >= END_TIME) {
                endGame();
            } else if (i == 0) {
                playGameShip();
            } else {
                playGameDrone(cs[i], i);
            }
        }
    }

    private void playGameShip() {
        toPlanet = false;
        if (toShip[1] || toShip[2]) {
            s.setThrustF(0);
        } else if (s.getStorage() >= SHIP_MAX && (r.getStorage() > 0 || r.getStorage() > 0)) {
            toPlanet = true;
            getTo(s, p.getPos(), 0.5);
        } else {
            //if in default position
            if (getTo(s, new Pos(((m.getMax().x / 2) + p.getPos().x) / 2, ((m.getMax().y / 2) + p.getPos().y) / 2), 10)) {
                //for every ship if still, turn to and fire
                for (int i = 0; i < m.getControllables().length; i++) {
                    if (m.getControllables()[i].getVel().getSpeed() == 0) {
                        if (turnTo(s, m.getControllables()[i].getPos(), 1)) {
                            s.fireBullet();
                        }
                    }
                }
            }
        }
    }

    private void playGameDrone(Controllable c, int k) {
        toShip[k] = false;
        if (!harvesting[k] && ((Drone) (cs[k])).getStorage() < DRONE_MAX) {
            //Change to calculate smallest distance
            int harvestMax = m.getHarvest().length;
            Random rand = new Random();
            int randomNum = rand.nextInt((harvestMax));
            Pos toPos = new Pos(m.getHarvest()[randomNum].getPos().x, m.getHarvest()[randomNum].getPos().y);
            harvesting[k] = true;
            if (getTo(cs[k], toPos, 1)) {
                harvesting[k] = false;
            }
        } else if (!harvesting[k] && !toPlanet) {
            toShip[k] = true;
            chase(cs[k], s, 50);
        } else if (!harvesting[k]) {
            getTo(cs[k], s.getPos(), 50);
        }
    }

    private void avoidBeHit(Controllable c) {
        boolean thrustF = c.getThrustF() > 50;
        boolean thrustRot = c.getThrustRotL() > 50 || c.getThrustRotR() > 50;
        //if moving and rotating do nothing
        //if moving and not rotaing, stop moving
        //if not moving, move and rotate in a random direction
        if (thrustF && thrustRot) {
        } else if (thrustF && !thrustRot) {
            c.setThrustF(0);
        } else if (!thrustF) {
            Random rand = new Random();
            circle(c, 100, rand.nextBoolean());
        }

    }

    private boolean willBeHit(Controllable c, int k) {
        for (int i = 0; i < m.getBullets().size(); i++) {
            //pulses if bullets too close
            if (k == 0 && distance(c.getPos(), m.getBullets().get(i).getPos()) <= 30 && c.getStorage() > 0) {
                s.pulse();
            }
            //checks if will collide
            if (willCollide(c, m.getBullets().get(i), COLLIDE_TOLERANCE)) {
                return true;
            }
        }

        return false;
    }

    private void endGame() {
        //returns all resources to the planet
        getTo(s, p.getPos(), 0.5);
        chase(r, s, 50);
        chase(l, s, 50);
    }

}
