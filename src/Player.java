import java.util.ArrayList;
import java.util.List;

// Player.java: The Player in the card game, it's scalable to allow multiple players.
public class Player implements Comparable<Player> {
    private String name;
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

    public Player(String n) {
        name = n;
        Hand h = new Hand();
        this.hands = new ArrayList<>();
        hands.add(h);
        this.wallet = 1000;
        this.bet = 0;
    }

    public Player(int fund) {
        this.wallet = fund;
    }

    public String get_name(){
        return this.name;
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

    public int compareTo(Player p){
        if (this.wallet == p.getWallet()){
            return 0;
        } else if(this.wallet > p.getWallet()){
            return 1;
        } else{
            return -1;
        }
    }

}
