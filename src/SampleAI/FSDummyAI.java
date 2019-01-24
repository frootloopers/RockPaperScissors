/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Blocks.Pos;
import Development.AIShell;
import static Development.Command.getTo;
import Entities.*;

/**
 * A very dumb AI that targets the first rock created in the game with its drones and makes its motherships go infinitely forward.
 * 
 * @author Jia Jia Chen
 */
public class FSDummyAI extends AIShell {

    public FSDummyAI() {
        name = "FSDummyAI";
    }

    @Override
    public void act() {
        ship.setThrustF(100);
        gather(drone1);
        gather(drone2);
    }

    private void gather(Drone drone) {
        Pos pos = drone.getMap().getHarvestData()[0].getPos();
        getTo(drone, pos, 1);
    }
    
        @Override
    public String getDesc() {
        return "A Dummy AI Algorithm";
    }

    @Override
    public String getName() {
        return "FSDummyAI";
    }

    @Override
    public String getAuthor() {
        return "Foresight Software Developers";
    }

}
