/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lineas;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author rober
 */
public class lienzo extends Canvas {
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        
        

        
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
}
