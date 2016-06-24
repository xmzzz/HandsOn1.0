package com.xmz.handson10.util;

import com.xmz.handson10.data.River;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jinxu on 2016/6/22.
 */
public interface RiverParser {
    /*
     * 解析输入流 得到Book对象集合
     */
    public List<River> parse(InputStream is) throws Exception;

    /*
     * 序列化Book对象集合 得到XML形式的字符串
     */
    public String serialize (List<River> rivers) throws Exception;

}
