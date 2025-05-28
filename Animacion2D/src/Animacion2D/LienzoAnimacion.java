/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animacion2D;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class LienzoAnimacion extends Canvas {
    private Stickman stickman;
    private AnimadorStickman animador;
    private BufferedImage offscreenBuffer;
    private Graphics2D offscreenGraphics;
    private int ancho = 1500;
    private int alto = 1000;
    

    private ArrayList<LetraArrastrable> letras = new ArrayList<>();
    private LetraArrastrable letraSeleccionada = null;
    
    public LienzoAnimacion() {
        setBackground(Color.ORANGE);
        stickman = new Stickman(100, 500);
        

        letras.add(new LetraArrastrable('P', 100, 100, new Color(200, 50, 50), "pausar"));
        letras.add(new LetraArrastrable('C', 200, 100, new Color(50, 200, 50), "continuar"));
        letras.add(new LetraArrastrable('T', 300, 100, new Color(50, 50, 200), "terminar"));
        

        configurarEventosMouse();
        

        offscreenBuffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreenBuffer.createGraphics();
        

        offscreenGraphics.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        iniciarAnimacion();
    }
    

    private void configurarEventosMouse() {
        // Manejar clic del mouse (para seleccionar una letra)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Verificar si se hizo clic en alguna letra
                for (LetraArrastrable letra : letras) {
                    if (letra.contienePunto(e.getX(), e.getY())) {
                        letraSeleccionada = letra;
                        letra.setSeleccionada(true);
                        System.out.println("Letra seleccionada: " + letra.getLetra());
                        repaint();
                        break;
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (letraSeleccionada != null) {
                    // Verificar colisión con el stickman al soltar
                    boolean colision = letraSeleccionada.colisionaCon(stickman);
                    
                    if (colision) {
                        System.out.println("¡Colisión detectada con letra " + letraSeleccionada.getLetra() + "!");
                        
                        // Ejecutar acción según la letra
                        switch (letraSeleccionada.getLetra()) {
                            case 'P': // Pausar
                                if (animador != null && !animador.isPaused()) {
                                    System.out.println("Ejecutando acción: PAUSAR");
                                    animador.pausar();
                                }
                                break;
                            case 'C': // Continuar
                                if (animador != null && animador.isPaused()) {
                                    System.out.println("Ejecutando acción: CONTINUAR");
                                    animador.reanudar();
                                }
                                break;
                            case 'T': // Terminar
                                if (animador != null) {
                                    System.out.println("Ejecutando acción: TERMINAR");
                                    animador.detener();
                                }
                                break;
                        }
                    } else {
                        System.out.println("No hubo colisión con el stickman");
                    }
                    
                    // Deseleccionar la letra
                    letraSeleccionada.setSeleccionada(false);
                    letraSeleccionada = null;
                    repaint();
                }
            }
        });
        
        // Manejar movimiento del mouse (para arrastrar la letra seleccionada)
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (letraSeleccionada != null) {
                    letraSeleccionada.mover(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }
    
    public void iniciarAnimacion() {
        animador = new AnimadorStickman(this, stickman);
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
        
        // Dibujar la línea del suelo
        offscreenGraphics.setStroke(new BasicStroke(3));
        offscreenGraphics.setColor(Color.GRAY);
        offscreenGraphics.drawLine(0, 700, 1500, 700);
        
        // Dibujar el stickman en el buffer
        stickman.dibujar(offscreenGraphics);
        
        // Dibujar las letras arrastrables
        for (LetraArrastrable letra : letras) {
            letra.dibujar((Graphics2D)offscreenGraphics);
        }
        

        offscreenGraphics.setColor(Color.BLACK);
        offscreenGraphics.setFont(new Font("Arial", Font.BOLD, 16));
        if (animador != null) {
            String estado = animador.isPaused() ? "PAUSADO" : "REPRODUCIENDO";
            offscreenGraphics.drawString("Estado: " + estado, 10, 30);
        }
        
        // Dibujar instrucciones
        offscreenGraphics.setFont(new Font("Arial", Font.PLAIN, 14));
        offscreenGraphics.drawString("Arrastra las letras", 300, 30);
        offscreenGraphics.drawString("P = Pausar la animación", 300, 50);
        offscreenGraphics.drawString("C = Continuar la animación", 300, 70);
        offscreenGraphics.drawString("T = Terminar la animación", 300, 90);
        

        if (animador != null) {
            for (LetraArrastrable letra : letras) {
                if (letra.colisionaCon(stickman)) {
                    offscreenGraphics.setColor(Color.RED);
                    offscreenGraphics.setFont(new Font("Arial", Font.BOLD, 18));
                    offscreenGraphics.drawString("¡Colisión detectada con letra " + letra.getLetra() + "!", 500, 30);
                    break;
                }
            }
        }
        

        g.drawImage(offscreenBuffer, 0, 0, this);
    }
    
    public Point rotarPunto(Point p, double angulo, Point centro) {
        double rad = Math.toRadians(angulo);
        int x = centro.x + (int)((p.x - centro.x) * Math.cos(rad) - (p.y - centro.y) * Math.sin(rad));
        int y = centro.y + (int)((p.x - centro.x) * Math.sin(rad) + (p.y - centro.y) * Math.cos(rad));
        return new Point(x, y);
    }
    

    public AnimadorStickman getAnimador() {
        return animador;
    }
}
