package cowboymahjong.game;

public class MahjongTile {
	public enum Suit {
		DOT, BAMBOO, CHARACTER, JOKER
	}
	
    private final String texturePath;
    private final Suit suit;
    private final int value;

    public MahjongTile(String texturePath) {
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