/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pioggia extends Thread
{
    private int numero;
    private int attesa;
    private boolean piove; 
    BufferedImage img_goccia;
    private final int maxVel = 20;
    Gioco main;
    Random rand;
    
    //arrayList puo contenere gli oggetti
    private ArrayList<Goccia> gocce;
    
    public Pioggia(BufferedImage image, int numero, int attesa, Gioco main)
    {
        this.img_goccia = image;
        this.attesa = attesa;
        this.numero = numero;
        this.main = main;
        
        gocce = new ArrayList();
        rand = new Random();
    }
    
    public void disegna(Graphics g)
    {
        for(int i = 0; i<gocce.size(); i++)
        { 
            try{
            Goccia temp = gocce.get(i);
            g.drawImage(img_goccia, temp.getX(), temp.getY(), temp.getLarghezza(), temp.getAltezza(), main);
            temp.disegna(g);
        }catch(NullPointerException e) {}
        }
    }
    
    @Override
    public void run()
    {
        piove = true;
        while(piove)
        {
            for(int i = 0; i < numero; i++)
            {
                gocce.add(new Goccia(img_goccia, 20, 50, (rand.nextInt(main.getLarghezza() ) ), -50, (rand.nextInt(maxVel)+2), main));
            }
           
            try 
            {
                Thread.sleep(attesa);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Pioggia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList getGocce()
    {
        return gocce;
    }
   
}
