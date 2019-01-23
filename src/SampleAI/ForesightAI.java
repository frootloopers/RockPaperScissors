/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Development.AI;
import Entities.*;

/**
 * A demo AI Algorithm created by Foresight Software
 *
 * @author John Popovici
 */
public class ForesightAI implements AI{
    
    private Planet planet;
    private Ship ship;
    private Drone drone1;
    private Drone drone2;

    //name entities

//    private Drone r = getTeams().getEntities(3);
//    
//    
//    private Drone l = getDrone();
//    private Ship s = getShip();
//    private Planet p = getPlanet();

    //dictates if drones are going to ship
    private boolean toShipR = false;
    private boolean toShipL = false;
    //dictates if ship is going to planet
    private boolean toPlanet = false;
    //dictates if drones are harvesting
    private boolean harvestingR = false;
    private boolean harvestingL = false;
    //end time
    private int end = 55000; //Map.getTime();
    
//    System.out.println(Map.getTime());
//    
//
//    if (Map.getTime() >= end) {
//        chase(s, p, 0);
//        chase(r, s, 50);
//        chase(l, s, 50);
//    }

    
    
    /*
    if (timer ending)
    > ship to planet
    > drone to ship
    
    SHIP
    if (bullet in range) deploy shied
    if (toShip) thrust = 0
    if (cargo >= 10 & drone stock not empty)
    > toPlanet = true
    > go to planet
    else
    > ship go between middle and planet (between max/2 and planet.getPos() )
    
    DRONE
    if (collide with bullet) dodge
    else if (!harvesting & resources below 5)
    > find one that minimizes distance on your side
    > go to harvestable
    > harvesting = false
    else if (!harveting & !toPlanet)
    > toShip = true & get to ship
    else if (!harvesting)
    > go toward ship but do not collide
    
    */
    
    @Override
    public void act() {

    }

    @Override
    public void setUnits(Planet planet, Ship ship, Drone drone1, Drone drone2){
        this.planet = planet;
        this.ship = ship;
        this.drone1 = drone1;
        this.drone2 = drone2;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
