package Tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ReadExcel {
    /**
     * 读取excel的内容
     * @param path 文件路径
     * @return
     */
    public String[][] Read(String path){

        String[][] result = null;//结果存在二维数组中

        try {
            File excel = new File(path);
            if(excel.isFile() && excel.exists()){
                String[] split = excel.getName().split("\\.");//以分隔符“.”分割文件名得到文件的后缀名
                Workbook workbook;
                if("xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);
                    workbook = new HSSFWorkbook(fis);
                }
                else if ("xlsx".equals(split[1])){
                    workbook = new XSSFWorkbook(excel);
                }
                else if ("txt".equals(split[1])){
                    workbook = null;
                    ReadTxt readTxt = new ReadTxt();
                    result = readTxt.Read(path);
                    return result;
                }
                else {
                    System.out.println("文件类型错误");
                    return null;
                }

                Sheet sheet = workbook.getSheetAt(0);//读取sheet0

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();

                result = new String[lastRowIndex+1][];//动态创建第一维，行数从0开始，到lastRowIndex结束，共有lastRowIndex+1行
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        result[rIndex] = new String[lastCellIndex];//动态创建第二维，列数也从0开始，但最后一列全为0，不需要遍历
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                result[rIndex][cIndex]=cell.toString();
                            }
                        }
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
