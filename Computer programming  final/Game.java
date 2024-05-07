import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class Game extends JPanel implements ActionListener, KeyListener{
    private class location { //tracks character location
        int x;
        int y;
        Image image;
        location(int x, int y, Image image){
            this.x = x;
            this.y = y;
            this.image = image;
        }
    }

    int score; //store the score

    //size
    int height; //height of game
    int width; //width of game
    int size = 50; //size of box
    location character;     //main character
    location coins;     //coins

    location enemy; // enemy
    Random random; //random function
    Image Background;     //background
    Timer timer;     //timer
    int x_velocity; //velocity in x direction
    int y_velocity; //velocity in y direction

    boolean dead = false; //tracks death


    Game(int height, int width){
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        //background
        Background = new ImageIcon("Assets/background.png").getImage();

        //character and coins stuff
        character = new location(6, 11, new ImageIcon("Assets/cat.png").getImage());
        coins = new location(10, 10, new ImageIcon("Assets/coins.png").getImage());
        random = new Random();
        coin_spawn();
        enemy = new location(2, 2, new ImageIcon("Assets/enemy.png").getImage());

        //timer or fps
        timer = new Timer(100, this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        create(g);
    }

    public void create(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        //background
        g2D.drawImage(Background, 0,0, null);

        //layout
        for (int i = 0; i < width/size; i++){
            g2D.drawLine(i*size, 0, i*size, height);
            g2D.drawLine(0, i*size, width, i*size);
        }
        //Coins
        g2D.drawImage(coins.image, coins.x*size, coins.y*size, size, size, null);
        //Block
        g2D.drawImage(character.image, character.x*size, character.y*size, size, size, null);

        //test enemy
        g2D.drawImage(enemy.image, enemy.x*size, enemy.y*size, size, size, null);

        //score
        g2D.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        if (dead) {
            g.setColor(Color.green);
            g.drawString("You are dead! Score: " + score, size*4, size*6);
        }
        else {
            Color gold = new Color(255, 215,0); //score color
            g.setColor(gold);
            g.drawString("Score: " + score, size-45, size-30);
        }
    }

    public void coin_spawn(){
        coins.x = random.nextInt(width/size);
        coins.y = random.nextInt(height/size);
    }

    public boolean collison_detect(location location1 , location location2){
        return location1.x == location2.x && location1.y == location2.y;
    }

    public void move(){
        //collect coins
        if (collison_detect(character, coins)){
            score ++;
            coin_spawn();
        }

        //enemy collison
        if (collison_detect(character, enemy)){
            dead = true;
        }

        //movment
        int x = character.x += x_velocity;
        int y  = character.y += y_velocity;

        //boundaries for x-coordinate
        if (x < 0) {
            character.x = 0;
        } else if (x + 1 > width / size) {
            character.x = width / size - 1;
        }

        //boundaries for y-coordinate
        if (y < 0) {
            character.y = 0;
        } else if (y + 1 > height / size) {
            character.y = height / size - 1;
        } else {
            character.y = y;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (dead){
            timer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            x_velocity = 0;
            y_velocity = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            x_velocity = -1;
            y_velocity = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            x_velocity = 0;
            y_velocity = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            x_velocity = 1;
            y_velocity = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            x_velocity = 0;
            y_velocity = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            x_velocity = 0;
            y_velocity = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            x_velocity = 0;
            y_velocity = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            x_velocity = 0;
            y_velocity = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} //don't need
}