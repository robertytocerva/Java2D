/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaintJava;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author user
 */
public class LienzoPaint2D extends Canvas{
     private Vector puntos = new Vector();
    private Point punto = new Point();
    private  Vector puntosAux = new Vector();
    private Vector figuras = new Vector();

    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.pink);

        if(figuras.size()>0){

            for(int f = 0; f<figuras.size(); f++){
                puntosAux = (Vector) figuras.elementAt(f);

                for(int r =0; r<puntosAux.size(); r++){
                    punto = (Point) puntosAux.elementAt(r);
                    g.fillOval((int)punto.getX()-5,(int)punto.getY()-5,10,10);

                    if(r>0) {
                        Point puntoAux = new  Point();
                        puntoAux = (Point) puntosAux.elementAt(r-1);
                        g.drawLine((int) punto.getX(),(int) punto.getY(),(int) puntoAux.getX(),(int) puntoAux.getY());
                    }
                }
            }
        }


        for(int r =0; r<puntos.size(); r++){
            punto = (Point) puntos.elementAt(r);
            g.fillOval((int)punto.getX()-5,(int)punto.getY()-5,10,10);

            if(r>0) {
                Point puntoAux = new  Point();
                puntoAux = (Point) puntos.elementAt(r-1);
                g.drawLine((int) punto.getX(),(int) punto.getY(),(int) puntoAux.getX(),(int) puntoAux.getY());

            }
        }




    }

    @Override
    public boolean mouseDown(Event evt, int x, int y){
        if(x<900 && y<900){
            System.out.println("Ratón Doown  en Canvas: (" + x + "," + y + ")");
            puntos.addElement(new Point(x,y));
            this.repaint();
            return true;

        }
        return  false;
    }

    public void nuevaFigura(){
        figuras.addElement(new Vector(puntos));
        puntos.clear();
    }

    public void moverFiguara(int fig, String dir) {
        if (puntosAux.size() > 0) {
            figuras.addElement(new Vector(puntos));
            puntos.clear();
        }

        puntosAux = (Vector) figuras.elementAt(fig - 1);

        int dx = 0, dy = 0;

        switch (dir) {
            case "Izq": dx = -5; break;
            case "Der": dx = 5; break;
            case "Arriba": dy = -5; break;
            case "Abajo": dy = 5; break;
            default: return;
        }

        // Verificar que ningún punto salga del lienzo
        for (int r = 0; r < puntosAux.size(); r++) {
            Point p = (Point) puntosAux.elementAt(r);
            int nuevoX = p.x + dx;
            int nuevoY = p.y + dy;
            if (nuevoX < 0 || nuevoX > 900 || nuevoY < 0 || nuevoY > 900) {
                return; // Detener si algún punto saldría del lienzo
            }
        }

        // Si todo está bien, mover todos los puntos
        for (int r = 0; r < puntosAux.size(); r++) {
            Point p = (Point) puntosAux.elementAt(r);
            p.x += dx;
            p.y += dy;
        }

        figuras.set(fig - 1, puntosAux); // Actualizar figura
        this.repaint();
        chocar(fig);
    }

    public  boolean chocar(int numFig){
        puntosAux = (Vector) figuras.elementAt(numFig-1);

        for(int r =0; r<puntosAux.size(); r++){
            punto = (Point) puntosAux.elementAt(r);
            Vector puntosFigComparar = new Vector();
            for(int nFig =0; nFig<figuras.size(); nFig++){
                if (nFig != numFig-1){
                    puntosFigComparar = (Vector) figuras.elementAt(nFig);
                    int x1 = 1000, x2 =0, y1 = 1000, y2 =0;
                    for (int p = 0; p < puntosFigComparar.size(); p++) {
                        Point puntoAux = new Point();
                        puntoAux = (Point) puntosFigComparar.elementAt(p);
                        if(puntoAux.x<x1) x1=puntoAux.x;
                        if(puntoAux.x>x2) x2=puntoAux.x;
                        if(puntoAux.y<y1) y1=puntoAux.y;
                        if(puntoAux.y>y2) y2=puntoAux.y;
                    }
                    if (punto.x>x1 && punto.x<x2 && punto.y>y1 && punto.y<y2){
                        System.out.println("Choco con la figura: " + ((int)nFig+1));
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public void escalarFigura(int indiceFigura, double escala) {
        if (indiceFigura >= 0 && indiceFigura < figuras.size()) {
            Vector figura = (Vector) figuras.elementAt(indiceFigura-1);

            // Calcular el centro de la figura
            int sumX = 0, sumY = 0;
            for (int i = 0; i < figura.size(); i++) {
                Point p = (Point) figura.get(i);
                sumX += p.x;
                sumY += p.y;
            }
            double centroX = sumX / (double) figura.size();
            double centroY = sumY / (double) figura.size();

            // Escalar cada punto respecto al centro
            for (int i = 0; i < figura.size(); i++) {
                Point p = (Point) figura.get(i);
                int newX = (int) (centroX + (p.x - centroX) * escala);
                int newY = (int) (centroY + (p.y - centroY) * escala);
                p.setLocation(newX, newY);
                figura.set(i, p);
            }

            repaint();
        } else {
            System.out.println("Índice de figura inválido.");
        }
    }

    public void rotarFigura(int indiceFigura, double grados) {
        if (indiceFigura >= 0 && indiceFigura < figuras.size()) {
            Vector figura = (Vector) figuras.elementAt(indiceFigura - 1);


            int sumX = 0, sumY = 0;
            for (int i = 0; i < figura.size(); i++) {
                Point p = (Point) figura.get(i);
                sumX += p.x;
                sumY += p.y;
            }
            double centroX = sumX / (double) figura.size();
            double centroY = sumY / (double) figura.size();


            for (int i = 0; i < figura.size(); i++) {
                Point p = (Point) figura.get(i);
                double x = p.x;
                double y = p.y;

                int newX = (int) ((centroX + (x - centroX) * Math.cos(grados * Math.PI / 180)) - ((y - centroY) * Math.sin(grados * Math.PI / 180)));
                int newY = (int) ((centroY + (x - centroX) * Math.sin(grados * Math.PI / 180)) + ((y - centroY) * Math.cos(grados * Math.PI / 180)));

                p.setLocation(newX, newY);
                figura.set(i, p);
            }

            repaint();
        } else {
            System.out.println("Índice de figura inválido.");
        }
    }
}
