import java.util.ArrayList;

public abstract class CardGame {

    protected ArrayList<Player> players = new ArrayList<Player>();
    protected Dealer dealer = new Dealer();
    protected Deck deck = new Deck();

    public abstract void startGame();

    public abstract void round();

    public abstract void checkWinner();

}
