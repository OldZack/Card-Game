import java.util.Scanner;

public class BlackJackGame extends CardGame{

//    ArrayList<Player> playerList = new ArrayList<Player>();
//    Dealer dealer  = new Dealer();
//    Deck deck = new Deck();

    @Override
    public void startGame() {

        System.out.println("Welcome to Black Jack!");
//        while(true) {
//            round();
//        }

    }

    @Override
    public void round() {

        Scanner input = new Scanner(System.in);
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Dear Player, how much do you want to bet?");
            while (true) {
                try {
                    int amount = Integer.parseInt(input.next());
                    if (amount > players.get(i).getWallet()) {
                        System.out.println("The amount is out of what you have(%d). Please input again.", players[i].getWallet());
                        continue;
                    } else {
                        players.get(i).bet(amount);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Wrong amount! Please input again.");
                }
            }
            // The Dealer deals two cards to the Player.
            deck.deal(players.get(i));
            System.out.println("The two cards dealt to you are:");
            System.out.println(players.get(i).getCards());
        }
        // The Dealer is dealt two cards, one face up and another down.
        deck.deal(dealer);
        System.out.println("The Dealer gets %s and another unknown.", dealer.getUpCard());

        for (int i = 0; i < players.size(); i++) {
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
                    if(n==1) {
                        players.get(i).hit();
                        // If the Player goes bust.
                        if(players.get(i).getValue() > 21) {
                            System.out.println("You go bust.");
                            players.get(i).setWallet(players.get(i).getWallet() - players.get(i).getBet());
//                            players.remove(i);
                            break;
                        }else if(players.get(i).getValue() == 21) {
                            System.out.println("Your hand value is 21. ");
                            int value = dealer.getValue();
                            while(value < 17) {
                                dealer.hit();
                                value = dealer.getValue();
                            }
                        }
                    }else if(n==2) {
                        System.out.println("The face down card is revealed by the Dealer:" + dealer.getDownCard());
                        int value = dealer.getValue();
                        while(value < 17) {
                            dealer.hit();
                            value = dealer.getValue();
                        }
                        checkWinner();
                        break;
                    }else if(n==3) {
                        if(!players[i].ifAvailable()) {
                            System.out.println("You can not split.");
                            continue;
                        }

                    }
                }
            }
        }

    }


    @Override
    public void checkWinner() {

        int value = dealer.getValue();
        // The Player wins.
        if((value > 21) || value < players[i].getValue()) {
            System.out.println("You win! Get your bet.");
            players[i].setWallet(players[i].getWallet() + players[i].getBet());
        }
        // The Player loses.
        else if(value > players[i].getValue()) {
            System.out.println("Unfortunately, you lose.");
            players[i].setWallet(players[i].getWallet() - players[i].getBet());
        }
        else
            System.out.println("It's a tie.");

    }
}
