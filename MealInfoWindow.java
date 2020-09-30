import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MealInfoWindow implements Windows {

    private JFrame frame;
    private JList ingredientsList;
    private JList GramsList;
    private JLabel pictureLabel;
    private JTextArea caloriesTextArea;
    private JTextArea instructionsTextArea;
    private JPanel RecipePanel;

    private RecipeProvider recipeProvider;

    private WindowService windowService;

    public MealInfoWindow() {

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
    }

    public void start(String recipeName, String recipeFromServer) {
        windowService.setMainComponentTitle(recipeName, RecipePanel, Color.BLACK);
        pictureLabel.setIcon(new ImageIcon("src/Images/Meal.jpg"));
        showRecipeInfo(recipeFromServer);
        setWindowVisibile();
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Przepis na:");
    }

    @Override
    public void addMainComponent() {
        frame.add(RecipePanel);
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

    protected void showRecipeInfo(String receivedRecipe) {
        String[] recipe = receivedRecipe.split("!");
        addRecipe(recipe);
        addIngredients(recipe);
        addGramsAndCalories(recipe);
    }

    protected void addRecipe(String[] recipe) {
        String[] recipeSplitted = recipe[0].split(";");
        instructionsTextArea.setText(recipeSplitted[1]);
    }

    protected void addIngredients(String[] recipe) {
        String[] products = recipe[1].split(";");
        ingredientsList.setListData(products);
    }

    protected void addGramsAndCalories(String[] recipe) {
        String[] gramsText = recipe[2].split(";");
        String[] caloriesText = recipe[3].split(";");
        Double sum = 0.0;
        for (int i = 0; i < gramsText.length; ++i) {
            sum = sum + (Double.parseDouble(gramsText[i]) * Double.parseDouble(caloriesText[i])) / 400.0;
        }
        GramsList.setListData(gramsText);
        caloriesTextArea.setText(String.format("%.2f", sum));
    }

}
