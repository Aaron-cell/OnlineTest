package com.ssm.entity;

import java.io.IOException;  
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;    
  
  
public class ExcelImport {          
    /** 
     * ��������ȡIO���е����ݣ���װ��List<List<Object>>���� 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{  
        List<List<Object>> list = null;  
          
        //����Excel������  
        HSSFWorkbook work = new HSSFWorkbook(in);  
        Sheet sheet = null;  
        Row row = null;
        
        Cell cell = null;  
          
        list = new ArrayList<List<Object>>();  
        //����Excel�����е�sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);
            if(sheet==null)
            {continue;}  
            
            //������ǰsheet�е�������  ֱ�Ӵӵ�һ�б���
            for (int j = sheet.getFirstRowNum()+1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j); 
                //�������е���  
                List<Object> li = new ArrayList<Object>();  
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    	cell = row.getCell(y);
                    	li.add(this.getCellValue(cell)); 
                   
                }  
                list.add(li);  
            }  
        } 
        in.close();  
        return list;  
    }  

	/** 
     * �������Ա������ֵ���и�ʽ�� 
     * @param cell 
     * @return 
     */  
    public  Object getCellValue(Cell cell){  
        Object value = null;  
        if(cell==null) {
        	return "";
        }
        switch (cell.getCellType()) {  
        case STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case NUMERIC://��Ϊ��ȡ���������ո�ʽ�޸�
	        if (HSSFDateUtil.isCellDateFormatted(cell)) {
	            Date date = cell.getDateCellValue();
	            value = DateFormatUtils.format(date, "yyyy-MM-dd");
            } else {
	            value = cell.getNumericCellValue();
	            DecimalFormat ddf = new DecimalFormat("0");
	            value = ddf.format(value);
            }
            break; 
        case BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;
            //��ֵ
        case BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value;  
    }  
}
