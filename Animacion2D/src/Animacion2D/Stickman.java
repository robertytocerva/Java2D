package Animacion2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Stickman {
    private ArrayList<Point> puntos;
    private Point posicionInicial;

    public Stickman(int x, int y) {
        posicionInicial = new Point(x, y);
        construirCuerpo(x, y);
    }

    private void construirCuerpo(int x, int y) {
        puntos = new ArrayList<>();


        puntos.add(new Point(x, y));
        
        // Añadimos puntos temporales para la cabeza (serán actualizados después)
        for (int i = 0; i < 8; i++) {
            puntos.add(new Point(0, 0)); // Placeholder para los puntos de la cabeza
        }
    
        // Resto del cuerpo (índices desplazados)
        puntos.add(new Point(x, y + 30));      // cuello (9)
        puntos.add(new Point(x, y + 120));     // torso (10)
        puntos.add(new Point(x - 60, y + 60)); // brazo izq (11)
        puntos.add(new Point(x + 60, y + 60)); // brazo der (12)
        puntos.add(new Point(x - 40, y + 200)); // pierna izq (13)
        puntos.add(new Point(x + 40, y + 200)); // pierna der (14)
        
        // Ahora actualizamos los puntos de la cabeza basados en el centro
        actualizarPuntosCabeza();
    }


    public void reset() {
        construirCuerpo(posicionInicial.x, posicionInicial.y);
    }


    public void dibujar(Graphics2D g) {
        // Guardar la configuración original del Graphics2D
        Stroke originalStroke = g.getStroke();
        Color originalColor = g.getColor();
        
        // Configurar para calidad
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);
    
        // Crear el path completo
        GeneralPath path = new GeneralPath();
    
        // Cabeza con GeneralPath
        path.moveTo(puntos.get(1).x, puntos.get(1).y); // Empezar en cabeza superior izquierda
        path.lineTo(puntos.get(2).x, puntos.get(2).y); // Superior centro
        path.lineTo(puntos.get(3).x, puntos.get(3).y); // Superior derecha
        path.lineTo(puntos.get(4).x, puntos.get(4).y); // Derecha
        path.lineTo(puntos.get(5).x, puntos.get(5).y); // Inferior derecha
        path.lineTo(puntos.get(6).x, puntos.get(6).y); // Inferior centro
        path.lineTo(puntos.get(7).x, puntos.get(7).y); // Inferior izquierda
        path.lineTo(puntos.get(8).x, puntos.get(8).y); // Izquierda
        path.closePath(); // Cerrar el path de la cabeza
    
        // Cuello a torso
        path.moveTo(puntos.get(9).x, puntos.get(9).y);
        path.lineTo(puntos.get(10).x, puntos.get(10).y);
    
        // Brazos
        path.moveTo(puntos.get(11).x, puntos.get(11).y);
        path.lineTo(puntos.get(9).x, puntos.get(9).y);
        path.lineTo(puntos.get(12).x, puntos.get(12).y);
    
        // Piernas
        path.moveTo(puntos.get(10).x, puntos.get(10).y);
        path.lineTo(puntos.get(13).x, puntos.get(13).y);
        path.moveTo(puntos.get(10).x, puntos.get(10).y);
        path.lineTo(puntos.get(14).x, puntos.get(14).y);
    
        // Dibujar todo el path de una vez
        g.draw(path);
        
        // Restaurar la configuración original
        g.setStroke(originalStroke);
        g.setColor(originalColor);
    }

    public void trasladar(int dx, int dy) {
        for (int i = 0; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            puntos.set(i, new Point(p.x + dx, p.y + dy));
        }
        
        // Esto es opcional, ya que la traslación simple no debería deformar la cabeza
        // Pero lo incluimos por consistencia con los otros métodos
        if (dx != 0 || dy != 0) {
            actualizarPuntosCabeza();
        }
    }

    public void escalar(double factor) {
        Point ref = puntos.get(0); // centro cabeza como punto de escala
        
        // Escalar solo el resto del cuerpo (desde el índice 9 en adelante)
        for (int i = 9; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            int newX = (int) (ref.x + (p.x - ref.x) * factor);
            int newY = (int) (ref.y + (p.y - ref.y) * factor);
            puntos.set(i, new Point(newX, newY));
        }
        
        // Reconstruir los puntos de la cabeza con el tamaño original
        // Si queremos que la cabeza también se escale un poco, multiplicamos por un factor más pequeño
        // Por ejemplo, factor * 0.5 para que la deformación sea menos notoria
        double factorCabeza = 1.0 + (factor - 1.0) * 0.3; // Escala la cabeza de forma más sutil
        
        // Primero actualizamos con la forma base
        actualizarPuntosCabeza();
        
        // Luego aplicamos un escalado más sutil a la cabeza si se desea
        if (factorCabeza != 1.0) {
            for (int i = 1; i <= 8; i++) {
                Point p = puntos.get(i);
                int newX = (int) (ref.x + (p.x - ref.x) * factorCabeza);
                int newY = (int) (ref.y + (p.y - ref.y) * factorCabeza);
                puntos.set(i, new Point(newX, newY));
            }
        }
    }
    public void alinearAlturaBase(int baseY) {
        Point torso = puntos.get(10); // Nuevo índice del torso
        int dy = baseY - torso.y;
        trasladar(0, dy);
    }

    public void rotar(double angulo, LienzoAnimacion lienzo) {
        Point centro = puntos.get(10); // Torso como centro de rotación
        
        // Rotar solo el centro de la cabeza y el resto del cuerpo (no los puntos de detalle de la cabeza)
        puntos.set(0, lienzo.rotarPunto(puntos.get(0), angulo, centro)); // Centro de la cabeza
        
        // Rotar el resto del cuerpo desde el índice 9 (cuello) en adelante
        for (int i = 9; i < puntos.size(); i++) {
            puntos.set(i, lienzo.rotarPunto(puntos.get(i), angulo, centro));
        }
        
        // Reconstruir los puntos de la cabeza basados en la nueva posición del centro
        actualizarPuntosCabeza();
    }



    public ArrayList<Point> getPuntos() {
        return puntos;
    }
    
    /**
     * Actualiza la posición de los puntos de la cabeza basándose en el punto central
     * Esto mantiene la forma circular de la cabeza independientemente de las transformaciones
     */
    private void actualizarPuntosCabeza() {
        if (puntos == null || puntos.isEmpty()) {
            return; // Protección contra errores
        }
        
        Point centro = puntos.get(0);
        if (centro == null) {
            return; // Protección contra puntos nulos
        }
        
        // Usar constantes para las dimensiones de la cabeza
        final int RADIO_X = 20;
        final int RADIO_Y = 20;
        
        // Actualizar puntos de la cabeza basándose en el centro y distancias fijas
        // Usando una forma más elíptica para mejor apariencia
        puntos.set(1, new Point(centro.x - (int)(RADIO_X * 0.75), centro.y - (int)(RADIO_Y * 0.75))); // cabeza superior izquierda
        puntos.set(2, new Point(centro.x, centro.y - RADIO_Y));                  // cabeza superior centro
        puntos.set(3, new Point(centro.x + (int)(RADIO_X * 0.75), centro.y - (int)(RADIO_Y * 0.75))); // cabeza superior derecha
        puntos.set(4, new Point(centro.x + RADIO_X, centro.y));                  // cabeza derecha
        puntos.set(5, new Point(centro.x + (int)(RADIO_X * 0.75), centro.y + (int)(RADIO_Y * 0.75))); // cabeza inferior derecha
        puntos.set(6, new Point(centro.x, centro.y + RADIO_Y));                  // cabeza inferior centro
        puntos.set(7, new Point(centro.x - (int)(RADIO_X * 0.75), centro.y + (int)(RADIO_Y * 0.75))); // cabeza inferior izquierda
        puntos.set(8, new Point(centro.x - RADIO_X, centro.y));                  // cabeza izquierda
    }
}