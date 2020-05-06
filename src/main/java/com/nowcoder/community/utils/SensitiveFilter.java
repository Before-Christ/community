package com.nowcoder.community.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符
    private static final String REPLACEMENT = "* * *";

    //根节点
    private TrieNode rootNode = new TrieNode();
    //spring的生命周期了解一下
    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-word.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        ) {
            String keyword;
            while ((keyword = reader.readLine())  != null){
                this.addKeyword(keyword);
            }

        }catch (IOException e){
            logger.error("加载敏感词文件失败" + e.getMessage());
        }
    }
    //将一个敏感词加入前缀树
    public void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            //初始化子节点
            if (subNode == null){
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            //指针指向子节点
            tempNode = subNode;
            //最后一个字符，设置标志
            if (i == keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     *
     * @param text : 需要过滤的文本
     * @return ： 过滤后的文本
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }
        //指针用来遍历
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        final StringBuilder sb = new StringBuilder();
        //遍历
        while (position < text.length()){
            char c = text.charAt(position);
            //跳过符号
            if (isSymbol(c)){
                //如果指针tempNode是根节点，将此字符计入结果
                if (tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                //无论符号在开头还是中间，此指针都往前走
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                //进入下一个位置
                position = ++begin;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd){
                //发现敏感词
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            }else {
                position++;
            }
        }
        //将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    //判断是否是符号
    private boolean isSymbol(Character c){
        return CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class TrieNode{
        //关键词结束标识
        private boolean isKeywordEnd = false;

        //子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        //添加子节点
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c, node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
