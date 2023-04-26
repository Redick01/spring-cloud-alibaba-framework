package io.redick.cloud.code.utils;

import io.redick.cloud.code.GeneratorConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * @author Redick01
 */
public final class XmlParserUtil {

    public static void parse(GeneratorConfig config, String xmlContent) throws DocumentException {
        // 根据xml配置内容生成Document对象
        Document document = DocumentHelper.parseText(xmlContent);
        // url
        List<Element> url = document.getRootElement().elements("url");
        if (url.size() != 1) {
            throw new IllegalArgumentException("xml中url标签未配置或配置错误");
        }
        config.setUrl(url.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // username
        List<Element> username = document.getRootElement().elements("username");
        if (username.size() != 1) {
            throw new IllegalArgumentException("xml中username标签未配置或配置错误");
        }
        config.setUsername(username.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // password
        List<Element> password = document.getRootElement().elements("password");
        if (password.size() != 1) {
            throw new IllegalArgumentException("xml中password标签未配置或配置错误");
        }
        config.setPassword(password.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // author
        List<Element> author = document.getRootElement().elements("author");
        if (author.size() >= 1) {
            config.setAuthor(author.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        }
        // outputDir
        List<Element> outputDir = document.getRootElement().elements("outputDir");
        if (outputDir.size() != 1) {
            throw new IllegalArgumentException("xml中outputDir标签未配置或配置错误");
        }
        config.setOutputDir(outputDir.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // parentPackage
        List<Element> parentPackage = document.getRootElement().elements("parentPackage");
        if (parentPackage.size() != 1) {
            throw new IllegalArgumentException("xml中parentPackage标签未配置或配置错误");
        }
        config.setParentPackage(parentPackage.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // mapperPath
        List<Element> mapperPath = document.getRootElement().elements("mapperPath");
        if (mapperPath.size() != 1) {
            throw new IllegalArgumentException("xml中mapperPath标签未配置或配置错误");
        }
        config.setMapperPath(mapperPath.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        // tablePrefix
        List<Element> tablePrefix = document.getRootElement().elements("tablePrefix");
        if (tablePrefix.size() >= 1) {
            config.setTablePrefix(tablePrefix.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        }
        // service
        List<Element> service = document.getRootElement().elements("service");
        if (service.size() >= 1) {
            config.setService(service.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        }
        // serviceImpl
        List<Element> serviceImpl = document.getRootElement().elements("serviceImpl");
        if (serviceImpl.size() >= 1) {
            config.setServiceImpl(serviceImpl.get(0).getText().replace(" ","").replaceAll("\\s*", ""));
        }
        // tables
        List<Element> tables = document.getRootElement().elements("tables");
        if (tables.size() != 1) {
            throw new IllegalArgumentException("xml中tables标签未配置或配置错误");
        }
        Element element = tables.get(0);
        List<Element> elementList = element.elements();
        List<String> tablesNames = new ArrayList<>();
        elementList.forEach(ts -> tablesNames.add(ts.getText().replace(" ","").replaceAll("\\s*", "")));
        config.setTables(tablesNames);
    }
}
