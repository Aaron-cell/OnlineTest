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
		// ��һ��������һ��workbook����Ӧһ��Excel�ļ�
				HSSFWorkbook workbook = new HSSFWorkbook();
			     // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
			    HSSFSheet hssfSheet = workbook.createSheet("sheet1");                 
			                    
			                     
			    // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short
			                     
			    HSSFRow row = hssfSheet.createRow(0);
			    // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
			    HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
			                     
			   //������ʽ 
			    hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
			         
		         HSSFCell hssfCell = null;
		         for (int i = 0; i < titles.length; i++) {
		             hssfCell = row.createCell(i);//��������0��ʼ
		             hssfCell.setCellValue(titles[i]);//����1
		             hssfCell.setCellStyle(hssfCellStyle);//�о�����ʾ                
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
