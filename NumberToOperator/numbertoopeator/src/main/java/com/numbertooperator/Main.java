package com.numbertooperator;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) {
        TreeMap<Long, String> range = new TreeMap<>();
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
            
            if(input.length()==11 && !input.matches(".*\\D.*")){
                    findOperator(range, input);
            }
        }
    } 
    public static void readNumbers(String filePath, TreeMap<Long,String> range){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row ABCDEFRow = sheet.getRow(1);
            Cell ABCDEFCell = ABCDEFRow.getCell(0);
            Row limitRow = sheet.getRow(1);
            Cell limitCell = limitRow.getCell(1);
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
                    limitCell = limitRow.getCell(1);
                    operatorCell = operatorRow.getCell(4);
                    ABCDEFCell = ABCDEFRow.getCell(0);
                    if(((int)limitCell.getNumericCellValue() + "").length() == 7){ // В базе есть номера, которые не могут существовать?
                        range.put((Long.parseLong((long)ABCDEFCell.getNumericCellValue() + "" + (int)limitCell.getNumericCellValue())), operatorCell.getStringCellValue());
                    } 
                }      
            }
            } catch (Exception e) {
                System.out.println(e);
            } 
    }

    public static void findOperator(TreeMap<Long, String> range, String input) {
        Long numberShortened = Long.parseLong(input.substring(1));
        if(range.floorEntry(numberShortened) != null){
            System.out.println(range.floorEntry(numberShortened).getValue());
        }
        else
        System.out.println("Данные не найдены!");
    } 
}
