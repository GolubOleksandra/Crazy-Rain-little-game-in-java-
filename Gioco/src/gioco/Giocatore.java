/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Giocatore 
{
    private int x;
    private int y;
    private int larghezza;
    private int altezza;
    private final int velocita = 10;
    Gioco main;
    
    BufferedImage img_ombrello;
    
    public Giocatore(){}
    
    //la "y" non mettere perche non 'e richiesto nel gioco
    public Giocatore(BufferedImage image, int x, int larghezza, int altezza, Gioco main)
    {
        this.x = x;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.img_ombrello = image;
        y = 400; //la posizione dell'ombrello
        this.main = main;
    }
    
    public void disegna(Graphics g)
    {
        g.drawImage(img_ombrello, x, y, larghezza, altezza, null);
    }
    
    public void setX(int valore)
    {
        this.x = valore;
    }
    
    public void setY(int valore)
    {
        this.y = valore;
    }
    
    public void setLarghezza(int valore)
    {
        this.larghezza = valore;
    }
    
    public void setAltezza(int valore)
    {
        this.altezza = valore;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getLarghezza()
    {
        return larghezza;
    }
    
    public int getAltezza()
    {
        return altezza;
    }
    
    public Rectangle getBordi()
    {
        return new Rectangle(x, y, larghezza, altezza);
    }
}

