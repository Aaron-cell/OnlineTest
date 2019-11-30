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
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{  
        List<List<Object>> list = null;  
          
        //创建Excel工作薄  
        HSSFWorkbook work = new HSSFWorkbook(in);  
        Sheet sheet = null;  
        Row row = null;
        
        Cell cell = null;  
          
        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);
            if(sheet==null)
            {continue;}  
            
            //遍历当前sheet中的所有行  直接从第一行遍历
            for (int j = sheet.getFirstRowNum()+1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j); 
                //遍历所有的列  
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
     * 描述：对表格中数值进行格式化 
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
        case NUMERIC://因为获取不到年月日格式修改
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
            //空值
        case BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value;  
    }  
}
