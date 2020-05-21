package com.my.studio.configUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.*;

public class XmlConfigReader {
    public static Document getConfig(String path) {
        try {
            FileInputStream in = new FileInputStream(new File(path));
            Reader reader = new InputStreamReader(in, "utf-8");
            SAXReader saxReader = new SAXReader();
            return saxReader.read(reader);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}
