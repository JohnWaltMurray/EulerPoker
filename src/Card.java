
public class Card implements Comparable<Card> {
	
	private String suit;
	private int value;
	
	public Card(String v, String s) {
		
		if(v.matches("[TJQKA]")) {
			switch(v) {
			case "T":
				setValue(10);
				break;
			case "J":
				setValue(11);
				break;
			case "Q":
				setValue(12);
				break;
			case "K":
				setValue(13);
				break;
			case "A":
				setValue(14);
				break;
			}
		} else {
			setValue(Integer.parseInt(v));
		}
		setSuit(s);
		
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int compareTo(Card c) {
		if(this.getValue() > c.getValue()) {
			return 1;
		} else if (this.getValue() < c.getValue()) {
			return -1;
		}
		return 0;
	}
	
}
