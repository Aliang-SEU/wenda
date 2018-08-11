package com.aliang.wenda.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 12:50
 * @Version 1.0
 **/
@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    private TrieNode rootNode = new TrieNode();

    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "***";

//    public static void main(String[] args) {
//        SensitiveService s = new SensitiveService();
//        s.addWord("色情");
//        s.addWord("赌博");
//        System.out.println(s.filter("你好色 情《, %赌@***博#@@奥术大师 "));
//    }

    /**
     * 敏感词过滤算法
     *
     * @param text
     * @return
     */
    public String filter(String text) {
        //判断文件是否是空
        if(StringUtils.isBlank(text)) {
            return text;
        }
        //结果字符串
        StringBuilder result = new StringBuilder();
        //敏感词替换字符串
        String replacement = DEFAULT_REPLACEMENT;

        TrieNode tempNode = rootNode;
        int begin = 0;  //回滚位置
        int position = 0;   //当前位置

        while(position < text.length()) {
            char c = text.charAt(position);
            //跳过字符
            if(isSymbol(c)) {
                //如果是头结点 则直接越过位置
                if(tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            //寻找下一个子树
            tempNode = tempNode.getSubNode(c);

            // 当前位置的匹配结束
            if(tempNode == null) {
                //以begin字符开头的字符串不是敏感词
                result.append(text.charAt(begin));
                //从下一个字符为头进行测试
                position = begin + 1;
                begin = position;
                //回到树的初始节点
                tempNode = rootNode;
            } else if(tempNode.isKeyWordEnd()) {
                //发现敏感词,从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                position++;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    /**
     * 启动时加载敏感词文件
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineTxt;
            while((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }
            reader.close();

        } catch(Exception e) {
            logger.error("读取铭感词文件失败" + e.getMessage());
        }
    }

    /**
     * 添加一个词
     *
     * @param lineTxt
     */
    private void addWord(String lineTxt) {
        //从根节点往下搜索
        TrieNode tempNode = rootNode;
        //循环每个字符
        for(int i = 0; i < lineTxt.length(); ++i) {
            Character c = lineTxt.charAt(i);
            //过滤敏感词内的空格()
            if(isSymbol(c)) {
                continue;
            }
            //尝试获取节点
            TrieNode node = tempNode.getSubNode(c);

            //找不到节点
            if(node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }

            tempNode = node;
            if(i == lineTxt.length() - 1) {
                // 关键词结束， 设置结束标志
                tempNode.setKeywordEnd(true);
            }

        }

    }

    private class TrieNode {
        //是否关键词的结尾
        private boolean end = false;

        //当前节点下的下一个节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        /**
         * 向指定的位置添加节点树
         *
         * @param key
         * @param node
         */
        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        /**
         * 获取下一个节点
         *
         * @param key
         * @return
         */
        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        /**
         * 判断是否到达树的尾巴
         *
         * @return
         */
        boolean isKeyWordEnd() {
            return end;
        }

        /**
         * 设置树的尾巴
         *
         * @param end
         */
        void setKeywordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    private boolean isSymbol(char c) {
        int ic = (int) c;
        //东亚文字 0x2E80 - 0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);

    }

}
