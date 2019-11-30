package com.ssm.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelImpl {
	public void export(String[] titles, ServletOutputStream out,List<TestGrade> list) throws Exception{
	    
		// 第一步，创建一个workbook，对应一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
	     // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	    HSSFSheet hssfSheet = workbook.createSheet("sheet1");                 
	                    
	                     
	    // 第三步，在sheet中添加表头第0行
	                     
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
	              
         //存放数据库中数据
         for (int i = 0; i < list.size(); i++) {
             row = hssfSheet.createRow(i+1);                
             TestGrade testgrade = list.get(i);
             
             // 第六步，创建单元格，并设置值
             //试卷id
             int  testid = 0;
             if(testgrade.getTestid() !=0){
                     testid = testgrade.getTestid();
             }
            row.createCell(0).setCellValue(testid);
            //试卷名称
             String testname = "";
             if(testgrade.getTestname() != null){
                 testname = testgrade.getTestname();
             }
            row.createCell(1).setCellValue(testname);
            //用户名
             String username = "";
             if(testgrade.getUsername() != null){
                 username=testgrade.getUsername();
             }
             row.createCell(2).setCellValue(username);
             //成绩
             int grade=0;
             if(testgrade.getGrade() !=0){
                 grade=testgrade.getGrade();
             }
             row.createCell(3).setCellValue(grade);
             //是否完成
             String flag="";
             if(testgrade.getFlag()!=null) {
            	 flag=testgrade.getFlag();
             }
             row.createCell(4).setCellValue(flag);
             //提交时间
             Timestamp st=null;
             String submitdate = null;
             // 第七步，将文件输出到客户端浏览器
             if(testgrade.getSubmitdate()!=null) {
            	 st=testgrade.getSubmitdate();
            	 submitdate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(st);
             }
             row.createCell(5).setCellValue(submitdate);
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
