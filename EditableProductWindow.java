import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EditableProductWindow implements Windows {

    private JFrame frame;
    private JPanel productPanel;
    private JTextArea MainIngredientTextArea;
    private JTextArea CaloriesPer100GramsTextArea;
    private JTextArea GramsPerServingTextArea;
    private JButton submitButton;
    private JTextArea ProductNameTextArea;

    private ProductProvider productProvider;

    public boolean productAdded = false;

    private WindowService windowService;

    public EditableProductWindow(String productName) {

        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();

        productProvider = new ProductProvider();

        ProductNameTextArea.setText(productName);
        windowService.setMainComponentTitle(productName, productPanel, Color.BLACK);
        setWindowVisibile();

        submitButton.addActionListener(ActionEvent -> {

            if (!AreSomeTextAreasEmpty()) {
                JOptionPane.showMessageDialog(frame, sendProductToServerAndReceiveAnswer());

                CaloriesPer100GramsTextArea.setText("");
                GramsPerServingTextArea.setText("");
                MainIngredientTextArea.setText("");

                productAdded = true;
                frame.dispose();
            } else{
                JOptionPane.showMessageDialog(frame, "Nie wypełniłeś wszystkich pól");
            }
        });
    }

    private String sendProductToServerAndReceiveAnswer() {
        String toSend = ProductNameTextArea.getText() + ";" + CaloriesPer100GramsTextArea.getText() + ";" +
                GramsPerServingTextArea.getText() + ";" + MainIngredientTextArea.getText();
        return productProvider.addItem(toSend);
    }

    private boolean AreSomeTextAreasEmpty() {
        return isEmpty(ProductNameTextArea) || isEmpty(CaloriesPer100GramsTextArea) ||
                isEmpty(GramsPerServingTextArea) || isEmpty(MainIngredientTextArea);
    }

    private boolean isEmpty(JTextArea textArea) {
        return (textArea.getText().length() <= 0);
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Dodawanie produktu");
    }

    @Override
    public void addMainComponent() {
        frame.add(productPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(650, 450);
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
