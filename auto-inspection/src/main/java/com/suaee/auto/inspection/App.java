package com.suaee.auto.inspection;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.core.page.Response;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        //自动下载，第一次下载后不会再下载
        try {
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            Browser browser = Puppeteer.launch(options);
            Page page = browser.newPage();
            Response response = page.goTo("https://www.taobao.com/about/");
            System.out.println("status  ==================== " + response.status());
            browser.close();

        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (IOException e) {
        }

    }
}
