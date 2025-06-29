
package sep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LogFoodModule extends JFrame {
    private JTextField userIdField, foodItemField, quantityField;
    private JTextArea logArea;
    private Connection conn;
    private Map<String, int[]> foodDatabase;
    String mail;
    public LogFoodModule(String name,String mail) {
        this.mail=mail;
        setTitle("Log Food Intake");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize input fields
        add(new JLabel(name));
        userIdField = new JTextField(15);
        add(userIdField);
        userIdField.setText(mail);
        userIdField.setEditable(false);

        add(new JLabel("Food Item:"));
        foodItemField = new JTextField(15);
        add(foodItemField);

        add(new JLabel("Quantity (g):"));
        quantityField = new JTextField(10);
        add(quantityField);

        // Log area
        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        add(new JScrollPane(logArea));

        // Log Food Button
        JButton logFoodButton = new JButton("Log Food");
        logFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logFood();
            }
        });
        add(logFoodButton);

        // Initialize database connection and food database
        initDatabaseConnection();
        initFoodDatabase();

        setVisible(true);
    }

    // Initializes a simple food database with nutritional values per 100g
    private void initFoodDatabase() {
        foodDatabase = new HashMap<>();
        // {calories, protein (g), carbs (g)}
        foodDatabase.put("Apple", new int[]{52, 0, 14});
        foodDatabase.put("Banana", new int[]{89, 1, 23});
        foodDatabase.put("Chicken Breast", new int[]{165, 31, 0});
        foodDatabase.put("Rice", new int[]{130, 2, 28});
        // Add more food items as needed
    }

    // Initializes the database connection
    private void initDatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sep", "root", "system");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage());
        }
    }

    // Logs food intake, calculates nutrients, and saves to the database
    private void logFood() {
        try {
            
            String userId = userIdField.getText();
            String foodItem = foodItemField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            if (!foodDatabase.containsKey(foodItem)) {
                JOptionPane.showMessageDialog(this, "Food item not found in database.");
                return;
            }

            // Calculate total nutrients
            int[] nutrients = foodDatabase.get(foodItem);
            int calories = (nutrients[0] * quantity) / 100;
            int protein = (nutrients[1] * quantity) / 100;
            int carbs = (nutrients[2] * quantity) / 100;

            // Display the food log entry
            String logEntry = String.format("%s - %dg of %s: %d cal, %d g protein, %d g carbs", 
                                            userId, quantity, foodItem, calories, protein, carbs);
            logArea.append(logEntry + "\n");

            // Save to the database
            String query = "INSERT INTO food_log (user_email, food_item, quantity, calories, protein, carbs) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, foodItem);
            stmt.setInt(3, quantity);
            stmt.setInt(4, calories);
            stmt.setInt(5, protein);
            stmt.setInt(6, carbs);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Food intake logged successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving log to database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LogFoodModule("","");
    }
}
