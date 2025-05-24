/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rotacionrpunto;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author user
 */
public class Lienzo extends Canvas{
    public  int x= 100,y  = 100;
    
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.red);

        g.drawOval(x,y,10,10);
    }

    public  void  girar(int grados){
        double xp,yp;
        xp = ((x*Math.cos(grados*Math.PI/180))-(y*Math.sin(grados*Math.PI/180)));
        yp = ((x*Math.sin(grados*Math.PI/180))+(y*Math.cos(grados*Math.PI/180)));
        x = (int) xp;
        y = (int) yp;
        repaint();
    }
    
}
