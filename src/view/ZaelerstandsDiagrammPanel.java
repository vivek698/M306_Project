package view;

import data.DataHandler;
import model.Messwerte;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

public class ZaelerstandsDiagrammPanel extends JPanel {

    public ZaelerstandsDiagrammPanel(){
        initUI();
    }

    private void initUI() {

            XYDataset dataset = createDataset();
            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            chartPanel.setBackground(Color.white);

            add(chartPanel);


        }

        private XYDataset createDataset() {

            TimeSeries seriesA = new TimeSeries("A");
            seriesA.add(new FixedMillisecond(1552427100000l), 0);
            seriesA.add(new FixedMillisecond(1552437100000l),0);
            seriesA.add(new FixedMillisecond(1552447100000l), 612);
            seriesA.add(new FixedMillisecond(1552457100000l), 800);
            seriesA.add(new FixedMillisecond(1552467100000l), 980);
            seriesA.add(new FixedMillisecond(1552477100000l), 1410);
            seriesA.add(new FixedMillisecond(1552487100000l), 2350);




            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(seriesA);


            return dataset;
        }





        private JFreeChart createChart(final XYDataset dataset) {

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Average salary per age",
                    "Age",
                    "Salary (€)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));
            renderer.setDefaultShapesVisible(false);
            renderer.setSeriesPaint(1, Color.BLUE);
            renderer.setSeriesStroke(1, new BasicStroke(2.0f));

            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);
            plot.setRangeGridlinesVisible(false);
            plot.setDomainGridlinesVisible(false);

            chart.getLegend().setFrame(BlockBorder.NONE);

            chart.setTitle(new TextTitle("Average Salary per Age",
                            new Font("Serif", Font.BOLD, 18)
                    )
            );

            DateAxis dateAxis = new DateAxis("Time");
            dateAxis.setAutoRange(false);
            dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd-hh-mm"));
            dateAxis.setVerticalTickLabels(true);
            plot.setDomainAxis(dateAxis);
            plot.mapDatasetToRangeAxis(0, 0);
            plot.mapDatasetToRangeAxis(1, 1);

            return chart;
    }

}

