package ProjectGUI;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import student.ReadQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


import static java.awt.Color.*;
import static java.awt.Frame.NORMAL;

public class AverageComparison {
    ChartPanel chartPanel;
    ReadQuery read = new ReadQuery();
    CategoryDataset myData;

    BufferedImage drawChart(JFreeChart chart, int w, int h) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D gr = bi.createGraphics();
        try {
            chart.draw(gr, new Rectangle2D.Double(50, 50, w, h));
        } finally {
            gr.dispose();
        }
        return bi;
    }

    //Round to next multiple of 5
    int round(double value) {
        return (int) Math.round(value/5) * 5;
    }

    /*Renderer to customize plot*/
    static class CustomRenderer extends BarRenderer {
        //The colors
        final private Paint[] colors;

        //Creates a renderer
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;}

        //Returns paint for an item
        public Paint getItemPaint(final int row, final int column) {

            return this.colors[column % this.colors.length];
        }
    }

    /*Constructor to plot the chart*/
    public AverageComparison(String studentID) {
        myData = createDataset(studentID);

        //Creates bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Student " + studentID + " vs. averages of each module",
                "Modules",
                "Grade",
                myData,
                PlotOrientation.HORIZONTAL,
                true, true, false);

        chartPanel = new ChartPanel(barChart); //set chartPanel - used in JFrames

        //Edit title style
        barChart.getTitle().setFont(new Font("Sans Serif", Font.PLAIN, 16));


        /* Set chart size - commented this out since this can be adjusted when adding to pane
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 )); */

        //Rotating x-axis labels
        CategoryPlot plot = barChart.getCategoryPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        //Adjust space btwn series of data
        xAxis.setCategoryMargin(0.3);

        //Fix y-axis range
        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        //Set chart colour
        //Colors - NEED TO CHANGE APPLICATION OF COLOR
        Color module_average = new Color(236, 236, 236);
        Color student = new Color(157, 217, 243);
        Color background = new Color(246, 250, 255);

        //Colors for HISTOGRAM pop-up
        Color c1 = new Color(255, 224, 252);
        Color c2 = new Color(253, 216, 250);
        Color c3 = new Color(251, 209, 247);
        Color c4 = new Color(248, 201, 245);
        Color c5 = new Color(246, 193, 242);
        Color c6 = new Color(244, 185, 240);
        Color c7 = new Color(241, 177, 238);
        Color c8 = new Color(238, 170, 235);
        Color c9 = new Color(236, 162, 233);
        Color c10 = new Color(233, 154, 230);
        Color c11 = new Color(230, 146, 228);
        Color c12 = new Color(227, 138, 226);
        Color c13 = new Color(224, 130, 223);
        Color c14 = new Color(221, 121, 221);
        Color c15 = new Color(218, 113, 219);
        Color c16 = new Color(215, 104, 216);
        Color c17 = new Color(211, 96, 214);
        Color c18 = new Color(208, 86, 212);
        Color c19 = new Color(205, 77, 209);
        Color c20 = new Color(201, 66, 207);

        barChart.setBackgroundPaint(background);


        //Category item renderer
        CategoryItemRenderer renderer = plot.getRenderer();

        renderer.setSeriesPaint(1, module_average);
        renderer.setSeriesPaint(0, student);

        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        //BAR RENDERER
        BarRenderer rendererB = (BarRenderer) barChart.getCategoryPlot().getRenderer();

        //Make grade value visible on top of each bar
        rendererB.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.TOP_CENTER);
        rendererB.setBasePositiveItemLabelPosition(position);

        //Adjust spaces btwn series
        rendererB.setMaximumBarWidth(0.5);
        rendererB.setItemMargin(0);

        //Set renderers to plot
        plot.setRenderer(rendererB);
        plot.setRenderer(renderer);


        //Mouse listener when clicking on bars
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            //Click -> Hightlighted Histogram
            @Override
            public void chartMouseClicked(ChartMouseEvent e) {
                try{
                ChartEntity entity = e.getEntity();
                String ent = entity.toString();
                String[] temp = ent.split(" ");
                String choice = temp[3].split("=")[1]; //Module name
//                System.out.println(ent);

                //Get clicked module index
                int selectedModInd = 0;
                for (int i = 0; i < myData.getColumnCount(); i++){
//                    System.out.println(myData.getColumnKey(i)+" "+ choice);
                    if (myData.getColumnKey(i).equals(choice)){
                        selectedModInd = i;
                    }
                }

                Histogram h = new Histogram(choice);
                JFreeChart histoChart = h.chartPanel.getChart();

                int selectedInd = 0;
                int selectedVal = round(myData.getValue(0,selectedModInd).intValue());
                for (int i = 0; i < h.myData.getItemCount(0); i++){
//                    System.out.println(selectedVal+" "+ round(h.myData.getXValue(0, i)));
                    if (selectedVal == round(h.myData.getXValue(0, i))){
                        selectedInd = i;
                    }
                }

                Paint[] paint = {c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16,
                        c17, c18, c19, c20};

                paint[selectedInd] = student; //*SpEcIAL* color to selected student

                XYBarRenderer newRenderer = new Histogram.histogramRenderer(paint);
                histoChart.getXYPlot().setRenderer(newRenderer); //set the new renderer

                JFrame frame = new JFrame();
                frame.getContentPane().add(h.chartPanel);
                frame.pack();
                frame.setVisible(true);

                } catch (Exception except){
                    except.printStackTrace();
                }
            }
            //Displays directions to click the chart
            public void chartMouseMoved(ChartMouseEvent e) {
//                e.getChart().getPlot().add
//                valueMarker.setLabel(  " Distance=" + value );

            }
        });

    }


    /*Creates the dataset by inputting values*/
    public CategoryDataset createDataset(String studentID){
        DefaultCategoryDataset myData = new DefaultCategoryDataset( );
        //Series
        final String series1 = "Module average";
        final String series2 = studentID;

        //Get selected student's grade for every module
        HashMap map = read.getStudentGrades(studentID);
//        read.sortHMDescending(map); //Columns in descending order of student's grade
        for (Object key : map.keySet()) {
            String module = key.toString();
            Integer grade = (Integer) map.get(key);
        myData.addValue(grade, series2, module);
        }
        //Get average grade of EVERY MODULE
        for (Object key : map.keySet()) {
            String module = key.toString();
            int avg_double = read.getModuleAverage(module);
            myData.addValue(avg_double, series1, module);
        }
        return myData;
    }

    //For Debugging - 5 seconds
    public static void main( String[ ] args ) {
        JFrame frame = new JFrame();
        AverageComparison chart = new AverageComparison("2500231");
        ChartPanel chartPan = chart.chartPanel;
        chartPan.setPreferredSize(chartPan.getPreferredSize());
        frame.add(chartPan);
        frame.setSize(900, 500);
        frame.setVisible(true);}
}
