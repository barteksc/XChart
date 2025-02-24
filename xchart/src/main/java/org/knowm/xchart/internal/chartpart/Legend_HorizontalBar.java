package org.knowm.xchart.internal.chartpart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import org.knowm.xchart.HorizontalBarSeries;
import org.knowm.xchart.internal.chartpart.RenderableSeries.LegendRenderType;
import org.knowm.xchart.style.Styler;

public class Legend_HorizontalBar<ST extends Styler, S extends HorizontalBarSeries>
    extends Legend_<ST, S> {

  private final ST axesChartStyler;

  /**
   * Constructor
   *
   * @param chart
   */
  public Legend_HorizontalBar(Chart<ST, S> chart) {

    super(chart);
    axesChartStyler = chart.getStyler();
  }

  @Override
  public void doPaint(Graphics2D g) {

    // Draw legend content inside legend box
    double startx = xOffset + chart.getStyler().getLegendPadding();
    double starty = yOffset + chart.getStyler().getLegendPadding();

    Object oldHint = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Map<String, S> map = chart.getSeriesMap();
    for (S series : map.values()) {

      if (!series.isShowInLegend()) {
        continue;
      }
      if (!series.isEnabled()) {
        continue;
      }

      Map<String, Rectangle2D> seriesTextBounds = getSeriesTextBounds(series);
      float legendEntryHeight = getLegendEntryHeight(seriesTextBounds, BOX_SIZE);

      // paint line and marker

      // paint inner box
      Shape rectSmall = new Rectangle2D.Double(startx, starty, BOX_SIZE, BOX_SIZE);
      g.setColor(series.getFillColor());
      g.fill(rectSmall);

      // paint series text
      double x = startx + BOX_SIZE + chart.getStyler().getLegendPadding();
      paintSeriesText(g, seriesTextBounds, BOX_SIZE, x, starty);

      if (chart.getStyler().getLegendLayout() == Styler.LegendLayout.Vertical) {
        starty += legendEntryHeight + chart.getStyler().getLegendPadding();
      } else {
        int markerWidth = BOX_SIZE;
        if (series.getLegendRenderType() == LegendRenderType.Line) {
          markerWidth = chart.getStyler().getLegendSeriesLineLength();
        }
        float legendEntryWidth = getLegendEntryWidth(seriesTextBounds, markerWidth);
        startx += legendEntryWidth + chart.getStyler().getLegendPadding();
      }
    }
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHint);
  }

  @Override
  public double getSeriesLegendRenderGraphicHeight(S series) {

    return BOX_SIZE;
  }
}
