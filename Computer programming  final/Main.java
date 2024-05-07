import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 600;
        int height = 600;
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setVisible(true);

        Game game = new Game(height, width);
        frame.add(game);
        frame.pack();
        game.requestFocus();
    }

}
