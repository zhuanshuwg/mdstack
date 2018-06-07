package org.framework.core.common.exception;

public class MyBusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5699403551552280724L;

	public MyBusinessException(){
        super();
    }
    public MyBusinessException(String msg){
        super(msg);
    }

}
