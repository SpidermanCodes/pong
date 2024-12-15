import javax.swing.SwingUtilities;

public class PongGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame());
    }
}