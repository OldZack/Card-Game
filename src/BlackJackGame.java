import java.util.Scanner;

// BlackJackGame.java: Control the process of Black Jack and store information of the game.
public class BlackJackGame extends CardGame{

    // The maximum value the player can not exceed.
    private final int maxValue = 21;
    // The minimum value the Dealer should exceed while hitting.
    private final int minValue = 17;

    public BlackJackGame() {
        super();
    }

    @Override
    public void deal(Player p) {
        dealer.getHands().get(0).add_card(deck.draw());
        dealer.getHands().get(0).add_card(deck.draw());
        p.getHands().get(0).add_card(deck.draw());
        p.getHands().get(0).add_card(deck.draw());
    }

    @Override
    public void startGame() {

        System.out.println("Welcome to Black Jack!");
        boolean broke = false;
        while(!broke) {
            round();
            players.removeIf(p -> p.getWallet() <= 0);
            if (players.size() == 0) {
                broke = true;
            }
            System.out.println("Would you like to cash out? Press Y or N.");
            Scanner input = new Scanner(System.in);
            String pattern = "[YN]";
            String yOrN = "";
            while(true) {
                yOrN = input.next();
                if(!yOrN.matches(pattern)) {
                    System.out.println("Error: Wrong input! Please input again.");
                    continue;
                }
                else
                    break;
            }
            if (yOrN.equals("Y")) {
                if (broke) {
                    System.out.println("You already went broke gg.");
                    break;
                }
                System.out.println("You cashed out. Bye.");
                break;
            }
        }

    }

    @Override
    public void round() {

        deck.shuffle();
        dealer.resetHands();
        for (Player p : players) {
            p.resetHands();
        }
        Hand dh = dealer.getHands().get(0);
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).resetBet();
            deal(players.get(i));
            // The Dealer deals two cards to the Player.
            System.out.println("The two cards dealt to you are:");
            System.out.println(players.get(i).getHands().get(0).displayCards());
            System.out.format("You have %d now.\n", players.get(i).getWallet());
            // The Dealer is dealt two cards, one face up and another down.
            System.out.format("The Dealer gets %s and another unknown. \n", dealer.getHands().get(0).get_cards().get(0));
            System.out.println("Dear Player, how much do you want to bet?");
            while (true) {
                try {
                    int amount = Integer.parseInt(input.next());
                    if (amount > players.get(i).getWallet()) {
                        System.out.format("The amount is out of what you have(%d). Please input again.\n", players.get(i).getWallet());
                        continue;
                    } else {
                        players.get(i).setBet(amount);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Wrong amount! Please input again.");
                }
            }

        }

        // allowed for multiple players
        for (int i = 0; i < players.size(); i++) {
            // for player's each hand
            for (int j = 0; j < players.get(i).getHands().size(); j++) {
                Hand hand = players.get(i).getHands().get(j);
                boolean end = false;
                while(hand.getValue(maxValue) <= maxValue && !end ) {
                    System.out.println("Your turn, what would you like to do next:(just choose the number)");
                    System.out.println("1.Hit\n" +
                            "2.Stand\n" +
                            "3.Split\n" +
                            "4.Double Up");
                    String pattern = "[1234]";
                    while(true) {
                        String action = input.next();
                        if(!action.matches(pattern)) {
                            System.out.println("Error: Wrong input! Please input again.");
                            continue;
                        }
                        else {
                            int n = Integer.parseInt(action);
                            if(n==1) { // Hit
                                Card c = deck.draw();
                                hand.hit(c);
                                System.out.println("You got " + c.toString() + ".");
                                // If the Player goes bust.
                                if(hand.getValue(maxValue) > maxValue) {
                                    System.out.println("Bust.");
                                    players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
                                    System.out.format("You have %d now.\n", players.get(i).getWallet());
                                    end = true;
                                }
                                // current hand hits and gets 21
                                else if(hand.getValue(maxValue) == maxValue) {
                                    System.out.println("Your hand value is 21. ");
                                    int value = dh.getValue(minValue);
                                    while(value < minValue) {
                                        Card tempC = deck.draw();
                                        dh.hit(tempC);
                                        System.out.println("Dealer drew " + tempC.toString() + ".");
                                        value = dh.getValue(minValue);
                                    }
                                    int winner = checkWinner(hand);
                                    if (winner > 0) {
                                        players.get(i).setWallet(players.get(i).getWallet() + players.get(i).getBet());
                                    }
                                    if (winner < 0) {
                                        players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
                                    }
                                    System.out.format("You have %d now.\n", players.get(i).getWallet());
                                    end = true;
                                }
                                break;
                            }else if(n==2) { // Stand
                                System.out.println("The face down card is revealed by the Dealer:" + dh.get_cards().get(1));
                                int value = dh.getValue(minValue);
                                while(value < minValue) {
                                    Card tempC2 = deck.draw();
                                    dh.hit(tempC2);
                                    System.out.println("Dealer drew " + tempC2.toString() + ".");
                                    value = dh.getValue(minValue);
                                }
                                int winner = checkWinner(hand);
                                if (winner > 0) {
                                    players.get(i).setWallet(players.get(i).getWallet() + players.get(i).getBet());
                                }
                                if (winner < 0) {
                                    players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
                                }
                                System.out.format("You have %d now.\n", players.get(i).getWallet());
                                end = true;
                                break;
                            }else if(n==3) { // Split
                                if(!hand.ifAvailable()) {
                                    System.out.println("You can not split.");
                                    continue;
                                }
                                else {
                                    Hand dupeHand = hand.split_hand();
                                    hand.hit(deck.draw());
                                    dupeHand.hit(deck.draw());
                                    System.out.println("One hand:" + hand.displayCards() +"  The other hand:" + dupeHand.displayCards());
                                    players.get(i).getHands().add(dupeHand);
                                }
                                //end = true;
                                break;
                            }
                            else if (n == 4) { // Double Up
                                int bet = players.get(i).getBet();
                                Card c4 = deck.draw();
                                hand.hit(c4);
                                System.out.println("You drew " + c4 + ".");
                                System.out.println("The face down card is revealed by the Dealer:" + dh.get_cards().get(1));
                                int value = dh.getValue(minValue);
                                while(value < minValue) {
                                    Card tempC3 = deck.draw();
                                    dh.hit(tempC3);
                                    System.out.println("Dealer drew " + tempC3.toString() + ".");
                                    value = dh.getValue(minValue);
                                }
                                int winner = checkWinner(hand);
                                if (winner > 0) {
                                    players.get(i).setWallet(players.get(i).getWallet() + 2*bet);
                                }
                                if (winner < 0) {
                                    players.get(i).setWallet(players.get(i).getWallet() - 2*bet);
                                }
                                System.out.format("You have %d now.\n", players.get(i).getWallet());
                                end = true;
                                break;
                            }
                        }
                    }
                }
            }


        }

    }


    @Override
    // only called when player didn't go bust.
    public int checkWinner(Hand h) {

        int value = dealer.getHands().get(0).getValue(minValue);
        // The Player wins.
        if (h.getValue(maxValue) > maxValue) {
            System.out.format("Your value is %d.\n", h.getValue(maxValue));
            System.out.println("You already Bust.");
            return -1;
        }
        if((value > 21) || value < h.getValue(maxValue)) {
            System.out.format("Your value is %d, and the Dealer's is %d.\n", h.getValue(maxValue), value);
            System.out.println("You win! Get your bet.");
            return 1;
            //players.get(i).setWallet(players.get(i).getWallet() + players.get(i).getBet());
        }
        // The Player loses.
        else if(value > h.getValue(maxValue)) {
            System.out.format("Your value is %d, and the Dealer's is %d.\n", h.getValue(maxValue), value);
            System.out.println("Unfortunately, you lose.");
            return -1;
            //players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
        }
        else {
            System.out.format("Your value is %d, and the Dealer's is %d.\n", h.getValue(maxValue), value);
            System.out.println("It's a tie.");
            return 0;
        }
    }
}
