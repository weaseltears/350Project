package cowboymahjong.game;

import java.util.ArrayList;
import java.util.List;

public class TripletStrategy implements CombinationStrategy {

    @Override
    public List<List<MahjongTile>> findCombinations(List<MahjongTile> hand) {
        List<List<MahjongTile>> results = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                for (int k = j + 1; k < hand.size(); k++) {
                    MahjongTile a = hand.get(i);
                    MahjongTile b = hand.get(j);
                    MahjongTile c = hand.get(k);
                    if (a.getSuit() == b.getSuit() && b.getSuit() == c.getSuit()
                            && a.getValue() == b.getValue() && b.getValue() == c.getValue()
                            && a.getSuit() != MahjongTile.Suit.JOKER) {
                        List<MahjongTile> triplet = new ArrayList<>();
                        triplet.add(a);
                        triplet.add(b);
                        triplet.add(c);
                        results.add(triplet);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public String getName() {
        return "Triplet";
    }
}