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
public class PieceOutcome {
    Set<List<Square>> outcomes;
    
    PieceOutcome() {
        outcomes = new HashSet<>();
    }

    public void addDirection(List<Square> direction) {
        outcomes.add(direction);
    }

    public Set<List<Square>> getOutcomes() {
        return outcomes;
    }


}
