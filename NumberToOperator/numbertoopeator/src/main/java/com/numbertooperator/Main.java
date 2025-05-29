package com.numbertooperator;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) {
        String input = "79001212942";
        String filePath = null;
        // сканнер читающий текст
        if(input.length()==11){
        if(Integer.parseInt(input.substring(1, 4))>=900){
            filePath = "numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\DEF-9xx.xlsx";
        } else if(Integer.parseInt(input.substring(1, 4))>=800){
            filePath = "numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\DEF-8xx.xlsx";
        } else if(Integer.parseInt(input.substring(1, 4))>=400){
            filePath = "numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\DEF-4xx.xlsx";
        } else if(Integer.parseInt(input.substring(1, 4))>=300){
            filePath = "numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\DEF-3xx.xlsx";
        }
                try{
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
                Map<Double, String> range = new HashMap<>();
                XSSFSheet sheet = workbook.getSheetAt(0);
                Row ABCDEFRow = sheet.getRow(1);
                Cell ABCDEFCell = ABCDEFRow.getCell(0);
                Row limitRow = sheet.getRow(1);
                Cell limitCell = limitRow.getCell(2);
                Row operatorRow = sheet.getRow(1);
                Cell operatorCell = operatorRow.getCell(4);
                label:
                for(int i = 0; i<15730;i++){
                    limitRow = sheet.getRow(i+1);
                    if(limitRow != null){
                        ABCDEFRow = sheet.getRow(i+1);
                        operatorRow = sheet.getRow(i+1);
                        limitCell = limitRow.getCell(2);
                        operatorCell = operatorRow.getCell(4);
                        ABCDEFCell = ABCDEFRow.getCell(0);
                        if(ABCDEFCell.getNumericCellValue() == (Double.parseDouble(input.substring(1, 4)))){
                            range.put(limitCell.getNumericCellValue(), operatorCell.getStringCellValue());
                            if(range.keySet().stream().sorted().filter(x->Integer.parseInt(input.substring(4, 11))<=x).limit(1).collect(Collectors.toList()).size()==1){
                                System.out.println(range.keySet().stream().sorted().filter(x->Integer.parseInt(input.substring(4, 11))<=x).limit(1).map(x->range.get(x)).collect(Collectors.toList()));
                                break label;
                            }   // Завершает цикл при нахождении первого нужного оператора. Удобно если проверка используется один раз, при каждой новой input будет проходить через цикл снова.
                        
                      }
                    }   
                }
             



                
                } catch (Exception e) {
                    System.out.println(e);
            }
                
        }
       

    }
}
