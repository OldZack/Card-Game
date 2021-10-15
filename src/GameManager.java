import java.util.Scanner;

// GameManager class is like a "menu" for all games available. Only contains gameList which is a list of games.
public class GameManager {
    private CardGame[] gameList;

    // The entire list will have 2 default games for now :)
    public GameManager() {
        BlackJackGame b = new BlackJackGame();
        this.gameList = new CardGame[]{b};
    }

    // get the current game list
    public CardGame[] getGameList() {
        return this.gameList;
    }

    // prompts the user to select a game from the game list.
    public CardGame selection() {
        Scanner in = new Scanner(System.in);
        String pattern = "[12]";
        String bORt = "";
        while(true) {
            bORt = in.next();
            if(!bORt.matches(pattern)) {
                System.out.println("Error: Wrong input! Please input again.");
                continue;
            }
            else
                break;
        }
        if (bORt.equals("1")) {
            return getGameList()[0];
        }
        else {
            return getGameList()[1];
        }
    }

    // the GameManager system starts. It allows user to select and play the games in game list.
    public void run() {
        System.out.println("Welcome to the Game Center. What game you want to play?");
        System.out.println("Enter 1 for BlackJack, 2 for Trianta Ena.");
        boolean next = true;

        CardGame g = selection();
        g.startGame();

    }

}
