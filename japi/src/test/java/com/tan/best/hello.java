package com.tan.best;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

public class hello {

    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("D:\\Job\\repos\\gitRepos\\parent"); // 项目根目录
        config.setProjectName("japi"); // 项目名称
        config.setApiVersion("V1.0");       // 声明该API的版本
        config.setDocsPath("C:\\Users\\Administrator\\Desktop\\apidoc"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        Docs.buildHtmlDocs(config); // 执行生成文档
    }
}
