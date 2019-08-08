package com.movitech.mbox.modules.wdt.taskStatus.resp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年9月5日 下午3:05:05 类说明
 */
public class TaskReport {

	private int finishInTime;//1
	private int finishOutTime;//2
	private int doingInTime;//3
	private int doingOutTime;//4
	private int other;//5
	
	public TaskReport(List<Map<String, Integer>> list) {
		super();
		if(list!=null&&list.size()>0){
			int a=0;
			for (Map<String, Integer> map : list) {
				a=Integer.valueOf(""+map.get("count"));
				if(1==map.get("status")){
					//按时办结
					this.finishInTime=a;
				}else if(2==map.get("status")){
					//逾期办结
					this.finishOutTime=a;
				}else if(3==map.get("status")){
					//进行中
					this.doingInTime=a;
				}else if(4==map.get("status")){
					//逾期未完成
					this.doingOutTime=a;
				}else if(5==map.get("status")){
					this.other=a;
				}
			}
		}
		
	}
	public TaskReport() {
		super();
	}
	public int getTotal() {
		return finishInTime+finishOutTime+doingInTime+doingOutTime+other;
	}
	public double getFinishRate(){
		int total=getTotal();
		if(0==total){
			return 0;
		}
		double result=(double)((finishInTime+finishOutTime)*100)/getTotal();
		BigDecimal bigDecimal=new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
		double rdouble=bigDecimal.doubleValue();
		return  rdouble;
	}
	public String getRate(){
		double re=getFinishRate();
		if(re==0.0||re==100.0){
			int result=(int)re;
			return  String.valueOf(result);
		}else {
			return  String.valueOf(re);
		}
	}
	public int getFinishInTime() {
		return finishInTime;
	}
	public void setFinishInTime(int finishInTime) {
		this.finishInTime = finishInTime;
	}


	public int getDoingInTime() {
		return doingInTime;
	}
	public void setDoingInTime(int doingInTime) {
		this.doingInTime = doingInTime;
	}


	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}


	public int getFinishOutTime() {
		return finishOutTime;
	}

	public void setFinishOutTime(int finishOutTime) {
		this.finishOutTime = finishOutTime;
	}

	public int getDoingOutTime() {
		return doingOutTime;
	}

	public void setDoingOutTime(int doingOutTime) {
		this.doingOutTime = doingOutTime;
	}
}
