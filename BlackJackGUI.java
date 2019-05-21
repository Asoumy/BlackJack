import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;

public class BlackJackGUI implements ActionListener {

    private BlackJack bj;
    private JPanel playerPanel;
    private JPanel bankPanel;

    JButton anotherButton;
    JButton noMoreButton;
    JButton resetButton;

    public BlackJackGUI() {
        JFrame frame = new JFrame("BlackJack GUI");
        BlackJack bj = new BlackJack();
        this.bj = bj;

        frame.setMinimumSize(new Dimension(640,480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton anotherCardBtn = new JButton("Another Card");
        anotherCardBtn.setActionCommand("anotherCard");
        anotherCardBtn.addActionListener(this);
        this.anotherButton = anotherCardBtn;

        JButton noMoreCardBtn = new JButton("No More Card");
        noMoreCardBtn.setActionCommand("noMoreCard");
        noMoreCardBtn.addActionListener(this);
        this.noMoreButton = noMoreCardBtn;

        JButton resetBtn = new JButton("Reset");
        resetBtn.setActionCommand("reset");
        resetBtn.addActionListener(this);
        this.resetButton = resetBtn;

        topPanel.add(anotherCardBtn);
        topPanel.add(noMoreCardBtn);
        topPanel.add(resetBtn);

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player"));
        this.playerPanel = playerPanel;

        JPanel bankPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bankPanel.setBorder(BorderFactory.createTitledBorder("Bank"));
        this.bankPanel = bankPanel;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.add(bankPanel);
        centerPanel.add(playerPanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        try {
            this.updateBankPanel();
            this.updatePlayerPanel();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BlackJackGUI();
    }

    private void addToPanel(JPanel p, String token) throws FileNotFoundException {
        File file = null;
        try {
            file = new File("./img/card_" + token + ".png");
        } catch (Exception ex) {
            throw new FileNotFoundException("Can't find file " + token);
        }
        ImageIcon icon = new ImageIcon(file.getPath()); // Create the image from the filename
        JLabel label = new JLabel(icon); // Associate the image to a label
        p.add(label); // Add the label to a panel
    }

    private void updatePlayerPanel() throws FileNotFoundException {
        List<Card> cardList = this.bj.getPlayerCardList();
        this.playerPanel.removeAll();

        for (int i = 0; i < cardList.size(); i++) {
            Card card = cardList.get(i);
            this.addToPanel(this.playerPanel, card.getColorName() + "_" + card.getValueSymbole());
        }

        this.playerPanel.updateUI();
    }

    private void updateBankPanel() throws FileNotFoundException {
        List<Card> cardList = this.bj.getBankCardList();
        this.bankPanel.removeAll();

        for (int i = 0; i < cardList.size(); i++) {
            Card card = cardList.get(i);
            this.addToPanel(this.bankPanel, card.getColorName() + "_" + card.getValueSymbole());
        }

        this.bankPanel.updateUI();
    }

    public void toggleButtonsState() {
        try {
            if (this.bj.isGameFinished()) {
                this.anotherButton.setEnabled(false);
                this.noMoreButton.setEnabled(false);
                JLabel playerBestLabel = new JLabel("Player best : " + String.valueOf(this.bj.getPlayerBest()));
                this.playerPanel.add(playerBestLabel);
                JLabel bankBestLabel = new JLabel("Bank best : " + String.valueOf(this.bj.getBankBest()));
                this.bankPanel.add(bankBestLabel);

                if (this.bj.isBankWinner()) {
                    this.addToPanel(this.playerPanel, "looser");
                    this.addToPanel(this.bankPanel, "winner");
                } else if (this.bj.isPlayerWinner()) {
                    this.addToPanel(this.playerPanel, "winner");
                    this.addToPanel(this.bankPanel, "looser");
                } else {
                    this.addToPanel(this.playerPanel, "draw");
                    this.addToPanel(this.bankPanel, "draw");
                }

                if (this.bj.getPlayerBest() == 21) {
                    this.addToPanel(this.playerPanel, "blackjack");
                }
                if (this.bj.getBankBest() == 21) {
                    this.addToPanel(this.playerPanel, "blackjack");
                }
            } else {
                this.anotherButton.setEnabled(true);
                this.noMoreButton.setEnabled(true);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            switch(e.getActionCommand()) {
                case "anotherCard":
                    this.bj.playerDrawAnotherCard();
                    this.updateBankPanel();
                    this.updatePlayerPanel();
                    this.toggleButtonsState();
                    break;
                case "noMoreCard":
                    this.bj.bankLastTurn();
                    this.updateBankPanel();
                    this.updatePlayerPanel();
                    this.toggleButtonsState();
                    break;
                case "reset":
                    this.bj.reset();
                    this.updateBankPanel();
                    this.updatePlayerPanel();
                    this.toggleButtonsState();
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }
}
