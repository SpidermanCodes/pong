import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 100;

    private int player1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int player2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballXSpeed = 3;
    private int ballYSpeed = 3;
    private int player1Score = 0;
    private int player2Score = 0;

    private final Timer timer;
    private final int PADDLE_SPEED = 10;
    private boolean wPressed = false, sPressed = false;
    private boolean upPressed = false, downPressed = false;

    GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePaddles();
        moveBall();
        checkCollisions();
        repaint();
    }

    private void movePaddles() {
        if (wPressed && player1Y > 0) player1Y -= PADDLE_SPEED;
        if (sPressed && player1Y < HEIGHT - PADDLE_HEIGHT) player1Y += PADDLE_SPEED;
        if (upPressed && player2Y > 0) player2Y -= PADDLE_SPEED;
        if (downPressed && player2Y < HEIGHT - PADDLE_HEIGHT) player2Y += PADDLE_SPEED;
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Ball collision with top and bottom
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed *= -1;
        }

        // Ball collision with paddles
        if (new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE).intersects(new Rectangle(0, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT))) {
            ballXSpeed *= -1;
        }

        if (new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE).intersects(new Rectangle(WIDTH - PADDLE_WIDTH, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT))) {
            ballXSpeed *= -1;
        }

        // Scoring
        if (ballX <= 0) {
            player2Score++;
            resetBall();
        }

        if (ballX >= WIDTH - BALL_SIZE) {
            player1Score++;
            resetBall();
        }
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = ballXSpeed > 0 ? 3 : -3;
        ballYSpeed = ballYSpeed > 0 ? 3 : -3;
    }

    private void checkCollisions() {
        // Ensure ball doesn't move too fast
        ballXSpeed = Math.min(5, Math.max(-5, ballXSpeed));
        ballYSpeed = Math.min(5, Math.max(-5, ballYSpeed));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(Color.WHITE);

        // Draw paddles
        g.fillRect(0, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Draw scores
        g.setFont(new Font("Consolas", Font.PLAIN, 24));
        g.drawString("Player 1: " + player1Score, WIDTH / 4, 30);
        g.drawString("Player 2: " + player2Score, 3 * WIDTH / 4 - 100, 30);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}