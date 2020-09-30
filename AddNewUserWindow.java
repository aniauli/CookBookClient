import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowStateListener;
import java.util.Arrays;

public class AddNewUserWindow implements Windows {

    private JFrame frame;
    private JTextField loginTextField;
    private JPasswordField passwordSubmitField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JPanel AddUserPanel;

    private WindowService windowService;

    private UserProvider userProvider;

    public AddNewUserWindow() {
        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();

        userProvider = new UserProvider();

        submitButton.addActionListener(actionEvent -> {
            String username = loginTextField.getText();
            String password = new String(passwordField.getPassword());
            String passwordCommit = new String(passwordSubmitField.getPassword());

            if (userProvider.sendCheckIfExists(username).equals("Nick jest zajęty")) {
                JOptionPane.showMessageDialog(frame, "Login jest zajęty");
            } else {
                if (password.equals(passwordCommit)) {
                    JOptionPane.showMessageDialog(frame, userProvider.addItem(username + ";" + password));
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Hasła są różne!");
                }
            }
        });
    }

    public void start(){
        setWindowVisibile();
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Dodawanie użytkownika");
    }

    @Override
    public void addMainComponent() {
        frame.add(AddUserPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(300, 300);
    }

    @Override
    public void setWindowDefaultCloseOperation() {
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void setWindowVisibile() {
        frame.setVisible(true);
    }
}
