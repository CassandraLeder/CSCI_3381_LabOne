import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// I'm going to be real with you... I have no idea what I'm doing <3
public class MakingChange {
    public static void main(String[] args) {
        // GUI
        JFrame frame = new JFrame("Purse and Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.pack();

        JPanel panel = new RegisterPanel();
        JPanel panel2 = new PursePanel.ChangePanel();
        frame.getContentPane().add(panel);
        frame.getContentPane().add(panel2);
        frame.add(panel);
    }
}

class PursePanel {
    public static class ChangePanel extends JPanel {

        public Purse purse = new Purse();

        public void paintComponent (Graphics g){
            super.paintComponent(g);
            Toolkit t = Toolkit.getDefaultToolkit();

            for (Denomination denomination : purse.cash.keySet()) {
                Image img = t.getImage(denomination.img());
                g.drawImage(img, 0, 0, this);
            }
            ImageIcon img = new ImageIcon("purse.png");
            super.paintComponent(g);
        }

        PursePanel changePanel () {
            PursePanel panel = new PursePanel();
            return panel;
        }
    }
}

class RegisterPanel extends JPanel {
    private Register register = new Register();
    private JLabel label;
    private JPanel panel = new PursePanel.ChangePanel();
    private JTextField input = new JTextField();


    public RegisterPanel()
    {
        this.setPreferredSize(new Dimension(700,700));
        this.setBackground(Color.WHITE);

        input.setPreferredSize(new Dimension(690,100));
        input.setFont(new Font("Arial",Font.PLAIN,40));
        input.addActionListener(new InputListener());

        label = new JLabel("Enter your amount to add in purse : ");

        this.add(label);
        this.add(input);
    }

    private class InputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = input.getText();
            double amt = Double.parseDouble(text);

            Register register = new Register();

            PursePanel.ChangePanel pursepanel = new PursePanel.ChangePanel();
            PursePanel panel1 = pursepanel.changePanel();
            pursepanel.purse = register.makeChange(amt);
            pursepanel.setPreferredSize(new Dimension(690,100));

            label.setText("$" + amt);

            pursepanel.repaint();
            pursepanel.revalidate();
        }
    }
}