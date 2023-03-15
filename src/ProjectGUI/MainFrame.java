package ProjectGUI;


import ProjectGUI.FirstWindow;

import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
//        JFrame mainFrame = new JFrame();
//        mainFrame.add(fWindow); //The constructore
//        mainFrame.setVisible(true);

        SecondWindow fWindow = new SecondWindow();
        JFrame f = new JFrame(fWindow.getClass().getSimpleName());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);

        f.setContentPane(fWindow.getUI());
        f.pack();
        f.setMinimumSize(f.getSize());
        f.setSize(1000, 600);
        f.setVisible(true);
//        fWindow.setVisible(true); - The class constructor already sets the JFrame setVisible(true)



    }

}
