import java.util.List;
import java.util.LinkedList;

public class BlackJack {
    private Deck deck;
    private Hand playerHand;
    private Hand bankHand;

    public boolean gameFinished = false;

    public BlackJack() {
        // initialize les différents attributs et appelle la méthode reset
        this.deck = new Deck(4);
        this.playerHand = new Hand();
        this.bankHand = new Hand();

        try {
            reset();
        } catch (EmptyDeckException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }

    public void reset() throws EmptyDeckException {
        this.playerHand.clear();
        this.bankHand.clear();

        this.gameFinished = false;

        try {
            this.playerHand.add(this.deck.draw());
            this.playerHand.add(this.deck.draw());

            this.bankHand.add(this.deck.draw());
        } catch (EmptyDeckException ex) {
            throw new EmptyDeckException("The deck is empty.");
        }
    }

    public String getPlayerHandString() {
        return this.playerHand.toString();
    }

    public String getBankHandString() {
        return this.bankHand.toString();
    }

    public int getPlayerBest() {
        return this.playerHand.best();
    }

    public int getBankBest() {
        return this.bankHand.best();
    }

    public boolean isPlayerWinner() {
        if (this.playerHand.best() <= 21
            && (this.bankHand.best() < this.playerHand.best() || this.bankHand.best() > 21)
            && this.gameFinished) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBankWinner() {
        if (this.bankHand.best() <= 21
        && (this.playerHand.best() < this.bankHand.best() || this.playerHand.best() > 21)
        && this.gameFinished) {
            return true;
        } else {
            return false;
        }
    }

    public void playerDrawAnotherCard() throws EmptyDeckException {
        if (!this.isGameFinished()) {
            Card card = this.deck.draw();
            this.playerHand.add(card);
            if (this.playerHand.best() > 21) {
                this.gameFinished = true;
            }
        }
    }

    public void bankLastTurn() throws EmptyDeckException {
        if (!this.gameFinished) {
            if (this.playerHand.best() <= 21 && this.bankHand.best() <= 21) {
                while (this.bankHand.best() < this.playerHand.best() && this.bankHand.best() < 21) {
                    Card card = this.deck.draw();
                    this.bankHand.add(card);
                }
                this.gameFinished = true;
            }
        }
    }

    public boolean isGameFinished() {
        return this.gameFinished;
    }

    public List<Card> getPlayerCardList() {
        List<Card> originalList = this.playerHand.getCardList();
        List<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }

    public List<Card> getBankCardList() {
        List<Card> originalList = this.bankHand.getCardList();
        List<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }
}
