package ProjectGUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UtilityMethods {
    public String getStudentID(String studentID){
        return studentID;
    }

    //Get scaled image
    public ImageIcon getIcon(String path, int width, int height){
        ClassLoader cl= this.getClass().getClassLoader();
        URL imageURL = cl.getResource(path);
        ImageIcon icon = new ImageIcon(imageURL);

        Image imageP = icon.getImage();
        imageP = imageP.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(imageP);

        return icon;}
}
