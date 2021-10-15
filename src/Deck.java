import java.util.*;

// Deck.java: 52 cards in stock. Each card is dealt from the deck.
public class Deck {
    private List<Card> cards;

    Deck(){
        cards = new ArrayList<Card>();
        for (int i = 0; i < 52; i++){
            String rank = "" + (( i + 1 ) % 13);
            rank = switch (rank) {
                case "1" -> "A";
                case "11" -> "J";
                case "12" -> "Q";
                case "0" -> "K";
                default -> rank;
            };

            String suit = "" + (i / 13);
            suit = switch (suit) {
                case "0" -> "♣";
                case "1" -> "♠";
                case "2" -> "♦";
                case "3" -> "♥";
                default -> suit;
            };
            cards.add(new Card(rank, suit));
        }
    }

    /*This is a function used for testing. */
    public void print_deck(){
        for (int i = 0; i < this.cards.size(); i++){
            System.out.println(this.cards.get(i));
        }
    }

    public void shuffle() {
        cards.clear();
        for (int i = 0; i < 52; i++) {
            String rank = "" + ((i + 1) % 13);
            rank = switch (rank) {
                case "1" -> "A";
                case "11" -> "J";
                case "12" -> "Q";
                case "0" -> "K";
                default -> rank;
            };

            String suit = "" + (i / 13);
            suit = switch (suit) {
                case "0" -> "♣";
                case "1" -> "♠";
                case "2" -> "♦";
                case "3" -> "♥";
                default -> suit;
            };
            cards.add(new Card(rank, suit));
        }
    }

    // The dealer or player draws.
    public Card draw(){
        int spot = (int)(Math.random()*cards.size());
        Card c = cards.get(spot);
        cards.remove(spot);
        return c;
    }
}
