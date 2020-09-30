import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class MealChooserWindow implements Windows {

    protected JFrame frame;
    private JPanel mealChooserPanel;
    private JList<String> recipesList;
    private JTextArea caloriesTextArea;
    private JTextArea instructionsTextArea;
    private JList ingredientsList;
    private JLabel pictureLabel;
    private JPanel recipePanel;
    private JList GramsList;

    private RecipeProvider recipeProvider;
    private WindowService windowService;

    public MealChooserWindow() {

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

        recipesList.addListSelectionListener(new ListSelectionListener() {
            String selectedRecipe;
            String receivedRecipe;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRecipe = recipesList.getSelectedValue();
                receivedRecipe = recipeProvider.findItem(selectedRecipe);
                setPanelTitle(recipesList.getSelectedValue(), recipePanel);
                fillFieldsWithReceivedRecipe(receivedRecipe);
            }
        });
    }

    public void start(String mealName, String[] mealList, Icon icon) {
        clearEverythingWithoutList();
        windowService.setMainComponentTitle(mealName, mealChooserPanel, Color.BLACK);
        recipesList.setListData(mealList);
        pictureLabel.setIcon(icon);
        setWindowVisibile();
    }

    protected void clearEverythingWithoutList() {
        String[] clearing = {""};
        ingredientsList.setListData(clearing);
        GramsList.setListData(clearing);
        instructionsTextArea.setText("");
        caloriesTextArea.setText("");
    }

    public void fillFieldsWithReceivedRecipe(String receivedRecipe) {

        if (receivedRecipe.equals("SQL Error") || receivedRecipe.equals("Server Error")) {
            windowService.showMessageServerError(frame);
        } else {
            showRecipeInfo(receivedRecipe);
        }
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
        for(int i = 0; i < gramsText.length; ++i){
            sum = sum + (Double.parseDouble(gramsText[i])*Double.parseDouble(caloriesText[i]))/400.0;
        }
        GramsList.setListData(gramsText);
        caloriesTextArea.setText(String.format("%.2f", sum));
    }

    protected void setPanelTitle(String productName, JPanel panel) {
        TitledBorder title;
        title = BorderFactory.createTitledBorder(productName);
        title.setTitleFont(new Font("Segoe Print", Font.BOLD, 36));
        title.setTitleColor(Color.WHITE);
        panel.setBorder(title);
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Pomysł na ...");
    }

    @Override
    public void addMainComponent() {
        frame.add(mealChooserPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(900, 650);
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
