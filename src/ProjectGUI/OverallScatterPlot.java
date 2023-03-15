package ProjectGUI;


import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import student.ReadQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Iterator;

public class OverallScatterPlot extends JFrame {
    ReadQuery read = new ReadQuery();
    ChartPanel chartPanel;
    XYSeriesCollection dataset;
    XYSeriesCollection temp; //Create this to use for creating a trend line

    //Creating colors for data points
    private static final Color failColor = new Color(255, 135, 135);
        private static final Color thirdColor = new Color(119, 141, 250);
    private static final Color lowerSecColor = new Color(104, 207, 130);
    private static final Color upperSecColor = new Color(252, 219, 53);
    private static final Color firstColor = new Color(223, 175, 242);


    OverallScatterPlot(String module) {
        createScatterDataset(module);
        createScatterPlot(module);
    }



    public void createScatterPlot(String module) {
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                module + "   VS   Overall average", // Chart title
                "Student performance for " + module, // X-Axis Label
                "Average across all modules", // Y-Axis Label
                createScatterDataset(module), // Dataset for the Chart
                PlotOrientation.VERTICAL,
                true, true, false);

        scatterPlot.setBackgroundPaint(new Color(246, 250, 255));

        XYPlot plot = (XYPlot) scatterPlot.getPlot(); //Get plot from Jfreechart

        //Set x-axis of plot
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(100);

        //Set y-axis of plot
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0, 100);


        /* Customize Plot */
        MyRenderer renderer = new MyRenderer(false, true); //Color change of data points
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // change point shape to circle
        renderer.setSeriesShape(1, new Ellipse2D.Double(-3, -3, 6, 6)); // change point shape to circle
        renderer.setSeriesShape(2, new Ellipse2D.Double(-3, -3, 6, 6)); // change point shape to circle
        renderer.setSeriesShape(3, new Ellipse2D.Double(-3, -3, 6, 6)); // change point shape to circle
        renderer.setSeriesShape(4, new Ellipse2D.Double(-3, -3, 6, 6)); // change point shape to circle
        plot.setRenderer(renderer);

        //Plot linear regression line
        double[] lineValues = Regression.getOLSRegression(temp, 0);
        LineFunction2D linefunction2d = new LineFunction2D(lineValues[0], lineValues[1]);

        XYDataset series6 = DatasetUtilities.sampleFunction2D(linefunction2d, 0, 100, 100, "Linear Regression Line");
        plot.setDataset(5, series6);

        //Set renderer to regression line
        XYLineAndShapeRenderer lineDrawer = new XYLineAndShapeRenderer(true, false);
        Color line_color = new Color(255,173,190);
        lineDrawer.setSeriesPaint(5, line_color); //??Why is this color not being applied??
        plot.setRenderer(5, lineDrawer);

        //Changing chart legend color accordingly - ??Can't figure this out??
        LegendItemCollection legend_collection = plot.getLegendItems();


        chartPanel = new ChartPanel(scatterPlot);
    }



    public XYDataset createScatterDataset(String module) {
        dataset = new XYSeriesCollection();
        temp = new XYSeriesCollection();

        //So for module A, i need scores of EVERY student which is going to be the X AXIS
        //And then get EACH student's TOTAL AVERAGE
        XYSeries series1 = new XYSeries("Fail");
        XYSeries series2 = new XYSeries("3");
        XYSeries series3 = new XYSeries("2:2");
        XYSeries series4 = new XYSeries("2:1");
        XYSeries series5 = new XYSeries("1");
        XYSeries series_combined = new XYSeries("Combined");


        HashMap map = read.getModuleStudentHM(module);
        for (Object key : map.keySet()) {
            String studentID = key.toString();
            int module_grade = (Integer) map.get(key);
//            int student_average = read.getStudentAverage(studentID);

            series_combined.add(module_grade, read.getStudentAverage(studentID));

            if (module_grade < 40){
                series1.add(module_grade, read.getStudentAverage(studentID));
            }
            else if (40<=module_grade && module_grade <=49){
                series2.add(module_grade, read.getStudentAverage(studentID));
            }
            else if (50<=module_grade && module_grade <=59){
                series3.add(module_grade, read.getStudentAverage(studentID));
            }
            else if (60<=module_grade && module_grade <=69){
                series4.add(module_grade, read.getStudentAverage(studentID));
            }
            else if (70<=module_grade){
                series5.add(module_grade, read.getStudentAverage(studentID));
            }

        }

        //Add series to datset
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);

        temp.addSeries(series_combined);


        return dataset;
    }

    //Custom renderer for scatter plot
    private class MyRenderer extends XYLineAndShapeRenderer {

        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes); //calls superclass constructor
        }

        @Override
        public Paint getItemPaint(int series, int category) {
            if (series == 0) {
                return failColor;
            }
            else if(series == 1) {
                return thirdColor;
            }
            else if(series == 2) {
                return lowerSecColor;
            }
            else if(series == 3) {
                return upperSecColor;
            }
            else {
                return firstColor;
            }
        }
    }



    // 22 seconds -> 13
    public static void main(String[] args) {
        OverallScatterPlot plot = new OverallScatterPlot("CE101_4_SP");

        JFrame frame = new JFrame();
        frame.getContentPane().add(plot.chartPanel);

        frame.pack();
        frame.setVisible(true);

        }
    }

