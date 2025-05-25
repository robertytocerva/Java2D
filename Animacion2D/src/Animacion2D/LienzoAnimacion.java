/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animacion2D;

import java.awt.*;

/**
 *
 * @author user
 */
public class LienzoAnimacion extends Canvas{
    private Stickman stickman;
    public LienzoAnimacion() {
        stickman = new Stickman(100, 500);
        iniciarAnimacion();
    }
    public void iniciarAnimacion() {
        AnimadorStickman animador = new AnimadorStickman(this, stickman);
        animador.start();
    }

    @Override
    public void paint (Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.black);

        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, 700, 1500, 700);
        stickman.dibujar(g2d);
    }


}
