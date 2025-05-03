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
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.pink);

        for(int r =0; r<puntos.size(); r++){
            punto = (Point) puntos.elementAt(r);
            //g.fillOval((int)punto.getX()-1,(int)punto.getY()-1,10,10); //circulos rellenos
            g.drawOval((int)punto.getX()-1,(int)punto.getY()-1,10,10); //circunferencias
            //g.drawLine((int)punto.getX (),(int)punto.getY (),(int)punto.getX(), (int)punto.getY());
            //g.drawLine(70, 240, (int)punto.getX(), (int)punto.getY());
        }
        

        
        g.drawLine(70, 240, 240, 240);
        g.drawLine(70,240,70,130);
        g.drawLine(240,240,240,130);
        g.drawLine(70,130,240,130);
        g.drawLine(70,130,150,50);
        g.drawLine(240,130,150,50);
        g.drawLine(100,240,100,200);
        g.drawLine(130,240,130,200);
        g.drawLine(100,200,130,200);
        g.drawLine(170,195,200,195);
        g.drawLine(170,195,170,170);
        g.drawLine(200,195,200,170);
        g.drawLine(170,170,200,170);
    }

    @Override
    public boolean mouseDown(Event evt, int x, int y){
        if(x<300 && y<300){
            System.out.println("Ratón Doown  en Canvas: (" + x + "," + y + ")");
            puntos.addElement(new Point(x,y));
            this.repaint();
            return true;

        }
        return  false;
    }
}
