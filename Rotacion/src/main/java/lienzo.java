import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class lienzo extends Canvas {
    ArrayList puntos = new ArrayList();
    Point pun = new Point();
    public lienzo (){
        puntos.add(new Point(100,100));
        puntos.add(new Point(300,100));
        puntos.add(new Point(300,300));
        puntos.add(new Point(100,300));
    }
    @Override
    public void paint (Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        GeneralPath gp = new GeneralPath();
        super.paint(g);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.RED);
        g.drawOval(200,200,3,3);

        for (int cont = 0; cont < puntos.size(); cont++){
            pun = (Point) puntos.get(cont);
            g2d.drawOval(pun.x,pun.y,3,3);
            if (cont == 0){
                gp.moveTo(pun.x,pun.y);
            }else {
                gp.lineTo(pun.x, pun.y);
            }
        }
        gp.closePath();
        g2d.draw(gp);

    }
    public void girar(int grados){
        double xp,yp,x,y,xc,yc;
        Point pun1 = new Point();
        pun1 = (Point) puntos.getFirst();
        xc=200;
        yc=200;
        for(int cont = 0; cont < puntos.size(); cont++){

            pun = (Point) puntos.get(cont);
            x = pun.x;
            y = pun.y;
            xp = ((xc+(x-xc)*Math.cos(grados*Math.PI/180))-((y-yc)*Math.sin(grados*Math.PI/180)));
            yp = ((yc+(x-xc)*Math.sin(grados*Math.PI/180))+((y-yc)*Math.cos(grados*Math.PI/180)));
            pun.x = (int) Math.rint(xp);
            pun.y = (int) Math.rint(yp);
            puntos.set(cont,pun);
        }
        repaint();
    }
}
