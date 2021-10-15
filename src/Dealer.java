import java.util.ArrayList;
import java.util.List;

// Dealer.java: The opponent to the player, deals cards and interacts with players.
public class Dealer {
    private List<Hand> hands;
    private int wallet;

    public Dealer() {
        this.hands = new ArrayList<Hand>();
        Hand h = new Hand();
        hands.add(h);
        this.wallet = 1000;
    }

    public Dealer(int fund) {
        this.wallet = fund;
    }

    // get the hands from the player
    public List<Hand> getHands() {
        return this.hands;
    }

    public int getWallet(){
        return this.wallet;
    }

    // set the player's wallet to the one provided
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void resetHands() {
        this.hands.clear();
        Hand h = new Hand();
        hands.add(h);
    }

}
