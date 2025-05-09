import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ventana extends JFrame {
    private JPanel contentPane;
    private JPanel panelLienzo;
    private JButton btnGirar;
    private JTextField textField1;

    private lienzo lienzo = new lienzo();

    public Ventana() {
        setTitle("Ventana con Lienzo");
        setContentPane(contentPane);  // Usa el contentPane generado por el diseñador
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null); // Centrado

        // Configurar el lienzo
        lienzo.setBackground(Color.BLUE);

        // Asegúrate de que el panelLienzo tenga un layout (por ejemplo, BorderLayout)

        panelLienzo.setLayout(null);
        lienzo.setBounds(10,10,700    ,700);
        panelLienzo.add(lienzo);  // Agrega el lienzo dentro del panel visible


        btnGirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lienzo.girar(Integer.parseInt(textField1.getText()));
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ventana().setVisible(true);
        });
    }


}
