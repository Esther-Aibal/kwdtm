/**
 * 
 */
package com.movitech.mbox.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * 数组工具类
 * @author felix.jin
 * 2017年8月10日
 */
public class ArrayUtils {
    
	/**
	 * 数组找不同
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <T> List<T> compare(T[] t1, T[] t2) {
	    List<T> list1 = Arrays.asList(t1);
	    List<T> result = new ArrayList<T>();
	    for (T t : t2) {
	      if (!list1.contains(t)) {
	    	  result.add(t);
	      }
	    }
	    return result;
	}
	
	/**
	 * 数组找不同
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <T> List<T> compare( List<T> t1,  List<T> t2) {
	    List<T> result = new ArrayList<T>();
	    for (T t : t2) {
	      if (!t1.contains(t)) {
	    	  result.add(t);
	      }
	    }
	    return result;
	}
    
}
