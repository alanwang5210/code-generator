package com.alan.cloud.codegenerator.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ：10100
 * @date ：2020/5/21 15:18
 * @description：
 */
public class FZ {
    public static void main(String args[]) {
        String content = "[\n" +
                "\t{\n" +
                "\t\t\"type\": \"title\",\n" +
                "\t\t\"text\": \"第一章总则\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"content\",\n" +
                "\t\t\"text\": \"第一条为规范生产安全事故应急预案管理工作，迅速有效处置生产安全事故，依据《中华人民共和国突发事件应对法》、《中华人民共和国安全生产法》等法律和《突发事件应急预案管理办法》(国办发〔2013〕101号)，制定本办法。\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"title\",\n" +
                "\t\t\"text\": \"第二章应急预案的编制\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"content\",\n" +
                "\t\t\"text\": \"第八条应急预案的编制应当符合下列基本要求：\\n\\n（一）有关法律、法规、规章和标准的规定；\\n\\n（二）本地区、本部门、本单位的安全生产实际情况；\\n\\n（三）本地区、本部门、本单位的危险性分析情况；\\n\\n（四）应急组织和人员的职责分工明确，并有具体的落实措施；\\n\\n（五）有明确、具体的应急程序和处置措施，并与其应急能力相适应；\\n\\n（六）有明确的应急保障措施，满足本地区、本部门、本单位的应急工作需要；\\n\\n（七）应急预案基本要素齐全、完整，应急预案附件提供的信息准确；\\n\\n（八）应急预案内容与相关应急预案相互衔接。\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"type\": \"contact\",\n" +
                "\t\t\"text\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\": \"9dac7d088e3918c22c944bb9761478af\",\n" +
                "\t\t\t\t\"name\": \"张三\",\n" +
                "\t\t\t\t\"duty\": \"局长\",\n" +
                "\t\t\t\t\"telNumber\": \"15768499605\",\n" +
                "\t\t\t\t\"phoneNumber\":\"666666666\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "]";

        JSONArray array = JSON.parseArray(content);
        for (Object c : array) {
            JSONObject o = (JSONObject) c;
            o.put("flag", "111111111");
            if (StringUtils.equals("contact", o.getString("type"))) {
                JSONArray contacts = o.getJSONArray("text");
                contacts.forEach(b -> {
                    JSONObject v = (JSONObject) b;
                    v.put("oooooo", "111111111");
                });
            }
        }
        System.out.println();
    }
}
