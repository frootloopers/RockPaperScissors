/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Development;

import Entities.*;

/**
 *
 * @author Jia Jia Chen
 */
public class DummyAI extends AIShell{
    
    public DummyAI() {
        name="Dummy";
    }
    
    @Override
    public void act() {
        ship.setThrustF(100);
    }
    
}
