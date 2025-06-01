package Animacion2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class LetraArrastrable {
    private char letra;
    private ArrayList<Point> puntos;
    private Point posicionCentral;
    private Point posicionInicial;
    private Color color;
    private boolean seleccionada = false;
    private String accion;
    private int grosorLinea = 4;
    

    public LetraArrastrable(char letra, int x, int y, Color color, String accion) {
        this.letra = letra;
        this.posicionCentral = new Point(x, y);
        this.posicionInicial = new Point(x, y);
        this.color = color;
        this.accion = accion;


        inicializarPuntos();
    }
    

    private void inicializarPuntos() {
        puntos = new ArrayList<>();
        int baseX = posicionCentral.x;
        int baseY = posicionCentral.y;
        int tam = 30;
        
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
    

    public void dibujar(Graphics2D g) {

        Stroke strokeOriginal = g.getStroke();
        Color colorOriginal = g.getColor();
        

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        

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
        

        if (seleccionada) {
            g.setColor(color.brighter());
            

            Area area = crearAreaLetra();
            g.fill(area);
        }
        

        g.setColor(color);
        g.setStroke(new BasicStroke(grosorLinea, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(path);
        

        g.setStroke(strokeOriginal);
        g.setColor(colorOriginal);
        

        if (seleccionada) {
            g.setColor(Color.RED);
            for (Point p : puntos) {
                g.fillOval(p.x - 2, p.y - 2, 4, 4);
            }
        }
    }


    private Area crearAreaLetra() {

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
        ArrayList<Point> areaPuntos = new ArrayList<>();
        areaPuntos.add(new Point(minX - margen, minY - margen));
        areaPuntos.add(new Point(maxX + margen, minY - margen));
        areaPuntos.add(new Point(maxX + margen, maxY + margen));
        areaPuntos.add(new Point(minX - margen, maxY + margen));

        Path2D areaPath = new Path2D.Double();
        areaPath.moveTo(areaPuntos.get(0).x, areaPuntos.get(0).y);
        for (int i = 1; i < areaPuntos.size(); i++) {
            areaPath.lineTo(areaPuntos.get(i).x, areaPuntos.get(i).y);
        }
        areaPath.closePath();

        return new Area(areaPath);
    }
    

    public boolean contienePunto(int px, int py) {
        Area area = crearAreaLetra();
        return area.contains(px, py);
    }
    

    public void mover(int px, int py) {

        int dx = px - posicionCentral.x;
        int dy = py - posicionCentral.y;
        

        for (int i = 0; i < puntos.size(); i++) {
            Point p = puntos.get(i);
            puntos.set(i, new Point(p.x + dx, p.y + dy));
        }
        

        posicionCentral.x = px;
        posicionCentral.y = py;
    }


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
        

        for (Point punto : puntosStickman) {
            if (punto.x >= minXLetra && punto.x <= maxXLetra && 
                punto.y >= minYLetra && punto.y <= maxYLetra) {
                return true;
            }
        }
        

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
    
    public void volverAPosicionInicial() {
        mover(posicionInicial.x, posicionInicial.y);
    }
}
