import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.File;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Score score;
    private Snake cobra;
    private final String frenchFries = "images/fries.png";
    private Image fries;
    private boolean isPlaying = false;
    private boolean gameOver = true;
    private boolean paused = false;
    private boolean intro = true;
    private Font font;
    private String direction = "LEFT";
    private boolean cima = true, baixo = true, esq = false, dir = true;
    private final int LIM_E = 0, LIM_C = 0, LIM_B = 540, LIM_D = 750; 
    private Lista lista;
    private int RX;
    private int RY;
    private int length;
    private int record = 0;
       
    public Board() {
        addKeyListener(new TAdapter());
        
        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(Color.WHITE);
        
        timer = new Timer(200, this);
        timer.start();
    }
    
    public void NewGame() {
        Random();
        isPlaying = true;
        gameOver = false;
        score = new Score();
        cobra = new Snake();
        lista = new Lista(); 
        direction = "LEFT";
        length = 3;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (isPlaying && !gameOver) {
            Snake aux = cobra;
    		lista.inserir(aux);
                        
            if (lista.getSize() > length) lista.remover();
            
            
            collision(cobra);
            
            if((cobra.getX() < LIM_E | cobra.getY() < LIM_C | cobra.getX() > LIM_D | cobra.getY() > LIM_B)){
                gameOver = true;
                isPlaying = false;
                intro = false;
            }
            
            
            move();
            
            
            // Validando mesmo lugar SNAKE X FRIES
            if(((cobra.getX() <= RX+20) && (cobra.getX() >= RX-20)) &&
    	       ((cobra.getY() <= RY+20) && (cobra.getY() >= RY-20))){
                score.addScore(1);
                Random();
                lista.inserir(cobra);
            }
        }
        repaint();  
    }  

    public void paint(Graphics g) {
        super.paint(g);
        
        if (intro && !isPlaying && gameOver) {
            intro(g);
        }
        
        Graphics2D g2d = (Graphics2D)g;

        paintGameOver(g2d);
        
        if (isPlaying) {
            score.paintComponent(g);
         
            drawBatata(g2d);
            
            if (direction == "RIGHT") {     
                AffineTransform transform = new AffineTransform(-1, 0, 0, 1, cobra.getX()+40, cobra.getY());
                g2d.drawImage(cobra.getHead(), transform , this); 
            } else if (direction == "UP") {     
                AffineTransform transform = new AffineTransform(0, 1, 1, 0, cobra.getX(), cobra.getY());
                g2d.drawImage(cobra.getHead(), transform , this); 
            } else if (direction == "DOWN") {       
                AffineTransform transform = new AffineTransform(0, -1, 1, 0, cobra.getX(), cobra.getY()+30);
                g2d.drawImage(cobra.getHead(), transform , this); 
            } else g2d.drawImage(cobra.getHead(),cobra.getX(),cobra.getY(),this);
            
            int i = 0;
        	int t = lista.getSize();
        	
        	Snake aux = cobra;
        	
        	while (i < t) {
        		g2d.drawImage(aux.getBody(), aux.getX(), aux.getY(), null); 
        		aux = aux.getNext();
        		i++;
    		}
    		
            pause(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public void paintGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if(gameOver && !isPlaying && !intro) {
             
            try{
                 File file = new File("fonts/VT323-Regular.ttf");
                 font = Font.createFont(Font.TRUETYPE_FONT, file);
                 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                 ge.registerFont(font);
                 font = font.deriveFont(Font.PLAIN,55);
                 g2d.setFont(font);
             }catch (Exception e){
                 System.out.println(e.toString());
             }

            int newRec = score.getScore();
            
            if (newRec > record) {
                record = newRec;
            }
            
            String drawScore = "";
            drawScore += score.getScore();
            
            String drawRecord = "";
            drawRecord += record;
            
            g2d.setColor(Color.RED);
            g2d.drawString("G A M E  O V E R", 200,150);
            font = font.deriveFont(Font.PLAIN,30);
            g2d.setFont(font);
            g2d.drawString("Your Score was: ",250, 200);
            g2d.drawString(drawScore,480, 200);
            g2d.drawString("Your Record is: ",250, 250);
	        g2d.drawString(drawRecord,480, 250);
            g2d.setColor(Color.BLACK);
            font = font.deriveFont(Font.PLAIN,25);
            g2d.setFont(font);
            g2d.drawString("'Press Enter to Play Again'", 250,400);
            g2d.drawString("'Press BackSpace to back to menu'",230, 450);
        }
    }

    public void paintIntro(Graphics g) {
        if(!isPlaying && intro){
            
            Graphics2D g2d = (Graphics2D) g;            

            try{
                File file = new File("fonts/VT323-Regular.ttf");
                font = Font.createFont(Font.TRUETYPE_FONT, file);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
                font = font.deriveFont(Font.PLAIN,40);
                g2d.setFont(font);
            }catch (Exception e){
                System.out.println(e.toString());
            }   
            g2d.drawString("S N A K E ", 300, 200);            
            font = font.deriveFont(Font.PLAIN,30);
            g2d.setFont(font);
            g2d.drawString("'Press Enter to Start'", 250, 300);
        }
    }

    public void intro(Graphics g) {
        super.paintComponent(g);
        paintIntro(g);
    }
    
    public void move() {        
        int mx = cobra.getX();
		int my = cobra.getY();
		int tx = lista.getSize();

        if (direction == "RIGHT" && !paused) {
            cobra.move(25, 0);
            moveBody(mx, my, tx);
            cima = true;
            baixo = true; 
            esq = false; 
            dir = true;
        }
        
        if (direction == "LEFT" && !paused) {
            cobra.move(-25,0);
            moveBody(mx, my, tx);
            cima = true;
            baixo = true; 
            esq = true; 
            dir = false;
        }
        
        if (direction == "UP" && !paused) {
            cobra.move(0, -25);
            moveBody(mx, my, tx);
            cima = true;
            baixo = false;
            esq = true; 
            dir = true;
        }
        
        if (direction == "DOWN" && !paused) {
            cobra.move(0, 25);
            moveBody(mx, my, tx);
            cima = false;
            baixo = true; 
            esq = true; 
            dir = true;
        }
    }
    
    public void moveBody(int x, int y, int t) {
    	int ax, ay, bx, by;
    	Snake aux = cobra;
    	int i = 1;
    	ax = x;
    	ay = y;
    	while (i < t) {
    		aux = aux.getNext();
    		bx = aux.getX();
    	    by = aux.getY();
    		aux.moveBody(ax, ay);
    		ax = bx;
    		ay = by;
    		i++;
    	}
    }
    
    public void pause(Graphics g2d) {
        if(paused) {
            font = font.deriveFont(Font.PLAIN,40);
            g2d.setFont(font);
            g2d.setColor(Color.RED);
            g2d.drawString("Game Paused",300, 200);
            font = font.deriveFont(Font.PLAIN,30);
            g2d.setFont(font);
            g2d.setColor(Color.BLACK);
            g2d.drawString("'Press Space to continue'", 250, 300);
        }
    }
    
    public void collision(Snake head){
    }
        
    public void drawBatata(Graphics g2d) {
        ImageIcon i = new ImageIcon(frenchFries);
        fries = i.getImage();
        g2d.drawImage(fries, RX , RY , this);
    }
    
    public void Random () {
        Random rand = new Random();
        
        int x = rand.nextInt(750);
        int y = rand.nextInt(540);
        
        if((x % 5 == 0) && (y % 5 == 0)) {
            RX = x;
            RY = y;
        } else Random();        
    }
    
    private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            // Obtém o código da tecla
            int key =  e.getKeyCode();

            switch (key){
                case KeyEvent.VK_ENTER:
                    if (!isPlaying && gameOver) {
                        NewGame();
                    } 
                    break;

                case KeyEvent.VK_SPACE:
                    if (paused && isPlaying) {
                        paused = false;
                    } else if (!paused && isPlaying) {
                        paused = true;
                    }
                    break;
                    
                case KeyEvent.VK_LEFT:
                    if (isPlaying && !paused && esq) {
                        direction = "LEFT";
                    }                   
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    if (isPlaying && !paused && dir) {
                        direction = "RIGHT"; 
                    }                   
                    break;
                    
                case KeyEvent.VK_UP:
                    if (isPlaying && !paused && cima) {
                        direction = "UP";
                    }
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if (isPlaying && !paused && baixo) {
                        direction = "DOWN";
                    }
                    break;
                    
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                    
                case KeyEvent.VK_R:
                    if(!paused && isPlaying) {
                        NewGame();
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    if (!isPlaying && gameOver) {
                      intro = true;  
                    } 
            }
        }
    }
}