/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

//Goccia e' un thread
public class Goccia  extends Thread 
{
    private int x;
    private int y;
    private int larghezza;
    private int altezza;
    private int velocita;
    private boolean attivo;
    private Gioco main;
    
    BufferedImage img_goccia;
    
    public Goccia(BufferedImage image, int larghezza, int altezza, int x, int y,  int velocita, Gioco main)
    {
        this.x = x;
        this.y = y;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.img_goccia = image;
        attivo = true;
        this.main = main;
        this.velocita = velocita;
        this.start(); //appena una goccia viene createa, quella comincia gia a scendere
    }
    
    @Override
    public void run()
    {
        attivo = true;
        while(attivo)
        {
            aggiorna();
            try 
            {
                Thread.sleep(100);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Goccia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private void aggiorna()
    {
        y += velocita;
    }
    
    public void disegna(Graphics g)
    {
        g.drawImage(img_goccia, x, y, larghezza, altezza, main);
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
