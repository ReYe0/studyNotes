package com.study.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class LineChartExample {

    public static void main(String[] args) throws IOException {
        // 创建时间序列并添加数据
        TimeSeries series = new TimeSeries("名字");
        series.add(new Second(new Date(1683786570904l)), 0.01);
        series.add(new Second(new Date(1684789471783l)), 0.02);
//        series.add(new Month(2, 2015), 200.0);
//        series.add(new Month(3, 2015), 150.0);
//        series.add(new Month(4, 2015), 300.0);
//        series.add(new Month(5, 2015), 250.0);
//        series.add(new Month(6, 2015), 400.0);
//        series.add(new Month(7, 2015), 350.0);
//        series.add(new Month(7, 2015), 500.0);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        // 创建折线图
        JFreeChart chart = ChartFactory.createTimeSeriesChart("某某风场", "x", "y", dataset);

        // 设置背景色和渐变效果
        GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, new Color(48, 57, 114),
//                0.0f, 0.0f, new Color(140, 152, 186)
                0.0f, 0.0f, new Color(9, 46, 85)
        );
        chart.setBorderVisible(false);
        Font font = new Font("MicrosoftYaHei", Font.PLAIN, 12);
        chart.getTitle().setFont(font);
        chart.getLegend().setItemFont(font);
        XYPlot plot = chart.getXYPlot();
        chart.getLegend().setFrame(BlockBorder.NONE);
//        plot.setDomainAxis(new DateAxis()); // 设置横轴
//        NumberAxis rangeAxis = new NumberAxis(); // 新建一个纵轴
//        rangeAxis.setLabel("纵轴"); // 设定纵轴标签
//        plot.setRangeAxis(rangeAxis); // 设置纵轴
        plot.getDomainAxis().setLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.setBackgroundPaint(gp1);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
//        plot.setDomainAxis(new DateAxis());
        plot.setDomainAxis(new DateAxis("x"));
        plot.setRenderer(createRenderer());

        // 将折线图保存为PNG格式的图片
        File file = new File("linechart.png");
        int width = 762;
        int height = 277;
        ChartUtilities.saveChartAsPNG(file, chart, width, height);
    }

    // 创建折线图渲染器
    private static XYLineAndShapeRenderer createRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDrawSeriesLineAsPath(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBasePaint(Color.white);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.blue);
        renderer.setBaseItemLabelPaint(Color.blue);
        renderer.setBaseOutlinePaint(Color.blue);
        return renderer;
    }
}
