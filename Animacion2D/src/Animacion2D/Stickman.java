package Animacion2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Stickman {
    private ArrayList<Point> puntos;
    private Point posicionInicial;

    public Stickman(int x, int y) {
        posicionInicial = new Point(x, y);
        construirCuerpo(x, y);
    }

    private void construirCuerpo(int x, int y) {
        puntos = new ArrayList<>();

        puntos.add(new Point(x, y));           // cabeza centro

        puntos.add(new Point(x, y + 30));      // cuello
        puntos.add(new Point(x, y + 120));     // torso
        puntos.add(new Point(x - 60, y + 60)); // brazo izq
        puntos.add(new Point(x + 60, y + 60)); // brazo der
        puntos.add(new Point(x - 40, y + 200)); // pierna izq
        puntos.add(new Point(x + 40, y + 200)); // pierna der
    }


    public void reset() {
        construirCuerpo(posicionInicial.x, posicionInicial.y);
    }


    public void dibujar(Graphics2D g) {
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);

        GeneralPath path = new GeneralPath();

        // Cabeza
        Point cabeza = puntos.get(0);
        g.drawOval(cabeza.x - 15, cabeza.y - 15, 40, 40);

        // Cuello a torso
        path.moveTo(puntos.get(1).x, puntos.get(1).y);
        path.lineTo(puntos.get(2).x, puntos.get(2).y);

        // Brazos
        path.moveTo(puntos.get(3).x, puntos.get(3).y);
        path.lineTo(puntos.get(1).x, puntos.get(1).y);
        path.lineTo(puntos.get(4).x, puntos.get(4).y);

        // Piernas
        path.moveTo(puntos.get(2).x, puntos.get(2).y);
        path.lineTo(puntos.get(5).x, puntos.get(5).y);
        path.moveTo(puntos.get(2).x, puntos.get(2).y);
        path.lineTo(puntos.get(6).x, puntos.get(6).y);

        g.draw(path);
    }

    public void trasladar(int dx, int dy) {
        for (int i = 0; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            puntos.set(i, new Point(p.x + dx, p.y + dy));
        }
    }

    public void escalar(double factor) {
        Point ref = puntos.get(0); // centro cabeza como punto de escala
        for (int i = 0; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            int newX = (int) (ref.x + (p.x - ref.x) * factor);
            int newY = (int) (ref.y + (p.y - ref.y) * factor);
            puntos.set(i, new Point(newX, newY));
        }
    }
    public void alinearAlturaBase(int baseY) {
        Point torso = puntos.get(2);
        int dy = baseY - torso.y;
        trasladar(0, dy);
    }

    public void rotar(double angulo, LienzoAnimacion lienzo) {
        Point centro = puntos.get(2); // o calcula el centro de la figura
        for (int i = 0; i < puntos.size(); i++) {
            puntos.set(i, lienzo.rotarPunto(puntos.get(i), angulo, centro));
        }
    }



    public ArrayList<Point> getPuntos() {
        return puntos;
    }
}