package cowboymahjong.game;

import java.util.ArrayList;
import java.util.List;

public class RunStrategy implements CombinationStrategy {

    @Override
    public List<List<MahjongTile>> findCombinations(List<MahjongTile> hand) {
        List<List<MahjongTile>> results = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            for (int j = 0; j < hand.size(); j++) {
                for (int k = 0; k < hand.size(); k++) {
                    if (i == j || j == k || i == k) continue;

                    MahjongTile a = hand.get(i);
                    MahjongTile b = hand.get(j);
                    MahjongTile c = hand.get(k);

                    if (a.getSuit() == b.getSuit() && b.getSuit() == c.getSuit()
                            && a.getSuit() != MahjongTile.Suit.JOKER
                            && b.getValue() == a.getValue() + 1
                            && c.getValue() == b.getValue() + 1) {
                        List<MahjongTile> run = new ArrayList<>();
                        run.add(a);
                        run.add(b);
                        run.add(c);
                        results.add(run);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public String getName() {
        return "Run";
    }
}