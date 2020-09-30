import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ProductWindow implements Windows {

    private JFrame frame;
    private JPanel productPanel;
    private JTextArea CaloriesPer100GramsTextArea;
    private JTextArea MainIngredientTextArea;
    private JTextArea CaloriesPerServingTextArea;
    private JTextArea GramsPerServingTextArea;

    private WindowService windowService;

    public ProductWindow() {

        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();
    }

    public void start(String serverAnswer){
        String[] splittedAnswer = serverAnswer.split(";", 5);
        windowService.setMainComponentTitle(splittedAnswer[0], productPanel, Color.WHITE);
        CaloriesPer100GramsTextArea.setText(splittedAnswer[1]);
        GramsPerServingTextArea.setText(splittedAnswer[2]);
        CaloriesPerServingTextArea.setText(caloriesPerServing(splittedAnswer[1], splittedAnswer[2]));
        MainIngredientTextArea.setText(splittedAnswer[3]);
        setWindowVisibile();
    }

    private String caloriesPerServing(String caloriesPer100Grams, String gramsPerServing) {
        Double caloriesPerServing = ((Double.parseDouble(caloriesPer100Grams) * Double.parseDouble(gramsPerServing) / 100.0));
        return String.format("%s", caloriesPerServing);
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Produkt Info");
    }

    @Override
    public void addMainComponent() {
        frame.add(productPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(600, 450);
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
