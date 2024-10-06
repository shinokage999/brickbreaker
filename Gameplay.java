
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 28;
    private Timer timer;
    private int delay = 6;

    private int playerX = 310;
    private int ballposX = 220;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -4;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(4,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //draw
        map.draw((Graphics2D)g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        // Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.black);
            g.fillRect(1, 1, 692, 592);
    

            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Congratulations,You Won !!",190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("press space to restart",230,350);
        }

        if(ballposY>570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.black);
            g.fillRect(1, 1, 692, 592);
    
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over",250,250);
            g.drawString("Score:"+score,250,320);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("press space to restart",250,370);
        }
        
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
        if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
            ballYdir= -ballYdir;
        }

        A:for(int i=0;i<map.map.length;i++){
            for(int j=0;j<map.map[0].length;j++){
                if(map.map[i][j]>0){
                    int brickx= j*map.brickwidth+80;
                    int bricky= i*map.brickheight+50;
                    int brickwidth= map.brickwidth;
                    int brickheight= map.brickheight;

                   Rectangle rect = new Rectangle(brickx,bricky,brickwidth,brickheight);
                   Rectangle ballrect = new Rectangle(ballposX,ballposY,20,20);
                   Rectangle brickrect = rect;

                   if (ballrect.intersects(brickrect)){
                    map.setBrickValue(0,i,j);
                    totalBricks--;
                    score+=5;
                    if(ballposX+19 <= brickrect.x || ballposX+1 >= brickrect.x+brickrect.width){
                        ballXdir = -ballXdir;
                    }
                    else{
                        ballYdir= -ballYdir;
                    } 
                    break A;                  
                }
                }
            }
        }
         ballposX+= ballXdir;
         ballposY+= ballYdir;
            if(ballposX<0){
                ballXdir= -ballXdir;
            }
            
            if(ballposY<0){
                ballYdir= -ballYdir;
            }
           
            if(ballposX>670){
                ballXdir= -ballXdir;
            }
        }
        repaint();
    }

    
    public void keyTyped(KeyEvent e) {
    }

    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 590) {
                playerX = 576;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(!play){
                play = true;
                ballposX= 220;
                ballposY= 350;
                ballXdir = -2;
                ballYdir = -4;
                playerX = 310;
                score = 0;
                totalBricks = 28;
                map = new MapGenerator(4,7);

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}


