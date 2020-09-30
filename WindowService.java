import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class WindowService {

    public WindowService(){

    }

    public void setMainComponentTitle(String productName, JPanel panel, Color color) {
        TitledBorder title;
        title = BorderFactory.createTitledBorder(productName);
        title.setTitleFont(new Font("Segoe Print", Font.BOLD, 36));
        title.setTitleColor(color);
        panel.setBorder(title);
    }

    public void setDefaultWindowOptions(JFrame frame) {
        frame.pack();
        frame.setLocationByPlatform(true);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    public void showMessageServerError(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Błąd serwera");
    }

}
