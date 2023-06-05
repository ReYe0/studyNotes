package com.study.example.jfreeChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class LineChart_AWT extends ApplicationFrame {

    public LineChart_AWT( String applicationTitle , String chartTitle ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Dates","Temperature",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        //plot.getRangeAxis().setRange(25, 27);
        //plot.getRangeAxis().setAutoRange(true);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        dataset.addValue( 26.44,"Temperature","2019-08-18 00:00");
        dataset.addValue( 26.2,"Temperature","2019-08-18 01:00");
        dataset.addValue( 25.93,"Temperature","2019-08-18 02:00");
        dataset.addValue( 25.71,"Temperature","2019-08-18 03:00");
        dataset.addValue( 25.54,"Temperature","2019-08-18 04:00");
        dataset.addValue( 25.42,"Temperature","2019-08-18 05:00");
        dataset.addValue( 25.25,"Temperature","2019-08-18 06:00");
        dataset.addValue( 25.19,"Temperature","2019-08-18 07:00");
        dataset.addValue( 25.25,"Temperature","2019-08-18 08:00");
        dataset.addValue( 25.36,"Temperature","2019-08-18 09:00");
        dataset.addValue( 25.52,"Temperature","2019-08-18 10:00");
        dataset.addValue( 25.86,"Temperature","2019-08-18 11:00");
        dataset.addValue( 26.51,"Temperature","2019-08-18 12:00");
        dataset.addValue( 26.82,"Temperature","2019-08-18 13:00");


        return dataset;
    }

    public static void main( String[ ] args ) {
        LineChart_AWT chart = new LineChart_AWT(
                "X-axis demo" ,
                "Y-range is wrong");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
