package org.knowm.xchart.demo.charts.pie;

import java.awt.Color;
import java.util.Collection;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.LabelGenerator;
import org.knowm.xchart.style.PieStyler.LabelType;

/**
 * Pie Chart Custom Color Palette
 *
 * <p>Demonstrates the following:
 *
 * <ul>
 *   <li>Pie Chart
 *   <li>PieChartBuilder
 *   <li>Custom series palette
 *   <li>Custom label type
 *   <li>Value Annotations
 *   <li>Tooltips
 */
public class PieChart02 implements ExampleChart<PieChart> {

  public static void main(String[] args) {

    ExampleChart<PieChart> exampleChart = new PieChart02();
    PieChart chart = exampleChart.getChart();
    new SwingWrapper<>(chart).displayChart();
  }

  @Override
  public PieChart getChart() {

    // Create Chart
    PieChart chart =
        new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();

    // Series
    chart.addSeries("Gold", 24);
    chart.addSeries("Silver", 21);
    chart.addSeries("Platinum", 39);
    chart.addSeries("Copper", 17);
    chart.addSeries("Zinc", 40);

    // Customize Chart
    Color[] sliceColors =
        new Color[] {
          new Color(224, 68, 14),
          new Color(230, 105, 62),
          new Color(236, 143, 110),
          new Color(243, 180, 159),
          new Color(246, 199, 182)
        };
    chart.getStyler().setSeriesColors(sliceColors);
    chart.getStyler().setLabelType(LabelType.Custom);
    chart.getStyler().setLabelGenerator(new CustomLabelGenerator(chart.getSeriesMap().values()));
    // chart.getStyler().setDecimalPattern("#0.000");
    chart.getStyler().setToolTipsEnabled(true);
    //    chart.getStyler().setToolTipsAlwaysVisible(true);

    return chart;
  }

  @Override
  public String getExampleChartName() {

    return getClass().getSimpleName() + " - Pie Chart Custom Color Palette";
  }

  private static class CustomLabelGenerator implements LabelGenerator {

    private final double total;

    public CustomLabelGenerator(Collection<PieSeries> values) {

      this.total = values.stream().map(PieSeries::getValue).mapToDouble(Number::doubleValue).sum();
    }

    @Override
    public String generateSeriesLabel(PieSeries series) {

      double percent = (series.getValue().doubleValue() / total) * 100;
      return String.format("%s (%.2f%%)", series.getValue(), percent);
    }
  }
}
