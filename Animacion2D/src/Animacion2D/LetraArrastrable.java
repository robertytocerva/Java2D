package Animacion2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Clase que representa una letra que puede ser arrastrada en el lienzo
 * y que puede interactuar con el stickman.
 */
public class LetraArrastrable {
    private char letra;
    private ArrayList<Point> puntos; // Puntos que forman la letra
    private Point posicionCentral;   // Punto central para translaciones
    private Color color;
    private boolean seleccionada = false;
    private String accion;
    private int grosorLinea = 4;
    
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
        this.posicionCentral = new Point(x, y);
        this.color = color;
        this.accion = accion;
        
        // Inicializar los puntos según la letra
        inicializarPuntos();
    }
    
    /**
     * Inicializa los puntos para formar la letra
     */
    private void inicializarPuntos() {
        puntos = new ArrayList<>();
        int baseX = posicionCentral.x;
        int baseY = posicionCentral.y;
        int tam = 30; // Tamaño de la letra
        
        switch(letra) {
            case 'P':
                // Línea vertical
                puntos.add(new Point(baseX - tam/2, baseY - tam));
                puntos.add(new Point(baseX - tam/2, baseY + tam));
                
                // Semicírculo superior
                puntos.add(new Point(baseX - tam/2, baseY - tam));
                puntos.add(new Point(baseX, baseY - tam));
                puntos.add(new Point(baseX + tam/2, baseY - tam/2));
                puntos.add(new Point(baseX, baseY));
                puntos.add(new Point(baseX - tam/2, baseY));
                break;
                
            case 'C':
                // Semicírculo izquierdo
                puntos.add(new Point(baseX + tam/2, baseY - tam));
                puntos.add(new Point(baseX, baseY - tam));
                puntos.add(new Point(baseX - tam/2, baseY - tam/2));
                puntos.add(new Point(baseX - tam/2, baseY + tam/2));
                puntos.add(new Point(baseX, baseY + tam));
                puntos.add(new Point(baseX + tam/2, baseY + tam));
                break;
                
            case 'T':
                // Línea horizontal superior
                puntos.add(new Point(baseX - tam/2, baseY - tam));
                puntos.add(new Point(baseX + tam/2, baseY - tam));
                
                // Línea vertical central
                puntos.add(new Point(baseX, baseY - tam));
                puntos.add(new Point(baseX, baseY + tam));
                break;
        }
    }
    
    /**
     * Dibuja la letra en el lienzo usando GeneralPath
     * @param g Contexto gráfico donde dibujar
     */
    public void dibujar(Graphics2D g) {
        // Guardar configuración original
        Stroke strokeOriginal = g.getStroke();
        Color colorOriginal = g.getColor();
        
        // Configurar para calidad
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Crear el path para la letra
        GeneralPath path = new GeneralPath();
        
        if (puntos.size() > 1) {
            switch(letra) {
                case 'P':
                    // Dibujar línea vertical
                    path.moveTo(puntos.get(0).x, puntos.get(0).y);
                    path.lineTo(puntos.get(1).x, puntos.get(1).y);
                    
                    // Dibujar semicírculo
                    path.moveTo(puntos.get(2).x, puntos.get(2).y);
                    path.lineTo(puntos.get(3).x, puntos.get(3).y);
                    path.quadTo(puntos.get(4).x, puntos.get(4).y, puntos.get(5).x, puntos.get(5).y);
                    path.lineTo(puntos.get(6).x, puntos.get(6).y);
                    break;
                    
                case 'C':
                    // Dibujar semicírculo
                    path.moveTo(puntos.get(0).x, puntos.get(0).y);
                    path.lineTo(puntos.get(1).x, puntos.get(1).y);
                    path.quadTo(puntos.get(2).x, puntos.get(2).y, puntos.get(3).x, puntos.get(3).y);
                    path.quadTo(puntos.get(4).x, puntos.get(4).y, puntos.get(5).x, puntos.get(5).y);
                    break;
                    
                case 'T':
                    // Línea horizontal
                    path.moveTo(puntos.get(0).x, puntos.get(0).y);
                    path.lineTo(puntos.get(1).x, puntos.get(1).y);
                    
                    // Línea vertical
                    path.moveTo(puntos.get(2).x, puntos.get(2).y);
                    path.lineTo(puntos.get(3).x, puntos.get(3).y);
                    break;
            }
        }
        
        // Dibujar fondo si está seleccionada
        if (seleccionada) {
            g.setColor(color.brighter());
            
            // Crear un área alrededor de la letra
            Area area = crearAreaLetra();
            g.fill(area);
        }
        
        // Dibujar la letra
        g.setColor(color);
        g.setStroke(new BasicStroke(grosorLinea, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(path);
        
        // Restaurar configuración
        g.setStroke(strokeOriginal);
        g.setColor(colorOriginal);
        
        // Dibujar puntos para debug (opcional)
        if (seleccionada) {
            g.setColor(Color.RED);
            for (Point p : puntos) {
                g.fillOval(p.x - 2, p.y - 2, 4, 4);
            }
        }
    }
    
    /**
     * Crea un área alrededor de la letra para mostrar selección
     */
    private Area crearAreaLetra() {
        // Crear un área basada en el contorno de la letra
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (Point p : puntos) {
            if (p.x < minX) minX = p.x;
            if (p.y < minY) minY = p.y;
            if (p.x > maxX) maxX = p.x;
            if (p.y > maxY) maxY = p.y;
        }
        
        // Añadir un margen
        int margen = 10;
        return new Area(new RoundRectangle2D.Double(
                minX - margen, minY - margen, 
                maxX - minX + 2*margen, maxY - minY + 2*margen, 
                20, 20));
    }
    
    /**
     * Verifica si un punto está dentro de la letra
     * @param px Coordenada X del punto
     * @param py Coordenada Y del punto
     * @return true si el punto está dentro de la letra
     */
    public boolean contienePunto(int px, int py) {
        // Crear un área de interacción más amplia alrededor de la letra
        Area area = crearAreaLetra();
        return area.contains(px, py);
    }
    
    /**
     * Mueve la letra a una nueva posición
     * @param px Nueva coordenada X central
     * @param py Nueva coordenada Y central
     */
    public void mover(int px, int py) {
        // Calcular el desplazamiento
        int dx = px - posicionCentral.x;
        int dy = py - posicionCentral.y;
        
        // Aplicar desplazamiento a todos los puntos
        for (int i = 0; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            puntos.set(i, new Point(p.x + dx, p.y + dy));
        }
        
        // Actualizar posición central
        posicionCentral.x = px;
        posicionCentral.y = py;
    }
    
    /**
     * Verifica si la letra colisiona con el stickman usando detección manual
     * @param stickman Referencia al stickman
     * @return true si hay colisión
     */
    public boolean colisionaCon(Stickman stickman) {
        ArrayList<Point> puntosStickman = stickman.getPuntos();
        
        // Obtener límites de la letra
        int minXLetra = Integer.MAX_VALUE;
        int minYLetra = Integer.MAX_VALUE;
        int maxXLetra = Integer.MIN_VALUE;
        int maxYLetra = Integer.MIN_VALUE;
        
        for (Point p : puntos) {
            if (p.x < minXLetra) minXLetra = p.x;
            if (p.y < minYLetra) minYLetra = p.y;
            if (p.x > maxXLetra) maxXLetra = p.x;
            if (p.y > maxYLetra) maxYLetra = p.y;
        }
        
        // Verificar si algún punto del stickman está dentro de los límites de la letra
        for (Point punto : puntosStickman) {
            if (punto.x >= minXLetra && punto.x <= maxXLetra && 
                punto.y >= minYLetra && punto.y <= maxYLetra) {
                return true;
            }
        }
        
        // Verificar si algún punto de la letra está dentro de los límites del stickman
        int minXStickman = Integer.MAX_VALUE;
        int minYStickman = Integer.MAX_VALUE;
        int maxXStickman = Integer.MIN_VALUE;
        int maxYStickman = Integer.MIN_VALUE;
        
        for (Point p : puntosStickman) {
            if (p.x < minXStickman) minXStickman = p.x;
            if (p.y < minYStickman) minYStickman = p.y;
            if (p.x > maxXStickman) maxXStickman = p.x;
            if (p.y > maxYStickman) maxYStickman = p.y;
        }
        
        for (Point punto : puntos) {
            if (punto.x >= minXStickman && punto.x <= maxXStickman && 
                punto.y >= minYStickman && punto.y <= maxYStickman) {
                return true;
            }
        }
        
        return false;
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
    
    public ArrayList<Point> getPuntos() {
        return puntos;
    }
}
