package ProjectGUI;

import org.jfree.chart.ChartPanel;
import student.ReadQuery;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class ThirdWindow {
    UtilityMethods u = new UtilityMethods();
    private JComponent ui = null; //JComponent - Objects in Swing?
    public static JTextArea notes;
    static JFrame frame;

    ThirdWindow(){

        GUI();
    }

    //Fetches icon image - for creating buttons
    protected ImageIcon getIcon(String path){
        ClassLoader cl= this.getClass().getClassLoader();
        URL imageURL = cl.getResource(path);
        ImageIcon icon = new ImageIcon(imageURL);
        return icon;}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI();
            }
        });
    }

    private static boolean hasBeenClickedID = false;
    private static boolean hasBeenClickedAvg = false;

    public static String getString(String[] arr){
        String concated = "";
        for (int i = 1; i < arr.length-1; i++) {
            concated += arr[i];
        }
        return concated;
    }



    public JComponent getUI() {
        return ui;}

    public static void GUI() {
        ReadQuery read = new ReadQuery();
        CustomComboBoxRenderer cbRenderer = new CustomComboBoxRenderer();
        HashMap<String, Integer> map = read.getStudentAveragesMap(); //key-student & value-avg
        UtilityMethods u = new UtilityMethods();

        //Images <3
        ImageIcon image = u.getIcon("images/Grades.png", 100, 60);
        ImageIcon warning_image = u.getIcon("images/pngegg.png", 35, 35);
        ImageIcon stu_image = u.getIcon("images/Student.png", 350, 70);
        ImageIcon notes_image = u.getIcon("images/Notes.png", 400, 75);
        ImageIcon bar_image = u.getIcon("images/bar.png", 300, 300);
        ImageIcon report_image = u.getIcon("images/report.png", 200, 250);
        ImageIcon pie_image = u.getIcon("images/pie.png", 200, 200);
        ImageIcon home_img = u.getIcon("images/Home_Modules.png", 55, 53);
        ImageIcon save_img = u.getIcon("images/Save.png", 40, 90);

        //Types of colors:
        Color background = new Color(246, 250, 255);

        //Types of Borders:
        Border noBorder = new LineBorder(Color.WHITE, 0);
        Border glowBorder = new LineBorder(new Color(255, 117, 175), 1); //USED when button is click

        //MAIN FRAME
        frame = new JFrame("Student Window");
//      Setting up size for new frame
        frame.setPreferredSize(new Dimension(1000,600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //MAIN PANEL
        JComponent ui = new JPanel(new BorderLayout(4,4));
        ui.setBorder(new TitledBorder("Students")); //main panel
        ui.setBackground(Color.white);
        frame.add(ui);

//      <LINESTART Panel> WEST boxlayout panel, that will contain west side of the frame, with width of 167.
        JPanel westPanel = new JPanel();
//        westPanel.setPreferredSize(new Dimension(167, 600));
        westPanel.setLayout(new BoxLayout(westPanel,BoxLayout.PAGE_AXIS));
        westPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 5, 2));
        westPanel.setBackground(Color.white);

//      <Home Button> - LEFT UPPER CORNER North western label "Student"
        //<Home button panel>
        JPanel home_btn_panel = new JPanel(new FlowLayout());
        home_btn_panel.setBackground(Color.white);

        JButton Home = new JButton(home_img);
        Home.setSize(new Dimension(14,14)); //Sets button size to match icon size
        Home.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));

        home_btn_panel.add(Home);
        westPanel.add(home_btn_panel);

//      <Sorting buttons> panel (previously combobox panel)
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 17));
        Color button_color = new Color(255, 191, 218);
        btnPanel.setBackground(background);
        //Button 1 - sort by student ID
        JButton sortByID = new JButton("ID \u2304");
        sortByID.setFont(new Font("Consolas", Font.PLAIN, 12));
        sortByID.setBackground(new Color(255, 222, 236));
        sortByID.setBorder(glowBorder);
        sortByID.setOpaque(true);
//        sortByID.setBorder(BorderFactory.createEmptyBorder(0, 7, 2, 10));
        sortByID.setPreferredSize(new Dimension(80,20));
        //Button 2 - sort by student average
        JButton sortByAvg = new JButton("Avg \u2304");
        sortByAvg.setBackground(new Color(255, 222, 236));
        sortByAvg.setBorder(glowBorder);
        sortByAvg.setOpaque(true);
        sortByAvg.setFont(new Font("Consolas", Font.PLAIN, 12));
//        sortByAvg.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 7));
        sortByAvg.setPreferredSize(new Dimension(80,20));
        //Add button panels to parent panels
        btnPanel.add(sortByID, BorderLayout.WEST);
        btnPanel.add(sortByAvg, BorderLayout.EAST);
        westPanel.add(btnPanel);

//      <STUDENT LIST PANEL>
        JPanel studentLi = new JPanel(new BorderLayout());
//        studentLi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        //Scrollable list of the student leaderboard
        JList rankedList = new JList();
        JScrollPane scrollableList = new JScrollPane(rankedList);
        //Add contents to JLIst
        Object[] objArray;
        objArray = map.keySet().toArray(); //get list of student IDs

        HashMap<Integer, Integer> rankMap = read.getStudentRankHM(); // key-grade & value-rank

        int i = 0;
        int rank = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String student = entry.getKey(); //student ID
            int grade = entry.getValue(); //student AVG
            if (rankMap.get(grade)!=null){
                rank = rankMap.get(grade);
            }
            objArray[i] =  "[" + rank + "]   " +student+ "   (" + grade + ")";
            i++;
        }

        rankedList.setCellRenderer(cbRenderer);
        rankedList.setListData(objArray);
//        rankedList.setSize(new Dimension(100, 1000));
        studentLi.add(scrollableList);
        westPanel.add(studentLi);

        //Adding west panel to the frame.
        ui.add(westPanel, BorderLayout.WEST);


//      *CENTER_PANEL - that will contain students number, both grades by subject and individual grades chart. Width 500.
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.white);
        middlePanel.setPreferredSize(new Dimension(490,600));
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.PAGE_AXIS));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


//      <TITLE LABEL>
        JPanel titleElements = new JPanel();
        titleElements.setLayout(new OverlayLayout(titleElements)); //Overlay image & text

        JLabel studentBackground = new JLabel(stu_image);
        studentBackground.setHorizontalAlignment(JLabel. CENTER);
        studentBackground.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel studentNr = new JLabel("Student");
        studentNr.setHorizontalAlignment(JLabel. CENTER);
        studentNr.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentNr.setFont(new Font("SansSerif", Font.PLAIN, 30));
        studentNr.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
//        studentNr.setBackground(new Color(205, 227, 250));

//        studentNr.setBackground(new Color(194, 231, 255));
//        studentNr.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

//        studentNr.setBorder(BorderFactory.createLineBorder(new Color(163, 204, 255), 2));

        titleElements.setBackground(Color.white);
        titleElements.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        titleElements.add(studentNr, Component.CENTER_ALIGNMENT); titleElements.add(studentBackground, Component.CENTER_ALIGNMENT);
        middlePanel.add(titleElements);


//      <AVERAGE COMPARISON>
        JPanel individualGrades = new JPanel(new GridLayout());
//        individualGrades.setMaximumSize(new Dimension(Integer.MAX_VALUE,250));
//        individualGrades.setPreferredSize(individualGrades.getPreferredSize());
        individualGrades.setBackground(background);
        individualGrades.setBorder(BorderFactory.createLineBorder(new Color(163, 204, 255), 1));
        JLabel bar_temp = new JLabel(bar_image);
        individualGrades.setBackground(Color.white);
        individualGrades.add(bar_temp);
        middlePanel.add(individualGrades);

        //Add middle panel to the frame
        ui.add(middlePanel, BorderLayout.CENTER);


//      *LINE_END PANEL* - contains information breakdown & pie chart
        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(343,600));
        eastPanel.setBackground(Color.white);
        eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.PAGE_AXIS));
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 9));

        ui.add(eastPanel, BorderLayout.EAST);

//      <FAILED? panel>
//        JPanel ifFailed = new JPanel(new GridLayout(1,2));
        JPanel ifFailed = new JPanel();
        ifFailed.setLayout(new BoxLayout(ifFailed,BoxLayout.LINE_AXIS));
        ifFailed.setBackground(Color.white);
        ifFailed.setBorder(BorderFactory.createEmptyBorder(2, 0, 13, 0));

        eastPanel.add(ifFailed);

        JLabel warningImage = new JLabel(warning_image);
        warningImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JLabel warning_desc = new JLabel();
        warning_desc.setFont(new Font("Sans Serif", Font.BOLD, 15));
        warning_desc.setForeground(new Color(255, 74, 95));
        warningImage.setVisible(false);

        ifFailed.add(warningImage); ifFailed.add(warning_desc);


//        warningImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//        warning_desc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));



//      <INFO BREAKDOWN panel>
        JPanel infoBreakdown_container = new JPanel();
        infoBreakdown_container.setLayout(new OverlayLayout(infoBreakdown_container));

        JPanel infoBreakdown_temp = new JPanel();
//        infoBreakdown_temp.setBackground(new Color(251, 245, 252));
        infoBreakdown_temp.setBackground(Color.white);
        Color b = new Color(248, 234, 251);
        infoBreakdown_temp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, b));

        JLabel infoBreakdown_img = new JLabel(report_image);
        infoBreakdown_temp.add(infoBreakdown_img, Component.CENTER_ALIGNMENT);

        JPanel infoBreakdown = new JPanel(new GridLayout(5, 2));
        infoBreakdown.setVisible(false);
//        infoBreakdown.setBorder(BorderFactory.createLineBorder(new Color(163, 204, 255), 2));
//        infoBreakdown.setBorder(BorderFactory.createMatteBorder(12, 0, 10, 0, Color.white));

        infoBreakdown.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(12, 0, 10, 0, Color.white),
                BorderFactory.createMatteBorder(2, 2, 2, 2, b)));
//        infoBreakdown.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
//        breakdown.setEditable(false);

        //Value labels
        JLabel overall = new JLabel();
        overall.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
        overall.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));


        JLabel avg_value = new JLabel();
        avg_value.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        avg_value.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JLabel ranking_value = new JLabel();
        ranking_value.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        ranking_value.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));


        JLabel best_module = new JLabel();
        best_module.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        best_module.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JLabel worst_module = new JLabel();
        worst_module.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        worst_module.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 0));



        //Description labels
        JLabel OVERALL = new JLabel("Overall");
        OVERALL.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        JLabel AVG = new JLabel("Average");
        AVG.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        JLabel RANK = new JLabel("Rank");
        RANK.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        JLabel BEST = new JLabel("Best Module");
        BEST.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        JLabel WORST = new JLabel("Worst Module");
        WORST.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        WORST.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        infoBreakdown.add(overall); infoBreakdown.add(OVERALL);
        infoBreakdown.add(avg_value); infoBreakdown.add(AVG);
        infoBreakdown.add(ranking_value); infoBreakdown.add(RANK);
        infoBreakdown.add(best_module); infoBreakdown.add(BEST);
        infoBreakdown.add(worst_module); infoBreakdown.add(WORST);

        infoBreakdown.setMinimumSize(new Dimension(Integer.MAX_VALUE,300));
        infoBreakdown.setBackground(new Color(251, 245, 252));

        infoBreakdown_container.add(infoBreakdown_temp);
        infoBreakdown_container.add(infoBreakdown);
        eastPanel.add(infoBreakdown_container);

        //"Info breakdown" speech
        MouseListener infobreakdown_listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new Text2Speech("Overview");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String s = best_module.getText();
                String bM = s.replaceAll("_", "");
                bM = bM.replaceAll(".(?!$)", "$0 ");

                String sw = worst_module.getText();
                String bW = sw.replaceAll("_", "");
                bW = bW.replaceAll(".(?!$)", "$0 ");

                String student_txt = studentNr.getText();
                student_txt = student_txt.replaceAll(".(?!$)", "$0 ");

                new Text2Speech("Student " + student_txt + " " + OVERALL.getText() +
                        " "+ overall.getText() +
                        " " + AVG.getText() + " " + avg_value.getText() +
                        " " + RANK.getText() + ranking_value.getText() +
                        " " + BEST.getText() + " " + bM +
                        " " + WORST.getText() + " " + bW);
            }
        };
        infoBreakdown.addMouseListener(infobreakdown_listener);


//      <TEACHER NOTES panel>
        JPanel notes_panel = new JPanel();
        notes_panel.setLayout(new BoxLayout(notes_panel, BoxLayout.X_AXIS));
        notes_panel.setBackground(Color.white);

        notes = new JTextArea();
//        notes.setHorizontalAlignment(JTextField. CENTER);
        notes.setAlignmentX(Component.CENTER_ALIGNMENT);
        notes.setPreferredSize(new Dimension(Integer.MAX_VALUE, 130));

        notes.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 4, 1, Color.white),
                BorderFactory.createTitledBorder("Notes")));

        JButton save_btn = new JButton(save_img);
        save_btn.setSize(new Dimension(40,90));
        save_btn.setBorder(noBorder);


        notes.setVisible(false);
        eastPanel.add(notes);
//        notes_panel.add(save_btn);
//        eastPanel.add(notes_panel);




//      <PIE CHART Panel>
        JPanel pieChartPanel = new JPanel(new GridLayout());
//        pieChartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        pieChartPanel.setBackground(Color.white);
//        pieChartPanel.setBorder(BorderFactory.createLineBorder(new Color(184, 215, 255), 1));
        pieChartPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(10, 1, 4, 1, Color.white),
                BorderFactory.createLineBorder(new Color(184, 215, 255), 1)));
//        pieChartPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        JLabel pie_temp = new JLabel(pie_image);
        pie_temp.setBackground(background);
        pieChartPanel.add(pie_temp);
        eastPanel.add(pieChartPanel);


//      Adding eastern panel to the frame.
        ui.add(eastPanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);

        //Home button action listener
        Home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SecondWindow homeFrame = new SecondWindow();
                homeFrame.frame.setVisible(true);
            }
        });

        //LIST ACTION LISTENER
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                String selectedItem = (String) rankedList.getSelectedValue();
                String[] result = selectedItem.split(" ");
                String comboChoice = result[3];
                String[] r = result[0].split(""); //student rank arr
                String[] avg = result[6].split(""); //student avg arr

                infoBreakdown_temp.setVisible(false);
                infoBreakdown.setVisible(true);

                //Add title
                studentNr.setText(comboChoice);
//                studentNr.setFont(new Font("SansSerif", Font.PLAIN, 30));

                //Add IF failed:
                warning_desc.setVisible(false);
                warningImage.setVisible(false);
                HashMap <String, Integer> grades_map = read.getStudentGrades(comboChoice);
                int count = 0;
                for (int grade: grades_map.values()){
                    if (grade < 40){
                        count++;
                    }
                }
                if (count==1){
                    warningImage.setVisible(true);
                    warning_desc.setText("Failed 1 module!");
                    warning_desc.setVisible(true);
                } else if (count>1){
                    warningImage.setVisible(true);
                    warning_desc.setText("Failed " + count + " modules!");
                    warning_desc.setVisible(true);
                }


                //Student info breakdown
//                infoBreakdown.removeAll();
                avg_value.removeAll();
                avg_value.setText(getString(avg));
                ranking_value.removeAll();
                ranking_value.setText(getString(r)+ "/" + map.size());
                best_module.removeAll();
                best_module.setText(read.bestWorstModule(comboChoice)[0]);
                worst_module.removeAll();
                worst_module.setText(read.bestWorstModule(comboChoice)[2]);
                    //Overall label
                overall.removeAll();
                if (Integer.valueOf(getString(avg)) < 40){
                    overall.setText("FAILED");
                    overall.setForeground(new Color(234, 97, 120));

                }
                else if (Integer.valueOf(getString(avg)) < 50){
                    overall.setText("Poor");
                    overall.setForeground(new Color(248, 169, 144));
                }
                else if (Integer.valueOf(getString(avg)) < 60){
                    overall.setText("Average");
                    overall.setForeground(new Color(145, 216, 220));
                }
                else if (Integer.valueOf(getString(avg)) < 70){
                    if (count==0){ //Evern if average is > 60 -> if student has failed sth then not good
                        overall.setText("Good");
                        overall.setForeground(new Color(9, 132, 227));
                    }
                    else{
                        overall.setText("Average");
                        overall.setForeground(new Color(145, 216, 220));
                    }
                }
                else {
                    if (count==0){ //Evern if average is > 70 -> if student has failed sth then not excellent
                        overall.setText("Excellent");
                        overall.setForeground(new Color(0, 188, 113));
                    }
                    else{
                        overall.setText("Good");
                        overall.setForeground(new Color(9, 132, 227));
                    }
                }

                //Clear notes
                notes.setVisible(true);

                //Add Pie Chart
//                pie_temp.setVisible(false);
                pieChartPanel.removeAll();
                StudentPieChart pieChart = new StudentPieChart(comboChoice);
                ChartPanel piePanel = pieChart.chartPanel;
                piePanel.setBackground(background);
                piePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                pieChartPanel.add(piePanel, Component.CENTER_ALIGNMENT);
//                piePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));

                //Add avg comparison bar chart
                individualGrades.removeAll();
                AverageComparison avgG = new AverageComparison(comboChoice);
                ChartPanel avgPanel = avgG.chartPanel;
//                avgPanel.setPreferredSize(individualGrades.getPreferredSize());
                avgPanel.setBackground(background);
                avgPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 20));
                individualGrades.add(avgPanel);
            }
        };
        rankedList.addMouseListener(mouseListener);

        /* BUTTON ACTIONLISTENERS */
        sortByID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankedList.removeAll(); //Clear out JList
                sortByAvg.setBackground(new Color(255, 222, 236));
                sortByID.setBackground(button_color);

                Object[] objArray;
                objArray = map.keySet().toArray(); //get list of student IDs

                //Ascending studentID
                if ( ! hasBeenClickedID) {
                    sortByID.setText("ID \u2303"); // ^ (ascending)
                    Arrays.sort(objArray, Collections.reverseOrder()); //sort by descending order
                }

                //Descending studentID
                else {
                    sortByID.setText("ID \u2304"); // \/ (descending)
                    Arrays.sort(objArray); //Sort by ascending order
                }

                int i = 0;
                for (Object studentObj: objArray){
                    String student = String.valueOf(studentObj);
                    int grade = map.get(student);
                    int rank = 0;
                    if (rankMap.get(grade)!=null){
                        rank = rankMap.get(grade);
                    }
                    objArray[i] =  "[" + rank + "]   " +student+ "   (" + grade + ")";
                    i++;
                }
                hasBeenClickedID = ! hasBeenClickedID;
                rankedList.setCellRenderer(cbRenderer);
                rankedList.setListData(objArray);
                rankedList.setSize(new Dimension(100, 1000));
            }
        });

        sortByAvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankedList.removeAll(); //Clear out JList
                Object[] objArray;
                objArray = map.keySet().toArray(); //get list of student IDs
                sortByID.setBackground(new Color(255, 222, 236));
                sortByAvg.setBackground(button_color);

                if ( ! hasBeenClickedAvg) {
                    sortByAvg.setText("Avg \u2303"); // ^ (ascending)
                    HashMap<String, Integer> descMap = read.sortHMAscending(map);

                    int i = 0;
                    int r = descMap.size();
                    for (Map.Entry<String, Integer> entry : descMap.entrySet()) {
                        String student = entry.getKey();
                        int grade = entry.getValue();
                        int rank = 0;
                        if (rankMap.get(grade)!=null){
                            rank = rankMap.get(grade);
                        }
                        objArray[i] =  "[" + r + "]   " +student+ "   (" + grade + ")";
                        i++;
                        r--;
                    }
                }
                else {
                    sortByAvg.setText("Avg \u2304"); // \/ (descending)

                    int i = 0;
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        String student = entry.getKey();
                        int grade = entry.getValue();
                        int rank = 0;
                        if (rankMap.get(grade)!=null){
                            rank = rankMap.get(grade);
                        }
                        objArray[i] =  "[" + rank + "]   " +student+ "   (" + grade + ")";
                        i++;
                    }
                }
                hasBeenClickedAvg = ! hasBeenClickedAvg;
                rankedList.setCellRenderer(cbRenderer);
                rankedList.setListData(objArray);
                rankedList.setSize(new Dimension(100, 1000));
            }
        });
        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Select option for closing frame
    }
}
