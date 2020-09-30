import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LogInWindow implements Windows {

    private JFrame frame;
    private JPanel LogInPanel;
    private JTextArea logInTextArea;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton addUserButton;

    private WindowService windowService;

    private UserProvider userProvider;

    private AddNewUserWindow addNewUserWindow;

    public LogInWindow() {
        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();

        userProvider = new UserProvider();

        addNewUserWindow = new AddNewUserWindow();

        submitButton.addActionListener(actionEvent -> {
            char[] password = passwordField.getPassword();
            if (userProvider.findItem(logInTextArea.getText() + ";" + new String(password)).equals("Poprawne hasło")) {
                userProvider.sendCurrentUserToServer(logInTextArea.getText());
                JOptionPane.showMessageDialog(frame, "Pomyślnie zalogowano.");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Wpisałeś błędny login lub hasło!");
            }
        });

        addUserButton.addActionListener(actionEvent -> {
            addNewUserWindow.start();
            frame.dispose();
        });
    }

    public void start(){
        setWindowVisibile();
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Logowanie");
    }

    @Override
    public void addMainComponent() {
        frame.add(LogInPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(300, 200);
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
