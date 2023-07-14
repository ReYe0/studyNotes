package com.study.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LineChartExample2 {
    public static void main(String[] args) throws IOException {
        // 创建一个时间序列
        TimeSeries series = new TimeSeries("数据");

        // 添加数据点
//        series.add(new Minute(1, 1, 1, 1, 2022), 0.5);
//        series.add(new Minute(2, 1, 1, 2, 2022), 0.6);
//        series.add(new Minute(3, 1, 1, 4, 2022), 0.8);
        series.add(new Second(30,3, 1, 1, 1, 2022), 0.8);
        series.add(new Second(33,2, 3, 2, 2, 2022), 0.8);
        series.add(new Second(34,4, 4, 4, 4, 2022), 0.8);
//        series.add(new Second(new Date(1663891200000L)), 0.8);
        // ...

        // 创建时间序列集合
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        // 创建横坐标轴并设置日期格式
        DateAxis domainAxis = new DateAxis("时间");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        domainAxis.setDateFormatOverride(dateFormat);

        // 创建纵坐标轴
        // ...

        // 创建折线图
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "折线图",
                "日期",
                "数值",
                dataset,
                true,
                true,
                false
        );

        // 获取图表绘图区域对象
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainAxis(domainAxis);

        // 将折线图保存为PNG格式的图片
        File file = new File("linechart2.png");
        int width = 762;
        int height = 277;
        ChartUtilities.saveChartAsPNG(file, chart, width, height);

        // 创建图表窗口并显示折线图
//        ChartFrame frame = new ChartFrame("折线图示例", chart);
//        frame.pack();
//        frame.setVisible(true);
    }
}