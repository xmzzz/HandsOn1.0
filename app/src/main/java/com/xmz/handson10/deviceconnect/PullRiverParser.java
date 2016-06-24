package com.xmz.handson10.deviceconnect;

import android.util.Xml;

import com.xmz.handson10.data.River;
import com.xmz.handson10.util.RiverParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinxu on 2016/6/22.
 */
public class PullRiverParser implements RiverParser {
    @Override
    public List<River> parse(InputStream is) throws Exception {
        List<River> rivers = null;
        River river = null;

        XmlPullParser parser = Xml.newPullParser();//由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    rivers = new ArrayList<River>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("river")) {
                        river = new River();
                    } else if (parser.getName().equals("type")) {
                        eventType = parser.next();
                        river.setType(parser.getText());
                    }else if (parser.getName().equals("name")){
                        eventType = parser.next();
                        river.setName(parser.getText());
                    }else if (parser.getName().equals("num")) {
                        eventType = parser.next();
                        river.setNum(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("function1")) {
                        eventType = parser.next();
                        river.setFunction1(parser.getText());
                    } else if (parser.getName().equals("function2")) {
                        eventType = parser.next();
                        river.setFunction2(parser.getText());
                    } else if (parser.getName().equals("function3")) {
                        eventType = parser.next();
                        river.setFunction3(parser.getText());
                    } else if (parser.getName().equals("imageres")) {
                        eventType = parser.next();
                        river.setImageres(parser.getText());
                    } else if (parser.getName().equals("function")) {
                        eventType = parser.next();
                        river.setFunction(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("river")) {
                        rivers.add(river);
                        river = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return rivers;
    }

    @Override
    public String serialize(List<River> rivers) throws Exception {
        XmlSerializer serializer = Xml.newSerializer();//由android.util.Xml创建一个XmlSerializer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);   //设置输出方向为writer
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "river");
        for (River river : rivers) {
            serializer.startTag("", "river");
//            serializer.attribute("","type",river.getType());

            serializer.startTag("", "type");
            serializer.text(river.getType());
            serializer.endTag("", "type");

            serializer.startTag("", "name");
            serializer.text(river.getName());
            serializer.endTag("", "name");

            serializer.startTag("", "num");
            serializer.text(river.getNum() + "");
            serializer.endTag("", "num");

            serializer.startTag("", "function1");
            serializer.text(river.getFunction1());
            serializer.endTag("", "function1");

            serializer.startTag("", "function2");
            serializer.text(river.getFunction2());
            serializer.endTag("", "function=2");

            serializer.startTag("", "function3");
            serializer.text(river.getFunction3());
            serializer.endTag("", "function3");

            serializer.startTag("", "imageres");
            serializer.text(river.getImageres());
            serializer.endTag("", "imageres");

            serializer.startTag("", "function");
            serializer.text(river.getFunction());
            serializer.endTag("", "function");

            serializer.endTag("", "river");
        }
        serializer.endTag("", "rivers");
        serializer.endDocument();
        return writer.toString();
    }
}
