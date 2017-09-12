package pantallas;

import info.Info;
import root.Pantalla;

/**
 * Created by ricar on 11/09/2017.
 */
public class PInicio extends Pantalla {

    int  b1, b2, c1, c2;
    int Y_AXIS = 1;
    int X_AXIS = 2;
    int altodegra=app.height-230;

    public PInicio(){

        c1 = app.color(240, 132, 130);
        c2 = app.color(219, 64, 105);

    }

    @Override
    public void iniciar() {
        Info.getInstance().loadInicio();

    }

    @Override
    public void pintar() {
        app.image(Info.getInstance().flat,0, altodegra-85);
        setGradient(0, 0, app.width, altodegra, c1, c2, X_AXIS);
        app.imageMode(app.CENTER);
        app.image(Info.getInstance().play,app.width/2, altodegra+4);
        app.image(Info.getInstance().title,app.width/2, app.height/3);
        app.imageMode(app.CORNER);
    }

    @Override
    public void finalizar() {

    }

    void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

        app.noFill();

        if (axis ==Y_AXIS) {  // Top to bottom gradient
            for (int i = y; i <= y+h; i++) {
                float inter = app.map(i, y, y+h, 0, 1);
                int col=app.lerpColor(c1, c2, inter);
                app.stroke(col);
                app.line(x, i, x+w, i);
            }
        }
        else if (axis == X_AXIS) {  // Left to right gradient
            for (int i = x; i <= x+w; i++) {
                float inter = app.map(i, x, x+w, 0, 1);
                int co=app.lerpColor(c1, c2, inter);
                app.stroke(co);
                app.line(i, y, i, y+h);
            }
        }
    }
}
