package root;

import info.Info;
import pantallas.PInicio;
import processing.core.PApplet;

/**
 * Created by ricar on 11/09/2017.
 */
public class Logica {

    private static PApplet app;

    public Logica(PApplet app){
        this.app=app;

        Info.getInstance();

            AdministradorPantalla.cambiarPantalla(new PInicio());
            AdministradorPantalla.getCurrentScreen().iniciar();

    }

    public void pintar() {
        AdministradorPantalla.getCurrentScreen().pintar();
    }

    public static PApplet getApp() {
        return app;
    }




}
