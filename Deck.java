import java.util.LinkedList;
import java.util.Collections;

public class Deck {
	private LinkedList<Card> cardList;

	public Deck(int nbBox) {
		this.cardList = new LinkedList<Card>();

		for (int i = 0; i < nbBox; i++) {
			this.addBoxToDeck();
		}

		Collections.shuffle(this.cardList);
	}

	private void addBoxToDeck() {
		Value[] cardValues = Value.values();
		Color[] colorValues = Color.values();

		for (int i = 0; i < colorValues.length; i++) {
			for (int j = 0; j < cardValues.length; j++) {
				Card card = new Card(cardValues[j], colorValues[i]);
				this.cardList.add(card);
			}
		}
	}

	public Card draw() throws EmptyDeckException {
        if (this.cardList.size() == 0) {
            throw new EmptyDeckException("The deck is empty.");
        }
        Card card = this.cardList.pollFirst();
        return card;
	}

	public String toString() {
		return "Here is the deck " + this.cardList.toString();
	}
}
