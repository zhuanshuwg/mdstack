package org.framework.core.util.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class StringUtils implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {		
		String arg0 = args.get(0).toString();
		
		if(arg0.equals("notEmptyForJS")){
			String arg1 = args.get(1).toString();
			if (arg1 != null && !arg1.equals("") && !arg1.equals("null") && !arg1.equals("undefined")) {
				return true;
			}
			return false;
		}
		if(arg0.equals("test")){
			return args.get(1).toString();
		}
		return null;
	}

}
