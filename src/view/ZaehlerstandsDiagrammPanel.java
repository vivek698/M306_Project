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

public class ZaehlerstandsDiagrammPanel extends JPanel {

    public ZaehlerstandsDiagrammPanel(){
        setLayout(new BorderLayout());
        createChartPanel();
    }

    public void createChartPanel() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        chartPanel.setRangeZoomable(false);
        int leftRight = 120;
        int TopBottom = 20;
        chartPanel.setBorder(BorderFactory.createEmptyBorder(TopBottom,leftRight,TopBottom,leftRight));
        add(chartPanel, BorderLayout.CENTER);
    }

    private XYDataset createDataset() {

        TimeSeries seriesA = new TimeSeries("Absoluter Bezug");
        TimeSeries seriesB = new TimeSeries("Absolute Einspeisung");


        for (Map.Entry<Long, Messwerte>
                entry : DataHandler.getMap().entrySet())
            seriesA.add(new FixedMillisecond(entry.getKey()), entry.getValue().getAbsoluterBezug());

        for (Map.Entry<Long, Messwerte>
                entry : DataHandler.getMap().entrySet())
            seriesB.add(new FixedMillisecond(entry.getKey()), entry.getValue().getAbsoluteEinspeisung());


        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(seriesA);
        dataset.addSeries(seriesB);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Verbrauchsdiagramm",
                "Zeit",
                "Verbrauch In KwH",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.00f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(1.00f));
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Zählerstandsdiagramm",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        DateAxis dateAxis = new DateAxis("Zeit");
        dateAxis.setAutoRange(true);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd-hh-mm"));
        dateAxis.setVerticalTickLabels(true);
        dateAxis.setUpperMargin(.10);
        plot.getRangeAxis().setLabel("Verbrauch in KwH");
        plot.setDomainAxis(dateAxis);
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);

        return chart;
    }

}

