package com.owen.file;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfTwo {
	private static Font headfont;// 设置字体大小
	private static Font keyfont;// 设置字体大小
	private static Font textfont;// 设置字体大小

	static {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			headfont = new Font(bfChinese, 14, Font.BOLD);// 设置字体大小
			keyfont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
			textfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    int maxWidth = 520;  


	public void writePdf(String title) throws Exception {
		// 1.新建document对象
		// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		
		// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
		// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("e:\\ITextTest.pdf"));
		
		// 3.打开文档
		document.open();
		
		// 4.向文档中添加内容
		// 通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
		Paragraph pt = new Paragraph( title, headfont);// 设置字体样式
		pt.setAlignment(1);// 设置文字居中 0靠左 1，居中 2，靠右
		document.add(pt);
		document.add(new Paragraph("\n"));
		
	/*	String content = "诊断时间：20150125 11:35:20" + "\t\t\t\t\t\t" + "初诊/复诊：复诊";
		pt = new Paragraph( content, textfont);// 设置字体样式
		pt.setAlignment(1);// 设置文字居中 0靠左 1，居中 2，靠右
		document.add(pt);
		document.add(new Paragraph("\n"));
		
		content = "登录人姓名：wzb" + "\t\t\t\t\t\t" + "病人姓名：张明";
		pt = new Paragraph( content, textfont);// 设置字体样式
		pt.setAlignment(1);// 设置文字居中 0靠左 1，居中 2，靠右
		document.add(pt);
		document.add(new Paragraph("\n"));*/
		
		//创建一表格
		PdfPTable table = this.createTable(4);  
		
		//第一行
		table.addCell(createCell("诊断时间：", keyfont, 2));
		table.addCell(createCell("20150125 11:35:20", textfont, 0));
		table.addCell(createCell("初诊/复诊：", keyfont, 2));
		table.addCell(createCell("初诊", textfont, 0));
		
		//第二行
		table.addCell(createCell("登录人姓名：", keyfont, 2));
		table.addCell(createCell("张明", textfont, 0));
		table.addCell(createCell("病人姓名：", keyfont, 2));
		table.addCell(createCell("张明病人", textfont, 0));
		
		//第三行
		table.addCell(createCell("病人性别：", keyfont, 2));
		table.addCell(createCell("男性", textfont, 0));
		table.addCell(createCell("病人年龄：", keyfont, 2));
		table.addCell(createCell("34", textfont, 0));
		
		//第四行
		table.addCell(createCell("主诉：", keyfont, 2));
		table.addCell(createCell("xxxxxxxxzxcvsdfdxzcvcvxcvvcx", textfont, 0,3));
		
		//第五行
		table.addCell(createCell("病史与检查：", keyfont, 2));
		table.addCell(createCell("棒棒棒棒棒棒棒棒棒棒棒棒棒棒棒棒棒棒", textfont, 0,3));
		
		//第六行
		table.addCell(createCell("输入的症状：", keyfont, 2));
		table.addCell(createCell("囟凸（自填）,羞明红肿,鼻塞,项强", textfont, 0,3));
		
		//第七行
		table.addCell(createCell("主要症状：", keyfont, 2));
		table.addCell(createCell("", textfont, 0,3));
	  
		//第八行
		table.addCell(createCell("（内）病位：", keyfont, 2));
		table.addCell(createCell("津液，心气", textfont, 0));
		table.addCell(createCell("（外）病位：", keyfont, 2));
		table.addCell(createCell("34", textfont, 0));
		
		//第九行
		table.addCell(createCell("（内）病因：", keyfont, 2));
		table.addCell(createCell("心血", textfont, 0));
		table.addCell(createCell("（外）病因：", keyfont, 2));
		table.addCell(createCell("（外）外", textfont, 0));
		
		//第十行
		table.addCell(createCell("处方：", keyfont, 2));
		table.addCell(createCell("木香10g,紫菀10g,大黄30g,大黄（后下）12g,山药6g,鸡子黄1个", textfont, 0,3));
		
		
		//第十一行
		table.addCell(createCell("注意事项：", keyfont, 2));
		table.addCell(createCell("（用法及禁忌：3剂， 每日1剂 ，中火水煎 ，温服， 忌食生冷腥辣油腻）", textfont, 0,3));
		
		table.completeRow();
		document.add(table);
        //table.addCell(createCell("学生信息列表：", keyfont,Element.ALIGN_LEFT,4,false));    
                
//        table.addCell(createCell("姓名", keyfont, Element.ALIGN_CENTER));    
//        table.addCell(createCell("年龄", keyfont, Element.ALIGN_CENTER));    
//        table.addCell(createCell("性别", keyfont, Element.ALIGN_CENTER));    
//        table.addCell(createCell("住址", keyfont, Element.ALIGN_CENTER)); 
		
     	//table.addCell(createCell("姓名", keyfont, Element.ALIGN_CENTER));
		
		/*pt = new Paragraph(createTime + "\t\t\t\t\t\t" + authorName, keyfont);
		pt.setAlignment(2);
		document.add(pt);
		document.add(new Paragraph("\n"));
		
		document.add(new Paragraph(createTime + "\t\t\t\t\t\t" + authorName,keyfont));
		document.add(new Paragraph("\n"));
		
		document.add(new Paragraph(
				"Some more text on the 胜多负少的身份的分公司的风格发的电饭锅的分公司的分公司的的分公司电饭锅是的分公司的风格的分公司的分公司的复合弓好几顿饭发的寡鹄单凫过好地方风格和的发干活的风格和发干活的风格和地方过电饭锅好地方干活的风格和电饭锅好地方干活负少的身份的分公司的风格发的电饭锅的分公司的分公司的的分公司电饭锅是的分公司的风格的分公司的分公司的复合弓好几顿饭发的寡鹄单凫过好地方风格和的发干活的风格和发干活的风格和地方过电饭锅好地方干活的风格和电饭锅好地方干活负少的身份的分公司的风格发的电饭锅的分公司的分公司的的分公司电饭锅是的分公司的风格的分公司的分公司的复合弓好几顿饭发的寡鹄单凫过好地方风格和的发干活的风格和发干活的风格和地方过电饭锅好地方干活的风格和电饭锅好地方干活的风格和符合斯蒂夫 first page with different color andsdfsadfffffffffffffffffffffffffff font type.",
				keyfont));*/
		// 5.关闭文档
		document.close();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		PdfTwo ppt = new PdfTwo();
		ppt.writePdf("诊断结果输出");
		System.out.println("end");
	}
	
    /**
     * 创建表格  (设置内容存放位置)
     * @param value 内容
     * @param font  字体
     * @param align  位置
     * @return
     */
    public PdfPCell createCell(String value,Font font,int align){    
        PdfPCell cell = new PdfPCell();    
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);            
        cell.setHorizontalAlignment(align);        
        cell.setPhrase(new Phrase(value,font));  
        
        //设置边框为白色,相当于隐藏
        cell.setBorderColor(BaseColor.WHITE);
        cell.setUseBorderPadding(true);
        
        //设置表格差距
        cell.setBorderWidthTop(5f);
        
        return cell;    
    }    
       
    /**
     * 创建表格  (设置内容存放位置 默认中间)
     * @param value 内容
     * @param font  字体
     * @return
     */
    public PdfPCell createCell(String value,Font font){    
        PdfPCell cell = new PdfPCell();    
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);     
        cell.setPhrase(new Phrase(value,font));    
       return cell;    
   }    
   
    /**
     * 创建表格  (设置内容存放位置)  跨度
     * @param value 内容
     * @param font  字体
     * @param align  位置
     * @param colspan 跨度
     * @return
     */
   public PdfPCell createCell(String value,Font font,int align,int colspan){    
        PdfPCell cell = new PdfPCell();    
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
        cell.setHorizontalAlignment(align);        
        cell.setColspan(colspan);    
        cell.setPhrase(new Phrase(value,font));   
        
        //设置边框为白色,相当于隐藏
        cell.setBorderColor(BaseColor.WHITE);
        cell.setUseBorderPadding(true);
        
        //设置表格差距
        cell.setBorderWidthTop(5f);
        
       return cell;    
   }    
   
   /**
    * 
    * 创建表格  (设置内容存放位置)  跨度
     * @param value 内容
     * @param font  字体
     * @param align  位置
    * @param colspan  跨度
    * @param boderFlag 表格样式
    * @return
    */
   public PdfPCell createCell(String value,Font font,int align,int colspan,boolean boderFlag){    
        PdfPCell cell = new PdfPCell();    
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
        cell.setHorizontalAlignment(align);        
        cell.setColspan(colspan);    
        cell.setPhrase(new Phrase(value,font));    
        cell.setPadding(3.0f);    
        if(!boderFlag){    
            cell.setBorder(0);    
            cell.setPaddingTop(15.0f);    
            cell.setPaddingBottom(8.0f);    
        }    
       return cell;    
   }    
   
   /**
    * 创建表格
    * @param colNumber
    * @return
    */
   public PdfPTable createTable(int colNumber){    
       PdfPTable table = new PdfPTable(colNumber);    
       try{    
           table.setTotalWidth(maxWidth);    
           table.setLockedWidth(true);    
           table.setHorizontalAlignment(Element.ALIGN_CENTER);         
           table.getDefaultCell().setBorder(0);  
           
           
          // table.getDefaultCell().setBorder(0);    
           //table.addCell(createCell("", keyfont));             
          /* table.setSpacingAfter(20.0f);    
           table.setSpacingBefore(20.0f); */
           
       }catch(Exception e){    
           e.printStackTrace();    
       }    
       return table;    
   }   
   
   /**
    * 
    * @param widths
    * @return
    */
	public PdfPTable createTable(float[] widths){    
	       PdfPTable table = new PdfPTable(widths);    
	       try{    
	           table.setTotalWidth(maxWidth);    
	           table.setLockedWidth(true);    
	           table.setHorizontalAlignment(Element.ALIGN_CENTER);         
	           table.getDefaultCell().setBorder(1);    
	       }catch(Exception e){    
	           e.printStackTrace();    
	       }    
	       return table;    
	   }    
       
	/**
	 * 创建表格
	 * @return
	 */
    public PdfPTable createBlankTable(){    
        PdfPTable table = new PdfPTable(1);    
        table.getDefaultCell().setBorder(0);    
        table.addCell(createCell("", keyfont));             
        table.setSpacingAfter(20.0f);    
        table.setSpacingBefore(20.0f);    
        return table;    
    }  
	
}




//int maxWidth = 520; 

//public void writeSimplePdf() throws Exception {
//	// 1.新建document对象
//	// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
//	Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//	// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
//	// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
//	PdfWriter writer = PdfWriter.getInstance(document,
//			new FileOutputStream("C:\\ITextTest.pdf"));
//	// 3.打开文档
//	document.open();
//	// 4.向文档中添加内容
//	// 通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
//	document.add(new Paragraph("First page of the document."));
//	document.add(new Paragraph(
//			"Some more text on the first page with different color and font type.",
//			textfont));
//	
//	//Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
//	
//	// 5.关闭文档
//	document.close();
//}
//
//public void writeCharpter() throws Exception {
//	// 新建document对象 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
//	Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//	// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
//	PdfWriter writer = PdfWriter.getInstance(document,
//			new FileOutputStream("c:\\ITextTest.pdf"));
//	// 打开文件
//	document.open();
//	// 标题
//	document.add(new Paragraph("\n11111111111111111111111111111111111111"));
//
//	document.addTitle("Hello mingri example");
//	// 作者
//	document.addAuthor("wolf");
//	// 主题
//	document.addSubject("This example explains how to add metadata.");
//	document.addKeywords("iText, Hello mingri");
//	document.addCreator("My program using iText");
//	// document.newPage();
//	// 向文档中添加内容
//	document.add(new Paragraph(
//			"\n22222222222222222222222222222222222222222222222222222222222222222"));
//	document.add(new Paragraph("\n"));
//	document.add(new Paragraph("\n"));
//	document.add(new Paragraph("\n"));
//	document.add(new Paragraph("\n"));
//	document.add(new Paragraph("\n"));
//	document.add(new Paragraph("First page of the document.", keyfont));
//	document.add(new Paragraph("First page of the document."));
//	document.add(new Paragraph("First page of the document."));
//	document.add(new Paragraph("First page of the document."));
//	document.add(new Paragraph(
//			"Some more text on the first page with different color and font type.",
//			textfont));
//	Paragraph title1 = new Paragraph("Chapter 1", textfont);
//	// 新建章节
//	Chapter chapter1 = new Chapter(title1, 1);
//	chapter1.setNumberDepth(0);
//	Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
//			textfont);
//	Section section1 = chapter1.addSection(title11);
//	Paragraph someSectionText = new Paragraph(
//			"This text comes as part of section 1 of chapter 1.");
//	section1.add(someSectionText);
//	someSectionText = new Paragraph("Following is a 3 X 2 table.");
//	section1.add(someSectionText);
//	document.add(chapter1);
//	// 关闭文档
//	document.close();
//}
