package ProjectGUI;

import ProjectGUI.SecondWindow;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class FirstWindow extends JFrame {

    FirstWindow(){
        JFrame frame = new JFrame("Project Menu"); //Main JFrame

        //Main Panel
        JPanel main = new JPanel(new GridLayout(2, 0));
        main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));

        //Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new OverlayLayout(titlePanel));
        JLabel title = new JLabel("EduMan Toolbox");
        JLabel titlePicture = new JLabel();

        titlePanel.add(title);
        titlePanel.add(titlePicture);
        main.add(titlePanel);

        //Huge button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));
        JPanel studentButton = new JPanel();
        JLabel studentL = new JLabel("Students");
        studentButton.add(studentL);
        studentButton.setBackground(Color.pink);

        JPanel moduleButton = new JPanel();
        moduleButton.setBackground(Color.yellow);
        JLabel moduleL = new JLabel("Modules");
        moduleButton.add(moduleL);

        buttonPanel.add(studentButton);
        buttonPanel.add(moduleButton);
        main.add(buttonPanel);


//        //Create 3 option buttons
//        JButton butOptOne = new JButton("Students");
//        JButton butOptTwo = new JButton("Modules");
////        JButton butOptThree = new JButton("Option Three");

//        //Create 3 panels
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(new EmptyBorder(2, 3, 2, 3)); //No border
//
//        JPanel layout = new JPanel(new GridBagLayout()); //?What is the purpose of creating layout panel?
//        layout.setBorder(new EmptyBorder(5, 5, 5, 5)); //No border
//
//        JPanel firstPanel = new JPanel(new GridLayout(0, 1, 10, 10)); //Button panel
//        firstPanel.add(butOptOne);
//        firstPanel.add(butOptTwo);
//        firstPanel.add(butOptThree);
//
//        butOptOne.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                StudentWindow w = new StudentWindow();
////                JOptionPane.showMessageDialog(null,
////                        "This button will open a new window which will contain graphs focused on students");
//            }
//        });
//
//        butOptTwo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //Linking the 2nd window to modules button
//                SecondWindow w = new SecondWindow();
//                JFrame f = new JFrame(w.getClass().getSimpleName());
//                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//                f.setContentPane(w.getUI());
//                f.pack();
////                f.setMinimumSize(f.getSize());
//                f.setSize(900, 500);
//
//                f.setVisible(true);
//
////                JOptionPane.showMessageDialog(null, "This button will open a new window which will contain graphs focused on modules");
//            }
//        });
//
//        butOptThree.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "This button will open a document containing all of the graphs");
//            }
//        });
//
//        layout.add(firstPanel); //Add button panel to layout panel
//        panel.add(layout, BorderLayout.CENTER); //Add layout panel to the center of JPanel panel

        //HUGE BUTTON mouse listeners
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SecondWindow w = new SecondWindow();
                JFrame f = new JFrame(w.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                f.setContentPane(w.getUI());
                f.pack();
//                f.setMinimumSize(f.getSize());
                f.setSize(900, 500);
                f.setVisible(true);

                JOptionPane.showMessageDialog(null, "This button will open a new window which will contain graphs focused on modules");
            }
        };
        studentButton.addMouseListener(mouseListener);

        MouseListener mouseListener2 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ThirdWindow w = new ThirdWindow();
                JFrame f = new JFrame(w.getClass().getSimpleName());
//                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                f.setContentPane(w.getUI());
                f.pack();
//                f.setMinimumSize(f.getSize());
                f.setSize(900, 500);
                f.setVisible(true);

                JOptionPane.showMessageDialog(null, "This button will open a new window which will contain graphs focused on modules");
            }
        };
        moduleButton.addMouseListener(mouseListener2);

        frame.add(main); //Add panel to JFrame
        frame.setSize(400, 350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Select option for closing frame
    }
}