package ProjectGUI;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import student.ReadQuery;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Histogram {
    ReadQuery read = new ReadQuery();
    ChartPanel chartPanel;
    HistogramDataset dataset = new HistogramDataset();
    HistogramDataset myData;

    /*Renderer to customize histogram*/
    static class histogramRenderer extends XYBarRenderer {
        //The colors
        final private Paint[] colors;

        //Creates a renderer
        public histogramRenderer(final Paint[] colors) {
            this.colors = colors;}

        //Returns paint for an item
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];}
    }

    //Constructor
    Histogram(String module){
        createDataset(module);
        createHistogram(module);
    }

    //Creates Histogram
    public void createHistogram(String module){
        myData = createDataset(module);
        JFreeChart histogram = ChartFactory.createHistogram(
                "Grade distribution in " + module, // Chart title
                "Grades", // X-Axis Label
                "Number of students", // Y-Axis Label
                myData, // Dataset for the Chart
                PlotOrientation.VERTICAL,
                false, true, false);

        //Set chart background color
        histogram.setBackgroundPaint(new Color(246, 250, 255));

        //Set histogram bar colors
        XYPlot plot = histogram.getXYPlot();

        //Histogram Colors
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

        final XYBarRenderer renderer = new histogramRenderer(
                new Paint[] {c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16,
                c17, c18, c19, c20});
        plot.setRenderer(renderer);

        //Set x-axis of plot
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(100);

        //Set y-axis minimum range
        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());



        //Set chartPanel
        chartPanel = new ChartPanel(histogram);

        //Mouse listener when clicking on bars
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            public void chartMouseClicked(ChartMouseEvent e) {
                try{
//
                } catch (Exception except){
                    except.printStackTrace();
                }
            }
            public void chartMouseMoved(ChartMouseEvent e) {
                ChartEntity entity = e.getEntity();
            }
        });

    }


    public HistogramDataset createDataset(String module){

        //Return a HashMap of
        HashMap map = read.getModuleStudentHM(module);
        double[] gradeArray = new double[map.size()];
        int i = 0;

        //Create an array of the values of the map (so storing just the grades)
        for (Object key : map.keySet()) {
            int temp = (int) map.get(key);
            double grade = temp;
            gradeArray[i++] = grade;
        }
        //Add series to histogram dataset
        dataset.addSeries("Grades", gradeArray, 20);

        return dataset;
    }

    //For Debugging:
    public static void main(String[] args) {
        Histogram h = new Histogram("CE101_4_FY");
        JFrame frame = new JFrame();
        frame.getContentPane().add(h.chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
