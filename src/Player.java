import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Hand> hands;
    private int wallet;
    private int bet;

    // default marker is set to 'x'
    public Player() {
        this.hands = new ArrayList<>();
        this.wallet = 1000;
        this.bet = 0;
    }

    public Player(int fund) {
        this.wallet = fund;
    }

    // get the hands from the player
    public List<Hand> getHands() {
        return this.hands;
    }

    public int getWallet() {
        return this.wallet;
    }

    // set the player's wallet to the one provided
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

}
