import java.util.*;

public class Hand {
    private List<Card> cards;

    public Hand(){
        cards = new ArrayList<>();
    }

    public void add_card(Card c){
        cards.add(c);
    }

    public boolean able_to_split(){
        return cards.get(0) == cards.get(1);
    }

    public List<Card> get_cards(){
        return cards;
    }

    public String displayCards() {
        String res = "";
        for (Card c : cards) {
            res += c.toString();
        }
        return res;
    }

    public int getValue(int ceiling) {
        int res = 0;
        boolean haveA = false;
        for (Card c : cards) {
            if (c.get_rank().equals("J") || c.get_rank().equals("Q") || c.get_rank().equals("K")) {
                res += 10;
            }
            else if (c.get_rank().equals("A")) {
                res += 1;
                haveA = true;
            }
            else {
                res += Integer.parseInt(c.get_rank());
            }
        }
        // if there exists A in cards and the rest value is smaller than 10, we convert A to 11.
        if (res <= ceiling - 10 && haveA) {
            res += 10;
        }
        return res;
    }

    public Hand split_hand(){
        Hand newCards = new Hand();
        newCards.add_card(cards.get(1));
        cards.remove(1);
        return newCards;
    }

    public void hit(Card c) {
        add_card(c);
    }

    // check if the current hand is split-able.
    public boolean ifAvailable() {
        return (get_cards().size() == 2 && get_cards().get(0).get_rank().equals(get_cards().get(1).get_rank()));
    }
}
