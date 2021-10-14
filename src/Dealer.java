import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private List<Hand> hands;
    private int wallet;

    public Dealer() {
        this.hands = new ArrayList<Hand>();
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

}
