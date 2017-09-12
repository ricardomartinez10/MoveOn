package root;

/**
 * Created by ricar on 11/09/2017.
 */
public class AdministradorPantalla {
    private static Pantalla pantallaVisible;

    public static void cambiarPantalla(Pantalla pantalla) {
        if (pantallaVisible != null)
            pantallaVisible.finalizar();// mata la anterior.

        pantallaVisible = pantalla;// inicia la nueva
        pantallaVisible.iniciar();
    }

    public static Pantalla getCurrentScreen() {
        return pantallaVisible;
    }



}
