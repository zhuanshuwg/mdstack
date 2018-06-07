package org.framework.core.statement;

import freemarker.template.Template;

/**
 * 语句模板处理类
 * @author wangguan
 * @version 1.0
 */
public class Statement {
	
	
	/**
	 * 语句类型
	 */
	private Type type;
	private Template template;
	
	/**
	 * 用枚举定义查询方式
	 */
	public static enum Type{
		HQL,
		SQL
	} 
	
	public Statement(){
		
	}
	
	public Statement(Type type,Template template){
		this.type = type;
		this.template = template;
	}
	
	public Type getType(){
		return type;
	}
	
	public Template getTemplate(){
		return template;
	}
	
	
	public static void main(String[] args){
		
	}
}
