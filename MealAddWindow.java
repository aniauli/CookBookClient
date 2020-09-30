import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MealAddWindow implements Windows {

    private JFrame frame;
    private JList ingredientsList;
    private JTextArea instructionsTextArea;
    private JPanel RecipeAddPanel;
    private JButton addRecipeButton;
    private JButton productSubmitButton;
    private JTextArea productTextArea;
    private JTextArea mealTextArea;

    private String Name;

    private EditableProductWindow editableProductWindow;

    private RecipeProvider recipeProvider;
    private ProductProvider productProvider;

    private WindowService windowService;

    private StringBuilder ingredientsWithGramsForServer = new StringBuilder();
    private DefaultListModel ingredientsAndGrams = new DefaultListModel();

    public MealAddWindow() {

        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();

        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);

        recipeProvider = new RecipeProvider();
        productProvider = new ProductProvider();

        addRecipeButton.addActionListener(ActionEvent -> {
            if (!wrongFilled()) {
                JOptionPane.showMessageDialog(frame, sendProductToServerAndReceiveAnswer());
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Nie wypełniłeś wszystkich pól");
            }
        });

        productSubmitButton.addActionListener(ActionEvent -> {
            String product = productTextArea.getText();
            System.out.println(product);
            if (itemNotNull(product)) {
                findIfProductExists(product);
                System.out.println("Sprawdzono czy istnieje");
            } else {
                JOptionPane.showMessageDialog(frame, "Nie wpisałeś produktu");
            }
        });
    }

    private String sendProductToServerAndReceiveAnswer() {
        String toSend = Name + "!" + ingredientsWithGramsForServer.toString() + "!" +
                instructionsTextArea.getText() + "!" + mealTextArea.getText();
        return recipeProvider.addRecipeIngredientsInstructionsMeal(toSend);
    }

    private boolean wrongFilled() {
        return (ingredientsList.getModel().getSize() == 0 || instructionsTextArea.getText() == null || mealTextArea.getText() == null);
    }

    public void start(String recipeName) {
        Name = recipeName;
        windowService.setMainComponentTitle(recipeName, RecipeAddPanel, Color.BLACK);
        setWindowVisibile();
    }

    private boolean itemNotNull(String item) {
        return item.length() > 0;
    }

    private void findIfProductExists(String product) {
        String answer = productProvider.sendFindIfProductExists(product);
        System.out.println(answer);
        if (answer.equals("not exists")) {
            askAboutAddingNewProduct(product);
        } else {
            fillIngredientsList(product);
        }
    }

    private void fillIngredientsList(String product) {
        String grams = getGramsFromClient();
        if (grams.equals("null")) {
            JOptionPane.showMessageDialog(frame, "Nie wpisałeś ilości produktu");
        } else {
            ingredientsWithGramsForServer.append(product).append("-").append(grams).append(";");
            ingredientsAndGrams.addElement(product + "   " + grams);
            ingredientsList.setModel(ingredientsAndGrams);
        }
    }

    private String getGramsFromClient() {
        String result = (String) JOptionPane.showInputDialog(frame, "Wpisz ilość produktu w gramach", "Gramy produktu",
                JOptionPane.PLAIN_MESSAGE, null, null, "100");
        if (result != null && result.length() > 0) {
            return result;
        }
        return "null";
    }

    private void askAboutAddingNewProduct(String product) {
        int clientChoice;
        Object[] options = {"Tak", "Nie"};
        clientChoice = JOptionPane.showOptionDialog(frame, "Podanego produktu nie ma w bazie. Czy chciałbyś dodać " +
                        "ten produkt?", "Dodawanie produktu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (clientChoice == 0) {
            editableProductWindow = new EditableProductWindow(product);
            if (editableProductWindow.productAdded) {
                fillIngredientsList(product);
            }
        }
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Przepis na:");
    }

    @Override
    public void addMainComponent() {
        frame.add(RecipeAddPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(600, 650);
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
