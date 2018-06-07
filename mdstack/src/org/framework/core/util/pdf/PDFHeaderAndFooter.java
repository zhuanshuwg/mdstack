package org.framework.core.util.pdf;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFHeaderAndFooter extends PdfPageEventHelper {
	private static final Logger log = Logger.getLogger(PdfExporter.class);
	
	//页眉
	String header = "";
	//文档字体大小，页眉页脚最好和文本大小一致
	public int presentFontSize = 12;
	//文档页面大小最好前面传入否则默认为A4纸张
	public Rectangle pageSize = PageSize.A4;
	//模板
	PdfTemplate total;
	//基础字体对象
	public BaseFont bf = null;
	
	//利用基础字体生成的字体对象，一般用于生成中文文字
	public Font fontDetail = null;
	
	//无参构造函数
	public PDFHeaderAndFooter() {}
	
	//无参构造函数
	public PDFHeaderAndFooter(String header,int presentFontSize,
			Rectangle pageSize) {
		this.header = header;
		this.presentFontSize = presentFontSize;
		this.pageSize = pageSize;
	}
	

	public void setHeader(String header) {
		this.header = header;
	}

	public void setPresentFontSize(int presentFontSize) {
		this.presentFontSize = presentFontSize;
	}

	public void onOpenDocument(PdfWriter writer, Document document) {

		total = writer.getDirectContent().createTemplate(50, 50);

	}

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			if(bf == null){
				bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			}
			if(fontDetail == null){
				fontDetail = new Font(bf,presentFontSize,Font.NORMAL);
			}
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		/*ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_LEFT, new Phrase(header, fontDetail),
				document.left(), document.top() + 20, 0);*/
		
		int pageNum = writer.getPageNumber();
		String footStr = "第 " + pageNum + " 页/共 " ;
		Phrase footer = new Phrase(footStr,fontDetail);
		float len = bf.getWidthPoint(footStr, presentFontSize);
		PdfContentByte cb = writer.getDirectContent();
		
		ColumnText.showTextAligned(
						cb,
						Element.ALIGN_CENTER,
						footer,
						(document.rightMargin() + document.right()
						+ document.leftMargin() - document.left() - len) / 2.0f + 20f,
						document.bottom() - 20f, 0);
		cb.addTemplate(total, (document.rightMargin() + document.right()
				+ document.leftMargin() - document.left()) / 2.0f + 20f,
				document.bottom() - 20f);
	}
	
	public void onCloseDocument(PdfWriter writer, Document document) {
		total.beginText();
		total.setFontAndSize(bf, presentFontSize);
		String footStr = "" + (writer.getPageNumber() - 1) + " 页"; 
		total.showText(footStr);
		total.endText();
		total.closePath();
	}

}
