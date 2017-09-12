package info;

import processing.core.PApplet;
import processing.core.PImage;
import root.Pantalla;

/**
 * Created by ricar on 11/09/2017.
 */
public class Info {

    static PApplet app= Pantalla.app;

    // Pantalla inicial
    public PImage play,title,flat;

    private static Info info=null;

    public void loadInicio(){
        play=app.loadImage("../data/img/play.png");
        title=app.loadImage("../data/img/title.png");
        flat=app.loadImage("../data/img/flat.png");
    }
    public static Info getInstance(){
        if(info==null){
            info=new Info();
        }
        return  info;
    }
}
