package Animacion2D;

import java.awt.*;
import java.util.ArrayList;

public class AnimadorStickman extends Thread {
    private LienzoAnimacion lienzo;
    private Stickman stickman;

    public AnimadorStickman(LienzoAnimacion lienzo, Stickman stickman) {
        this.lienzo = lienzo;
        this.stickman = stickman;
    }

    private volatile boolean running = true;
    
    @Override
    public void run() {
        while (running) {
            try {
                // Esperar un momento antes de comenzar una nueva iteración
                Thread.sleep(200);
                
                caminar(20, 9);       // salto y caída
                if (!running) break;
                
                vueltaDeCarro();
                if (!running) break;
                
                sentadillas();
                if (!running) break;
                
                brinco();
                if (!running) break;
                
                caminar(20, 9);
                if (!running) break;
                
                vueltaDeCarro();
                if (!running) break;
                
                escalar(1.5);
                if (!running) break;
                
                caminar(20, 9);
                if (!running) break;
                
                brinco();
                if (!running) break;
                
                escalar(1.1);
                if (!running) break;
                
                saltar();
                if (!running) break;
                
                saltar();
                if (!running) break;
                
                saltar();
                if (!running) break;
                
                vueltaDeCarro();
                if (!running) break;
                
                reiniciar();
                if (!running) break;
                
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
    
    public void detener() {
        running = false;
    }

    private void vueltaDeCarro() throws InterruptedException {
        for (int i = 0; i <= 360; i += 15) {
            stickman.trasladar(8, 0); // avanzar a la derecha
            stickman.rotar(14.4, lienzo); // girar todo el cuerpo
            
            // Repintar y esperar para que se complete
            lienzo.repaint();
            
            // Usar espera sin bloquear repintado
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw e;
            }
            
            // Dar tiempo al sistema para procesar eventos gráficos
            Toolkit.getDefaultToolkit().sync();
        }
    }




    private void caminar(int paso, int repeticiones) throws InterruptedException {
        for (int i = 0; i < repeticiones; i++) {
            int direccion = (i % 2 == 0) ? 1 : -1;

            moverPierna(true, 10 * direccion);
            moverPierna(false, -10 * direccion);
            stickman.trasladar(paso, 0);
            
            // Repintar y sincronizar
            lienzo.repaint();
            Thread.sleep(70);
            Toolkit.getDefaultToolkit().sync();
        }
    }

    private void moverPierna(boolean izquierda, double grados) {
        ArrayList<Point> puntos = stickman.getPuntos();
        Point cadera = puntos.get(10); // torso como eje (ahora en índice 10)
    
        int index = izquierda ? 13 : 14; // nuevos índices para piernas
        puntos.set(index, rotarPunto(cadera, puntos.get(index), grados));
    }

    private void saltar() throws InterruptedException {
        levantarBrazos(-30); // subir brazos

        int[] desplazamientoY = {-14, -12, -10, -8, -5, -3, -1, 0, 1, 3, 5, 8, 10, 12, 14};
        for (int dy : desplazamientoY) {
            stickman.trasladar(9, dy);
            lienzo.repaint();
            Thread.sleep(40);
        }

        levantarBrazos(30); // regresar brazos
        stickman.alinearAlturaBase(650);
        lienzo.repaint();

    }

    private void levantarBrazos(double grados) {
        ArrayList<Point> puntos = stickman.getPuntos();
        Point hombro = puntos.get(9); // cuello como base (ahora en índice 9)
    
        // índice 11 = brazo izquierdo, 12 = brazo derecho (nuevos índices)
        puntos.set(11, rotarPunto(hombro, puntos.get(11), grados));
        puntos.set(12, rotarPunto(hombro, puntos.get(12), grados));
    }
    private Point rotarPunto(Point centro, Point p, double grados) {
        double rad = Math.toRadians(grados);
        int x = p.x - centro.x;
        int y = p.y - centro.y;

        int xr = (int) (x * Math.cos(rad) - y * Math.sin(rad));
        int yr = (int) (x * Math.sin(rad) + y * Math.cos(rad));

        return new Point(centro.x + xr, centro.y + yr);
    }

    private void escalar(double factor) throws InterruptedException {
        stickman.escalar(factor);
        lienzo.repaint();
        Thread.sleep(400);
    }

    private void sentadillas() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            rotarPierna(true, 15);  // baja pierna izq
            rotarPierna(false, 15); // baja pierna der
            lienzo.repaint();
            Thread.sleep(100);

            rotarPierna(true, -15);  // sube pierna izq
            rotarPierna(false, -15); // sube pierna der
            lienzo.repaint();
            Thread.sleep(100);
        }
        stickman.alinearAlturaBase(650);
        lienzo.repaint();
    }

    private void rotarPierna(boolean izquierda, double grados) {
        ArrayList<Point> puntos = stickman.getPuntos();
        Point cadera = puntos.get(10); // torso como eje (ahora en índice 10)
    
        int index = izquierda ? 13 : 14; // nuevos índices para piernas
        Point pierna = puntos.get(index);

        puntos.set(index, rotarPunto(cadera, pierna, grados));
    }

    private void brinco() throws InterruptedException {
        for (int i = 0; i < 3; i++) { // hace 3 flexiones
            // Posición baja
            stickman.trasladar(0, 10);     // cuerpo baja
            stickman.escalar(0.9);         // cuerpo se comprime un poco
            lienzo.repaint();
            Thread.sleep(200);

            // Posición alta
            stickman.trasladar(0, -10);    // cuerpo sube
            stickman.escalar(1.1);         // cuerpo se estira ligeramente
            lienzo.repaint();
            Thread.sleep(200);
        }
        //stickman.reset(); // vuelve a la pose original
        lienzo.repaint();
    }


    private void rotarBrazo(boolean izquierdo, double grados) {
        ArrayList<Point> puntos = stickman.getPuntos();
        Point hombro = puntos.get(9); // cuello como base (ahora en índice 9)
    
        int index = izquierdo ? 11 : 12; // nuevos índices para brazos
        Point brazo = puntos.get(index);

        puntos.set(index, rotarPunto(hombro, brazo, grados));
    }

    private void levantarse() throws InterruptedException {
        stickman.trasladar(0, -20);
        lienzo.repaint();
        Thread.sleep(300);

        stickman.alinearAlturaBase(700);
        lienzo.repaint();
    }

    private void reiniciar() throws InterruptedException {
        Thread.sleep(300);
        stickman.reset(); // volver a tamaño y posición original
        lienzo.repaint();
        Thread.sleep(300);
    }

}

