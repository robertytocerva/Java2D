import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    Lienzo lienzo = new Lienzo();
    private JPanel VentanaP;
    private JPanel panelLienzo;

    public Ventana() {
        setTitle("Proyecto 2D");
        setContentPane(VentanaP);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1700, 1000);
        setLocationRelativeTo(null);

        lienzo.setBackground(Color.BLACK);
//        panelLienzo.setLayout(null);
        lienzo.setBounds(10,10, 1600, 800);
        panelLienzo.add(lienzo);

    }

    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
    }
}
