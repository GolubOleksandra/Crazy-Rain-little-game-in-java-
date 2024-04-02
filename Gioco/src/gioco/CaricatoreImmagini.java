/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class CaricatoreImmagini 
{
    BufferedImage image;
    
    public BufferedImage caricaImmagine(String posizione)
    {
        try 
        {
            image = ImageIO.read(getClass().getResource(posizione)); //va a cercare la cartella scr, dove vengono contenuti tutte le risorse del programma (SEMPRE)
        } 
        catch (IOException ex) 
        {
            System.out.println("Immagine alla posizione" + posizione + " caricata correttamente!");
            Logger.getLogger(CaricatoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
}