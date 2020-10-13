/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

/**
 *
 * @author Ro7Rinke
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtilities;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
//import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarChartEx extends JFrame {
    private ChartType chartType;
    private ArrayList<Result> results;

    public BarChartEx(ChartType chartType, ArrayList<Result> results) {
        this.chartType = chartType;
        this.results = results;
        initUI();
    }

    private void initUI() {

        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartPanel.setBackground(Color.white);
        add(chartPanel);
        
        try {
            ChartUtilities.saveChartAsPNG(new File("./chart/" + chartType.title + ".png"), chart, 1366, 768);
        } catch (IOException ex) {
            Logger.getLogger(BarChartEx.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pack();
        setTitle(chartType.title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(Result result : results){
            dataset.setValue(result.getValue(chartType.alias), chartType.unit, result.name);
        }
//        dataset.setValue(11, "Gold medals", "Germany");

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                chartType.title,//Texto em cima
                "",//Texto em baixo
                chartType.titleLeft, //Texto lateral
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);

        return barChart;
    }

//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(() -> {
//
//            BarChartEx ex = new BarChartEx();
////            ex.setVisible(true);
//            ex.dispose();
//            
//        });
//    }
}
