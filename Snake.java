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

    private int x;
    private int y;
    private Image head;
    private Image body;
    private Snake proximo;
    
    public Snake() {
        ImageIcon i = new ImageIcon(snakeHead);
        ImageIcon ii = new ImageIcon(snakeBody);
        head = i.getImage();
        body = ii.getImage();
        x = 400;
        y = 300;
    }
    
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
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

    public void setProx(Snake proximo) {
        this.proximo = proximo;
    }

    public Snake getProx() {
        return this.proximo;
    }

}
