package com.chinaredstar.longyan.util;

import com.xiwa.base.util.StringUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niu on 2016/5/23.
 */
public class ReadExcelUtil {

    //读取小区Excel 单元格数量16
    public  static   List<List<String>>  readCommunityExcel(String filePath)  {
        List<List<String>> dataList =new ArrayList<List<String>>();
        List<String> cellList;
        try {
            //同时支持Excel 2003、2007
            File excelFile = new File(filePath); //创建文件对象
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            //int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 0; s < 1; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    //可能因格式问题,获取cellCount数据有误 所以写死当前的cellCount=15
                    //int cellCount; //获取总列数
                    //cellCount = row.getPhysicalNumberOfCells();
                    //遍历每一列
                    cellList = new ArrayList<String>();
                    for (int c = 0; c < 16; c++) {
                        //当前单元格
                        Cell cell = row.getCell(c);

                        String cellValue ="";

                        int cellType;
                        //因格式问题,可能读到的cell为null,必须进行此处判断,如果cell为null,默认该cell数据为空字符串
                        if (cell!=null){
                            cellType = cell.getCellType();
                            switch(cellType) {
                                case Cell.CELL_TYPE_STRING: //文本
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC: //数字、日期
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                    //对员工编号进行特殊处理
                                    if (c==14){
                                        cellValue=new DecimalFormat("#").format(cell.getNumericCellValue());
                                    }
                                    break;
        /*                        if(DateUtil.isCellDateFormatted(cell)) {
                                   cellValue=cell.getStringCellValue();
                                  *//*  cellValue = fmt.format(cell.getDateCellValue()); //日期型*//*
                                }
                                else {
                                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                                }
                                case Cell.CELL_TYPE_BOOLEAN: //布尔型
                                    cellValue ="";
                                    break;
        */
                                case Cell.CELL_TYPE_BLANK: //空白
                                    cellValue ="";
                                    break;
                                case Cell.CELL_TYPE_ERROR: //错误
                                    cellValue = "";
                                    break;
                                case Cell.CELL_TYPE_FORMULA: //公式
                                    cellValue = "";
                                    break;
                                default:
                                    cellValue = "";
                            }
                        }

                        if(c==0&&StringUtil.isInvalid(cellValue)){
                            break;
                        }else{
                            cellList.add(cellValue);
                        }
                    }

                    if(cellList!=null&&cellList.size()>0){
                        dataList.add(cellList);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(dataList);
        //System.out.println(dataList.size());
        return  dataList;
    }

    //读取住户Excel 单元格数量12
    public  static   List<List<String>>  readMemberExcel(String filePath)  {
        List<List<String>> dataList =new ArrayList<List<String>>();
        List<String> cellList;
        try {
            //同时支持Excel 2003、2007
            File excelFile = new File(filePath); //创建文件对象
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            //int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 0; s < 1; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    //可能因格式问题,获取cellCount数据有误 所以写死当前的cellCount=12
                    //int cellCount; //获取总列数
                    //cellCount = row.getPhysicalNumberOfCells();
                    cellList = new ArrayList<String>();//遍历每一列
                    for (int c = 0; c < 12; c++) {
                        Cell cell = row.getCell(c);
                        String cellValue ="";
                        int cellType;
                        //因格式问题,可能读到的cell为null,必须进行此处判断,如果cell为null,默认该cell数据为空字符串
                        if(cell!=null){
                            cellType=cell.getCellType();
                            switch(cellType) {
                                case Cell.CELL_TYPE_STRING: //文本
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC: //数字、日期
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                    break;
/*                                if (c==15){
                                    cellValue=new DecimalFormat("#").format(cell.getNumericCellValue());
                                }*/

        /*                        if(DateUtil.isCellDateFormatted(cell)) {
                                   cellValue=cell.getStringCellValue();
                                  *//*  cellValue = fmt.format(cell.getDateCellValue()); //日期型*//*
                                }
                                else {
                                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                                }*/

/*                                case Cell.CELL_TYPE_BOOLEAN: //布尔型
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;*/
                                case Cell.CELL_TYPE_BLANK: //空白
                                    cellValue ="";
                                    break;
                                case Cell.CELL_TYPE_ERROR: //错误
                                    cellValue = "";
                                    break;
                                case Cell.CELL_TYPE_FORMULA: //公式
                                    cellValue = "";
                                    break;
                                default:
                                    cellValue = "";
                            }
                        }
                        if(c==0&&StringUtil.isInvalid(cellValue)){
                            break;
                        }else{
                            cellList.add(cellValue);
                        }
                    }

                    if(cellList!=null&&cellList.size()>0){
                        dataList.add(cellList);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(dataList);
        //System.out.println(dataList.size());
        return  dataList;
    }

    public  static  void  main(String [] args) throws Exception {
        readCommunityExcel("d:/community_tpl.xlsx");
    }


}
