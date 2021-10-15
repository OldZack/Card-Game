
// Card.java: Store the information in a single card.
public class Card {
    private String rank;
    private String suit;

    Card(String r, String s){
        rank = r;
        suit = s;
    }

    public String get_rank(){
        return this.rank;
    }

    public String get_suit(){
        return this.suit;
    }

    public String toString(){
        return suit + rank + " ";
    }

}
