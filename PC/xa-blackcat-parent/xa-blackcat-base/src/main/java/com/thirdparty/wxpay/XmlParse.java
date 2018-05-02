package com.thirdparty.wxpay;

import java.io.Reader;
import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author hchen
 * @see 字符串解析
 *
 */
public class XmlParse {

	/**
	 * 解析xml字符串,返回根元素对象
	 * @param responseXml
	 * @return
	 * @throws DocumentException
	 */
	public static Element parseXml2RootElement(String responseXml) throws DocumentException{
		SAXReader saxReader = new SAXReader();
		saxReader.setIncludeExternalDTDDeclarations(false);
		saxReader.setIncludeInternalDTDDeclarations(false);
		Reader reader=new StringReader(responseXml);
		Document document = saxReader.read(reader);
		Element root = document.getRootElement();
		return root;
	}
}
