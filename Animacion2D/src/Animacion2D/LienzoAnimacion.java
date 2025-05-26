/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animacion2D;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public class LienzoAnimacion extends Canvas {
    private Stickman stickman;
    private BufferedImage offscreenBuffer;
    private Graphics2D offscreenGraphics;
    private int ancho = 1500;
    private int alto = 1000;
    
    public LienzoAnimacion() {
        setBackground(Color.ORANGE);
        stickman = new Stickman(100, 500);
        
        // Crear el buffer para doble búfer
        offscreenBuffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreenBuffer.createGraphics();
        
        // Configurar la calidad del renderizado
        offscreenGraphics.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        iniciarAnimacion();
    }
    
    public void iniciarAnimacion() {
        AnimadorStickman animador = new AnimadorStickman(this, stickman);
        animador.start();
    }

    @Override
    public void update(Graphics g) {
        // Sobreescribir update para evitar borrado automático
        paint(g);
    }
    
    @Override
    public void paint(Graphics g) {
        if (offscreenBuffer == null) {
            // Si el buffer no existe, créalo
            offscreenBuffer = new BufferedImage(getWidth() > 0 ? getWidth() : ancho, 
                                                getHeight() > 0 ? getHeight() : alto, 
                                                BufferedImage.TYPE_INT_ARGB);
            offscreenGraphics = offscreenBuffer.createGraphics();
            offscreenGraphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        // Limpiar el buffer
        offscreenGraphics.setColor(getBackground());
        offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        
        // Dibujar en el buffer
        offscreenGraphics.setStroke(new BasicStroke(3));
        offscreenGraphics.setColor(Color.GRAY);
        offscreenGraphics.drawLine(0, 700, 1500, 700);
        
        // Dibujar el stickman en el buffer
        stickman.dibujar(offscreenGraphics);
        
        // Copiar el buffer a la pantalla
        g.drawImage(offscreenBuffer, 0, 0, this);
    }
    
    public Point rotarPunto(Point p, double angulo, Point centro) {
        double rad = Math.toRadians(angulo);
        int x = centro.x + (int)((p.x - centro.x) * Math.cos(rad) - (p.y - centro.y) * Math.sin(rad));
        int y = centro.y + (int)((p.x - centro.x) * Math.sin(rad) + (p.y - centro.y) * Math.cos(rad));
        return new Point(x, y);
    }
}
