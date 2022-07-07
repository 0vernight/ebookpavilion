package com.mike.junit;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * @Author: 23236
 * @createTime: 2022/6/18   20:17
 * @description: xml文档解析
 **/
public class Dom4jTest {

    @Test
    public void test1() throws Exception {
        try {
            SAXReader saxReader = new SAXReader();
            Document read = saxReader.read("pom.xml");
            System.out.println(read);
            Element rootElement = read.getRootElement();
            System.out.println(rootElement);
            for (Element element : rootElement.elements()) {

                System.out.println(element);
                System.out.println(element.getText());
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } finally {
        }

    }

}
