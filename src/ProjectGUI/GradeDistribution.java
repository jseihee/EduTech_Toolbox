package ProjectGUI;

import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import student.ReadQuery;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class GradeDistribution { //Bar chart
    ChartPanel chartPanel;
    CategoryDataset myData;

    /*Renderer to customize plot*/
    static class CustomRenderer extends BarRenderer {
        //The colors
        final private Paint[] colors;

        //Creates a renderer
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;}

        //Returns paint for an item
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];}
    }

    public GradeDistribution(String module){
        myData = createDataset(module);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Distribution of student performance in " + module,
                "Performance",
                "% of students",
                myData,
                PlotOrientation.VERTICAL,
                true, true, false);

        //Set chart size
        chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
//        setContentPane(chartPanel);

        //Set chart colour
        CategoryPlot plot = barChart.getCategoryPlot();
        //Colors
        Color fail_color = new Color(255, 240, 246);
        Color poor_color = new Color(255, 222, 235);
        Color average_color = new Color(252, 194, 215);
        Color good_color = new Color(250, 162, 193);
        Color excellent_color = new Color(247, 131, 172);
        Color background = new Color(246, 250, 255);

        //Category Item renderer of plot
        CategoryItemRenderer renderer = new CustomRenderer(
                new Paint[] {fail_color, poor_color, average_color, good_color, excellent_color});
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        //Bar renderer of plot
        BarRenderer rendererB = (BarRenderer) barChart.getCategoryPlot().getRenderer();


        plot.setRenderer(rendererB); //bar renderer needs to be set FIRST
        plot.setRenderer(renderer);
        barChart.setBackgroundPaint(background);

        //Mouse listener when clicking on bars
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            public void chartMouseClicked(ChartMouseEvent e) {
                try{
                    //Split the student ID into alphabets for T2S function
                    String title = barChart.getTitle().getText();
                    String[] temp = title.split(" ");
                    String choice = temp[5];
                    choice = choice.replaceAll("_", "");
                    choice = choice.replaceAll(".(?!$)", "$0 ");

                    //Text 2 Speech
                    int[] print = new int[5]; //contains # of students in each category
                    for (int i = 0; i < 5; i++){
                        print[i]= (myData.getValue(0, i).intValue());
                    }
                    String s =" Fail " + print[0] + " Third " + print[1] + " Lower second"  + print[2] +
                            " Upper second " + print[3] + " First " + print[4];

                    String speech = temp[0]+ " "+temp[1]+" "+temp[2]+" "+temp[3]+ " "+temp[4] +" "+
                            choice + s; //speech to be heard

                    new Text2Speech(speech);

                } catch (Exception except){
                    except.printStackTrace();
                }
            }
            public void chartMouseMoved(ChartMouseEvent e) {
                ChartEntity entity = e.getEntity();
            }
        });
    }


    public CategoryDataset createDataset(String module){
        DefaultCategoryDataset myData = new DefaultCategoryDataset( );
        ReadQuery read = new ReadQuery();

        //Series
        final String series1 = "% of students";

        //Columns
        final String Excellent = "1";
        final String Good = "2:1";
        final String Average = "2:2";
        final String Poor = "3";
        final String Fail = "FAIL";


        //All modules - will optimize this code by using dynamic Strings
        String grade = module;


        try{
            //Adding column values to chart
            myData.addValue(read.getGradeDistribution(grade)[4], series1, Fail);
            myData.addValue(read.getGradeDistribution(grade)[3], series1, Poor);
            myData.addValue(read.getGradeDistribution(grade)[2], series1, Average);
            myData.addValue(read.getGradeDistribution(grade)[1], series1, Good);
            myData.addValue(read.getGradeDistribution(grade)[0], series1, Excellent);
        } catch (Exception e){e.printStackTrace();}

        return myData;
    }


//    /* For Debugging
    public static void main(String[] args) {
        GradeDistribution chart = new GradeDistribution("CE101_4_SP");
        JFrame frame = new JFrame();
        frame.getContentPane().add(chart.chartPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
