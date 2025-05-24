/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineas;

import java.awt.*;
import java.util.Objects;
import java.util.Vector;

/**
 *
 * @author rober
 */
public class lienzo extends Canvas {
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



        //dibujo de la casa
//        g.drawLine(70, 240, 240, 240);
//        g.drawLine(70,240,70,130);
//        g.drawLine(240,240,240,130);
//        g.drawLine(70,130,240,130);
//        g.drawLine(70,130,150,50);
//        g.drawLine(240,130,150,50);
//        g.drawLine(100,240,100,200);
//        g.drawLine(130,240,130,200);
//        g.drawLine(100,200,130,200);
//        g.drawLine(170,195,200,195);
//        g.drawLine(170,195,170,170);
//        g.drawLine(200,195,200,170);
//        g.drawLine(170,170,200,170);
    }

    @Override
    public boolean mouseDown(Event evt, int x, int y){
        if(x<700 && y<700){
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
            if (nuevoX < 0 || nuevoX > 700 || nuevoY < 0 || nuevoY > 700) {
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
    }

//    public  void  moverFiguara(int fig, String dir){
//        if(puntosAux.size()>0){
//            figuras.addElement(new Vector(puntos));
//            puntos.clear();
//        }
//        puntosAux = (Vector) figuras.elementAt(fig-1);
//
//        switch (dir){
//            case "Izq":
//                if(punto.x-10<0){
//                    break;
//                }else {
//                    for(int r =0; r<puntosAux.size(); r++){
//                        punto = (Point) puntosAux.elementAt(r);
//                        punto.x-=10;
//                        puntosAux.indexOf(punto, r);
//                    }
//                    figuras.indexOf(puntosAux, fig-1);
//                    break;
//                }
//
//            case "Der":
//                if(punto.x+10>700){
//                    break;
//                }else {
//                    for(int r =0; r<puntosAux.size(); r++){
//                        punto = (Point) puntosAux.elementAt(r);
//                        punto.x+=10;
//                        puntosAux.indexOf(punto, r);
//                    }
//                    figuras.indexOf(puntosAux, fig-1);
//                    break;
//                }
//
//            case "Arriba":
//                if(punto.y-10<0){
//                    break;
//                }else{
//                    for(int r =0; r<puntosAux.size(); r++){
//                        punto = (Point) puntosAux.elementAt(r);
//                        punto.y-=10;
//                        puntosAux.indexOf(punto, r);
//                    }
//                    figuras.indexOf(puntosAux, fig-1);
//                    break;
//                }
//
//            case "Abajo":
//                if(punto.y+10>700){
//                    break;
//                }else {
//                    for(int r =0; r<puntosAux.size(); r++){
//                        punto = (Point) puntosAux.elementAt(r);
//                        punto.y+=10;
//                        puntosAux.indexOf(punto, r);
//                    }
//                    figuras.indexOf(puntosAux, fig-1);
//                    break;
//                }
//
//            default:
//        }
//        this.repaint();
//    }

}
