package com.ssm.entity;

import java.util.List;

/**
 * Function:ʵ������������г�ȡn�����ظ�������
 * @author Aaron
 * Date:2019.4.28
 */
public class RandomTest {
	
	public RandomTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * ͨ������ʵ��������
	 */
	public String Test(List<Question> list,int count) {
		String libraryid="";
		//count>=list.size
		if(count<=list.size()) {
			for(int i=0;i<count;i++) {
				int index=(int)(Math.random()*list.size());
				libraryid+=list.get(index).getTid()+",";
				//�Ƴ���ѡ������ֵ
				list.remove(index);
			}
		}else {
			libraryid="";
		}
		return libraryid;	
	}
}
