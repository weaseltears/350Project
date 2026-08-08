package cowboymahjong.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Context class for the Strategy pattern. Holds a list of CombinationStrategy
// implementations and runs the current hand against all of them.
public class CombinationChecker {

    private final List<CombinationStrategy> strategies = new ArrayList<>();

    public CombinationChecker() {
        strategies.add(new PairStrategy());
        strategies.add(new TripletStrategy());
        strategies.add(new RunStrategy());
    }

    public void addStrategy(CombinationStrategy strategy) {
        strategies.add(strategy);
    }

    // Runs every registered strategy against the hand and returns a map
    // of strategy name -> list of combinations found for that strategy.
    public Map<String, List<List<MahjongTile>>> checkHand(List<MahjongTile> hand) {
        Map<String, List<List<MahjongTile>>> results = new LinkedHashMap<>();
        for (CombinationStrategy strategy : strategies) {
            results.put(strategy.getName(), strategy.findCombinations(hand));
        }
        return results;
    }
}