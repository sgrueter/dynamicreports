/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.dynamicreports.design.transformation;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.dynamicreports.design.base.component.DRDesignList;
import net.sf.dynamicreports.design.base.crosstab.DRDesignCrosstab;
import net.sf.dynamicreports.design.base.crosstab.DRDesignCrosstabCell;
import net.sf.dynamicreports.design.base.crosstab.DRDesignCrosstabCellContent;
import net.sf.dynamicreports.design.base.crosstab.DRDesignCrosstabColumnGroup;
import net.sf.dynamicreports.design.base.crosstab.DRDesignCrosstabRowGroup;
import net.sf.dynamicreports.design.base.style.DRDesignStyle;
import net.sf.dynamicreports.design.constant.DefaultStyleType;
import net.sf.dynamicreports.design.definition.DRIDesignBand;
import net.sf.dynamicreports.design.definition.DRIDesignGroup;
import net.sf.dynamicreports.design.definition.DRIDesignPage;
import net.sf.dynamicreports.design.exception.DRDesignReportException;
import net.sf.dynamicreports.jasper.base.tableofcontents.JasperTocHeading;
import net.sf.dynamicreports.report.constant.BooleanComponentType;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.constant.CrosstabPercentageType;
import net.sf.dynamicreports.report.constant.CrosstabTotalPosition;
import net.sf.dynamicreports.report.constant.GroupFooterPosition;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.Orientation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.Position;
import net.sf.dynamicreports.report.constant.RunDirection;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.constant.StretchType;
import net.sf.dynamicreports.report.constant.TimePeriod;
import net.sf.dynamicreports.report.constant.ValueLocation;
import net.sf.dynamicreports.report.constant.WhenNoDataType;
import net.sf.dynamicreports.report.constant.WhenResourceMissingType;
import net.sf.dynamicreports.report.defaults.Defaults;
import net.sf.dynamicreports.report.definition.DRIBand;
import net.sf.dynamicreports.report.definition.DRIField;
import net.sf.dynamicreports.report.definition.DRIGroup;
import net.sf.dynamicreports.report.definition.DRIMargin;
import net.sf.dynamicreports.report.definition.DRIReport;
import net.sf.dynamicreports.report.definition.DRIReportTemplate;
import net.sf.dynamicreports.report.definition.DRISubtotal;
import net.sf.dynamicreports.report.definition.DRITableOfContentsCustomizer;
import net.sf.dynamicreports.report.definition.DRITemplateDesign;
import net.sf.dynamicreports.report.definition.barcode.DRIBarbecue;
import net.sf.dynamicreports.report.definition.barcode.DRIBarcode;
import net.sf.dynamicreports.report.definition.chart.DRIChart;
import net.sf.dynamicreports.report.definition.chart.dataset.DRICategoryDataset;
import net.sf.dynamicreports.report.definition.chart.dataset.DRITimeSeriesDataset;
import net.sf.dynamicreports.report.definition.chart.plot.DRIAxisPlot;
import net.sf.dynamicreports.report.definition.chart.plot.DRIBasePlot;
import net.sf.dynamicreports.report.definition.chart.plot.DRIPiePlot;
import net.sf.dynamicreports.report.definition.chart.plot.DRIThermometerPlot;
import net.sf.dynamicreports.report.definition.column.DRIBooleanColumn;
import net.sf.dynamicreports.report.definition.column.DRIColumn;
import net.sf.dynamicreports.report.definition.column.DRIValueColumn;
import net.sf.dynamicreports.report.definition.component.DRIBooleanField;
import net.sf.dynamicreports.report.definition.component.DRIBreak;
import net.sf.dynamicreports.report.definition.component.DRIComponent;
import net.sf.dynamicreports.report.definition.component.DRIDimensionComponent;
import net.sf.dynamicreports.report.definition.component.DRIEllipse;
import net.sf.dynamicreports.report.definition.component.DRIFiller;
import net.sf.dynamicreports.report.definition.component.DRIGenericElement;
import net.sf.dynamicreports.report.definition.component.DRIImage;
import net.sf.dynamicreports.report.definition.component.DRILine;
import net.sf.dynamicreports.report.definition.component.DRIList;
import net.sf.dynamicreports.report.definition.component.DRIMap;
import net.sf.dynamicreports.report.definition.component.DRIMultiPageList;
import net.sf.dynamicreports.report.definition.component.DRIPageXofY;
import net.sf.dynamicreports.report.definition.component.DRIRectangle;
import net.sf.dynamicreports.report.definition.component.DRISubreport;
import net.sf.dynamicreports.report.definition.component.DRITextField;
import net.sf.dynamicreports.report.definition.component.DRIXyList;
import net.sf.dynamicreports.report.definition.crosstab.DRICrosstab;
import net.sf.dynamicreports.report.definition.crosstab.DRICrosstabColumnGroup;
import net.sf.dynamicreports.report.definition.crosstab.DRICrosstabMeasure;
import net.sf.dynamicreports.report.definition.crosstab.DRICrosstabRowGroup;
import net.sf.dynamicreports.report.definition.crosstab.DRICrosstabVariable;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.definition.style.DRIReportStyle;
import net.sf.dynamicreports.report.definition.style.DRISimpleStyle;
import net.sf.dynamicreports.report.definition.style.DRIStyle;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class TemplateTransform {
	private DRIReport report;
	private DesignTransformAccessor accessor;
	private DRIReportTemplate template;
	private DRITemplateDesign<?> templateDesign;

	public TemplateTransform(DesignTransformAccessor accessor) {
		this.accessor = accessor;
		this.report = accessor.getReport();
		this.template = report.getTemplate();
		this.templateDesign = report.getTemplateDesign();
	}

	public String getReportName() {
		if (report.getReportName() != null) {
			return report.getReportName();
		}
		if (templateDesign.getReportName() != null) {
			return templateDesign.getReportName();
		}
		return Defaults.getDefaults().getReportName();
	}

	public Locale getLocale() {
		if (report.getLocale() != null) {
			return report.getLocale();
		}
		if (template.getLocale() != null) {
			return template.getLocale();
		}
		return Defaults.getDefaults().getLocale();
	}

	protected boolean isShowColumnTitle() {
		if (report.getShowColumnTitle() != null) {
			return report.getShowColumnTitle();
		}
		if (template.getShowColumnTitle() != null) {
			return template.getShowColumnTitle();
		}
		return Defaults.getDefaults().isShowColumnTitle();
	}

	protected boolean isShowColumnValues() {
		if (report.getShowColumnValues() != null) {
			return report.getShowColumnValues();
		}
		if (template.getShowColumnValues() != null) {
			return template.getShowColumnValues();
		}
		return Defaults.getDefaults().isShowColumnValues();
	}

	public String getResourceBundleName() {
		if (report.getResourceBundleName() != null) {
			return report.getResourceBundleName();
		}
		if (templateDesign.getResourceBundleName() != null) {
			return templateDesign.getResourceBundleName();
		}
		return null;
	}

	public boolean isIgnorePagination() {
		if (report.getIgnorePagination() != null) {
			return report.getIgnorePagination();
		}
		if (templateDesign.getIgnorePagination() != null) {
			return templateDesign.getIgnorePagination();
		}
		if (template.getIgnorePagination() != null) {
			return template.getIgnorePagination();
		}
		return Defaults.getDefaults().isIgnorePagination();
	}

	public WhenNoDataType getWhenNoDataType(boolean emptyDetail, DRIDesignBand noDataBand) {
		if (report.getWhenNoDataType() != null) {
			return report.getWhenNoDataType();
		}
		if (templateDesign.getWhenNoDataType() != null) {
			return templateDesign.getWhenNoDataType();
		}
		if (template.getWhenNoDataType() != null) {
			return template.getWhenNoDataType();
		}
		if (noDataBand != null) {
			return WhenNoDataType.NO_DATA_SECTION;
		}
		if (emptyDetail) {
			return WhenNoDataType.ALL_SECTIONS_NO_DETAIL;
		}
		return Defaults.getDefaults().getWhenNoDataType();
	}

	public WhenResourceMissingType getWhenResourceMissingType() {
		if (report.getWhenResourceMissingType() != null) {
			return report.getWhenResourceMissingType();
		}
		if (templateDesign.getWhenResourceMissingType() != null) {
			return templateDesign.getWhenResourceMissingType();
		}
		if (template.getWhenResourceMissingType() != null) {
			return template.getWhenResourceMissingType();
		}
		return Defaults.getDefaults().getWhenResourceMissingType();
	}

	public boolean isTitleOnANewPage() {
		if (report.getTitleOnANewPage() != null) {
			return report.getTitleOnANewPage();
		}
		if (templateDesign.getTitleOnANewPage() != null) {
			return templateDesign.getTitleOnANewPage();
		}
		if (template.getTitleOnANewPage() != null) {
			return template.getTitleOnANewPage();
		}
		return Defaults.getDefaults().isTitleOnANewPage();
	}

	public boolean isSummaryOnANewPage() {
		if (report.getSummaryOnANewPage() != null) {
			return report.getSummaryOnANewPage();
		}
		if (templateDesign.getSummaryOnANewPage() != null) {
			return templateDesign.getSummaryOnANewPage();
		}
		if (template.getSummaryOnANewPage() != null) {
			return template.getSummaryOnANewPage();
		}
		return Defaults.getDefaults().isSummaryOnANewPage();
	}

	public boolean isSummaryWithPageHeaderAndFooter() {
		if (report.getSummaryWithPageHeaderAndFooter() != null) {
			return report.getSummaryWithPageHeaderAndFooter();
		}
		if (templateDesign.getSummaryWithPageHeaderAndFooter() != null) {
			return templateDesign.getSummaryWithPageHeaderAndFooter();
		}
		if (template.getSummaryWithPageHeaderAndFooter() != null) {
			return template.getSummaryWithPageHeaderAndFooter();
		}
		return Defaults.getDefaults().isSummaryWithPageHeaderAndFooter();
	}

	public boolean isFloatColumnFooter() {
		if (report.getFloatColumnFooter() != null) {
			return report.getFloatColumnFooter();
		}
		if (templateDesign.getFloatColumnFooter() != null) {
			return templateDesign.getFloatColumnFooter();
		}
		if (template.getFloatColumnFooter() != null) {
			return template.getFloatColumnFooter();
		}
		return Defaults.getDefaults().isFloatColumnFooter();
	}

	public Orientation getPrintOrder() {
		if (report.getPrintOrder() != null) {
			return report.getPrintOrder();
		}
		if (template.getPrintOrder() != null) {
			return template.getPrintOrder();
		}
		return Defaults.getDefaults().getPrintOrder();
	}

	public RunDirection getColumnDirection() {
		if (report.getColumnDirection() != null) {
			return report.getColumnDirection();
		}
		if (template.getColumnDirection() != null) {
			return template.getColumnDirection();
		}
		return Defaults.getDefaults().getColumnDirection();
	}

	public String getLanguage() {
		if (report.getLanguage() != null) {
			return report.getLanguage();
		}
		if (template.getLanguage() != null) {
			return template.getLanguage();
		}
		return Defaults.getDefaults().getLanguage();
	}

	public String getFieldDescription(DRIField<?> field) {
		if (field.getDescription() != null) {
			return field.getDescription();
		}
		if (isUseFieldNameAsDescription()) {
			return field.getName();
		}
		return null;
	}

	private boolean isUseFieldNameAsDescription() {
		if (report.getUseFieldNameAsDescription() != null) {
			return report.getUseFieldNameAsDescription();
		}
		if (template.getUseFieldNameAsDescription() != null) {
			return template.getUseFieldNameAsDescription();
		}
		return Defaults.getDefaults().isUseFieldNameAsDescription();
	}

	//table of contents
	public boolean isTableOfContents(Map<String, JasperTocHeading> tocHeadings) {
		if (tocHeadings != null) {
			return true;
		}
		if (report.getTableOfContents() != null) {
			return report.getTableOfContents();
		}
		if (template.getTableOfContents() != null) {
			return template.getTableOfContents();
		}
		return Defaults.getDefaults().isTableOfContents();
	}

	public DRITableOfContentsCustomizer getTableOfContentsCustomizer() {
		if (report.getTableOfContentsCustomizer() != null) {
			return report.getTableOfContentsCustomizer();
		}
		if (template.getTableOfContentsCustomizer() != null) {
			return template.getTableOfContentsCustomizer();
		}
		return Defaults.getDefaults().getTableOfContentsCustomizer();
	}

	public boolean isAddGroupToTableOfContents(DRIGroup group) {
		if (group.getAddToTableOfContents() != null) {
			return group.getAddToTableOfContents();
		}
		return Defaults.getDefaults().isAddGroupToTableOfContents();
	}

	//style
	protected DRISimpleStyle getDetailOddRowStyle() {
		if (isHighlightDetailOddRows()) {
			if (report.getDetailOddRowStyle() != null) {
				return report.getDetailOddRowStyle();
			}
			if (template.getDetailOddRowStyle() != null) {
				return template.getDetailOddRowStyle();
			}
			return Defaults.getDefaults().getDetailOddRowStyle();
		}
		return null;
	}

	protected DRISimpleStyle getDetailEvenRowStyle() {
		if (isHighlightDetailEvenRows()) {
			if (report.getDetailEvenRowStyle() != null) {
				return report.getDetailEvenRowStyle();
			}
			if (template.getDetailEvenRowStyle() != null) {
				return template.getDetailEvenRowStyle();
			}
			return Defaults.getDefaults().getDetailEvenRowStyle();
		}
		return null;
	}

	private boolean isHighlightDetailOddRows() {
		if (report.getHighlightDetailOddRows() != null) {
			return report.getHighlightDetailOddRows();
		}
		if (template.getHighlightDetailOddRows() != null) {
			return template.getHighlightDetailOddRows();
		}
		return Defaults.getDefaults().isHighlightDetailOddRows();
	}

	private boolean isHighlightDetailEvenRows() {
		if (report.getHighlightDetailEvenRows() != null) {
			return report.getHighlightDetailEvenRows();
		}
		if (template.getHighlightDetailEvenRows() != null) {
			return template.getHighlightDetailEvenRows();
		}
		return Defaults.getDefaults().isHighlightDetailEvenRows();
	}

	protected String getDefaultFontName() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getFontName() != null) {
			return report.getDefaultFont().getFontName();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getFontName() != null) {
			return template.getDefaultFont().getFontName();
		}
		return Defaults.getDefaults().getFont().getFontName();
	}

	protected Integer getDefaultFontSize() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getFontSize() != null) {
			return report.getDefaultFont().getFontSize();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getFontSize() != null) {
			return template.getDefaultFont().getFontSize();
		}
		return Defaults.getDefaults().getFont().getFontSize();
	}

	protected Boolean getDefaultFontBold() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getBold() != null) {
			return report.getDefaultFont().getBold();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getBold() != null) {
			return template.getDefaultFont().getBold();
		}
		return Defaults.getDefaults().getFont().getBold();
	}

	protected Boolean getDefaultFontItalic() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getItalic() != null) {
			return report.getDefaultFont().getItalic();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getItalic() != null) {
			return template.getDefaultFont().getItalic();
		}
		return Defaults.getDefaults().getFont().getItalic();
	}

	protected Boolean getDefaultFontUnderline() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getUnderline() != null) {
			return report.getDefaultFont().getUnderline();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getUnderline() != null) {
			return template.getDefaultFont().getUnderline();
		}
		return Defaults.getDefaults().getFont().getUnderline();
	}

	protected Boolean getDefaultFontStrikeThrough() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getStrikeThrough() != null) {
			return report.getDefaultFont().getStrikeThrough();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getStrikeThrough() != null) {
			return template.getDefaultFont().getStrikeThrough();
		}
		return Defaults.getDefaults().getFont().getStrikeThrough();
	}

	protected String getDefaultFontPdfFontName() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getPdfFontName() != null) {
			return report.getDefaultFont().getPdfFontName();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getPdfFontName() != null) {
			return template.getDefaultFont().getPdfFontName();
		}
		return Defaults.getDefaults().getFont().getPdfFontName();
	}

	protected String getDefaultFontPdfEncoding() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getPdfEncoding() != null) {
			return report.getDefaultFont().getPdfEncoding();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getPdfEncoding() != null) {
			return template.getDefaultFont().getPdfEncoding();
		}
		return Defaults.getDefaults().getFont().getPdfEncoding();
	}

	protected Boolean getDefaultFontPdfEmbedded() {
		if (report.getDefaultFont() != null && report.getDefaultFont().getPdfEmbedded() != null) {
			return report.getDefaultFont().getPdfEmbedded();
		}
		if (template.getDefaultFont() != null && template.getDefaultFont().getPdfEmbedded() != null) {
			return template.getDefaultFont().getPdfEmbedded();
		}
		return Defaults.getDefaults().getFont().getPdfEmbedded();
	}

	protected DRIReportStyle getTextStyle() {
		if (report.getTextStyle() != null) {
			return report.getTextStyle();
		}
		if (template.getTextStyle() != null) {
			return template.getTextStyle();
		}
		return Defaults.getDefaults().getTextStyle();
	}

	protected DRIReportStyle getColumnTitleStyle() {
		if (report.getColumnTitleStyle() != null) {
			return report.getColumnTitleStyle();
		}
		if (template.getColumnTitleStyle() != null) {
			return template.getColumnTitleStyle();
		}
		if (Defaults.getDefaults().getColumnTitleStyle() != null) {
			return Defaults.getDefaults().getColumnTitleStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getColumnStyle(boolean textStyle) {
		if (report.getColumnStyle() != null) {
			return report.getColumnStyle();
		}
		if (template.getColumnStyle() != null) {
			return template.getColumnStyle();
		}
		if (Defaults.getDefaults().getColumnStyle() != null) {
			return Defaults.getDefaults().getColumnStyle();
		}
		if (textStyle) {
			return getTextStyle();
		}
		return null;
	}

	protected DRIReportStyle getGroupTitleStyle() {
		if (report.getGroupTitleStyle() != null) {
			return report.getGroupTitleStyle();
		}
		if (template.getGroupTitleStyle() != null) {
			return template.getGroupTitleStyle();
		}
		if (Defaults.getDefaults().getGroupTitleStyle() != null) {
			return Defaults.getDefaults().getGroupTitleStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getGroupStyle() {
		if (report.getGroupStyle() != null) {
			return report.getGroupStyle();
		}
		if (template.getGroupStyle() != null) {
			return template.getGroupStyle();
		}
		if (Defaults.getDefaults().getGroupStyle() != null) {
			return Defaults.getDefaults().getGroupStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getSubtotalStyle() {
		if (report.getSubtotalStyle() != null) {
			return report.getSubtotalStyle();
		}
		if (template.getSubtotalStyle() != null) {
			return template.getSubtotalStyle();
		}
		if (Defaults.getDefaults().getSubtotalStyle() != null) {
			return Defaults.getDefaults().getSubtotalStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getImageStyle() {
		if (report.getImageStyle() != null) {
			return report.getImageStyle();
		}
		if (template.getImageStyle() != null) {
			return template.getImageStyle();
		}
		return Defaults.getDefaults().getImageStyle();
	}

	protected DRIReportStyle getChartStyle() {
		if (report.getChartStyle() != null) {
			return report.getChartStyle();
		}
		if (template.getChartStyle() != null) {
			return template.getChartStyle();
		}
		return Defaults.getDefaults().getChartStyle();
	}

	protected DRIReportStyle getBarcodeStyle() {
		if (report.getBarcodeStyle() != null) {
			return report.getBarcodeStyle();
		}
		if (template.getBarcodeStyle() != null) {
			return template.getBarcodeStyle();
		}
		return Defaults.getDefaults().getBarcodeStyle();
	}

	//page
	protected int getPageWidth() throws DRException {
		if (isIgnorePageWidth()) {
			return getDynamicPageWidth();
		} else {
			return getStaticPageWidth();
		}
	}

	private boolean isIgnorePageWidth() {
		if (report.getPage().getIgnorePageWidth() != null) {
			return report.getPage().getIgnorePageWidth();
		}
		if (template.getIgnorePageWidth() != null) {
			return template.getIgnorePageWidth();
		}
		return Defaults.getDefaults().isIgnorePageWidth();
	}

	private int getDynamicPageWidth() throws DRException {
		int maxPageWidth = 0;
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getTitleBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getPageHeaderBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getPageFooterBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getColumnHeaderBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getColumnFooterBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getDetailBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getLastPageFooterBand(), maxPageWidth);
		maxPageWidth = getMaxBandWidth(accessor.getBandTransform().getSummaryBand(), maxPageWidth);

		return maxPageWidth + getPageMargin().getLeft() + getPageMargin().getRight();
	}

	private int getMaxBandWidth(DRIDesignBand band, int maxWidth) throws DRException {
		if (band == null || band.getList() == null) {
			return maxWidth;
		}

		int bandWidth = detectWidth(band.getList());
		if (bandWidth > maxWidth) {
			return bandWidth;
		}
		return maxWidth;
	}

	private int getStaticPageWidth() throws DRException {
		if (accessor.getPageWidth() != null) {
			return accessor.getPageWidth();
		}
		if (report.getPage().getWidth() != null) {
			return report.getPage().getWidth();
		}
		if (templateDesign.getPageWidth() != null) {
			return templateDesign.getPageWidth();
		}
		if (template.getPageWidth() != null) {
			return template.getPageWidth();
		}
		return Defaults.getDefaults().getPageWidth();
	}

	protected int getPageHeight() throws DRException {
		if (report.getPage().getHeight() != null) {
			return report.getPage().getHeight();
		}
		if (templateDesign.getPageHeight() != null) {
			return templateDesign.getPageHeight();
		}
		if (template.getPageHeight() != null) {
			return template.getPageHeight();
		}
		return Defaults.getDefaults().getPageHeight();
	}

	protected PageOrientation getPageOrientation() throws DRException {
		if (report.getPage().getOrientation() != null) {
			return report.getPage().getOrientation();
		}
		if (templateDesign.getPageOrientation() != null) {
			return templateDesign.getPageOrientation();
		}
		if (template.getPageOrientation() != null) {
			return template.getPageOrientation();
		}
		return Defaults.getDefaults().getPageOrientation();
	}

	protected DRIMargin getPageMargin() throws DRException {
		if (report.getPage().getMargin() != null) {
			return report.getPage().getMargin();
		}
		if (templateDesign.getPageMargin() != null) {
			return templateDesign.getPageMargin();
		}
		if (accessor.getPageWidth() != null) {
			return Defaults.getDefaults().getSubreportPageMargin();
		}
		if (template.getPageMargin() != null) {
			return template.getPageMargin();
		}
		return Defaults.getDefaults().getPageMargin();
	}

	protected int getPageColumnsPerPage() throws DRException {
		if (report.getPage().getColumnsPerPage() != null) {
			return report.getPage().getColumnsPerPage();
		}
		if (templateDesign.getPageColumnsPerPage() != null) {
			return templateDesign.getPageColumnsPerPage();
		}
		if (template.getPageColumnsPerPage() != null) {
			return template.getPageColumnsPerPage();
		}
		return Defaults.getDefaults().getPageColumnsPerPage();
	}

	protected int getPageColumnSpace() throws DRException {
		if (report.getPage().getColumnSpace() != null) {
			return report.getPage().getColumnSpace();
		}
		if (templateDesign.getPageColumnSpace() != null) {
			return templateDesign.getPageColumnSpace();
		}
		if (template.getPageColumnSpace() != null) {
			return template.getPageColumnSpace();
		}
		return Defaults.getDefaults().getPageColumnSpace();
	}

	protected int getPageColumnWidth(DRIDesignPage page) {
		int columnWidth = page.getWidth() - page.getMargin().getLeft() - page.getMargin().getRight();
		columnWidth -= page.getColumnSpace() * (page.getColumnsPerPage() - 1);
		columnWidth = columnWidth / page.getColumnsPerPage();
		if (templateDesign.getPageColumnWidth() != null && templateDesign.getPageColumnWidth() > 0 &&
				templateDesign.getPageColumnWidth() < columnWidth && !isIgnorePageWidth()) {
			return templateDesign.getPageColumnWidth();
		}
		return columnWidth;
	}

	//column
	protected boolean isColumnPrintRepeatedDetailValues(DRIValueColumn<?> column) {
		if (column.getPrintRepeatedDetailValues() != null) {
			return column.getPrintRepeatedDetailValues();
		}
		if (template.getColumnPrintRepeatedDetailValues() != null) {
			return template.getColumnPrintRepeatedDetailValues();
		}
		return Defaults.getDefaults().isColumnPrintRepeatedDetailValues();
	}

	protected int getColumnWidth(DRIColumn<?> column, DRDesignStyle style) throws DRException {
		DRIComponent component = accessor.getColumnTransform().getColumnComponent(column);
		if (component != null) {
			if (component instanceof DRIList) {
				DRDesignList list = accessor.getComponentTransform().list((DRIList) component, DefaultStyleType.COLUMN, null, null);
				return detectWidth(list);
			}
			else if (component instanceof DRIDimensionComponent) {
				if (((DRIDimensionComponent) component).getWidth() != null) {
					return ((DRIDimensionComponent) component).getWidth();
				}
				if (component instanceof DRITextField<?>) {
					if (((DRITextField<?>) component).getColumns() != null) {
						return StyleResolver.getFontWidth(style, ((DRITextField<?>) component).getColumns());
					}
				}
			}
			else {
				throw new DRDesignReportException("Component " + component.getClass().getName() + " not supported");
			}
		}
		return getColumnWidth();
	}

	protected int getColumnWidth() {
		if (template.getColumnWidth() != null) {
			return template.getColumnWidth();
		}
		return Defaults.getDefaults().getColumnWidth();
	}

	//component
	protected boolean getRemoveLineWhenBlank(DRIComponent component) {
		if (component.getRemoveLineWhenBlank() != null) {
			return component.getRemoveLineWhenBlank();
		}
		return Defaults.getDefaults().isRemoveLineWhenBlank();
	}

	protected ComponentPositionType getPositionType(DRIComponent component) {
		if (component instanceof DRIDimensionComponent && ((DRIDimensionComponent)component).getPositionType() != null) {
			return ((DRIDimensionComponent)component).getPositionType();
		}
		return null;
	}

	protected StretchType getStretchType(DRIComponent component) {
		if (component instanceof DRIDimensionComponent && ((DRIDimensionComponent)component).getStretchType() != null) {
			return ((DRIDimensionComponent)component).getStretchType();
		}
		return null;
	}

	protected boolean getPrintInFirstWholeBand(DRIComponent component) {
		if (component instanceof DRIDimensionComponent && ((DRIDimensionComponent)component).getPrintInFirstWholeBand() != null) {
			return ((DRIDimensionComponent)component).getPrintInFirstWholeBand();
		}
		return Defaults.getDefaults().isPrintInFirstWholeBand();
	}

	protected boolean getPrintWhenDetailOverflows(DRIComponent component) {
		if (component instanceof DRIDimensionComponent && ((DRIDimensionComponent)component).getPrintWhenDetailOverflows() != null) {
			return ((DRIDimensionComponent)component).getPrintWhenDetailOverflows();
		}
		return Defaults.getDefaults().isPrintWhenDetailOverflows();
	}

	protected DRIDesignGroup getPrintWhenGroupChanges(DRIComponent component) {
		if (component instanceof DRIDimensionComponent && ((DRIDimensionComponent)component).getPrintWhenGroupChanges() != null) {
			return accessor.getGroupTransform().getGroup(((DRIDimensionComponent)component).getPrintWhenGroupChanges());
		}
		return null;
	}

	//group
	protected GroupHeaderLayout getGroupHeaderLayout(DRIGroup group) {
		if (group.getHeaderLayout() != null) {
			return group.getHeaderLayout();
		}
		if (template.getGroupHeaderLayout() != null) {
			return template.getGroupHeaderLayout();
		}
		return Defaults.getDefaults().getGroupHeaderLayout();
	}

	protected boolean isGroupHideColumn(DRIGroup group) {
		if (group.getHideColumn() != null) {
			return group.getHideColumn();
		}
		if (template.getGroupHideColumn() != null) {
			return template.getGroupHideColumn();
		}
		return Defaults.getDefaults().isGroupHideColumn();
	}

	protected boolean isGroupShowColumnHeaderAndFooter(DRIGroup group) {
		if (group.getShowColumnHeaderAndFooter() != null) {
			return group.getShowColumnHeaderAndFooter();
		}
		if (template.getGroupShowColumnHeaderAndFooter() != null) {
			return template.getGroupShowColumnHeaderAndFooter();
		}
		return Defaults.getDefaults().isGroupShowColumnHeaderAndFooter();
	}

	protected int getGroupPadding(DRIGroup group) {
		if (group.getPadding() != null) {
			return group.getPadding();
		}
		if (template.getGroupPadding() != null) {
			return template.getGroupPadding();
		}
		return Defaults.getDefaults().getGroupPadding();
	}

	protected boolean isGroupStartInNewPage(DRIGroup group) {
		if (group.getStartInNewPage() != null) {
			return group.getStartInNewPage();
		}
		if (template.getGroupStartInNewPage() != null) {
			return template.getGroupStartInNewPage();
		}
		return Defaults.getDefaults().isGroupStartInNewPage();
	}

	protected boolean isGroupStartInNewColumn(DRIGroup group) {
		if (group.getStartInNewColumn() != null) {
			return group.getStartInNewColumn();
		}
		if (template.getGroupStartInNewColumn() != null) {
			return template.getGroupStartInNewColumn();
		}
		return Defaults.getDefaults().isGroupStartInNewColumn();
	}

	protected boolean isGroupReprintHeaderOnEachPage(DRIGroup group) {
		if (group.getReprintHeaderOnEachPage() != null) {
			return group.getReprintHeaderOnEachPage();
		}
		if (template.getGroupReprintHeaderOnEachPage() != null) {
			return template.getGroupReprintHeaderOnEachPage();
		}
		return Defaults.getDefaults().isGroupReprintHeaderOnEachPage();
	}

	protected boolean isGroupResetPageNumber(DRIGroup group) {
		if (group.getResetPageNumber() != null) {
			return group.getResetPageNumber();
		}
		if (template.getGroupResetPageNumber() != null) {
			return template.getGroupResetPageNumber();
		}
		return Defaults.getDefaults().isGroupResetPageNumber();
	}

	protected Integer getGroupMinHeightToStartNewPage(DRIGroup group) {
		if (group.getMinHeightToStartNewPage() != null) {
			return group.getMinHeightToStartNewPage();
		}
		return Defaults.getDefaults().getGroupMinHeightToStartNewPage();
	}

	protected GroupFooterPosition getGroupFooterPosition(DRIGroup group) {
		if (group.getFooterPosition() != null) {
			return group.getFooterPosition();
		}
		if (template.getGroupFooterPosition() != null) {
			return template.getGroupFooterPosition();
		}
		return Defaults.getDefaults().getGroupFooterPosition();
	}

	protected boolean isGroupKeepTogether(DRIGroup group) {
		if (group.getKeepTogether() != null) {
			return group.getKeepTogether();
		}
		if (template.getGroupKeepTogether() != null) {
			return template.getGroupKeepTogether();
		}
		return Defaults.getDefaults().isGroupKeepTogether();
	}

	protected boolean isGroupHeaderWithSubtotal(DRIGroup group) {
		if (group.getHeaderWithSubtotal() != null) {
			return group.getHeaderWithSubtotal();
		}
		if (template.getGroupHeaderWithSubtotal() != null) {
			return template.getGroupHeaderWithSubtotal();
		}
		return Defaults.getDefaults().isGroupHeaderWithSubtotal();
	}

	protected boolean isGroupByDataType(DRIGroup group) {
		if (group.getGroupByDataType() != null) {
			return group.getGroupByDataType();
		}
		return Defaults.getDefaults().isGroupByDataType();
	}

	//subtotal
	public Position getSubtotalLabelPosition(DRISubtotal<?> subtotal) {
		if (subtotal.getLabelPosition() != null) {
			return subtotal.getLabelPosition();
		}
		if (template.getSubtotalLabelPosition() != null) {
			return template.getSubtotalLabelPosition();
		}
		return Defaults.getDefaults().getSubtotalLabelPosition();
	}

	//text field
	protected int getTextFieldWidth(DRITextField<?> textField, DRDesignStyle style) {
		if (textField.getWidth() != null) {
			return textField.getWidth();
		}
		if (textField.getColumns() != null) {
			return StyleResolver.getFontWidth(style, textField.getColumns());
		}
		if (template.getTextFieldWidth() != null) {
			return template.getTextFieldWidth();
		}
		return Defaults.getDefaults().getTextFieldWidth();
	}

	protected int getTextFieldHeight(DRITextField<?> textField, DRDesignStyle style) {
		if (textField.getHeight() != null) {
			return textField.getHeight();
		}
		if (textField.getRows() != null) {
			return StyleResolver.getFontHeight(style, textField.getRows());
		}
		return StyleResolver.getFontHeight(style, 1);
	}

	protected String getTextFieldPattern(DRITextField<?> textField, DRDesignStyle style) {
		if (textField.getPattern() != null) {
			return textField.getPattern();
		}
		if (StyleResolver.getPattern(style) != null) {
			return null;//StyleResolver.getPattern(style);
		}
		if (textField.getDataType() != null) {
			return textField.getDataType().getPattern();
		}
		return null;
	}

	protected HorizontalTextAlignment getTextFieldHorizontalTextAlignment(DRITextField<?> textField, DRDesignStyle style) {
		if (textField.getHorizontalTextAlignment() != null) {
			return textField.getHorizontalTextAlignment();
		}
		if (StyleResolver.getHorizontalTextAlignment(style) != null) {
			return null;//StyleResolver.getHorizontalTextAlignment(style);
		}
		if (textField.getDataType() != null) {
			return textField.getDataType().getHorizontalTextAlignment();
		}
		return null;
	}

	protected DRIValueFormatter<?, ?> getTextFieldValueFormatter(DRITextField<?> textField) {
		if (textField.getValueFormatter() != null) {
			return textField.getValueFormatter();
		}
		if (textField.getDataType() != null) {
			return textField.getDataType().getValueFormatter();
		}
		return null;
	}

	protected boolean getTextFieldStretchWithOverflow(DRITextField<?> textField) {
		if (textField.getStretchWithOverflow() != null) {
			return textField.getStretchWithOverflow();
		}
		return Defaults.getDefaults().isTextFieldStretchWithOverflow();
	}

	protected boolean isTextFieldPrintRepeatedValues(DRITextField<?> textField) {
		if (textField.getPrintRepeatedValues() != null) {
			return textField.getPrintRepeatedValues();
		}
		return Defaults.getDefaults().isTextFieldPrintRepeatedValues();
	}

	//text field
	protected int getBooleanFieldWidth(DRIBooleanField booleanField, DRDesignStyle style) {
		if (booleanField.getWidth() != null) {
			return booleanField.getWidth();
		}
		if (template.getTextFieldWidth() != null) {
			return template.getTextFieldWidth();
		}
		return Defaults.getDefaults().getTextFieldWidth();
	}

	protected int getBooleanFieldHeight(DRIBooleanField booleanField, DRDesignStyle style) {
		if (booleanField.getHeight() != null) {
			return booleanField.getHeight();
		}
		return StyleResolver.getFontHeight(style, 1);
	}

	//page x of y
	protected int getPageXofYWidth(DRIPageXofY pageXofY) {
		if (pageXofY.getWidth() != null) {
			return pageXofY.getWidth();
		}
		if (template.getTextFieldWidth() != null) {
			return template.getTextFieldWidth();
		}
		return Defaults.getDefaults().getTextFieldWidth();
	}

	protected int getPageXofYHeight(DRIPageXofY pageXofY, DRDesignStyle style) {
		if (pageXofY.getHeight() != null) {
			return pageXofY.getHeight();
		}
		return StyleResolver.getFontHeight(style, 1);
	}

	protected HorizontalTextAlignment getPageXofYHorizontalTextAlignment(DRIPageXofY pageXofY, DRDesignStyle style) {
		if (pageXofY.getHorizontalTextAlignment() != null) {
			return pageXofY.getHorizontalTextAlignment();
		}
		if (StyleResolver.getHorizontalTextAlignment(style) != null) {
			return StyleResolver.getHorizontalTextAlignment(style);
		}
		return Defaults.getDefaults().getPageXofYHorizontalTextAlignment();
	}

	//image
	protected int getImageWidth(DRIImage image) {
		if (image.getWidth() != null) {
			return image.getWidth();
		}
		if (template.getImageWidth() != null) {
			return template.getImageWidth();
		}
		return Defaults.getDefaults().getImageWidth();
	}

	protected int getImageHeight(DRIImage image, Integer imageHeight, DRDesignStyle style) {
		if (image.getHeight() != null) {
			return image.getHeight();
		}
		if (imageHeight != null) {
			return imageHeight + StyleResolver.getVerticalPadding(style);
		}
		if (template.getImageHeight() != null) {
			return template.getImageHeight();
		}
		return Defaults.getDefaults().getImageHeight();
	}

	//filler
	protected int getFillerWidth(DRIFiller filler) {
		if (filler.getWidth() != null) {
			return filler.getWidth();
		}
		return Defaults.getDefaults().getFillerWidth();
	}

	protected int getFillerHeight(DRIFiller filler) {
		if (filler.getHeight() != null) {
			return filler.getHeight();
		}
		return Defaults.getDefaults().getFillerHeight();
	}

	//line
	protected int getLineWidth(DRILine line) {
		if (line.getWidth() != null) {
			return line.getWidth();
		}
		return Defaults.getDefaults().getLineWidth();
	}

	protected int getLineHeight(DRILine line) {
		if (line.getHeight() != null) {
			return line.getHeight();
		}
		return Defaults.getDefaults().getLineHeight();
	}

	//ellipse
	protected int getEllipseWidth(DRIEllipse ellipse) {
		if (ellipse.getWidth() != null) {
			return ellipse.getWidth();
		}
		return Defaults.getDefaults().getEllipseWidth();
	}

	protected int getEllipseHeight(DRIEllipse ellipse) {
		if (ellipse.getHeight() != null) {
			return ellipse.getHeight();
		}
		return Defaults.getDefaults().getEllipseHeight();
	}

	//rectangle
	protected Integer getRectangleRadius(DRIRectangle rectangle) {
		if (rectangle.getRadius() != null) {
			return rectangle.getRadius();
		}
		return Defaults.getDefaults().getRectangleRadius();
	}

	protected int getRectangleWidth(DRIRectangle rectangle) {
		if (rectangle.getWidth() != null) {
			return rectangle.getWidth();
		}
		return Defaults.getDefaults().getRectangleWidth();
	}

	protected int getRectangleHeight(DRIRectangle rectangle) {
		if (rectangle.getHeight() != null) {
			return rectangle.getHeight();
		}
		return Defaults.getDefaults().getRectangleHeight();
	}

	//map
	protected int getMapWidth(DRIMap map) {
		if (map.getWidth() != null) {
			return map.getWidth();
		}
		return Defaults.getDefaults().getMapWidth();
	}

	protected int getMapHeight(DRIMap map) {
		if (map.getHeight() != null) {
			return map.getHeight();
		}
		return Defaults.getDefaults().getMapHeight();
	}

	//custom component
	protected int getCustomComponentWidth(DRIDimensionComponent component) {
		if (component.getWidth() != null) {
			return component.getWidth();
		}
		return Defaults.getDefaults().getCustomComponentWidth();
	}

	protected int getCustomComponentHeight(DRIDimensionComponent component) {
		if (component.getHeight() != null) {
			return component.getHeight();
		}
		return Defaults.getDefaults().getCustomComponentHeight();
	}

	//break
	protected int getBreakWidth(DRIBreak breakComponent) {
		return Defaults.getDefaults().getBreakWidth();
	}

	protected int getBreakHeight(DRIBreak breakComponent) {
		return Defaults.getDefaults().getBreakHeight();
	}

	//generic element
	protected Integer getGenericElementWidth(DRIGenericElement genericElement) {
		if (genericElement.getWidth() != null) {
			return genericElement.getWidth();
		}
		return Defaults.getDefaults().getGenericElementWidth();
	}

	protected Integer getGenericElementHeight(DRIGenericElement genericElement) {
		if (genericElement.getHeight() != null) {
			return genericElement.getHeight();
		}
		return Defaults.getDefaults().getGenericElementHeight();
	}

	//list
	protected Integer getListWidth(DRIList list) {
		if (list.getWidth() != null) {
			return list.getWidth();
		}
		return Defaults.getDefaults().getListWidth();
	}

	protected Integer getListHeight(DRIList list) {
		if (list.getHeight() != null) {
			return list.getHeight();
		}
		return Defaults.getDefaults().getListHeight();
	}

	protected int getListGap(DRIList list) {
		if (list.getGap() != null) {
			return list.getGap();
		}
		if (template.getListgap() != null) {
			return template.getListgap();
		}
		return Defaults.getDefaults().getListgap();
	}

	//xy list
	protected Integer getXyListWidth(DRIXyList xyList) {
		if (xyList.getWidth() != null) {
			return xyList.getWidth();
		}
		return Defaults.getDefaults().getListWidth();
	}

	protected Integer getXyListHeight(DRIXyList xyList) {
		if (xyList.getHeight() != null) {
			return xyList.getHeight();
		}
		return Defaults.getDefaults().getListHeight();
	}

	//multi page list
	protected int getMultiPageListWidth(DRIMultiPageList multiPageList) {
		if (multiPageList.getWidth() != null) {
			return multiPageList.getWidth();
		}
		if (template.getMultiPageListWidth() != null) {
			return template.getMultiPageListWidth();
		}
		return Defaults.getDefaults().getMultiPageListWidth();
	}

	protected int getMultiPageListHeight(DRIMultiPageList multiPageList) {
		if (multiPageList.getHeight() != null) {
			return multiPageList.getHeight();
		}
		if (template.getMultiPageListHeight() != null) {
			return template.getMultiPageListHeight();
		}
		return Defaults.getDefaults().getMultiPageListHeight();
	}

	//chart
	protected int getChartWidth(DRIChart chart) {
		if (chart.getWidth() != null) {
			return chart.getWidth();
		}
		if (template.getChartWidth() != null) {
			return template.getChartWidth();
		}
		return Defaults.getDefaults().getChartWidth();
	}

	protected int getChartHeight(DRIChart chart) {
		if (chart.getHeight() != null) {
			return chart.getHeight();
		}
		if (template.getChartHeight() != null) {
			return template.getChartHeight();
		}
		return Defaults.getDefaults().getChartHeight();
	}

	protected List<Color> getChartSeriesColors(DRIBasePlot plot) {
		if (plot.getSeriesColors() != null && !plot.getSeriesColors().isEmpty()) {
			return plot.getSeriesColors();
		}
		if (template.getChartSeriesColors() != null && !template.getChartSeriesColors().isEmpty()) {
			return template.getChartSeriesColors();
		}
		return Defaults.getDefaults().getChartSeriesColors();
	}

	protected String getChartValuePattern(DRIAxisPlot axisPlot) {
		if (axisPlot.getValuePattern() != null) {
			return axisPlot.getValuePattern();
		}
		if (template.getChartValuePattern() != null) {
			return template.getChartValuePattern();
		}
		return Defaults.getDefaults().getChartValuePattern();
	}

	protected String getChartValuePattern(DRIPiePlot piePlot) {
		if (piePlot.getValuePattern() != null) {
			return piePlot.getValuePattern();
		}
		if (template.getChartValuePattern() != null) {
			return template.getChartValuePattern();
		}
		return Defaults.getDefaults().getChartValuePattern();
	}

	protected String getChartPercentValuePattern(DRIAxisPlot axisPlot) {
		if (axisPlot.getPercentValuePattern() != null) {
			return axisPlot.getPercentValuePattern();
		}
		if (template.getChartPercentValuePattern() != null) {
			return template.getChartPercentValuePattern();
		}
		return Defaults.getDefaults().getChartPercentValuePattern();
	}

	protected String getChartPercentValuePattern(DRIPiePlot piePlot) {
		if (piePlot.getPercentValuePattern() != null) {
			return piePlot.getPercentValuePattern();
		}
		if (template.getChartPercentValuePattern() != null) {
			return template.getChartPercentValuePattern();
		}
		return Defaults.getDefaults().getChartPercentValuePattern();
	}

	protected boolean isChartCategoryDatasetUseSeriesAsCategory(DRICategoryDataset dataset) {
		if (dataset.getUseSeriesAsCategory() != null) {
			return dataset.getUseSeriesAsCategory();
		}
		return Defaults.getDefaults().isChartCategoryDatasetUseSeriesAsCategory();
	}

	protected TimePeriod getChartTimeSeriesDatasetTimePeriodType(DRITimeSeriesDataset dataset) {
		if (dataset.getTimePeriodType() != null) {
			return dataset.getTimePeriodType();
		}
		return Defaults.getDefaults().getChartTimeSeriesDatasetTimePeriodType();
	}

	protected ValueLocation getChartThermometerPlotValueLocation(DRIThermometerPlot plot) {
		if (plot.getValueLocation() != null) {
			return plot.getValueLocation();
		}
		return Defaults.getDefaults().getChartThermometerPlotValueLocation();
	}

	protected String getChartTheme(DRIChart chart) {
		if (chart.getTheme() != null) {
			return chart.getTheme();
		}
		if (template.getChartTheme() != null) {
			return template.getChartTheme();
		}
		return Defaults.getDefaults().getChartTheme();
	}

	//barcode
	protected int getBarcodeWidth(DRIBarcode barcode) {
		if (barcode.getWidth() != null) {
			return barcode.getWidth();
		}
		if (template.getBarcodeWidth() != null) {
			return template.getBarcodeWidth();
		}
		return Defaults.getDefaults().getBarcodeWidth();
	}

	protected int getBarcodeHeight(DRIBarcode barcode) {
		if (barcode.getHeight() != null) {
			return barcode.getHeight();
		}
		if (template.getBarcodeHeight() != null) {
			return template.getBarcodeHeight();
		}
		return Defaults.getDefaults().getBarcodeHeight();
	}

	//barbecue
	protected int getBarbecueWidth(DRIBarbecue barbecue) {
		if (barbecue.getWidth() != null) {
			return barbecue.getWidth();
		}
		if (template.getBarcodeWidth() != null) {
			return template.getBarcodeWidth();
		}
		return Defaults.getDefaults().getBarcodeWidth();
	}

	protected int getBarbecueHeight(DRIBarbecue barbecue) {
		if (barbecue.getHeight() != null) {
			return barbecue.getHeight();
		}
		if (template.getBarcodeHeight() != null) {
			return template.getBarcodeHeight();
		}
		return Defaults.getDefaults().getBarcodeHeight();
	}

	//subreport
	protected int getSubreportWidth(DRISubreport subreport) {
		if (subreport.getWidth() != null) {
			return subreport.getWidth();
		}
		if (template.getSubreportWidth() != null) {
			return template.getSubreportWidth();
		}
		return Defaults.getDefaults().getSubreportWidth();
	}

	protected int getSubreportHeight(DRISubreport subreport) {
		if (subreport.getHeight() != null) {
			return subreport.getHeight();
		}
		if (template.getSubreportHeight() != null) {
			return template.getSubreportHeight();
		}
		return Defaults.getDefaults().getSubreportHeight();
	}

	//crosstab
	protected int getCrosstabWidth(DRICrosstab crosstab) {
		if (crosstab.getWidth() != null) {
			return crosstab.getWidth();
		}
		if (template.getCrosstabWidth() != null) {
			return template.getCrosstabWidth();
		}
		return Defaults.getDefaults().getCrosstabWidth();
	}

	protected int getCrosstabHeight(DRICrosstab crosstab, DRDesignCrosstabCellContent whenNoDataCell) {
		int height;
		if (crosstab.getHeight() != null) {
			height = crosstab.getHeight();
		}
		else if (template.getCrosstabHeight() != null) {
			height = template.getCrosstabHeight();
		}
		else {
			height = Defaults.getDefaults().getCrosstabHeight();
		}
		int whenNoDataCellHeight = getCrosstabWhenNoDataCellHeight(whenNoDataCell);
		if (height == 0 && whenNoDataCellHeight > 0) {
			return whenNoDataCellHeight;
		}
		return height;
	}

	public CrosstabTotalPosition getCrosstabColumnGroupTotalPosition(DRICrosstabColumnGroup<?> columnGroup) {
		if (!isCrosstabColumnGroupShowTotal(columnGroup)) {
			return null;
		}
		if (columnGroup.getTotalPosition() != null) {
			return columnGroup.getTotalPosition();
		}
		return Defaults.getDefaults().getCrosstabColumnGroupTotalPosition();
	}

	public CrosstabTotalPosition getCrosstabRowGroupTotalPosition(DRICrosstabRowGroup<?> rowGroup) {
		if (!isCrosstabRowGroupShowTotal(rowGroup)) {
			return null;
		}
		if (rowGroup.getTotalPosition() != null) {
			return rowGroup.getTotalPosition();
		}
		return Defaults.getDefaults().getCrosstabRowGroupTotalPosition();
	}

	public boolean isCrosstabColumnGroupShowTotal(DRICrosstabColumnGroup<?> columnGroup) {
		if (columnGroup.getShowTotal() != null) {
			return columnGroup.getShowTotal();
		}
		return Defaults.getDefaults().isCrosstabColumnGroupShowTotal();
	}

	public boolean isCrosstabRowGroupShowTotal(DRICrosstabRowGroup<?> rowGroup) {
		if (rowGroup.getShowTotal() != null) {
			return rowGroup.getShowTotal();
		}
		return Defaults.getDefaults().isCrosstabRowGroupShowTotal();
	}

	public int getCrosstabColumnGroupHeaderHeight(DRICrosstabColumnGroup<?> columnGroup, DRDesignCrosstab designCrosstab, int availableHeight) {
		if (columnGroup.getHeaderHeight() != null) {
			return columnGroup.getHeaderHeight();
		}
		int maxHeight = 0;
		for (DRDesignCrosstabColumnGroup designColumnGroup : designCrosstab.getColumnGroups()) {
			if (designColumnGroup.getName().equals(columnGroup.getName())) {
				int height = detectHeight(designColumnGroup.getHeader().getList());
				if (maxHeight < height) {
					maxHeight = height;
				}
				if (designColumnGroup.getTotalHeader() != null) {
					height = detectHeight(designColumnGroup.getTotalHeader().getList());
					if (maxHeight < height && height > availableHeight) {
						maxHeight = height;
					}
				}
				break;
			}
		}
		return maxHeight;
	}

	public int getCrosstabColumnGroupTotalHeaderWidth(DRICrosstabColumnGroup<?> columnGroup, Integer cellWidth, DRDesignCrosstab designCrosstab) {
		if (columnGroup.getTotalHeaderWidth() != null) {
			return columnGroup.getTotalHeaderWidth();
		}
		if (cellWidth != null) {
			return cellWidth;
		}
		int maxWidth = 0;
		for (DRDesignCrosstabColumnGroup designColumnGroup : designCrosstab.getColumnGroups()) {
			if (designColumnGroup.getName().equals(columnGroup.getName())) {
				if (designColumnGroup.getTotalHeader() != null) {
					int height = detectWidth(designColumnGroup.getTotalHeader().getList());
					if (maxWidth < height) {
						maxWidth = height;
					}
				}
				break;
			}
		}
		for (DRDesignCrosstabCell designCell : designCrosstab.getCells()) {
			if (designCell.getColumnTotalGroup() == columnGroup.getName()) {
				int height = detectWidth(designCell.getContent().getList());
				if (maxWidth < height) {
					maxWidth = height;
				}
			}
		}
		if (maxWidth > Defaults.getDefaults().getCrosstabColumnGroupTotalHeaderMaxWidth()) {
			return Defaults.getDefaults().getCrosstabColumnGroupTotalHeaderMaxWidth();
		}
		return maxWidth;
	}

	public int getCrosstabRowGroupHeaderWidth(DRICrosstabRowGroup<?> rowGroup, DRDesignCrosstab designCrosstab) {
		if (rowGroup.getHeaderWidth() != null) {
			return rowGroup.getHeaderWidth();
		}
		int maxWidth = 0;
		for (DRDesignCrosstabRowGroup designRowGroup : designCrosstab.getRowGroups()) {
			if (designRowGroup.getName().equals(rowGroup.getName())) {
				int width = detectWidth(designRowGroup.getHeader().getList());
				if (maxWidth < width) {
					maxWidth = width;
				}
				if (designRowGroup.getTotalHeader() != null) {
					width = detectWidth(designRowGroup.getTotalHeader().getList());
					if (maxWidth < width) {
						maxWidth = width;
					}
				}
				break;
			}
		}
		if (maxWidth > Defaults.getDefaults().getCrosstabRowGroupHeaderMaxWidth()) {
			return Defaults.getDefaults().getCrosstabRowGroupHeaderMaxWidth();
		}
		return maxWidth;
	}

	public int getCrosstabRowGroupTotalHeaderHeight(DRICrosstabRowGroup<?> rowGroup, Integer cellHeight, DRDesignCrosstab designCrosstab) {
		if (rowGroup.getTotalHeaderHeight() != null) {
			return rowGroup.getTotalHeaderHeight();
		}
		if (cellHeight != null) {
			return cellHeight;
		}
		int maxHeight = 0;
		for (DRDesignCrosstabRowGroup designRowGroup : designCrosstab.getRowGroups()) {
			if (designRowGroup.getName().equals(rowGroup.getName())) {
				if (designRowGroup.getTotalHeader() != null) {
					int height = detectHeight(designRowGroup.getTotalHeader().getList());
					if (maxHeight < height) {
						maxHeight = height;
					}
				}
				break;
			}
		}
		for (DRDesignCrosstabCell designCell : designCrosstab.getCells()) {
			if (designCell.getRowTotalGroup() == rowGroup.getName()) {
				int height = detectHeight(designCell.getContent().getList());
				if (maxHeight < height) {
					maxHeight = height;
				}
			}
		}
		return maxHeight;
	}

	public int getCrosstabCellWidth(DRICrosstab crosstab, DRDesignCrosstab designCrosstab) {
		if (crosstab.getCellWidth() != null) {
			return crosstab.getCellWidth();
		}
		int maxWidth = 0;
		for (DRDesignCrosstabCell designCell : designCrosstab.getCells()) {
			if (designCell.getColumnTotalGroup() == null) {
				int width = detectWidth(designCell.getContent().getList());
				if (maxWidth < width) {
					maxWidth = width;
				}
			}
		}
		for (DRDesignCrosstabColumnGroup designColumnGroup : designCrosstab.getColumnGroups()) {
			int width = detectWidth(designColumnGroup.getHeader().getList());
			if (maxWidth < width) {
				maxWidth = width;
			}
		}
		if (maxWidth > Defaults.getDefaults().getCrosstabCellMaxWidth()) {
			return Defaults.getDefaults().getCrosstabCellMaxWidth();
		}
		return maxWidth;
	}

	public int getCrosstabCellHeight(DRICrosstab crosstab, DRDesignCrosstab designCrosstab) {
		if (crosstab.getCellHeight() != null) {
			return crosstab.getCellHeight();
		}
		int maxHeight = 0;
		for (DRDesignCrosstabCell designCell : designCrosstab.getCells()) {
			if (designCell.getRowTotalGroup() == null) {
				int height = detectHeight(designCell.getContent().getList());
				if (maxHeight < height) {
					maxHeight = height;
				}
			}
		}
		for (DRDesignCrosstabRowGroup designRowGroup : designCrosstab.getRowGroups()) {
			int height = detectHeight(designRowGroup.getHeader().getList());
			if (maxHeight < height) {
				maxHeight = height;
			}
		}
		return maxHeight;
	}

	public int getCrosstabWhenNoDataCellHeight(DRDesignCrosstabCellContent whenNoDataCell) {
		return detectHeight(whenNoDataCell.getList());
	}

	protected DRISimpleStyle getCrosstabOddRowStyle(DRICrosstab crosstab) {
		if (isCrosstabHighlightOddRows(crosstab)) {
			if (crosstab.getOddRowStyle() != null) {
				return crosstab.getOddRowStyle();
			}
			if (template.getCrosstabOddRowStyle() != null) {
				return template.getCrosstabOddRowStyle();
			}
			return Defaults.getDefaults().getCrosstabOddRowStyle();
		}
		return null;
	}

	protected DRISimpleStyle getCrosstabEvenRowStyle(DRICrosstab crosstab) {
		if (isCrosstabHighlightEvenRows(crosstab)) {
			if (crosstab.getEvenRowStyle() != null) {
				return crosstab.getEvenRowStyle();
			}
			if (template.getCrosstabEvenRowStyle() != null) {
				return template.getCrosstabEvenRowStyle();
			}
			return Defaults.getDefaults().getCrosstabEvenRowStyle();
		}
		return null;
	}

	private boolean isCrosstabHighlightOddRows(DRICrosstab crosstab) {
		if (crosstab.getHighlightOddRows() != null) {
			return crosstab.getHighlightOddRows();
		}
		if (template.getCrosstabHighlightOddRows() != null) {
			return template.getCrosstabHighlightOddRows();
		}
		return Defaults.getDefaults().isCrosstabHighlightOddRows();
	}

	private boolean isCrosstabHighlightEvenRows(DRICrosstab crosstab) {
		if (crosstab.getHighlightEvenRows() != null) {
			return crosstab.getHighlightEvenRows();
		}
		if (template.getCrosstabHighlightEvenRows() != null) {
			return template.getCrosstabHighlightEvenRows();
		}
		return Defaults.getDefaults().isCrosstabHighlightEvenRows();
	}

	protected DRIReportStyle getCrosstabGroupStyle(DRICrosstab crosstab) {
		if (crosstab.getGroupStyle() != null) {
			return crosstab.getGroupStyle();
		}
		if (template.getCrosstabGroupStyle() != null) {
			return template.getCrosstabGroupStyle();
		}
		if (Defaults.getDefaults().getCrosstabGroupStyle() != null) {
			return Defaults.getDefaults().getCrosstabGroupStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getCrosstabGroupTotalStyle(DRICrosstab crosstab) {
		if (crosstab.getGroupTotalStyle() != null) {
			return crosstab.getGroupTotalStyle();
		}
		if (template.getCrosstabGroupTotalStyle() != null) {
			return template.getCrosstabGroupTotalStyle();
		}
		if (Defaults.getDefaults().getCrosstabGroupTotalStyle() != null) {
			return Defaults.getDefaults().getCrosstabGroupTotalStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getCrosstabGrandTotalStyle(DRICrosstab crosstab) {
		if (crosstab.getGrandTotalStyle() != null) {
			return crosstab.getGrandTotalStyle();
		}
		if (template.getCrosstabGrandTotalStyle() != null) {
			return template.getCrosstabGrandTotalStyle();
		}
		if (Defaults.getDefaults().getCrosstabGrandTotalStyle() != null) {
			return Defaults.getDefaults().getCrosstabGrandTotalStyle();
		}
		return getTextStyle();
	}

	protected DRIReportStyle getCrosstabCellStyle(DRICrosstab crosstab) {
		if (crosstab.getCellStyle() != null) {
			return crosstab.getCellStyle();
		}
		if (template.getCrosstabCellStyle() != null) {
			return template.getCrosstabCellStyle();
		}
		if (Defaults.getDefaults().getCrosstabCellStyle() != null) {
			return Defaults.getDefaults().getCrosstabCellStyle();
		}
		return getTextStyle();
	}

	public DRIReportStyle getCrosstabMeasureTitleStyle(DRICrosstab crosstab, DRICrosstabMeasure<?> measure) {
		if (measure.getTitleStyle() != null) {
			return measure.getTitleStyle();
		}
		if (crosstab.getMeasureTitleStyle() != null) {
			return crosstab.getMeasureTitleStyle();
		}
		if (template.getCrosstabMeasureTitleStyle() != null) {
			return template.getCrosstabMeasureTitleStyle();
		}
		return Defaults.getDefaults().getCrosstabMeasureTitleStyle();
	}

	protected CrosstabPercentageType getCrosstabPercentageType(DRICrosstabVariable<?> variable) {
		if (variable.getPercentageType() != null) {
			return variable.getPercentageType();
		}
		return Defaults.getDefaults().getCrosstabPercentageType();
	}

	public BooleanComponentType getBooleanComponentType(DRIBooleanField booleanField) {
		if (booleanField.getComponentType() != null) {
			return booleanField.getComponentType();
		}
		if (template.getBooleanComponentType() != null) {
			return template.getBooleanComponentType();
		}
		return Defaults.getDefaults().getBooleanComponentType();
	}

	public boolean getBooleanEmptyWhenNullValue(DRIBooleanField booleanField) {
		if (booleanField.getEmptyWhenNullValue() != null) {
			return booleanField.getEmptyWhenNullValue();
		}
		if (template.getBooleanEmptyWhenNullValue() != null) {
			return template.getBooleanEmptyWhenNullValue();
		}
		return Defaults.getDefaults().isBooleanEmptyWhenNullValue();
	}

	public int getBooleanImageWidth(DRIBooleanField booleanField) {
		if (booleanField.getImageWidth() != null) {
			return booleanField.getImageWidth();
		}
		if (template.getBooleanImageWidth() != null) {
			return template.getBooleanImageWidth();
		}
		return Defaults.getDefaults().getBooleanImageWidth();
	}

	public int getBooleanImageHeight(DRIBooleanField booleanField) {
		if (booleanField.getImageHeight() != null) {
			return booleanField.getImageHeight();
		}
		if (template.getBooleanImageHeight() != null) {
			return template.getBooleanImageHeight();
		}
		return Defaults.getDefaults().getBooleanImageHeight();
	}

	protected HorizontalImageAlignment getBooleanHorizontalImageAlignment(DRIBooleanField booleanField, DRDesignStyle style) {
		if (booleanField.getHorizontalImageAlignment() != null) {
			return booleanField.getHorizontalImageAlignment();
		}
		if (StyleResolver.getHorizontalImageAlignment(style) != null) {
			return null;//StyleResolver.getHorizontalAlignment(style);
		}
		return Defaults.getDefaults().getBooleanHorizontalImageAlignment();
	}

	public DRIReportStyle getBooleanColumnStyle(DRIBooleanColumn column) {
		if (column.getComponent().getStyle() != null) {
			return column.getComponent().getStyle();
		}
		if (template.getBooleanColumnStyle() != null) {
			return template.getBooleanColumnStyle();
		}
		return Defaults.getDefaults().getBooleanColumnStyle();
	}

	public Map<String, DRIStyle> getTemplateStyles() {
		Map<String, DRIStyle> templateStyles = new HashMap<String, DRIStyle>();
		for (DRIStyle style : template.getTemplateStyles()) {
			templateStyles.put(style.getName(), style);
		}
		for (DRIStyle style : report.getTemplateStyles()) {
			templateStyles.put(style.getName(), style);
		}
		return templateStyles;
	}

	//split
	protected SplitType getTitleSplitType(DRIBand band) {
		return getSplitType(band, template.getTitleSplitType(), Defaults.getDefaults().getTitleSplitType());
	}

	protected SplitType getPageHeaderSplitType(DRIBand band) {
		return getSplitType(band, template.getPageHeaderSplitType(), Defaults.getDefaults().getPageHeaderSplitType());
	}

	protected SplitType getPageFooterSplitType(DRIBand band) {
		return getSplitType(band, template.getPageFooterSplitType(), Defaults.getDefaults().getPageFooterSplitType());
	}

	protected SplitType getColumnHeaderSplitType(DRIBand band) {
		return getSplitType(band, template.getColumnHeaderSplitType(), Defaults.getDefaults().getColumnHeaderSplitType());
	}

	protected SplitType getColumnFooterSplitType(DRIBand band) {
		return getSplitType(band, template.getColumnFooterSplitType(), Defaults.getDefaults().getColumnFooterSplitType());
	}

	protected SplitType getGroupHeaderSplitType(DRIBand band) {
		return getSplitType(band, template.getGroupHeaderSplitType(), Defaults.getDefaults().getGroupHeaderSplitType());
	}

	protected SplitType getGroupFooterSplitType(DRIBand band) {
		return getSplitType(band, template.getGroupFooterSplitType(), Defaults.getDefaults().getGroupFooterSplitType());
	}

	protected SplitType getDetailHeaderSplitType(DRIBand band) {
		return getSplitType(band, template.getDetailHeaderSplitType(), Defaults.getDefaults().getDetailHeaderSplitType());
	}

	protected SplitType getDetailSplitType(DRIBand band) {
		return getSplitType(band, template.getDetailSplitType(), Defaults.getDefaults().getDetailSplitType());
	}

	protected SplitType getDetailFooterSplitType(DRIBand band) {
		return getSplitType(band, template.getDetailFooterSplitType(), Defaults.getDefaults().getDetailFooterSplitType());
	}

	protected SplitType getLastPageFooterSplitType(DRIBand band) {
		return getSplitType(band, template.getLastPageFooterSplitType(), Defaults.getDefaults().getLastPageFooterSplitType());
	}

	protected SplitType getSummarySplitType(DRIBand band) {
		return getSplitType(band, template.getSummarySplitType(), Defaults.getDefaults().getSummarySplitType());
	}

	protected SplitType getNoDataSplitType(DRIBand band) {
		return getSplitType(band, template.getNoDataSplitType(), Defaults.getDefaults().getNoDataSplitType());
	}

	protected SplitType getBackgroundSplitType(DRIBand band) {
		return getSplitType(band, template.getBackgroundSplitType(), Defaults.getDefaults().getBackgroundSplitType());
	}

	private SplitType getSplitType(DRIBand band, SplitType templateSplitType, SplitType splitType) {
		if (band.getSplitType() != null) {
			return band.getSplitType();
		}
		if (templateSplitType != null) {
			return templateSplitType;
		}
		if (splitType != null) {
			return splitType;
		}
		if (template.getDefaultSplitType() != null) {
			return template.getDefaultSplitType();
		}
		return Defaults.getDefaults().getDefaultSplitType();
	}

	//band style
	protected DRIReportStyle getTitleStyle(DRIBand band) {
		return getBandStyle(band, template.getTitleStyle());
	}

	protected DRIReportStyle getPageHeaderStyle(DRIBand band) {
		return getBandStyle(band, template.getPageHeaderStyle());
	}

	protected DRIReportStyle getPageFooterStyle(DRIBand band) {
		return getBandStyle(band, template.getPageFooterStyle());
	}

	protected DRIReportStyle getColumnHeaderStyle(DRIBand band) {
		return getBandStyle(band, template.getColumnHeaderStyle());
	}

	protected DRIReportStyle getColumnFooterStyle(DRIBand band) {
		return getBandStyle(band, template.getColumnFooterStyle());
	}

	protected DRIReportStyle getGroupHeaderStyle(DRIBand band) {
		return getBandStyle(band, template.getGroupHeaderStyle());
	}

	protected DRIReportStyle getGroupFooterStyle(DRIBand band) {
		return getBandStyle(band, template.getGroupFooterStyle());
	}

	protected DRIReportStyle getDetailHeaderStyle(DRIBand band) {
		return getBandStyle(band, template.getDetailHeaderStyle());
	}

	protected DRIReportStyle getDetailStyle(DRIBand band) {
		return getBandStyle(band, template.getDetailStyle());
	}

	protected DRIReportStyle getDetailFooterStyle(DRIBand band) {
		return getBandStyle(band, template.getDetailFooterStyle());
	}

	protected DRIReportStyle getLastPageFooterStyle(DRIBand band) {
		return getBandStyle(band, template.getLastPageFooterStyle());
	}

	protected DRIReportStyle getSummaryStyle(DRIBand band) {
		return getBandStyle(band, template.getSummaryStyle());
	}

	protected DRIReportStyle getNoDataStyle(DRIBand band) {
		return getBandStyle(band, template.getNoDataStyle());
	}

	protected DRIReportStyle getBackgroundStyle(DRIBand band) {
		return getBandStyle(band, template.getBackgroundStyle());
	}

	private DRIReportStyle getBandStyle(DRIBand band, DRIReportStyle templateStyle) {
		if (band.getList().getStyle() != null) {
			return band.getList().getStyle();
		}
		if (templateStyle != null) {
			return templateStyle;
		}
		return Defaults.getDefaults().getBandStyle();
	}

	//band background component
	protected DRIComponent getTitleBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getTitleBackgroundComponent());
	}

	protected DRIComponent getPageHeaderBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getPageHeaderBackgroundComponent());
	}

	protected DRIComponent getPageFooterBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getPageFooterBackgroundComponent());
	}

	protected DRIComponent getColumnHeaderBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getColumnHeaderBackgroundComponent());
	}

	protected DRIComponent getColumnFooterBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getColumnFooterBackgroundComponent());
	}

	protected DRIComponent getGroupHeaderBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getGroupHeaderBackgroundComponent());
	}

	protected DRIComponent getGroupFooterBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getGroupFooterBackgroundComponent());
	}

	protected DRIComponent getDetailHeaderBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getDetailHeaderBackgroundComponent());
	}

	protected DRIComponent getDetailBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getDetailBackgroundComponent());
	}

	protected DRIComponent getDetailFooterBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getDetailFooterBackgroundComponent());
	}

	protected DRIComponent getLastPageFooterBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getLastPageFooterBackgroundComponent());
	}

	protected DRIComponent getSummaryBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getSummaryBackgroundComponent());
	}

	protected DRIComponent getNoDataBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getNoDataBackgroundComponent());
	}

	protected DRIComponent getBackgroundBackgroundComponent(DRIBand band) {
		return getBandBackgroundComponent(band, template.getBackgroundBackgroundComponent());
	}

	private DRIComponent getBandBackgroundComponent(DRIBand band, DRIComponent templateBackgroundComponent) {
		if (band.getList().getBackgroundComponent() != null) {
			return band.getList().getBackgroundComponent();
		}
		if (templateBackgroundComponent != null) {
			return templateBackgroundComponent;
		}
		return Defaults.getDefaults().getBandBackgroundComponent();
	}

	private int detectWidth(DRDesignList designList) {
		ComponentPosition.width(designList);
		return designList.getWidth();
	}

	private int detectHeight(DRDesignList designList) {
		ComponentPosition.height(designList);
		return designList.getHeight();
	}

}
