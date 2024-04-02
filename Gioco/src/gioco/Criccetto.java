/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//in questo caso non possiamo usare Canvas, perche abbiamo gia scritto Thread
//ci sono due possibilita': questo e quello che c'e' scritto in classe Gioco con Canvas e Runnable.
//unire Canvas con Thread non si puo'
//da quel momento non serve scrivere una riga di codice come questa Thread thread_gioco = new Thread(gioco);
//oggetto gia viene considerato di tipo thread, e quindi non serve convertirlo da Canvas a thread
public class Criccetto extends Thread
{
    private int x;
    private int y;
    private int larghezza;
    private int altezza;
    private boolean attivo;
    private int velocita = 2;
    private final int max_velocita = 16;
    private Gioco main;
    BufferedImage img_cricceto;
    public int vita;
    public int record;
    
    public Criccetto(BufferedImage image, int larghezza, int altezza, int x, int y, Gioco main)
    {
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.x = x;
        this.y = y;
        this.img_cricceto = image;
        attivo = true;
        this.main = main;
        vita = 100;
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
                //e' il tempo di attesa del movimento
                //al diminuire il valore l'oggetto si muove piu velore
                Thread.sleep(10);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Criccetto.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    //metodo che muove il topo
    public void aggiorna()
    {
        Random rnd = new Random();
        //il criccetto si muoverà
        //x++;
        //tutti if si poteva scrivere con un uniro OR cioe ||
        if(this.x <= 0)
        {
            velocita = rnd.nextInt(max_velocita) + 1; //ritorna un numero random tra 0 e il numero specificato da noi
            //genera positivita
        }
        else
            if(this.x >= (main.getLarghezza() - this.larghezza))
            {
                velocita = rnd.nextInt(max_velocita) + 1;
                //genera positivita
                velocita *= -1;
                //lo rende negativo
            }
        x+= velocita; //cambia velocita prima di cambiare il verso
    }
    
    public Rectangle getBordi()
    {
        return new Rectangle(x, y, larghezza, altezza);
    }
    
    //il metodo che useremo dalla classe Gioco e quindi bisogna passare come il parametro Graphics g
    public void disegna(Graphics g)
    {
        g.drawImage(img_cricceto, x, y, larghezza, altezza, main);
    }
    
    public boolean getAttivo()
    {
        return attivo;
    }
    
    public void setAttivo(boolean valore)
    {
        this.attivo = valore;
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
    
}
