/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escalar;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;


/**
 *
 * @author user
 */
public class Lienzo extends Canvas{
    ArrayList v = new ArrayList();
    Point p;
    public Lienzo (){
        v.add(new Point(100,100));
        v.add(new Point(150,100));
        v.add(new Point(150,150));
        v.add(new Point(100,150));
    }
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.red);

        GeneralPath gp = new GeneralPath();
        for (int i = 0; i < v.size(); i++) {
            p = (Point) v.get(i);
            g2d.drawOval(p.x, p.y, 1, 1);

            if (i == 0){
                gp.moveTo(p.x, p.y);
            }else{
                gp.lineTo(p.x, p.y);
            }

        }
        gp.closePath();
        g2d.draw(gp);

    }

    public void escalar(int escX, int escY){
        for (int i = 0; i < v.size(); i++) {
            p = (Point) v.get(i);
            p.x = p.x*escX;
            p.y = p.y*escY;
            v.set(i, p);

        }
        repaint();
    }
}
