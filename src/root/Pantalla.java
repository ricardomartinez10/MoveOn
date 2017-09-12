package root;

import processing.core.PApplet;

/**
 * Created by ricar on 11/09/2017.
 */
public abstract class Pantalla extends Thread {
    public static PApplet app;

    protected boolean vivo = true;

    public abstract void iniciar();

    public abstract void pintar();

    public abstract void finalizar();

    public void mousePressed() {


    }


    public void cargarMano() {
       // Hand.cargarMano(this);
    }

    public void pressHandRight() {

    }


    public void pintarHandRight() {
      //  Hand.pintar();
    }

    public void mouseDragged() {
    }

    ;

    public void mouseReleased() {
    }

    ;

    public void KeyPressed() {


    }
}
