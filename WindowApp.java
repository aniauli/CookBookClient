import javax.swing.*;

public class WindowApp implements Windows {

    private JFrame frame;
    private JPanel cookBookPanel;

    private JButton productSearchButton;
    private JFormattedTextField productSearchField;
    private JButton recipeSearchButton;
    private JFormattedTextField recipeSearchField;
    private JButton secondBreakfastIdeasButton;
    private JButton lunchIdeasButton;
    private JButton dinnerIdeasButton;
    private JButton supperIdeasButton;
    private JButton breakfastIdeasButton;
    private JButton LogInButton;

    private ProductProvider productProvider;
    private RecipeProvider recipeProvider;

    private WindowService windowService;

    private ProductWindow productWindow;

    private EditableProductWindow editableProductWindow;

    private MealChooserWindow mealChooserWindow;

    private MealInfoWindow mealInfoWindow;

    private MealAddWindow mealAddWindow;

    private LogInWindow logInWindow;

    private String searchingProduct;
    private String searchingRecipe;


    public WindowApp() {

        frame = new JFrame();

        windowService = new WindowService();

        setWindowTitle();
        addMainComponent();
        windowService.setDefaultWindowOptions(frame);
        setWindowSize();
        setWindowDefaultCloseOperation();

        productProvider = new ProductProvider();
        recipeProvider = new RecipeProvider();

        productWindow = new ProductWindow();

        mealInfoWindow = new MealInfoWindow();

        logInWindow = new LogInWindow();

        LogInButton.addActionListener(actionEvent -> {
            logInWindow.start();
        });

        productSearchButton.addActionListener(actionEvent -> {
            searchingProduct = productSearchField.getText();
            if (itemNotNull(searchingProduct)) {
                findProduct(searchingProduct);
            } else {
                JOptionPane.showMessageDialog(frame, "Nie wpisałeś produktu");
            }
        });

        recipeSearchButton.addActionListener(actionEvent -> {
            searchingRecipe = recipeSearchField.getText();
            if (itemNotNull(searchingRecipe)) {
                findRecipe(searchingRecipe);
            } else {
                JOptionPane.showMessageDialog(frame, "Nie wpisałeś nazwy przepisu");
            }
        });

        breakfastIdeasButton.addActionListener(actionEvent ->
                mealsButtonClicked("breakfast"));

        secondBreakfastIdeasButton.addActionListener(actionEvent ->
                mealsButtonClicked("secondBreakfast"));

        lunchIdeasButton.addActionListener(actionEvent ->
                mealsButtonClicked("lunch"));

        dinnerIdeasButton.addActionListener(actionEvent ->
                mealsButtonClicked("dinner"));

        supperIdeasButton.addActionListener(actionEvent ->
                mealsButtonClicked("supper"));
    }

    private boolean itemNotNull(String item) {
        return item.length() > 0;
    }

    private void findProduct(String productToFind) {
        String answer = productProvider.findItem(productToFind);
        if (answer.equals("There's no such item")) {
            askAboutAddingNewProduct();
        } else if (answer.equals("SQL Error") || answer.equals("Server Error")) {
            windowService.showMessageServerError(frame);
        } else {
            showProductInfo(answer);
        }
    }

    private void findRecipe(String searchingRecipe) {
        String receivedRecipe = recipeProvider.findItem(searchingRecipe);
        if (receivedRecipe.equals("There's no such item")) {
            askAboutAddingNewRecipe();
        } else if (receivedRecipe.equals("SQL Error") || receivedRecipe.equals("Server Error")) {
            windowService.showMessageServerError(frame);
        } else {
            showRecipeInfo(searchingRecipe, receivedRecipe);
        }
    }

    private void askAboutAddingNewProduct() {
        int clientChoice;
        Object[] options = {"Tak", "Nie"};
        clientChoice = JOptionPane.showOptionDialog(frame, "Podanego produktu nie ma w bazie. Czy chciałbyś dodać " +
                        "ten produkt?", "Dodawanie produktu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (clientChoice == 0) {
            editableProductWindow = new EditableProductWindow(searchingProduct);
        }
    }

    private void askAboutAddingNewRecipe() {
        int clientChoice;
        Object[] options = {"Tak", "Nie"};
        clientChoice = JOptionPane.showOptionDialog(frame, "Podanego przepisu nie ma w bazie. Czy chciałbyś dodać " +
                        "ten przepis?", "Dodawanie przepisu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (clientChoice == 0) {
            mealAddWindow = new MealAddWindow();
            mealAddWindow.start(searchingRecipe);
        }
    }

    private void showProductInfo(String serverAnswer) {
        productWindow.start(serverAnswer);
    }

    private void showRecipeInfo(String searchingRecipe, String serverAnswer) {
        mealInfoWindow.start(searchingRecipe, serverAnswer);
    }

    private void mealsButtonClicked(String mealName) {
        String serverAnswer = recipeProvider.showAllMeals(mealName);
        if (serverAnswer.equals("null")) {
            windowService.showMessageServerError(frame);
        } else {
            String[] recipes = createRecipesList(serverAnswer);
            startMealChooserWindow(mealName, recipes);
        }
    }

    private String[] createRecipesList(String serverAnswer) {
        return serverAnswer.split(";");
    }

    private void startMealChooserWindow(String mealName, String[] recipes) {
        switch (mealName) {
            case "breakfast":
                mealChooserWindow = new MealChooserWindow();
                mealChooserWindow.start("Śniadanie", recipes, new ImageIcon("src/Images/Breakfast.jpg"));
                break;
            case "secondBreakfast":
                mealChooserWindow = new MealChooserWindow();
                mealChooserWindow.start("Drugie śniadanie", recipes, new ImageIcon("src/Images/SecondBreakfast.jpg"));
                break;
            case "lunch":
                mealChooserWindow = new MealChooserWindow();
                mealChooserWindow.start("Lunch", recipes, new ImageIcon("src/Images/Lunch.jpeg"));
                break;
            case "dinner":
                mealChooserWindow = new MealChooserWindow();
                mealChooserWindow.start("Obiad", recipes, new ImageIcon("src/Images/Dinner.jpg"));
                break;
            case "supper":
                mealChooserWindow = new MealChooserWindow();
                mealChooserWindow.start("Kolacja", recipes, new ImageIcon("src/Images/Supper.JPG"));
                break;
        }
    }

    protected void start() {
        setWindowVisibile();
    }

    @Override
    public void setWindowTitle() {
        frame.setTitle("Cook Book");
    }

    @Override
    public void addMainComponent() {
        frame.add(cookBookPanel);
    }

    @Override
    public void setWindowSize() {
        frame.setSize(800, 600);
    }

    @Override
    public void setWindowDefaultCloseOperation() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void setWindowVisibile() {
        frame.setVisible(true);
    }

}

