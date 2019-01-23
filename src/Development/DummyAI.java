/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Blocks.Pos;
import static Development.Command.getTo;
import Entities.*;

/**
 *
 * @author Jia Jia Chen
 */
public class DummyAI extends AIShell {

    public DummyAI() {
        name = "Dummy";
    }

    @Override
    public void act() {
        ship.setThrustF(100);
        gather(drone1);
        gather(drone2);
    }

    private void gather(Drone drone) {
        Pos pos = drone.getMap().getHarvest()[0].getPos();
        getTo(drone, pos, 1);
    }

}
