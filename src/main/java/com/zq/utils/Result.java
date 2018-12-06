package com.zq.utils;

import java.util.HashMap;
import java.util.Map;

public class Result {
	 private static Map<String,Object> map=new HashMap<String,Object>();
	   
	   public static Map<String,Object> toMap(int count,String message){
		   if(count>0){
			   
			   map.put("success", true);
			   map.put("message", message+"成功");
			  
		   }else{
			   
			   map.put("success", false);
			   map.put("message", message+"失败");
		   }
		   return map;
	   }
	   
	   
	   public static Map<String,Object> toRemark(String remark){
		         map.put("success", false);
			   map.put("remark", "温馨提示："+remark);
		  
		   return map;
	   }
}
