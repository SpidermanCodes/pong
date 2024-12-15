import javax.swing.JFrame;

class GameFrame extends JFrame {
    GameFrame() {
        setTitle("Pong Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
