package ProjectGUI;

import org.jfree.chart.ChartPanel;
import student.ReadQuery;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

class SecondWindow {
    UtilityMethods u = new UtilityMethods();
    JFrame frame = new JFrame();
    private JComponent ui = null;
    ReadQuery read = new ReadQuery();
    String comboChoice;
    CustomComboBoxRenderer cbRenderer = new CustomComboBoxRenderer();
    JPanel bigCardPanel;


    //Constructor
    SecondWindow(){
        initUI();
    }


    public static void popUpMsg(String msg, String title)
    {
        JOptionPane.showMessageDialog(null, msg, "" + title, JOptionPane.INFORMATION_MESSAGE);
    }

    //Add element "Select Module" to comboBox
    public <Temp> Temp[] addArrays(Temp[] fst, Temp[] snd) {
        int fstLength = fst.length;
        int sndLength = snd.length;
        Temp[] sum = (Temp[]) Array.newInstance(fst.getClass().getComponentType(), fstLength + sndLength);
        System.arraycopy(fst, 0, sum, 0, fstLength);
        System.arraycopy(snd, 0, sum, fstLength, sndLength);

        return sum;
    }

    //Fetches icon image - for creating buttons
    protected ImageIcon getIcon(String path){
        ClassLoader cl= this.getClass().getClassLoader();
        URL imageURL = cl.getResource(path);
        ImageIcon icon = new ImageIcon(imageURL);
        return icon;}

    //Creates the main panel
    public JPanel createPanel(String panelName){
        JPanel main = new JPanel();
        main.setName(panelName);
        //Main layout settings
        main.setLayout(new OverlayLayout(main));
        main.setBackground(Color.white);
        main.setVisible(true);
        bigCardPanel.add(main, panelName);

        return main;
    }

    public void addHisto(JPanel main){ //for main
        //Set settings for histogram panel
        main.removeAll();
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 14, 20));
        main.setBackground(Color.white);
        main.setVisible(true);
//        System.out.println("It's working");
        //Adding histogram chart
        var histogram = new Histogram (main.getName());
        ChartPanel chartPanelHisto = histogram.chartPanel;
//        System.out.println(chartPanelHisto);
        chartPanelHisto.setPreferredSize(chartPanelHisto.getPreferredSize());
        chartPanelHisto.setVisible(true);
        main.add(chartPanelHisto);
    }

    public void addScatter(JPanel main){ //for main
        //Set settings for scatter panel
        main.removeAll();
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 14, 20));
        main.setBackground(Color.white);
        main.setVisible(true);
        //Adding scatter plot chart
        OverallScatterPlot scatterPlot = new OverallScatterPlot(main.getName());
        ChartPanel chartPanelScatter = scatterPlot.chartPanel;
        chartPanelScatter.setPreferredSize(chartPanelScatter.getPreferredSize());
        main.add(chartPanelScatter);
    }

    public void addBar(JPanel main){ //for main
        //Set settings for bar panel
        main.removeAll();
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 14, 20));
        main.setBackground(Color.white);
        main.setVisible(true);
        //Adding bar chart
        GradeDistribution barChart = new GradeDistribution(main.getName()); //Add chart to card2
        ChartPanel chartPanelBar = barChart.chartPanel;
        chartPanelBar.setPreferredSize(chartPanelBar.getPreferredSize());
        main.add(chartPanelBar);
    }

    //Method for Initializing UI
    public void initUI() {
        if (ui != null) {
            return;
        }


//  <FRAME panel>
        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new TitledBorder("Modules")); //main panel
        ui.setBackground(Color.white);
        frame.add(ui);

        //  <Big Card Panel>
        CardLayout cL = new CardLayout();
        bigCardPanel = new JPanel(cL);
        bigCardPanel.setBackground(Color.white);
        bigCardPanel.setName("Chart");
        ui.add(bigCardPanel);

        //"Chart" speech
        MouseListener bigCardPanel_enter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new Text2Speech(((JPanel)e.getSource()).getName());
            }
        };
//        bigCardPanel.addMouseListener(bigCardPanel_enter);


        /* <LINE START panel> */
        JPanel lineStart = new JPanel();
        lineStart.setLayout((new BoxLayout(lineStart, BoxLayout.Y_AXIS)));
        lineStart.setMaximumSize(lineStart.getPreferredSize());
        lineStart.setBackground(Color.white);
        lineStart.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));

        ui.add(lineStart, BorderLayout.LINE_START); //Add lineStart panel to main panel

        //<COMBO BOX PANEL>
        JPanel modulesList = new JPanel(new FlowLayout());
        modulesList.setBorder(new TitledBorder("Modules"));
        modulesList.setBackground(new Color(242, 245, 247));
        modulesList.setName("Modules");

        //"Modules" speech
        MouseListener modules_listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new Text2Speech("Modules");
            }
        };
//        modulesList.addMouseListener(modules_listener);


        //Create a ComboBox
        String[] module = read.getModuleList(); //Get array of list of modules
        String[] empty = {"Select module"};
        String[] modules = addArrays(empty, module);
        final JComboBox<String> comboB = new JComboBox<>(modules);
        comboB.setRenderer(cbRenderer);
        modulesList.add(comboB); //Add ComboBox to modulesList panel


//  Buttons
        //<Home button panel>
        JPanel home_btn_panel = new JPanel(new FlowLayout());
        home_btn_panel.setBackground(Color.white);

        //Button customizers
        Border noBorder = new LineBorder(Color.WHITE, 0);
        Border glowBorder = new LineBorder(new Color(142,245,255), 2); //USED when button is clicked

        ImageIcon home_img = u.getIcon("images/Home_Students.png", 55, 53);
        JButton Home = new JButton(home_img);
        Home.setSize(new Dimension(14,14)); //Sets button size to match icon size

        Home.setBorder(noBorder);
        home_btn_panel.add(Home);
        home_btn_panel.setAlignmentX(Home.CENTER_ALIGNMENT);
        home_btn_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));


        //<BUTTON Panel>
        JPanel button_panel = new JPanel(); //button_panel to put the 3 buttons
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));
        button_panel.setBackground(Color.white);
        button_panel.setBorder(BorderFactory.createEmptyBorder(7, 0, 20, 3));

        button_panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Create HISTOGRAM button - BUTTON 1
        JButton histo_btn = new JButton(getIcon("images/histo_button.png"));
        histo_btn.setPreferredSize(new Dimension(120,34)); //Sets button size to match icon size
        histo_btn.setBorder(noBorder);

        button_panel.setAlignmentX(histo_btn.CENTER_ALIGNMENT);
        button_panel.add(histo_btn);
        button_panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Create SCATTER PLOT button- BUTTON 2
        JButton scatter_btn = new JButton(getIcon("images/scatter_button.png"));
        scatter_btn.setPreferredSize(new Dimension(120,34));
        scatter_btn.setBorder(noBorder);

        button_panel.setAlignmentX(scatter_btn.CENTER_ALIGNMENT);
        button_panel.add(scatter_btn);
        button_panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Create BAR CHART button - BUTTON 3
        JButton bar_btn = new JButton(getIcon("images/bar_button.png"));
        bar_btn.setPreferredSize(new Dimension(120,34));
        bar_btn.setBorder(noBorder);
        bar_btn.setName("Bar chart Button");

        button_panel.setAlignmentX(bar_btn.CENTER_ALIGNMENT);
        button_panel.add(bar_btn);
        button_panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //"Chart" speech
        MouseListener bar_btn_listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new Text2Speech("Bar chart button");
            }
        };
//        bar_btn.addMouseListener(bar_btn_listener);


        //<OVERVIEW panel>
        JPanel overviewPanel = new JPanel(new GridBagLayout());
        overviewPanel.setBorder((new TitledBorder("Overview")));
        overviewPanel.setBackground(new Color(242, 245, 247));
        overviewPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 300));
        overviewPanel.setName("Overview");
        //Display stat
        JTextArea overview = new JTextArea();
        overview.setBackground(new Color(242, 245, 247));
        overview.setEditable(false);
        overviewPanel.add(overview);

        //"Overview" speech
        MouseListener overview_click = new MouseAdapter() {
            //            @Override
//            public void mouseClicked(MouseEvent e){
//                new Text2Speech("Moderate module Grades  Average 57.0  Lowest 47  Highest 73");
//            }
            @Override
            public void mouseEntered(MouseEvent e) {
                new Text2Speech(((JPanel)e.getSource()).getName());
            }
        };
//        overviewPanel.addMouseListener(overview_click);


        //Add all the panels to LineStart panel
        lineStart.add(home_btn_panel);
        lineStart.add(button_panel);
        lineStart.add(modulesList);
        lineStart.add(overviewPanel);


//  MIDDLE AREA - the "main" panels
        //Placeholder panel
        JPanel placeholder = createPanel("placeholder");
        placeholder.setBackground(Color.white);
        ImageIcon placeholder_image = u.getIcon("images/placeholder.png", 500, 500);

        JLabel placeholder_label = new JLabel(placeholder_image);
        placeholder_label.setHorizontalAlignment(JLabel. CENTER);
        placeholder_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeholder.add(placeholder_label);
        placeholder.setVisible(true); //placeholder is visible at opening

        //Module 1
        JPanel CE101_4_FY = createPanel("CE101_4_FY");
        //Module 2
        JPanel CE101_4_SP = createPanel("CE101_4_SP");
        // Module 3
        JPanel CE141_4_AU = createPanel("CE141_4_AU");
        //Module 4
        JPanel CE141_4_FY = createPanel("CE141_4_FY");
        //Module 5
        JPanel CE142_4_AU = createPanel("CE142_4_AU");
        // Module 6
        JPanel CE142_4_FY = createPanel("CE142_4_FY");
        // Module 7
        JPanel CE151_4_AU = createPanel("CE151_4_AU");
        // Module 8
        JPanel CE152_4_SP = createPanel("CE152_4_SP");
        // Module 9
        JPanel CE153_4_AU = createPanel("CE153_4_AU");
        // Module 10
        JPanel CE154_4_SP = createPanel("CE154_4_SP");
        // Module 11
        JPanel CE155_4_SP = createPanel("CE155_4_SP");
        // Module 12
        JPanel CE161_4_AU = createPanel("CE161_4_AU");
        // Module 13
        JPanel CE162_4_SP = createPanel("CE162_4_SP");
        // Module 14
        JPanel CE163_4_AU = createPanel("CE163_4_AU");
        // Module 15
        JPanel CE164_4_SP = createPanel("CE164_4_SP");


        frame.pack();
        frame.setSize(1000, 600);

        /* COMBO BOX ACTIONLISTENER */
        comboB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                comboChoice = (String) comboBox.getSelectedItem();
                cL.show(bigCardPanel, "placeholder");

                //Splitting string for text-to-speech
                String comboChoice_edit = comboChoice.replaceAll("_", "");
                comboChoice_edit = comboChoice_edit.replaceAll(".(?!$)", "$0 ");

                if (Objects.equals(comboChoice, "CE141_4_AU")) { //Module with no info
                    overviewPanel.setBackground(new Color(242, 245, 247));
                    overview.setText(null);

                    histo_btn.setBorder(noBorder);
                    scatter_btn.setBorder(noBorder);
                    bar_btn.setBorder(noBorder);
                    SecondWindow.popUpMsg("It appears that the database doesn't hold any information regarding student grades for module CE141_4_AU.", "No data detected");
                } else {
                    histo_btn.setBorder(noBorder);
                    scatter_btn.setBorder(noBorder);
                    bar_btn.setBorder(noBorder);

                    if (Objects.equals(comboChoice, "Select module")) {
                        overviewPanel.setBackground(new Color(242, 245, 247));
                        overview.setText(null);
                    } else {
                        //Get the lowest & highest grades
                        int[] array = read.getAllGrades(comboChoice);
                        Arrays.sort(array);
                        int lowest = array[0];
                        int highest = array[array.length - 1];

                        //Display statistics of module
                        overview.setText(null);
                        overview.append(read.getDifficulty(comboChoice) + " module \n\n\n");
                        overview.append("Grades: " + "\n\n");
                        overview.append("- Average: " + read.getModuleAverage(comboChoice) + "\n");
                        overview.append("- Lowest: " + lowest + "\n");
                        overview.append("- Highest: " + highest + "\n");

                        overview.setFont(new Font("Crimson Text", Font.PLAIN, 14));
                        if (read.getDifficulty(comboChoice) == "Easy") {
                            overview.setBackground(new Color(233, 250, 200));
                            overviewPanel.setBackground(new Color(233, 250, 200));
                        } else if (read.getDifficulty(comboChoice) == "Moderate") {
                            overview.setBackground(new Color(219, 228, 255));
                            overviewPanel.setBackground(new Color(219, 228, 255));
                        } else {
                            overview.setBackground(new Color(255, 227, 227));
                            overviewPanel.setBackground(new Color(255, 227, 227));
                        }
                    }
                }
//                new Text2Speech(comboChoice_edit);

            }
        });

        /* BUTTON ACTIONLISTENERS */
        Home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ThirdWindow homeFrame = new ThirdWindow();
                homeFrame.frame.pack();
                homeFrame.frame.setVisible(true);

            }
        });

        histo_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cL.show(bigCardPanel, comboChoice);

                scatter_btn.setBorder(noBorder);
                bar_btn.setBorder(noBorder);
                histo_btn.setBorder(glowBorder);

                if (comboChoice.equals("CE101_4_FY") || comboChoice.equals(null)){
                    addHisto(CE101_4_FY);
                }
                else if (comboChoice.equals("CE101_4_SP")){
                    addHisto(CE101_4_SP);
                }
                else if (comboChoice.equals("CE141_4_AU")){
                    addHisto(CE141_4_AU);
                }
                else if (comboChoice.equals("CE141_4_FY")){
                    addHisto(CE141_4_FY);
                }
                else if (comboChoice.equals("CE142_4_AU")){
                    addHisto(CE142_4_AU);
                }
                else if (comboChoice.equals("CE142_4_FY")){
                    addHisto(CE142_4_FY);
                }
                else if (comboChoice.equals("CE151_4_AU")){
                    addHisto(CE151_4_AU);
                }
                else if (comboChoice.equals("CE152_4_SP")){
                    addHisto(CE152_4_SP);
                }
                else if (comboChoice.equals("CE153_4_AU")){
                    addHisto(CE153_4_AU);
                }
                else if (comboChoice.equals("CE154_4_SP")){
                    addHisto(CE154_4_SP);
                }
                else if (comboChoice.equals("CE155_4_SP")){
                    addHisto(CE155_4_SP);
                }
                else if (comboChoice.equals("CE161_4_AU")){
                    addHisto(CE161_4_AU);
                }
                else if (comboChoice.equals("CE162_4_SP")){
                    addHisto(CE162_4_SP);
                }
                else if (comboChoice.equals("CE163_4_AU")){
                    addHisto(CE163_4_AU);
                }
                else if (comboChoice.equals("CE164_4_SP")){
                    addHisto(CE164_4_SP);
                }
            }
        });

        scatter_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cL.show(bigCardPanel, comboChoice);

                histo_btn.setBorder(noBorder);
                bar_btn.setBorder(noBorder);
                scatter_btn.setBorder(glowBorder);

                if (comboChoice.equals("CE101_4_FY") || comboChoice.equals(null)){
                    addScatter(CE101_4_FY);
                }
                else if (comboChoice.equals("CE101_4_SP")){
                    addScatter(CE101_4_SP);
                }
                else if (comboChoice.equals("CE141_4_AU")){
                    addScatter(CE141_4_AU);
                }
                else if (comboChoice.equals("CE141_4_FY")){
                    addScatter(CE141_4_FY);
                }
                else if (comboChoice.equals("CE142_4_AU")){
                    addScatter(CE142_4_AU);
                }
                else if (comboChoice.equals("CE142_4_FY")){
                    addScatter(CE142_4_FY);
                }
                else if (comboChoice.equals("CE151_4_AU")){
                    addScatter(CE151_4_AU);
                }
                else if (comboChoice.equals("CE152_4_SP")){
                    addScatter(CE152_4_SP);
                }
                else if (comboChoice.equals("CE153_4_AU")){
                    addScatter(CE153_4_AU);
                }
                else if (comboChoice.equals("CE154_4_SP")){
                    addScatter(CE154_4_SP);
                }
                else if (comboChoice.equals("CE155_4_SP")){
                    addScatter(CE155_4_SP);
                }
                else if (comboChoice.equals("CE161_4_AU")){
                    addScatter(CE161_4_AU);
                }
                else if (comboChoice.equals("CE162_4_SP")){
                    addScatter(CE162_4_SP);
                }
                else if (comboChoice.equals("CE163_4_AU")){
                    addScatter(CE163_4_AU);
                }
                else if (comboChoice.equals("CE164_4_SP")){
                    addScatter(CE164_4_SP);
                }
            }
        });

        bar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cL.show(bigCardPanel, comboChoice);

                histo_btn.setBorder(noBorder);
                scatter_btn.setBorder(noBorder);
                bar_btn.setBorder(glowBorder);

                if (comboChoice.equals("CE101_4_FY") || comboChoice.equals(null)){
                    addBar(CE101_4_FY);
                }
                else if (comboChoice.equals("CE101_4_SP")){
                    addBar(CE101_4_SP);
                }
                else if (comboChoice.equals("CE141_4_AU")){
                    addBar(CE141_4_AU);
                }
                else if (comboChoice.equals("CE141_4_FY")){
                    addBar(CE141_4_FY);
                }
                else if (comboChoice.equals("CE142_4_AU")){
                    addBar(CE142_4_AU);
                }
                else if (comboChoice.equals("CE142_4_FY")){
                    addBar(CE142_4_FY);
                }
                else if (comboChoice.equals("CE151_4_AU")){
                    addBar(CE151_4_AU);
                }
                else if (comboChoice.equals("CE152_4_SP")){
                    addBar(CE152_4_SP);
                }
                else if (comboChoice.equals("CE153_4_AU")){
                    addBar(CE153_4_AU);
                }
                else if (comboChoice.equals("CE154_4_SP")){
                    addBar(CE154_4_SP);
                }
                else if (comboChoice.equals("CE155_4_SP")){
                    addBar(CE155_4_SP);
                }
                else if (comboChoice.equals("CE161_4_AU")){
                    addBar(CE161_4_AU);
                }
                else if (comboChoice.equals("CE162_4_SP")){
                    addBar(CE162_4_SP);
                }
                else if (comboChoice.equals("CE163_4_AU")){
                    addBar(CE163_4_AU);
                }
                else if (comboChoice.equals("CE164_4_SP")){
                    addBar(CE164_4_SP);
                }
            }
        });
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Select option for closing frame
    }

    public JComponent getUI() {
        return ui;}

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception useDefault) {useDefault.printStackTrace();}
                SecondWindow o = new SecondWindow();

                JFrame f = new JFrame(o.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                f.setContentPane(o.getUI());
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setSize(1000, 600);
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
