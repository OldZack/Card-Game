import java.util.*;

public class Hand {
    private List<Card> cards;

    Hand(){
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

    public Hand split_hand(){
        Hand newCards = new Hand();
        newCards.add_card(cards.get(1));
        cards.remove(1);
        return newCards;
    }
}
