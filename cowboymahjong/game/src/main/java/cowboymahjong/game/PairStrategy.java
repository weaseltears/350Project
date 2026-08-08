package cowboymahjong.game;

import java.util.ArrayList;
import java.util.List;

public class PairStrategy implements CombinationStrategy {

    @Override
    public List<List<MahjongTile>> findCombinations(List<MahjongTile> hand) {
        List<List<MahjongTile>> results = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                MahjongTile a = hand.get(i);
                MahjongTile b = hand.get(j);
                if (a.getSuit() == b.getSuit() && a.getValue() == b.getValue()
                        && a.getSuit() != MahjongTile.Suit.JOKER) {
                    List<MahjongTile> pair = new ArrayList<>();
                    pair.add(a);
                    pair.add(b);
                    results.add(pair);
                }
            }
        }
        return results;
    }

    @Override
    public String getName() {
        return "Pair";
    }
}