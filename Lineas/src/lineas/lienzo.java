/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineas;

import java.awt.*;
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
    

}
