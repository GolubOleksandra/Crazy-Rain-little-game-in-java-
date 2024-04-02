/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;
public class GestoreCollisioni 
{
//e' statica - si puo accedere alla funzione fuori dalla classe senza definire oggetto
    public static boolean ControllaCollisione(Giocatore ombrello, Goccia goccia)
    {
        //se ombrello interseca la goccia ci da il valore true, altrimenti e' false
        return ombrello.getBordi().intersects(goccia.getBordi());
    }
    
    public static boolean ControllaCollisioneCriccetto(Criccetto criccetto, Goccia goccia)
    {
        //se ombrello interseca la goccia ci da il valore true, altrimenti e' false
        return criccetto.getBordi().intersects(goccia.getBordi());
    }

}
