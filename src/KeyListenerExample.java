import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class KeyListenerExample extends JPanel implements KeyListener {
    
    public KeyListenerExample() {
        addKeyListener(this); // Ajoute le KeyListener à ce JPanel
        setFocusable(true); // Assure que ce JPanel peut recevoir le focus pour les événements de clavier
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            // Si l'utilisateur appuie sur la touche 'A'
            System.out.println("La touche 'A' a été pressée");
        }
        // Vous pouvez ajouter des conditions pour d'autres touches ici
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Pas besoin de traiter cet événement pour cet exemple
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Pas besoin de traiter cet événement pour cet exemple
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("KeyListener Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setContentPane(new KeyListenerExample());
        frame.setVisible(true);
    }
}
