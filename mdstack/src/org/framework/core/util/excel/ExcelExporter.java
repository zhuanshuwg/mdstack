package org.framework.core.util.excel;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.framework.core.util.pdf.PdfExporter;

public abstract class ExcelExporter {
	
	private static final Logger log = Logger.getLogger(PdfExporter.class);

	public static final short TITLE_BG = IndexedColors.WHITE.getIndex();
	public static final short TITLE_FG = IndexedColors.BLACK.getIndex();
	
	protected CellStyle cellStyleLeft;
	protected CellStyle cellStyleCenter;
	protected CellStyle cellStyleRight;
	protected CellStyle cellStyleTitle;

	/**
	 * 四分之一字符尺寸 64
	 */
	public static final short SIZE_FACTOR = 64;
	
	/**
	 * 默认行高
	 */
	public static final short ROW_HEIGHT_DEFAULT = SIZE_FACTOR * 6;
	
	/**
	 * 
	 * @param dataList 数据列表
	 * @param response HTTP response
	 * @param fileName 文件名称
	 */
	public void initExcel(HttpServletResponse response, String fileName) {
		
		OutputStream out = null;
		
		if (fileName == null || fileName.trim().equals("")) {
			fileName = "导出文件";
		}
		
		
		try {

			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Connection", "close");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1")
					+ ".xls");

			out = response.getOutputStream();

			HSSFWorkbook wb = new HSSFWorkbook();
			cellStyleLeft = this.createValueStyle(wb, CellStyle.ALIGN_LEFT);
			cellStyleCenter = this.createValueStyle(wb, CellStyle.ALIGN_CENTER);
			cellStyleRight = this.createValueStyle(wb, CellStyle.ALIGN_RIGHT);
			
			cellStyleTitle = this.createValueStyle(wb, CellStyle.ALIGN_CENTER);
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints(new Integer(20).shortValue());
			cellStyleTitle.setFont(font);
			
			setContent(wb);

			wb.write(out);
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param wb
	 * @param sheet
	 * @param dataList
	 */
	public abstract void setContent(HSSFWorkbook wb);
	
	
	
	public void setTitle(Sheet sheet, String title, int rowIndex, int span) {
		Row row = sheet.createRow(rowIndex);
		row.setHeight((short) (10 * SIZE_FACTOR));
		Cell cell = row.createCell(0);
		cell.setCellValue(title);
		cell.setCellStyle(cellStyleTitle);
		
		sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,span));
	}

	
	
	/**
	 * 
	 * @param wb
	 * @return
	 */
	protected CellStyle createValueStyle(org.apache.poi.ss.usermodel.Workbook wb,
			Short align) {
		CellStyle style = wb.createCellStyle();

		if(align == null) {
			align = CellStyle.ALIGN_CENTER;
		} else {
			if (!align.equals(CellStyle.ALIGN_LEFT) 
					&& !align.equals(CellStyle.ALIGN_CENTER) 
					&& !align.equals(CellStyle.ALIGN_RIGHT) ) {
				align = CellStyle.ALIGN_LEFT;
			}
		}
		style.setAlignment(align);
		
		// 边框颜色
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setFillBackgroundColor(IndexedColors.GREY_80_PERCENT.getIndex());

		return style;
	}

	/**
	 * 
	 * @param wb
	 * @return
	 */
	protected CellStyle getHeaderCellStyle(
			org.apache.poi.ss.usermodel.Workbook wb) {
		CellStyle style = wb.createCellStyle();

		// 对齐方式
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 边框颜色
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setFillBackgroundColor(IndexedColors.GREY_80_PERCENT.getIndex());

		// 背景颜色
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// 字体
		Font font = wb.createFont();
		font.setColor(TITLE_FG);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		return style;
	}
	
	/**
	 * 
	 * @param wb
	 * @return
	 */
	protected CellStyle getHeaderCellStyle_(
			org.apache.poi.ss.usermodel.Workbook wb) {
		CellStyle style = wb.createCellStyle();

		// 对齐方式
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 边框颜色
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		style.setFillBackgroundColor(IndexedColors.GREY_80_PERCENT.getIndex());

		// 背景颜色
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// 字体
		Font font = wb.createFont();
		font.setColor(TITLE_FG);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		return style;
	}


}
