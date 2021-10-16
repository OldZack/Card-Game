import java.util.*;

public class TEGame extends CardGame{

    private int maxValue = 31;
    private int minValue = 27;

    public TEGame() {
        super();
    }

    @Override
    public void deal() {
        dealer.getHands().get(0).add_card(deck.draw());
        for (Player p : players){
            p.getHands().get(0).add_card(deck.draw());
        }
    }

    @Override
    public void startGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Trianta Ena!");
        boolean broke = false;
        int gameNum = 1;
        System.out.println("Please indicate the number of players: ");
        while (true){
            try {
                int s = Integer.parseInt(input.next());
                if (s > 1 && s <= 9){
                    for (int i = 0; i < s; i++){
                        String name = "Player" + (i+1);
                        players.add(new Player(name));
                    }
                    break;
                }
                else if (s <= 1){
                    System.out.println("The game must have at least two players to start. Please re-enter the number:");
                }
                System.out.println("The input number exceeds the maximum number of players(9). Please re-enter the number:");
            } catch (NumberFormatException e) {
                System.out.println("The input is not a number. Please re-enter the number of players:");
                continue;
            }
        }

        System.out.println("By default, Player1 is set to be the dealer.");
        Player temp = players.get(0);
        players.remove(0);

        while(!broke) {
            System.out.println("Welcome to Game" + gameNum);
            gameNum += 1;
            round();
            players.removeIf(p -> p.getWallet() <= 0);

            List<Player> deleteList = new ArrayList<>();
            for (Player p : players){
                System.out.println(p.get_name() + ", would you like to cash out? Enter y for yes; enter anything for no.");
                if (input.next().equals("y")) {
                    System.out.println("You cashed out " + p.getWallet() + ", Bye.");
                    deleteList.add(p);
                }
            }
            for (Player p : deleteList){
                players.remove(p);
            }

            if (players.size() == 0) {
                broke = true;
            }

            /*
            Choose the new banker.
            */
            List<Player> sortedPlayer = new ArrayList<>();
            sortedPlayer.addAll(players);
            sortedPlayer.sort(Comparator.reverseOrder());
            for (Player p : sortedPlayer){
                if (p.getWallet() > temp.getWallet()){
                    System.out.println(p.get_name() + ", please indicate whether you want to be the dealer (enter y for yes; anything else for no) : ");
                    if (input.next().equals("y")) {
                        System.out.println(p.get_name() + " becomes the new dealer.");
                        players.add((temp.get_name().charAt(6))-'1', temp);
                        temp = p;
                        players.remove(p);
                        break;
                    }
                }
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
        deal();
        for (Player p : players) {
            p.resetBet();
            // The Dealer deals one card to the Player.
            System.out.println(p.get_name() + ", the card dealt to you is:");
            System.out.println(p.getHands().get(0).displayCards());
            // The Dealer is dealt one card.
            System.out.format("The Dealer gets %s. \n", dealer.getHands().get(0).get_cards().get(0));
            System.out.format("You have %d now.\n", p.getWallet());
            System.out.println("Dear Player, how much would you like to bet (enter 0 as fold)?");
            while (true) {
                try {
                    int amount = Integer.parseInt(input.next());
                    if (amount == 0){
                        p.resetHands();
                        System.out.println("You chose to fold.");
                        break;
                    } else if (amount > p.getWallet()) {
                        System.out.format("The amount is out of what you have(%d). Please input again.", p.getWallet());
                        continue;
                    } else if (amount < 0){
                        System.out.format("The amount is less than 0. Please input again.");
                        continue;
                    } else {
                        p.setBet(amount);
                        p.getHands().get(0).add_card(deck.draw());
                        p.getHands().get(0).add_card(deck.draw());
                        System.out.println("You are dealt two more cards and now you have ");
                        System.out.println(p.getHands().get(0).displayCards());
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Wrong amount! Please input again.");
                }
            }

        }

        for (Player p : players) {
            Hand hand = p.getHands().get(0);
            if (hand.get_cards().size() == 0){
                continue;
            }
            boolean end = false;
            while(getValue(hand, maxValue) <= maxValue && !end ) {
                System.out.println("It's " + p.get_name() + "'s turn, you have got: \n" + hand.displayCards());
                System.out.println("what would you like to do next:(just choose the number)");
                System.out.println("1.Hit\n" + "2.Stand");
                String pattern = "[12]";
                while(true) {
                    String action = input.next();
                    if(!action.matches(pattern)) {
                        System.out.println("Error: Wrong input! Please input again.");
                        continue;
                    }
                    else {
                        int n = Integer.parseInt(action);
                        if(n==1) {
                            Card c = deck.draw();
                            hand.hit(c);
                            System.out.println("You got " + c.toString() + ".");
                            // If the Player goes bust.
                            if(getValue(hand, maxValue) > maxValue) {
                                System.out.println("Bust. You Lost.");
                                end = true;
                            }
                            // current hand hits and gets 21
                            else if(getValue(hand, maxValue) == maxValue) {
                                System.out.println("Your hand value reaches 31. ");
                                end = true;
                            }
                        break;
                        } else if(n==2) {
                            // If the player stands, he/she waits until everyone finishes.
                            end = true;
                            break;
                        }
                    }
                }
            }
        }

        // Dealer draws until gets a value higher than 27.
        System.out.println("Dealer's card is:" + dh.get_cards().get(0));
        int value = getValue(dh, minValue);
        while(value < minValue) {
            Card tempC2 = deck.draw();
            dh.hit(tempC2);
            System.out.println("Dealer drew " + tempC2.toString() + ".");
            value = getValue(dh, minValue);
        }

        // Compare each player's result with the dealer's.
        for (Player p :players) {
            if (p.getHands().get(0).get_cards().size() != 0){
                System.out.println(p.get_name() + ": ");
                Hand hand = p.getHands().get(0);
                int winner = checkWinner(hand);
                if (winner > 0) {
                    p.setWallet(p.getWallet() + p.getBet());
                }
                if (winner < 0) {
                    p.setWallet(p.getWallet() - p.getBet());
                }
            }

        }
    }


    @Override
    // only called when player didn't go bust.
    public int checkWinner(Hand h) {

        int value = getValue(dealer.getHands().get(0), minValue);
        // The Player wins.
        if (getValue(h, maxValue) > maxValue) {
            System.out.println("You already Bust.");
            return -1;
        }
        if((value > maxValue) || value < getValue(h, maxValue)) {
            System.out.println("You win! Get your bet.");
            return 1;
            //players.get(i).setWallet(players.get(i).getWallet() + players.get(i).getBet());
        }
        // The Player loses.
        else if(value > getValue(h, maxValue)) {
            System.out.println("Unfortunately, you lose.");
            return -1;
            //players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
        }
        else {
            System.out.println("It's a tie.");
            return 0;
        }
    }

    public int getValue(Hand h, int ceiling){
        int res = 0;
        boolean haveA = false;
        for (Card c : h.get_cards()) {
            if (c.get_rank().equals("J") || c.get_rank().equals("Q") || c.get_rank().equals("K")) {
                res += 10;
            }
            else if (c.get_rank().equals("A")) {
                res += 11;
                haveA = true;
            }
            else {
                res += Integer.parseInt(c.get_rank());
            }
        }
        // if there exists A in cards and the rest value is smaller than 10, we convert A to 11.
        if (res > ceiling && haveA) {
            res -= 10;
        }
        return res;
    }
}
