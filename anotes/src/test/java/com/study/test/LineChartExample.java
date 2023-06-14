package com.study.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.xlsx4j.sml.Col;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class LineChartExample {

    public static void main(String[] args) throws IOException {
        // 创建时间序列并添加数据
//        TimeSeries series = new TimeSeries("名字");
        TimeSeries series = new TimeSeries("");
        series.add(new Second(new Date(1683786570904l)), 0.01);
        series.add(new Second(new Date(1684789471783l)), 0.2);
//        series.add(new Second(new Date(1684789471998l)), 0.2);
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
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "测点名字测点名字", dataset);

        // 设置背景色和渐变效果
//        GradientPaint gp1 = new GradientPaint(
//                0.0f, 0.0f, new Color(48, 57, 114),
//                0.0f, 0.0f, new Color(9, 46, 85)
//        );
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
        plot.setBackgroundPaint(Color.white);
//        plot.setDomainGridlinePaint(Color.black);

        plot.setRangeGridlinePaint(Color.gray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(0.5f));
        plot.setDomainGridlinesVisible(false);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
//        plot.setDomainAxis(new DateAxis());
        plot.setDomainAxis(new DateAxis());
//        plot.getDomainAxis().setTickMarkPaint(Color.red);
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setTickLabelFont(font); // 设置字体
        rangeAxis.setLabelAngle(Math.PI / 2); // 设置角度（弧度制）
//        // 设置 Y 轴数字格式为保留2位小数
        DecimalFormat df = new DecimalFormat("#.##");
        rangeAxis.setNumberFormatOverride(df);
        rangeAxis.setLabelLocation(AxisLabelLocation.HIGH_END);
        String str = "测点名字测点你名字";
        int length = str.length();
        System.out.println(length);
        String space = " ";
        for (int i = 0; i < length*3; i++) {
            space += " ";
        }
        rangeAxis.setLabel(str.trim());
//        rangeAxis.setTickLabelInsets(new RectangleInsets());
//        rangeAxis.setAttributedLabel("测点名字测点名字");
        // 设置Y轴标签动态调整
//        rangeAxis.setAutoRangeIncludesZero(true);
//
//// 设置Y轴标签包括0时粘性放置0
//        rangeAxis.setAutoRangeStickyZero(true);
        chart.removeLegend();



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
        renderer.setSeriesPaint(0,Color.black);
        renderer.setBaseShapesVisible(false);
        renderer.setDrawSeriesLineAsPath(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBasePaint(Color.white);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.blue);
        // 设置所有点的形状为圆形，大小为6
//        renderer.setBaseShape(new Ellipse2D.Double(-1.0, -1.0, 1.0, 1.0));
//        renderer.setBaseItemLabelPaint(Color.yellow);
//        renderer.setBaseOutlinePaint(Color.cyan);
        return renderer;
    }
}
