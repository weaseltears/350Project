package cowboymahjong.game;

public class MahjongTile {

    public enum Suit {
        DOT, BAMBOO, CHARACTER, JOKER
    }

    private final String texturePath;
    private final Suit suit;
    private final int value; // 1-9 for normal tiles, 0 for jokers

    public MahjongTile(String texturePath, Suit suit, int value) {
        this.texturePath = texturePath;
        this.suit = suit;
        this.value = value;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}