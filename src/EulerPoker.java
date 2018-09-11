import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class EulerPoker {
	private static int Player1TieRank = 0;
	private static int Player2TieRank = 0;
	private static int PairRank = 0;
	public static void main(String[] args) {
		try {
			FileReader reader = new FileReader("resources/poker.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String pokerString = "a";
			String player1 = "";
			String player2 = "";
			ArrayList<Card> player1Hand = new ArrayList<Card>();
			ArrayList<Card> player2Hand = new ArrayList<Card>();
			int Player1WinCount = 0;
			int Player1HandRank = 0;
			
			int Player2WinCount = 0;
			int Player2HandRank = 0;
			
			try {
				pokerString = bufferedReader.readLine();
				while(pokerString != null) {
					
					player1 = pokerString.substring(0, 15);
					player2 = pokerString.substring(15, pokerString.length());
					player1Hand = convertString(player1);
					player2Hand = convertString(player2);
					Collections.sort(player1Hand);
					Collections.sort(player2Hand);
					
					if(RoyalFlush(player1Hand)) {
						Player1HandRank = 10;
					} else if (StraightFlush(player1Hand)) {
						Player1HandRank = 9;
					} else if (FourOf(player1Hand)) {
						Player1HandRank = 8;
						Player1TieRank = PairRank;
						PairRank = 0;
					} else if (FullHouse(player1Hand)) {
					 	Player1HandRank = 7;
					} else if (Flush(player1Hand)) {
						Player1HandRank = 6;
					} else if (Straight(player1Hand)) {
						Player1HandRank = 5;
					} else if (ThreeOf(player1Hand)) {
						Player1HandRank = 4;
						Player1TieRank = PairRank;
						PairRank = 0;
					} else if (TwoPairs(player1Hand)) {
						Player1HandRank = 3;
						Player1TieRank = PairRank;
						PairRank = 0;
					} else if (OnePair(player1Hand)) {
						Player1HandRank = 2;
						Player1TieRank = PairRank;
						PairRank = 0;
					} else {
						Player1HandRank = 1;
					}
					
					if(RoyalFlush(player2Hand)) {
						Player2HandRank = 10;
					} else if (StraightFlush(player2Hand)) {
						Player2HandRank = 9;
					} else if (FourOf(player2Hand)) {
						Player2HandRank = 8;
						Player2TieRank = PairRank;
						PairRank = 0;
					} else if (FullHouse(player2Hand)) {
						Player2HandRank = 7;
					} else if (Flush(player2Hand)) {
						Player2HandRank = 6;
					} else if (Straight(player2Hand)) {
						Player2HandRank = 5;
					} else if (ThreeOf(player2Hand)) {
						Player2HandRank = 4;
						Player2TieRank = PairRank;
						PairRank = 0;
					} else if (TwoPairs(player2Hand)) {
						Player2HandRank = 3;
						Player2TieRank = PairRank;
						PairRank = 0;
					} else if (OnePair(player2Hand)) {
						Player2HandRank = 2;
						Player2TieRank = PairRank;
						PairRank = 0;
					} else {
						Player2HandRank = 1;
					}
					
					if(Player1HandRank > Player2HandRank) {
						System.out.println(pokerString+"\t Winner: Player 1 by "+Player1HandRank);
						Player1WinCount++;
					} else if (Player1HandRank == Player2HandRank) {
						boolean winner = false;
						if(Player1TieRank != 0 && Player2TieRank != 0) {
							if(Player1TieRank > Player2TieRank) {
								System.out.println(pokerString+"\t Winner: High pair Player 1 by "+Player1HandRank);
								Player1WinCount++;
								winner = true;
							} else if(Player1TieRank < Player2TieRank) {
								System.out.println(pokerString+"\t Winner: High pair Player 2 by "+Player2HandRank);
								Player2WinCount++;
								winner = true;
							}
						}
						while(!winner) {
							if(HighCard(player1Hand) > HighCard(player2Hand)) {
								System.out.println(pokerString+"\t Winner: High card Player 1 by "+Player1HandRank);
								Player1WinCount++;
								winner = true;
							} else if(HighCard(player1Hand) < HighCard(player2Hand)) {
								System.out.println(pokerString+"\t Winner: High card Player 2 by "+Player2HandRank);
								Player2WinCount++;
								winner = true;
							} else {
								player1Hand.remove(player1Hand.size()-1);
								player2Hand.remove(player2Hand.size()-1);
							}
						}
					} else if (Player1HandRank < Player2HandRank) {
						System.out.println(pokerString+"\t Winner: Player 2 by "+Player2HandRank);
						Player2WinCount++;
					}
					Player2TieRank = 0;
					Player2TieRank = 0;
					PairRank = 0;
					pokerString = bufferedReader.readLine();
				}
				
				System.out.println("Player 1 Wincount: "+Player1WinCount);
				System.out.println("Player 2 Wincount: "+Player2WinCount);
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	
	//Converts the hand text to a list of Card objects
	public static ArrayList<Card> convertString(String s) {
		ArrayList<Card> cardList = new ArrayList<Card>();
		for(int i = 0; i < s.length(); i=i+3) {
			Card c = new Card(s.substring(i,i+1), s.substring(i+1, i+2));
			cardList.add(c);
		}
		return cardList;
	}
	
	public static boolean RoyalFlush(ArrayList<Card> hand) {
		String handSuit = hand.get(0).getSuit();
		for(Card c: hand) {
			if(!c.getSuit().equals(handSuit)) {
				return false;
			}
			if(c.getValue() < 10) {
				return false;
			}
		}
		return true;
	}
	public static boolean Flush(ArrayList<Card> hand) {
		String handSuit = hand.get(0).getSuit();
		for(Card c: hand) {
			if(!c.getSuit().equals(handSuit)) {
				return false;
			}
		}
		return true;
	}
	public static boolean Straight(ArrayList<Card> hand) {
		int lastValue = hand.get(0).getValue();
		for(int i = 1; i < hand.size(); i++) {
			if(hand.get(i).getValue() != lastValue+1) {
				return false;
			}
			lastValue = hand.get(i).getValue();
		}
		return true;
	}
	public static boolean StraightFlush(ArrayList<Card> hand) {
		return (Flush(hand)&&Straight(hand));
	}
	public static boolean FourOf(ArrayList<Card> hand) {
		int counter = 1;
		int lastValue = hand.get(0).getValue();
		for(int i = 1; i < hand.size(); i++) {
			if(counter == 4) {
				PairRank = lastValue;
				return true;
			}
			if(hand.get(i).getValue() == lastValue) {
				counter++;
			} else {
				counter = 1;
			}
			lastValue = hand.get(i).getValue();
		}
		if(counter == 4) {
			PairRank = lastValue;
			return true;
		}
		return false;
	}
	public static boolean FullHouse(ArrayList<Card> hand) {
		boolean full1 = (hand.get(0).getValue() == hand.get(1).getValue())
						&& (hand.get(1).getValue() == hand.get(2).getValue())
						&& (hand.get(3).getValue() == hand.get(4).getValue());
		
		boolean full2 = (hand.get(0).getValue() == hand.get(1).getValue())
						&& (hand.get(2).getValue() == hand.get(3).getValue())
						&& (hand.get(3).getValue() == hand.get(4).getValue());
		
		return (full1 || full2);
	}
	public static boolean ThreeOf(ArrayList<Card> hand) {
		int counter = 1;
		int lastValue = hand.get(0).getValue();
		for(int i = 1; i < hand.size(); i++) {
			if(counter == 3) {
				PairRank = lastValue;
				return true;
			}
			if(hand.get(i).getValue() == lastValue) {
				counter++;
			} else {
				counter = 1;
			}
			lastValue = hand.get(i).getValue();
		}
		if(counter == 3) {
			PairRank = lastValue;
			return true;
		}
		return false;
	}
	public static boolean TwoPairs(ArrayList<Card> hand) {
		
		int pairCount = 0;
		int lastValue = hand.get(0).getValue();
		for(int i = 1; i < hand.size(); i++) {
			if(pairCount == 2) {
				return true;
			}
			if(hand.get(i).getValue() == lastValue) {
				if(PairRank < lastValue) {
					PairRank = lastValue;
				}
				pairCount++;
			}
			lastValue = hand.get(i).getValue();
		}
		if(pairCount == 2) {
			return true;
		}
		PairRank = 0;
		return false;
	}
	public static boolean OnePair(ArrayList<Card> hand) {
		int lastValue = hand.get(0).getValue();
		for(int i = 1; i < hand.size(); i++) {
			if(hand.get(i).getValue() == lastValue) {
				PairRank = lastValue;
				return true;
			} 
			lastValue = hand.get(i).getValue();
		}
		return false;
	}
	public static int HighCard(ArrayList<Card> hand) {
		return hand.get(hand.size()-1).getValue();
	}
	
}
