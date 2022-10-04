package view;

import javax.swing.*;

import data.DataHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VerbrauchsDiagrammPanel extends JPanel {

    private JPanel diagrammPanel;

    public VerbrauchsDiagrammPanel() {
        diagrammPanel = new JPanel();

        setLayout(new BorderLayout());

        add(diagrammPanel, BorderLayout.CENTER);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.yellow);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.GREEN);
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.EAST);
        add(new JPanel(), BorderLayout.SOUTH);
        add(new JPanel(), BorderLayout.WEST);




        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        diagrammPanel.add(chartPanel);

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

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Verbrauchsdiagramm",
                "x",
                "y",
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
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Verbrauchsdiagramm",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        DateAxis dateAxis = new DateAxis("Time");
        dateAxis.setAutoRange(false);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd-hh-mm"));
        dateAxis.setVerticalTickLabels(true);
        dateAxis.setUpperMargin(.10);

        plot.setDomainAxis(dateAxis);
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);


        return chart;
    }
}
