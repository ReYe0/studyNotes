package com.study.test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.xlsx4j.sml.Col;

import javax.swing.*;

public class LineChartExample3 {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // 创建一个TimeSeries对象
            TimeSeries timeSeries = new TimeSeries("折线图");

            // 添加数据
//            timeSeries.add(new Second(new Date()), 10);
            timeSeries.add(new Second(new Timestamp(1686067200000l)), 0.22);
            timeSeries.add(new Second(new Timestamp(System.currentTimeMillis() + 120000*3)), 0.15);
            timeSeries.add(new Second(new Timestamp(System.currentTimeMillis() + 180000*40)), 0.5);
            timeSeries.add(new Second(new Timestamp(System.currentTimeMillis() + 180000*400)), 0.025);

            // 创建一个TimeSeriesCollection对象，并添加TimeSeries对象
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(timeSeries);

            // 创建一个JFreeChart对象
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "", // 标题
                    "", // x轴标签
                    "", // y轴标签
                    dataset, // 数据集
                    true, // 是否显示图例
                    true, // 是否生成工具
                    false // 是否生成URL链接
            );

            // 设置x轴为日期时间格式
            DateAxis dateAxis = (DateAxis) chart.getXYPlot().getDomainAxis();
            dateAxis.setDateFormatOverride(new java.text.SimpleDateFormat("yyyy-MM-dd"));// HH:mm:ss

            chart.setBackgroundPaint(Color.getHSBColor(0.5855f,0.8941f,0.3333f));
            chart.setBorderVisible(false);
            Font font = new Font("MicrosoftYaHei", Font.PLAIN, 12);
            chart.getTitle().setFont(font);
//            chart.getLegend().setItemFont(font);
//            chart.getLegend().setFrame(BlockBorder.NONE);
            chart.removeLegend();

            XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(Color.getHSBColor(0.5855f,0.8941f,0.3333f));
            plot.setRangeGridlinePaint(Color.gray);
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlineStroke(new BasicStroke(0.5f));
            plot.setDomainGridlinesVisible(false);
            plot.setDomainCrosshairVisible(true);
            plot.setRangeCrosshairVisible(true);
            //Y轴
            NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
            rangeAxis.setLabelAngle(Math.PI / 2); // 设置角度（弧度制）
            DecimalFormat df = new DecimalFormat("#.###");
            rangeAxis.setNumberFormatOverride(df);
            rangeAxis.setTickLabelPaint(Color.getHSBColor(0.0000f,0.0000f,0.7804f));

            //X轴
            ValueAxis domainAxis = plot.getDomainAxis();
            domainAxis.setTickLabelPaint(Color.getHSBColor(0.0000f,0.0000f,0.7804f));

            // 将折线图保存为PNG格式的图片
            File file = new File("linechart3.png");
            int width = 762;
            int height = 150;
            try {
                ChartUtilities.saveChartAsPNG(file, chart, width, height);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
