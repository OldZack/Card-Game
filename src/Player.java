import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Hand> hands;
    private int wallet;
    private int bet;

    // default marker is set to 'x'
    public Player() {
        Hand h = new Hand();
        this.hands = new ArrayList<>();
        hands.add(h);
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

    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet += bet;
    }

    public void resetBet() {
        this.bet = 0;
    }

    public void resetHands() {
        this.hands.clear();
        Hand h = new Hand();
        hands.add(h);
    }

}
