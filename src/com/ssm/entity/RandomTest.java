package com.ssm.entity;

import java.util.List;

/**
 * Function:实现随机从数组中抽取n个不重复的数字
 * @author Aaron
 * Date:2019.4.28
 */
public class RandomTest {
	
	public RandomTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * 通过集合实现随机组卷
	 */
	public String Test(List<Question> list,int count) {
		String libraryid="";
		//count>=list.size
		if(count<=list.size()) {
			for(int i=0;i<count;i++) {
				int index=(int)(Math.random()*list.size());
				libraryid+=list.get(index).getTid()+",";
				//移除所选的索引值
				list.remove(index);
			}
		}else {
			libraryid="";
		}
		return libraryid;	
	}
}
