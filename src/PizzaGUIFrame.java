import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private ButtonGroup crustGroup;
    private JComboBox<String> sizeComboBox;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    private static final double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};
    private static final String[] SIZE_OPTIONS = {"Small", "Medium", "Large", "Super"};
    private static final double TOPPING_PRICE = 1.00;
    private static final double TAX_RATE = 0.07;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel holds crust, size, and toppings side by side
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        topPanel.add(createCrustPanel());
        topPanel.add(createSizePanel());
        topPanel.add(createToppingsPanel());

        // Center panel holds the order display
        JPanel centerPanel = createOrderPanel();

        // Bottom panel holds buttons
        JPanel bottomPanel = createButtonPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createCrustPanel() {
        JPanel crustPanel = new JPanel();
        crustPanel.setLayout(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Crust",
                TitledBorder.LEFT, TitledBorder.TOP));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        return crustPanel;
    }

    private JPanel createSizePanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Size",
                TitledBorder.LEFT, TitledBorder.TOP));

        sizeComboBox = new JComboBox<>(SIZE_OPTIONS);
        sizeComboBox.setSelectedIndex(-1);
        sizePanel.add(sizeComboBox);

        return sizePanel;
    }

    private JPanel createToppingsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(3, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Toppings",
                TitledBorder.LEFT, TitledBorder.TOP));

        topping1 = new JCheckBox("Dragon Droppings");
        topping2 = new JCheckBox("Zombie Fingers");
        topping3 = new JCheckBox("Vampire Veins");
        topping4 = new JCheckBox("Werewolf Whiskers");
        topping5 = new JCheckBox("Goblin Guts");
        topping6 = new JCheckBox("Witch Warts");

        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);

        return toppingsPanel;
    }

    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Order",
                TitledBorder.LEFT, TitledBorder.TOP));
        orderPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), "Order",
                        TitledBorder.LEFT, TitledBorder.TOP)));

        orderTextArea = new JTextArea(12, 40);
        orderTextArea.setEditable(false);
        orderTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PizzaGUIFrame.this,
                        "Are you sure you want to quit?",
                        "Confirm Quit",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        return buttonPanel;
    }

    private void processOrder() {
        // Validate crust selection
        String crustType = "";
        if (thinCrust.isSelected()) {
            crustType = "Thin";
        } else if (regularCrust.isSelected()) {
            crustType = "Regular";
        } else if (deepDishCrust.isSelected()) {
            crustType = "Deep-dish";
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a crust type.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate size selection
        if (sizeComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a pizza size.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sizeName = SIZE_OPTIONS[sizeComboBox.getSelectedIndex()];
        double sizePrice = SIZE_PRICES[sizeComboBox.getSelectedIndex()];

        // Gather selected toppings
        JCheckBox[] toppings = {topping1, topping2, topping3, topping4, topping5, topping6};
        java.util.List<String> selectedToppings = new java.util.ArrayList<>();
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                selectedToppings.add(topping.getText());
            }
        }

        if (selectedToppings.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select at least one topping.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate totals
        double toppingsTotal = selectedToppings.size() * TOPPING_PRICE;
        double subTotal = sizePrice + toppingsTotal;
        double tax = subTotal * TAX_RATE;
        double total = subTotal + tax;

        // Build the receipt
        StringBuilder receipt = new StringBuilder();
        String separator = "=========================================";
        String divider = "---------------------------------------------";

        receipt.append(separator).append("\n");
        receipt.append(String.format("%-30s $%6.2f\n", crustType + " " + sizeName, sizePrice));

        for (String topping : selectedToppings) {
            receipt.append(String.format("%-30s $%6.2f\n", topping, TOPPING_PRICE));
        }

        receipt.append("\n");
        receipt.append(String.format("%-30s $%6.2f\n", "Sub-total:", subTotal));
        receipt.append(String.format("%-30s $%6.2f\n", "Tax:", tax));
        receipt.append(divider).append("\n");
        receipt.append(String.format("%-30s $%6.2f\n", "Total:", total));
        receipt.append(separator).append("\n");

        orderTextArea.setText(receipt.toString());
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(-1);
        topping1.setSelected(false);
        topping2.setSelected(false);
        topping3.setSelected(false);
        topping4.setSelected(false);
        topping5.setSelected(false);
        topping6.setSelected(false);
        orderTextArea.setText("");
    }
}
