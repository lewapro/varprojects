package com.exchelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Main {
    public static void main(String[] args) {

    try {  
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("./numberfile.xlsx"));
    System.out.println(workbook.getActiveSheetIndex());
    XSSFSheet sheet = workbook.getSheetAt(0);
    Scanner sc = new Scanner(System.in);
    System.out.print("Какая колонка? (A-ZZZ): ");
    String userColumn = sc.nextLine().toString().toUpperCase();
    int userColumnIndex = CellReference.convertColStringToIndex(userColumn);
    System.out.println("Выбрана колонка " + userColumn);
    System.out.println();   
    System.out.print("На какой цифре ряд начинается? (1-999): ");
    int userStartRow = sc.nextInt()+1;
    System.out.println("Начало ряда: " + (userStartRow - 1));
    System.out.print("На какой цифре ряд заканчивается? (" + (userStartRow - 1) + "-999): ");
    int userEndRow = sc.nextInt()+1;
    System.out.println("Значит будет идти (" + ((userStartRow - 1) + "-" + (userEndRow - 1)) + ")");
    System.out.print("Какова будет наценка (в процентах)?: ");
    int userPercentage = sc.nextInt();
    System.out.println("Значится умножаем на " + userPercentage + "%");
    Row row = sheet.getRow(userStartRow);
    Cell cell = row.getCell(userColumnIndex);
    System.out.println(cell.getCellType());
     for(int i = userStartRow; i<userEndRow; i++){
        row = sheet.getRow(i);
        if(cell != null){
        cell = row.getCell(userColumnIndex);}
        if(cell != null){
                if(cell.getCellType().equals(CellType.FORMULA)){
                        System.out.println("it was numeric: " + cell.getNumericCellValue());
                        cell.setCellValue((cell.getNumericCellValue() / 100 * userPercentage) + cell.getNumericCellValue());
                        System.out.println(cell.getNumericCellValue());
                    }
                    else{
                        System.out.println(i + " was not a formula!!");
                        System.out.println(cell.getCellType());
                    
                } 
            }
            else
            System.out.println("NULL ALERT! SKIPPA SKIPPA!! ");
    } 

        
    

    //Row row = sheet.getRow(11);   // gets a row
    //Cell cell = row.getCell(8);  // gets a column and sets a cell
    //cell.setCellValue("text2");       // creates value





    
    try(FileOutputStream outputStream = new FileOutputStream("./numberfile.xlsx")){
        workbook.write(outputStream);
    }
    catch(Exception e){
        throw new RuntimeException(e);
    }
    }
    catch(Exception e){
        throw new RuntimeException(e);
    }
}
}
