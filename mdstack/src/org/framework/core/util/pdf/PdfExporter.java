package org.framework.core.util.pdf;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class PdfExporter {

	private static final Logger log = Logger.getLogger(PdfExporter.class);

	public PdfExporter() {

	}

	/**
	 * @param response
	 * @param fileName
	 */
	public void initPdf(HttpServletResponse response, String fileName) {

		OutputStream out = null;

		if (fileName == null || fileName.trim().equals(""))
			fileName = "导出文件";

		try {
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Connection", "close");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1")
					+ ".pdf");

			out = response.getOutputStream();

			Document document = new Document(PageSize.A4.rotate(), 40, 40, 25,
					25);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
			pdfWriter.setPageEvent(new PDFHeaderAndFooter());
			document.open();
			// 设置PDF内容
			setContent(document);
			document.close();
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	public abstract void setContent(Document document);

	public void setTitle(Document document, String title) {

		Font contentFont = new Font(getFontChinese(), 16, Font.BOLD);
		try {
			Paragraph p = new Paragraph(title, contentFont);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(new Phrase(""));
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param content
	 * @param font
	 * @param backgroundColor
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @return
	 */
	protected PdfPCell createCell(String content, Font font,
			BaseColor backgroundColor, int horizontalAlignment,
			int verticalAlignment) {
		PdfPCell cell = new PdfPCell();
		if (backgroundColor != null) {
			cell.setBackgroundColor(backgroundColor);
		}
		cell.setPhrase(new Phrase(content, font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(verticalAlignment);
		return cell;
	}

	/**
	 * 
	 * @param content
	 * @param font
	 * @param backgroundColor
	 * @return
	 */
	protected PdfPCell createCenterCell(String content, Font font,
			BaseColor backgroundColor) {
		PdfPCell cell = new PdfPCell();
		if (backgroundColor != null) {
			cell.setBackgroundColor(backgroundColor);
		}
		cell.setPhrase(new Phrase(content, font));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		return cell;
	}

	/**
	 * 
	 * @return
	 */
	protected BaseFont getFontChinese() {
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return bfChinese;
	}
}
