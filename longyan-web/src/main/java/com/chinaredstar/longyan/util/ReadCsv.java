package com.chinaredstar.longyan.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangj on 2015/7/30.
 */
@Component("readCsv")
public class ReadCsv {

    private InputStreamReader fr = null;
    private BufferedReader br = null;

    public List<List<String>> readCSVFile(String f) throws IOException {
        fr = new InputStreamReader(new FileInputStream(f), "utf-8");
        br = new BufferedReader(fr);
        String rec = null;// 一行
        String str;// 一个单元格
        List<List<String>> listFile = new ArrayList<List<String>>();
        try {
            // 读取一行
            while ((rec = br.readLine()) != null) {
                rec = rec + ",";
                Pattern pCells = Pattern
                        .compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
                Matcher mCells = pCells.matcher(rec);
                List<String> cells = new ArrayList<String>();// 每行记录一个list
                // 读取每个单元格
                while (mCells.find()) {
                    str = mCells.group();
                    str = str.replaceAll(
                            "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
                    str = str.replaceAll("(?sm)(\"(\"))", "$2");
                    cells.add(str);
                }
                listFile.add(cells);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                fr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return listFile;
    }
}
