package com.lcy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class XMLParseAndProduce{
	public static void main(String[] args) {
//		writeXML();
		String pathName = "E:\\javatest\\TempCoding\\src\\com\\lcy\\output1.xml";
//		writeXMLJDOM(pathName);
//		readXMLJDOM(pathName);
		writeXMLDOM4J(pathName);
		readXMLDOM4J(pathName);
	}
	
	//DOM读取XML
	public static void readXMLDOM(String pathName) {
		//建立DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			//建立DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			//建立Document
			Document document = builder.parse(pathName);
			//建立NodeList
			NodeList nodeList = document.getElementsByTagName("linkman");
			for (int i = 0; i < nodeList.getLength(); i++) {
				org.w3c.dom.Element element = (org.w3c.dom.Element) nodeList.item(i);
				System.out.println("姓名:" + element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				System.out.println("Email:" + element.getElementsByTagName("email").item(0).getFirstChild().getNodeValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//DOM编写XML
	public static void writeXMLDOM(String pathName) {
		//建立DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//建立DocumentBuilder
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//定义Document接口对象，通过DocumentBuilder类进行DOM树的转换操作
		Document document = builder.newDocument();
		//建立各个操作节点
		Element addresslist = document.createElement("addresslist");
		Element linkman = document.createElement("linkman");
		Element name = document.createElement("name");
		Element email = document.createElement("email");
		//设置节点的文本内容
		name.appendChild(document.createTextNode("lixinghua"));
		email.appendChild(document.createTextNode("123@123.com"));
		//设置节点关系
		linkman.appendChild(name);
		linkman.appendChild(email);
		addresslist.appendChild(linkman);
		document.appendChild(addresslist);
		//输出文档到文件中
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		//创建TransFormer
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置编码类型
		transformer.setOutputProperty(OutputKeys.ENCODING, "GBK");
		//设置输出文档
		DOMSource source = new DOMSource(document);
		//指定输出位置
		StreamResult result = new StreamResult(new File(pathName ));
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//SAX读取XML
	public static void readXMLSAX(String pathName) {
		//建立SAXParserFactory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//建立SAXParser
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			parser.parse(pathName, new MySAX());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//JDOM生成XML文件
	public static void writeXMLJDOM(String pathName) {
		//定义各种类型节点
		org.jdom2.Element addresslist = new org.jdom2.Element("addresslist");
		org.jdom2.Element linkman = new org.jdom2.Element("linkman");
		org.jdom2.Element name = new org.jdom2.Element("name");
		org.jdom2.Element email = new org.jdom2.Element("email");
		//定义相关属性
		Attribute idAttribute = new Attribute("id",	"lxh");
		//声明一个Document对象，参数是根节点名称
		org.jdom2.Document document = new org.jdom2.Document(addresslist);
		//设置节点内容和相关属性
		name.setText("lichangying");
		email.setText("licy0210@126.com");
		name.setAttribute(idAttribute);
		//设置节点间相互关系
		linkman.addContent(name);
		linkman.addContent(email);
		addresslist.addContent(linkman);
		//建立XMLOutputter对象
		XMLOutputter outputter = new XMLOutputter();
		//设置输出的编码
		outputter.setFormat(outputter.getFormat().setEncoding("GBK"));
		//调用XMLOutputter类的output()函数将内容写入XML文件
		try {
			outputter.output(document, new FileOutputStream(pathName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//JDOM读取XML文件
	public static void readXMLJDOM(String pathName) {
		//建立SAXBuilder对象
		SAXBuilder builder = new SAXBuilder();
		//通过SAXBuilder对象的build()方法取得一个Document对象
		org.jdom2.Document document = null;
		try {
			document = builder.build(pathName);
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//通过Document对象的getRootElement()方法读取根元素Element
		org.jdom2.Element rootElement = document.getRootElement();
		//获取子元素列表
		List list = rootElement.getChildren("linkman");
		for (int i = 0; i < list.size(); i++) {
			org.jdom2.Element element = (org.jdom2.Element) list.get(i);
			String name = element.getChildText("name");
			String email = element.getChildText("email");
			String idString = element.getChild("name").getAttribute("id").getValue();
			System.out.println("Name:" + name + ",id: " + idString);
			System.out.println("email:" + email);
		}
	}
	
	//DOM4J生成XML文件
	public static void writeXMLDOM4J(String pathName) {
		//创建文档对象Document
		org.dom4j.Document document = DocumentHelper.createDocument();
		//定义父节点
		org.dom4j.Element addresslist = document.addElement("addresslist");
		//定义各个子节点
		org.dom4j.Element linkman = addresslist.addElement("linkman");
		org.dom4j.Element name = linkman.addElement("name");
		org.dom4j.Element email = linkman.addElement("email");
		//设置节点内容
		name.setText("lichangying");
		email.setText("licy0210@126.com");
		//设置输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		//设置输出编码
		format.setEncoding("GBK");
		//建立一个XML文档
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(pathName)),format);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//向XML文件中输出内容
		try {
			writer.write(document);
			//关闭输出流
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readXMLDOM4J(String pathName) {
		//建立SAX解析读取
		SAXReader reader = new SAXReader();
		//读取文档
		org.dom4j.Document document = null;
		try {
			document = reader.read(new File(pathName));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取根节点
		org.dom4j.Element rootElement = document.getRootElement();
		//获取子节点列表
		Iterator<org.dom4j.Element> iterator = rootElement.elementIterator();
		while (iterator.hasNext()) {
			org.dom4j.Element element = iterator.next();
			String name = element.elementText("name");
			String email = element.elementText("email");
			System.out.println("Name:" + name + ", email:" + email);
		}
	}
}
