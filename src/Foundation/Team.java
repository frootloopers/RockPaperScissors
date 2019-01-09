/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foundation;

import Entities.Entity;

/**
 *
 * @author Jia Jia Chen
 */
public class Team {
    int score;
    String aiName;
    Entity[] Entities;
    
    public Team(Entity[] Entities){
        score = 0;
        this.Entities=Entities;
    }
}
