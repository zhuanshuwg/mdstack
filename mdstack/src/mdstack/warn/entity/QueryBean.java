package mdstack.warn.entity;

import java.util.Date;

public class QueryBean {

private  String src_ip; 	//源ip
private  String src_port;	//源端口
private  String dst_ip;		//目的ip
private  String  dst_port;	//目的端口
private  String proto;		//协议
private  String log_source; //报警来源   日志/流量
private  String bk_wt;      //名单类型 bk 黑名单 wt白名单
private  String warn_type;  //报警类别  ip url domain email
private  String s_time;     //检测起始时间
private  String e_time;     //检测结束时间
private  String warn_value; //报警值


public String getSrc_ip() {
	return src_ip;
}

public void setSrc_ip(String src_ip) {
	this.src_ip = src_ip;
}

public String getSrc_port() {
	return src_port;
}

public void setSrc_port(String src_port) {
	this.src_port = src_port;
}

public String getDst_port() {
	return dst_port;
}

public void setDst_port(String dst_port) {
	this.dst_port = dst_port;
}

public String getDst_ip() {
	return dst_ip;
}

public void setDst_ip(String dst_ip) {
	this.dst_ip = dst_ip;
}



public String getProto() {
	return proto;
}

public void setProto(String proto) {
	this.proto = proto;
}

public String getLog_source() {
	return log_source;
}

public void setLog_source(String log_source) {
	this.log_source = log_source;
}

public String getBk_wt() {
	return bk_wt;
}

public void setBk_wt(String bk_wt) {
	this.bk_wt = bk_wt;
}

public String getWarn_type() {
	return warn_type;
}

public void setWarn_type(String warn_type) {
	this.warn_type = warn_type;
}

public String getS_time() {
	return s_time;
}

public void setS_time(String s_time) {
	this.s_time = s_time;
}

public String getE_time() {
	return e_time;
}

public void setE_time(String e_time) {
	this.e_time = e_time;
}

public String getWarn_value() {
	return warn_value;
}

public void setWarn_value(String warn_value) {
	this.warn_value = warn_value;
}





}
