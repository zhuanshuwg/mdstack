package org.framework.core.statement.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.framework.core.statement.DynamicStatementBuilder;
import org.framework.core.statement.DynamicStatementEntityResolver;
import org.hibernate.internal.util.xml.MappingReader;
import org.hibernate.internal.util.xml.OriginImpl;
import org.hibernate.internal.util.xml.XmlDocument;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 默认动态语句组装器
 * @author wangguan
 *
 */
public class DefaultDynamicStatementBuilder implements DynamicStatementBuilder, ResourceLoaderAware {
	
	private static final Logger LOGGER = Logger.getLogger(DefaultDynamicStatementBuilder.class);
	private Map<String, String> hqls;
	private Map<String, String> sqls;
	private String[] mappingResources = new String[0];
	private ResourceLoader resourceLoader;
	private EntityResolver entityResolver = new DynamicStatementEntityResolver();
	/**
	 * 查询语句名称缓存，不允许重复
	 */
	private Set<String> idCache = new HashSet<String>();

	public void setMappingResources(String[] mappingResources) {
		this.mappingResources = mappingResources;
	}

	
	public Map<String, String> getHqls() {
		return hqls;
	}

	public Map<String, String> getSqls() {
		return sqls;
	}

	public void init() throws IOException {
		
		hqls = new HashMap<String, String>();
		sqls = new HashMap<String, String>();
		
		boolean flag = this.resourceLoader instanceof ResourcePatternResolver;
		for (String _resource : mappingResources) {
			if (flag) {
				Resource[] resources = ((ResourcePatternResolver) this.resourceLoader).getResources(_resource);
				buildMap(resources);
			} else {
				Resource resource = resourceLoader.getResource(_resource);
				buildMap(resource);
			}
		}
		idCache.clear();
	}

	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	private void buildMap(Resource[] resources) throws IOException {
		if (resources == null) {
			return ;
		}
		for (Resource resource : resources) {
			buildMap(resource);
		}
	}

	
	private void buildMap(Resource resource) {
		InputSource inputSource = null;
		try {
			inputSource = new InputSource(resource.getInputStream());
			
			
			XmlDocument metadataXml = MappingReader.INSTANCE.readMappingDocument(entityResolver, inputSource,
					new OriginImpl("file", resource.getFilename()));
			if (isDynamicStatementXml(metadataXml)) {
				final Document doc = metadataXml.getDocumentTree();
				final Element dynamicHibernateStatement = doc.getRootElement();
				String namespace = dynamicHibernateStatement.attribute("namespace").getText();
				Iterator rootChildren = dynamicHibernateStatement.elementIterator();
				while (rootChildren.hasNext()) {
					final Element element = (Element) rootChildren.next();
					final String elementName = element.getName();
					if ("sql".equals(elementName)) {
						putStatementToCacheMap(resource,namespace, element, sqls);
					} else if ("hql".equals(elementName)) {
						putStatementToCacheMap(resource,namespace, element, hqls);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.toString());
		} finally {
			if (inputSource != null && inputSource.getByteStream() != null) {
				try {
					inputSource.getByteStream().close();
				} catch (IOException e) {
					LOGGER.error(e.toString());
				}
			}
		}

	}

	private void putStatementToCacheMap(Resource resource,String namespace, final Element element, Map<String, String> statementMap)
			throws IOException {
		String id = element.attribute("id").getText();
		Validate.notEmpty(id);
		
		String uuid = namespace + "." + id;
		
		if (idCache.contains(uuid)) {
			throw new RuntimeException("重复的sql/hql语句定义在文件:" + resource.getURI() + "中，必须保证id的唯一.");
		}
		idCache.add(uuid);
		String queryText = element.getText();
		statementMap.put(uuid, queryText);
	}

	private static boolean isDynamicStatementXml(XmlDocument xmlDocument) {
		return "mapper".equals(xmlDocument.getDocumentTree().getRootElement().getName());
	}
}
