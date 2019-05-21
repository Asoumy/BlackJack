import java.util.Scanner;

public class BlackJackConsole {
	public BlackJackConsole() {
		System.out.println("Welcome to the BlackJack table. Let's play !");
	}

	public static void main(String[] args) {
		BlackJack blackJack = new BlackJack();
		Scanner scan = new Scanner(System.in);

		System.out.println("The bank draw : " + blackJack.getBankHandString());
		System.out.println("You draw : " + blackJack.getPlayerHandString());

		while (!blackJack.isGameFinished()) {
			askForOtherCardInConsole(blackJack, scan);
		}
		displayWinnerInConsole(blackJack);
	}

	public static void askForOtherCardInConsole(BlackJack blackJack, Scanner scan) {
		System.out.println("Do you want another card ? [y/n]");
		String choice = scan.next();

		if (choice.equals("y")) {
            try {
    			blackJack.playerDrawAnotherCard();
    			System.out.println("Your new hand : " + blackJack.getPlayerHandString());
            } catch (EmptyDeckException ex) {
                System.err.println(ex.getMessage());
                System.exit(-1);
            }
		} else {
            try {
    			blackJack.bankLastTurn();
    			System.out.println("The bank draw cards. New hand : " + blackJack.getBankHandString());
            } catch (EmptyDeckException ex) {
                System.err.println(ex.getMessage());
                System.exit(-1);
            }
		}
	}

    public static void displayWinnerInConsole(BlackJack blackJack) {
        int playerBest = blackJack.getPlayerBest();
        int bankBest = blackJack.getBankBest();
        System.out.println("Player best : " + playerBest);
        System.out.println("Bank best : " + bankBest);
        if (blackJack.isPlayerWinner()) {
            System.out.println("The player wins !");
        } else if (blackJack.isBankWinner()) {
            System.out.println("The bank wins !");
        } else {
            System.out.println("Draw !");
        }
    }
}
