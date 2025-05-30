package com.numbertooperator;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) {
        Map<Double, String> range = new TreeMap<>();
        readNumbers("numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\DEF-9xx.xlsx", range);
        readNumbers("numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\ABC-8xx.xlsx", range);
        readNumbers("numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\ABC-4xx.xlsx", range);
        readNumbers("numbertoopeator\\\\src\\\\main\\\\java\\\\com\\\\numbertooperator\\\\ABC-3xx.xlsx", range);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Введите номер телефона в формате 79005551122. Для выхода ввести 'q'");
            String input = sc.nextLine();
            if(input.toUpperCase().equals("Q")){
                break;
            }
            String ABCDEF = input.substring(1, 4);
            label:
            if(input.length()==11 && !input.matches(".*\\D.*")){
                    findOperator(range, input, ABCDEF);
                    break label;
            }
        }
    } 
    public static void readNumbers(String filePath, Map<Double,String> range){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row ABCDEFRow = sheet.getRow(1);
            Cell ABCDEFCell = ABCDEFRow.getCell(0);
            Row limitRow = sheet.getRow(1);
            Cell limitCell = limitRow.getCell(2);
            Row operatorRow = sheet.getRow(1);
            Cell operatorCell = operatorRow.getCell(4);
            for(int i = 0; i<300000;i++){
                limitRow = sheet.getRow(i+1);
                if(limitRow == null){
                    break;
                }
                else{
                    ABCDEFRow = sheet.getRow(i+1);
                    operatorRow = sheet.getRow(i+1);
                    limitCell = limitRow.getCell(2);
                    operatorCell = operatorRow.getCell(4);
                    ABCDEFCell = ABCDEFRow.getCell(0);
                    if(((int)limitCell.getNumericCellValue() + "").length() == 7){ // В базе есть номера, которые не могут существовать?
                        range.put(Double.parseDouble((int)limitCell.getNumericCellValue() + "." + (int)ABCDEFCell.getNumericCellValue() + "" + 1), operatorCell.getStringCellValue());
                    }
                    // Так как ключ должен быть уникальным, к нему добавляется код ABC/DEF в виде данных после запятой. 1 добавляется, так как при .900 число сокращается до .9
                }      
            }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    public static void findOperator(Map<Double, String> range, String input, String ABCDEF) {
        
        int numberShortened = Integer.parseInt(input.substring(4, 11));
        String result = (range
                .keySet()
                .stream()
                .filter(x->numberShortened<=x && x.toString().substring(x.toString().length()-4, x.toString().length()-1).equals(ABCDEF))
                .limit(1).map(x->range.get(x)).collect(Collectors.joining()));
        if(!result.isBlank()){
            System.out.println(result);
        }
        else System.out.println(result + "Данные не найдены!");
    } 
}
