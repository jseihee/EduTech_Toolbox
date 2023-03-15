package ProjectGUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import student.ReadQuery;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StudentPieChart {
    ChartPanel chartPanel;
    ReadQuery read = new ReadQuery();

    StudentPieChart(String student){
        JFreeChart pieChart = ChartFactory.createPieChart(
                "UK Grade classification",
                createDataset(student),
                true,
                true,
                false);

        Color background = new Color(246, 250, 255);

        pieChart.getTitle().setFont(new Font("Sans Serif", Font.PLAIN, 16));
        pieChart.setBackgroundPaint(background);

        //Get plot from pie chart
        PiePlot plot = (PiePlot) pieChart.getPlot();
        //Customize label
        plot.setLabelPaint(Color.black);
        plot.setLabelBackgroundPaint(background);
        plot.setLabelShadowPaint(background);
        plot.setLabelFont(new Font("Sans Serif", Font.PLAIN, 12));
            //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) pieChart.getPlot()).setLabelGenerator(labelGenerator);

        //Customize pie
        plot.setCircular(false);
        plot.setShadowPaint(null);
        plot.setLabelGap(0.02);

        //Set paint for pie chart
        plot.setSectionPaint(0, new Color (163, 240, 174));
        plot.setSectionPaint(1, new Color(116, 185, 255));
        plot.setSectionPaint(2, new Color(137, 221, 255));
        plot.setSectionPaint(3, new Color(255, 192, 132));
        plot.setSectionPaint(4, new Color(255, 194, 243));
        //Remove outline
        plot.setOutlinePaint(null);
        //set background paint
        plot.setBackgroundPaint(background);

        //Remove Legend
        pieChart.removeLegend();



        //Get chart panel of pie chart
        chartPanel = new ChartPanel(pieChart);
    }

    public DefaultPieDataset createDataset(String student){
        DefaultPieDataset dataset = new DefaultPieDataset();
        int[] dist = read.getStudentGradeDistribution(student);

        dataset.setValue("1", dist[0]);
        dataset.setValue("2:1",  dist[1]);
        dataset.setValue("2:2",  dist[2]);
        dataset.setValue("3",  dist[3]);
        dataset.setValue("FAIL",  dist[4]);
        return dataset;

    }

    public static void main(String[] args) {
        StudentPieChart plot = new StudentPieChart("2500001");

        JFrame frame = new JFrame();
        frame.getContentPane().add(plot.chartPanel);

        frame.pack();
        frame.setVisible(true);
    }
}


