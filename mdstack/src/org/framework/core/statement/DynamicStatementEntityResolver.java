package org.framework.core.statement;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 动态sql/hql实体解析器
 */
public class DynamicStatementEntityResolver implements EntityResolver, Serializable{
	
	private static final long serialVersionUID = 2210183181529563553L;
	private static final Logger LOGGER = Logger.getLogger(DynamicStatementEntityResolver.class);
	private static final String DYNAMIC_STATEMENT_URL = "http://www.org/dtd/";

	public InputSource resolveEntity(String publicId, String systemId) {
		InputSource source = null; 
		if ( systemId != null ) {
			LOGGER.debug( "trying to resolve system-id [" + systemId + "]" );
			if ( systemId.startsWith( DYNAMIC_STATEMENT_URL ) ) {
				LOGGER.debug( "recognized dyanmic statement namespace; " +
						"attempting to resolve on classpath under org/framework/core/statement/" );
				source = resolveOnClassPath( publicId, systemId, DYNAMIC_STATEMENT_URL );
			}
		}
		return source;
	}

	private InputSource resolveOnClassPath(String publicId, String systemId, String namespace) {
		InputSource source = null;
		String path = "org/framework/core/statement/" + systemId.substring( namespace.length() );
		InputStream dtdStream = resolveInNamespace( path );
		if ( dtdStream == null ) {
			LOGGER.debug( "unable to locate [" + systemId + "] on classpath" );
		}
		else {
			LOGGER.debug( "located [" + systemId + "] in classpath" );
			source = new InputSource( dtdStream );
			source.setPublicId( publicId );
			source.setSystemId( systemId );
		}
		return source;
	}

	protected InputStream resolveInNamespace(String path) {
		return this.getClass().getClassLoader().getResourceAsStream( path );
	}

}
