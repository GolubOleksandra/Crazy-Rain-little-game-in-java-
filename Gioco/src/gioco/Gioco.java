/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import java.util.ConcurrentModificationException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

//Canvas serve per dire alla finestra che li ci sara' disegnato qualcosa
//Runnable serve per fare i thread che consente fare piu agioni contemporaneamente
//MouseMotionListener - un altra interfaccia implementata che vede la disposizione del mouse in tempo reale
//ma lo vede soltanto nel nostro oggetto
public class Gioco extends Canvas implements Runnable, MouseMotionListener, ActionListener, MouseListener{
    //costanti
    //dentro della classe main non si puo usare una variabile non statica
    private static final int larghezza = 1100; //1280
    private static final int altezza = 720; 
    private static final String nome_gioco = "Crazy Rain";
    
    public static final JFrame finestra_gioco = new JFrame(nome_gioco);
    
    public static STATE State = STATE.MENU;
    private boolean giocoAttivo = false;

    private Criccetto ogg_criccetto;
    private Giocatore giocatore;
    private Pioggia pioggia;
    
    public static Gioco gioco = new Gioco();
    public static Thread thread_gioco = new Thread(gioco); 
    
    BufferedImage sfondo;
    BufferedImage sfondomain;
    BufferedImage sfondoend;
    BufferedImage goccia;
    BufferedImage ombrello;
    BufferedImage criccetto;
    
    long Tempo1;
    long Tempo2;
    long Tempo;
    
    private static enum STATE
    {
        MENU,
        GAME, 
        END;
    }
    
    public Gioco() 
    {
        caricaRisorse();
        inizioGioco();
        Tempo1 = System.currentTimeMillis();
    }

    public static void main(String[] args) 
    {
        Dimension dimensione_finestra = new Dimension(larghezza, altezza);
        finestra_gioco.setPreferredSize(dimensione_finestra);
        finestra_gioco.setMaximumSize(dimensione_finestra);
        finestra_gioco.setResizable(false); // false - non si puo modificare le dimensioni della finestra, invece con true - si puo
        
        finestra_gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //per chiudere la finestra
        
        finestra_gioco.add(gioco); //e' un oggetto di tipo Canvas, cioe' un oggetto su cui si puo disegnare
        gioco.addMouseMotionListener(gioco);
        gioco.addMouseListener(gioco);
   
        finestra_gioco.pack(); //per creare tutto^ la finestra, il pannello e ecc
        finestra_gioco.setVisible(true);
        
        thread_gioco.run(); 
        thread_gioco.start(); 

    } 
    
    private void caricaRisorse()
    {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        
        sfondo = loader.caricaImmagine("/img/sfondo.png");
        sfondomain = loader.caricaImmagine("/img/sfondomain.png");
        ombrello = loader.caricaImmagine("/img/ombrello.png");
        criccetto = loader.caricaImmagine("/img/criccetto.png");
        goccia = loader.caricaImmagine("/img/goccia.png");
        sfondoend = loader.caricaImmagine("/img/sfondoend.png");
        
        System.out.println("Risorse caricate!!");
    }
       
    private void inizioGioco()
    {
        giocatore = new Giocatore(ombrello, 0, 250, 250, this);
        ogg_criccetto = new Criccetto(criccetto, 235, 230, 240, 470, this);
        ogg_criccetto.start();
        pioggia = new Pioggia(goccia, 5, 400, this);
        pioggia.start();
    }
    
    private void disegna()
    {
        //serve per le immagini che si muovovno (quelli possono muoversi a scatti)
        BufferStrategy buffer = getBufferStrategy();
        
        if(buffer == null)
        {
            createBufferStrategy(2);
            return;
        }
        //da quel momento Graghics noi prendiamo dal buffer
        //fa la stessa cosa di prima, pero questa volta abbiamo piu buffer
        Graphics g = buffer.getDrawGraphics();
        
        if(null != State)
        switch (State) 
        {
            case MENU:
            {
                g.drawImage(sfondomain, 0, 0, larghezza, altezza, this);
                
                buffer.show();
                g.dispose(); 
            break;
            }
                        
            case GAME:
            {
                Tempo2=System.currentTimeMillis();
                Tempo=(Tempo2-Tempo1)/1000;

                g.drawImage(sfondo, 0, 0, larghezza, altezza, this);
                ogg_criccetto.disegna(g);
                giocatore.disegna(g);
                pioggia.disegna(g);
                
                g.setColor(Color.black);
                g.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
                g.drawString("Vita = " + ogg_criccetto.vita, 25, 25);
                g.drawString("Time = " + Tempo, 25, 50);

                buffer.show();
                g.dispose(); 
            break;
            }

            case END:
            {
                g.clearRect(0, 0, larghezza, altezza);
                g.drawImage(sfondoend, 0, 0, larghezza, altezza, this);

                Font f = new Font("Lucida Sans", Font.PLAIN, 40);
                g.setFont(f);
                g.setColor(Color.black);
                g.drawString(Tempo + " sec", 860, 240);

                buffer.show();
                g.dispose(); 
            break;
            }
            
            default:
                break;
        }  
    }
    
    private void aggiorna()
    {   
        if(Gioco.State == Gioco.STATE.GAME)
        {
        ArrayList<Goccia> gocce = pioggia.getGocce();
        try
        {
            for(Goccia goc : gocce) //per ogni oggetto goccia di gocce fai questo:
            {
                if(GestoreCollisioni.ControllaCollisione(giocatore, goc))
                {
                    gocce.remove(goc);
                    break;
                }
                
                if(GestoreCollisioni.ControllaCollisioneCriccetto(ogg_criccetto, goc))
                {
                    gocce.remove(goc);
                    ogg_criccetto.vita -=5;
                    break;
                }
            }
        }
        catch(ConcurrentModificationException e) {}
        
        if(controllaSconfitta())
        {
            Gioco.State = Gioco.STATE.END;
        }
        }
    }
    
    private boolean controllaSconfitta()
    {
        return ogg_criccetto.vita == 0;
    }
    
    public int getAltezza()
    {
        return altezza;
    }
    
    public int getLarghezza()
    {
        return larghezza;
    }
    
    //visualizza le immagini sempre
    @Override
    public void run() 
    {
        giocoAttivo = true;
        try
        {
            while(giocoAttivo)
            {
                aggiorna(); //controlla le collisioni
                disegna(); 
           }
        }
        catch(NullPointerException e) {}
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        //vede il punto in cui si trova il mouse
        int posizione = (e.getPoint().x) - (giocatore.getLarghezza()/2); // e' la posizione del mouse
        
        if((posizione >= 0) && ((posizione + giocatore.getLarghezza()) <= larghezza))
        {
            giocatore.setX(posizione);
        }
        //System.out.println("Mouse moved!");
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        int mx = e.getX();
        int my = e.getY();
        
        //Play Button di start
        if(mx >= 970 && mx <= 830+270)
        {
            if(my >= 73 && my <= 73+87)
            {
                this.ogg_criccetto.vita = 100;
                Gioco.State = Gioco.STATE.GAME; 
                this.Tempo1 = 0;
                inizioGioco();
                Tempo1 = System.currentTimeMillis();
            }
        }
        
        //Exit Button di start
        if(mx >= 830 && mx <= 830 + 270)
        {
            if(my >= 185 && my <= 185+87)
            {
                System.exit(1);
            }
        } 
        
        //Restart Button di the end
        if(mx >= 875 && mx <= 875 + 280)
        {
            if(my >= 55 && my <= 55+48)
            {
                this.ogg_criccetto.vita = 100;
                Gioco.State = Gioco.STATE.GAME; 
                this.Tempo1 = 0;
                inizioGioco();
                Tempo1 = System.currentTimeMillis();
            }
        } 
        
        //Restart Button di game
        if(mx >= 1019 && mx <= 1019 + 206)
        {
            if(my >= 9 && my <= 9+72)
            {
                this.ogg_criccetto.vita = 100;
                Gioco.State = Gioco.STATE.GAME; 
                this.Tempo1 = 0;
                inizioGioco();
                Tempo1 = System.currentTimeMillis();
            }
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}  
}
