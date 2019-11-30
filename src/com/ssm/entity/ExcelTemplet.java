package com.ssm.entity;


import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelTemplet {
	public void export(String[] titles, ServletOutputStream out) throws Exception{
		// 第一步，创建一个workbook，对应一个Excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
			     // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			    HSSFSheet hssfSheet = workbook.createSheet("sheet1");                 
			                    
			                     
			    // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			                     
			    HSSFRow row = hssfSheet.createRow(0);
			    // 第四步，创建单元格，并设置值表头 设置表头居中
			    HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
			                     
			   //居中样式 
			    hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
			         
		         HSSFCell hssfCell = null;
		         for (int i = 0; i < titles.length; i++) {
		             hssfCell = row.createCell(i);//列索引从0开始
		             hssfCell.setCellValue(titles[i]);//列名1
		             hssfCell.setCellStyle(hssfCellStyle);//列居中显示                
		         }
		         try {
	                 workbook.write(out);
	                 out.flush();
	                out.close();
	 
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	}
}
