/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jchen
 */
public class PieceOutcome { //represents all the outcomes for a piece
    //set of list of outcomes
    //set contains all directions
    //list contains each outcome
    //outcomes contaisn the index differences
    Set<List<Outcome>> outcomes = new HashSet<>();

    public void addDirection(List<Outcome> direction) { //add a new direction
        outcomes.add(direction);
    }

    public Set<List<Outcome>> getOutcomes() { //get outcomes
        return outcomes;
    }
}
