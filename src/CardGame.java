import java.util.ArrayList;

public abstract class CardGame {

    protected ArrayList<Player> players = new ArrayList<Player>();
    protected Dealer dealer;
    protected Deck deck;

    public CardGame() {
        this.players.add(new Player());
        this.dealer = new Dealer();
        this.deck = new Deck();
    }

    public abstract void startGame();

    public abstract void round();

    public abstract int checkWinner(Hand h);

    public abstract void deal(Player p);

}
