import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
/**
 * Write a description of class Snake here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Snake extends JPanel {
	
    private final String snakeHead = "images/head.png";
    private final String snakeBody = "images/body.png";

    private int x, y;
    private Image head, body;
    private Snake next;
    
    public Snake() {
        ImageIcon i = new ImageIcon(snakeHead);
        head = i.getImage();
        x = 400;
        y = 300;
    }
    
    public Snake(int x, int y) {
    	ImageIcon ii = new ImageIcon(snakeBody);
    	body = ii.getImage();
    	this.x = x;
    	this.y = y;
    }
    
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    
    public void moveBody(int x, int y) {
    		this.x = x;
    		this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Image getHead() {
        return head;
    }

    public Image getBody() {
        return body;
    }

    public void setNext(Snake next) {
        this.next = next;
    }

    public Snake getNext() {
        return this.next;
    }

}