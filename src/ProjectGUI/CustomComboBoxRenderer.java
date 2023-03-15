package ProjectGUI;

import javax.swing.*;
import java.awt.*;

//Class used to render comboBox
class CustomComboBoxRenderer extends JTextField implements ListCellRenderer
{
    //Constructor of this class
    public CustomComboBoxRenderer() {
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
                //Creates compound border with original border of comboBox objects
                // & empty border to create space btwn objects
        this.setPreferredSize(new java.awt.Dimension(100, 43));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(new Color(255,220, 220)); //sets color of selected box
            setForeground(Color.black);
        }

        else {
            setBackground(new Color(242,245, 247));
            setForeground(Color.black);
        }
        this.setText(value.toString());
        return this; //The current object is returned
    }

}
