package cowboymahjong.game;

import java.util.ArrayList;
import java.util.List;

// Strategy interface: each implementation defines its own rule
// for what counts as a valid combination within a hand.
public interface CombinationStrategy {

    // Returns all combinations found in the hand that satisfy this strategy's rule.
    // Each combination is a list of the MahjongTile objects that make it up.
    List<List<MahjongTile>> findCombinations(List<MahjongTile> hand);

    String getName();
}