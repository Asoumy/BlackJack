import java.util.LinkedList;
import java.util.List;

public class Hand {
    private LinkedList<Card> cardList;

    public Hand() {
        this.cardList = new LinkedList<Card>();
    }

    public String toString() {
        return this.cardList.toString() + " : " + this.count();
    }

    public void add(Card card) {
        this.cardList.add(card);
    }

    public void clear() {
        this.cardList.clear();
    }

    public List<Integer> count() {
        LinkedList<Card> cardList = this.cardList;
        LinkedList<Integer> results = new LinkedList<Integer>();
        results.add(0);

        for (int i = 0; i < cardList.size(); i++) {
            int resultsSize = results.size();
            for (int j = 0; j < resultsSize; j++) {
                int val = results.get(j);
                results.set(j, val + cardList.get(i).getPoints());

                if (cardList.get(i).getPoints() == 1) {
                    results.add(val + 11);
                }
            }
        }

        return results;
    }

    public int best() {
        List<Integer> results = this.count();
        int best = -1;
        int max = 0;

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) <= 21 && results.get(i) > best) best = results.get(i);
            if (results.get(i) > max) max = results.get(i);
        }

        if (best != -1) {
            return best;
        } else {
            return max;
        }
    }

    public List<Card> getCardList() {
        return this.cardList;
    }
}
