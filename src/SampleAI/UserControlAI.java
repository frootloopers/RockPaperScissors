/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleAI;

import Development.AIShell;

/**
 * An empty algorithm such that users can control their own entities. Best
 * chances for competing is with speed as low as possible
 *
 * @author John Popovici
 */
public class UserControlAI extends AIShell {

    public UserControlAI() {
        name = "UserControlAI";
    }

    @Override
    public void act() {
    }
    
        @Override
    public String getDesc() {
        return "A User Controlled AI Algorithm";
    }

    @Override
    public String getName() {
        return "UserControlAI";
    }

    @Override
    public String getAuthor() {
        return "Foresight Software Developers";
    }
}
