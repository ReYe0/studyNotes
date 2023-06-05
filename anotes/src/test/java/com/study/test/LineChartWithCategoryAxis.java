package com.study.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class LineChartWithCategoryAxis {
    public static void main(String[] args) {
        double[] xData = {1, 2, 3, 4, 5};
        double[] yData = {1, 4, 9, 16, 25};
        XYSeries series = new XYSeries("y=f(x)");
        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "折线图",
                "X",  // X轴标签
                "Y",  // Y轴标签
                dataset,  // 数据集
                PlotOrientation.VERTICAL,  // 图表显示的方向
                true,  // 是否显示图例
                true,  // 是否生成工具
                false  // 是否生成URL链接
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        Panel panel = new Panel();
        Frame frame = new Frame();
        panel.add(chartPanel);
        frame.setVisible(true);
    }
}