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
