package Animacion2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Clase que representa una letra que puede ser arrastrada en el lienzo
 * y que puede interactuar con el stickman.
 */
public class LetraArrastrable {
    private char letra;
    private int x, y;
    private int ancho = 40;
    private int alto = 40;
    private Color color;
    private boolean seleccionada = false;
    private String accion;
    
    /**
     * Constructor de la letra arrastrable
     * @param letra Carácter a mostrar
     * @param x Posición inicial en X
     * @param y Posición inicial en Y
     * @param color Color de la letra
     * @param accion Acción asociada a la letra (pausa, continua, termina)
     */
    public LetraArrastrable(char letra, int x, int y, Color color, String accion) {
        this.letra = letra;
        this.x = x;
        this.y = y;
        this.color = color;
        this.accion = accion;
    }
    
    /**
     * Dibuja la letra en el lienzo
     * @param g Contexto gráfico donde dibujar
     */
    public void dibujar(Graphics2D g) {
        // Guardar configuración original
        Color colorOriginal = g.getColor();
        Font fuenteOriginal = g.getFont();
        
        // Dibujar fondo
        g.setColor(seleccionada ? color.brighter() : color);
        g.fillRoundRect(x, y, ancho, alto, 10, 10);
        
        // Dibujar borde
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, ancho, alto, 10, 10);
        
        // Dibujar letra
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int xTexto = x + (ancho - fm.charWidth(letra)) / 2;
        int yTexto = y + ((alto - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(String.valueOf(letra), xTexto, yTexto);
        
        // Restaurar configuración
        g.setColor(colorOriginal);
        g.setFont(fuenteOriginal);
    }
    
    /**
     * Verifica si un punto está dentro de la letra
     * @param px Coordenada X del punto
     * @param py Coordenada Y del punto
     * @return true si el punto está dentro de la letra
     */
    public boolean contienePunto(int px, int py) {
        return px >= x && px <= x + ancho && py >= y && py <= y + alto;
    }
    
    /**
     * Mueve la letra a una nueva posición
     * @param px Nueva coordenada X
     * @param py Nueva coordenada Y
     */
    public void mover(int px, int py) {
        x = px - ancho / 2;
        y = py - alto / 2;
    }
    
    /**
     * Verifica si la letra colisiona con el stickman
     * @param stickman Referencia al stickman
     * @return true si hay colisión
     */
    public boolean colisionaCon(Stickman stickman) {
        // Obtener el rectángulo de la letra
        Rectangle letraRect = new Rectangle(x, y, ancho, alto);
        
        // Verificar colisión con la cabeza del stickman (simplificación)
        Rectangle2D cabezaRect = stickman.obtenerRectanguloColision();
        return letraRect.intersects(cabezaRect);
    }
    
    // Getters y setters
    
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
    
    public boolean isSeleccionada() {
        return seleccionada;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public char getLetra() {
        return letra;
    }
}
