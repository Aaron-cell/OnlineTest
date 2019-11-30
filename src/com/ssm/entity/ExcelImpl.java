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
	    
		// ��һ��������һ��workbook����Ӧһ��Excel�ļ�
		HSSFWorkbook workbook = new HSSFWorkbook();
	     // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
	    HSSFSheet hssfSheet = workbook.createSheet("sheet1");                 
	                    
	                     
	    // ����������sheet����ӱ�ͷ��0��
	                     
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
	              
         //������ݿ�������
         for (int i = 0; i < list.size(); i++) {
             row = hssfSheet.createRow(i+1);                
             TestGrade testgrade = list.get(i);
             
             // ��������������Ԫ�񣬲�����ֵ
             //�Ծ�id
             int  testid = 0;
             if(testgrade.getTestid() !=0){
                     testid = testgrade.getTestid();
             }
            row.createCell(0).setCellValue(testid);
            //�Ծ�����
             String testname = "";
             if(testgrade.getTestname() != null){
                 testname = testgrade.getTestname();
             }
            row.createCell(1).setCellValue(testname);
            //�û���
             String username = "";
             if(testgrade.getUsername() != null){
                 username=testgrade.getUsername();
             }
             row.createCell(2).setCellValue(username);
             //�ɼ�
             int grade=0;
             if(testgrade.getGrade() !=0){
                 grade=testgrade.getGrade();
             }
             row.createCell(3).setCellValue(grade);
             //�Ƿ����
             String flag="";
             if(testgrade.getFlag()!=null) {
            	 flag=testgrade.getFlag();
             }
             row.createCell(4).setCellValue(flag);
             //�ύʱ��
             Timestamp st=null;
             String submitdate = null;
             // ���߲������ļ�������ͻ��������
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
