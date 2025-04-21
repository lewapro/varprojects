    /* 
    *   Работающий прототип, единственная проблема это то, как написан код.
    *   В идеале его нужно сделать более читаемым, а также запретить пользователю писать неправильные форматы.
    *   Хотелось бы ещё драг н дроп сделать чтоб файл просто закинуть, но выглядит слишком тяжело.
    */




package com.exchelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Main {
    public static void main(String[] args) {
        FrameEXCH frame = new FrameEXCH("НЕ УМНОЖАТОР!" ,480, 500); // Окно приложения
        TextAreaEXCH mainHint = new TextAreaEXCH("Программа для УМНОЖЕНИЯ ДЕНЕГ в эксель таблице!\nВсё что нужно сделать это выбрать файл и значения!!", 0, 0, 500, 40); // Подсказка сверху
            frame.add(mainHint);
        JFileChooser fileChooser = new JFileChooser(); // Dialog to choose a file
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx");
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("."));
        TextAreaEXCH fileLinkHelper1 = new TextAreaEXCH("Здесь нужно выбрать и проверить EXCEL файл ↓↓↓↓↓", 0, 80, 400, 20);
            frame.add(fileLinkHelper1);
        TextFieldEXCH fileLinkHelper2 = new TextFieldEXCH(0, 100, 400, 40); // Место под путь к файлу
            fileLinkHelper2.setHorizontalAlignment(TextFieldEXCH.RIGHT);
            frame.add(fileLinkHelper2);
        ButtonEXCH fileChooserButton = new ButtonEXCH("...", 400,100,50,40); // Кнопка для вызова окна для выбора пути к файлу
            frame.add(fileChooserButton);
            fileChooserButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int fileChooserResult = fileChooser.showOpenDialog(null);
                    if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                        File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        fileLinkHelper2.setText(selectedFile.getAbsolutePath());;
                        fileLinkHelper2.setBackground(Color.white);
                    }
                }
            });
        TextAreaEXCH ChooserColumnHint = new TextAreaEXCH("Теперь выбери колонку (A-XFD)", 0,140,200,20);
            frame.add(ChooserColumnHint);
        TextFieldEXCH ChooserColumn = new TextFieldEXCH(0, 160, 50, 40);    // Место под колонку
            frame.add(ChooserColumn);
        TextAreaEXCH ChooserStartRowHint = new TextAreaEXCH("Начало ряда (1-1048576)", 0,200,200,20);
            frame.add(ChooserStartRowHint);
        TextFieldEXCH ChooserStartRow = new TextFieldEXCH(0, 220, 50, 40);  // Место под начало ряда
            frame.add(ChooserStartRow);
        TextAreaEXCH ChooserEndRowHint = new TextAreaEXCH("Конец ряда (1-1048576)", 0,260,200,20);
            frame.add(ChooserEndRowHint);
        TextFieldEXCH ChooserEndRow = new TextFieldEXCH(0,280,50,40);       // Место под конец ряда
            frame.add(ChooserEndRow);
        TextAreaEXCH ChooserMultiplierHint = new TextAreaEXCH("Наценка в процентах (1-9999999)", 0,320,200,20);
            frame.add(ChooserMultiplierHint);
        TextFieldEXCH ChooserMultiplier = new TextFieldEXCH(0,340,50,40);   // Место под наценку
            frame.add(ChooserMultiplier);
        ButtonEXCH button = new ButtonEXCH("Умножировать", 11,394,130,50);
            frame.add(button);
        TextAreaEXCH completionStatus = new TextAreaEXCH("", 160,394,300,100);
            completionStatus.setAlignmentX(TextAreaEXCH.CENTER_ALIGNMENT);
            completionStatus.setAlignmentY(TextAreaEXCH.CENTER_ALIGNMENT);
            frame.add(completionStatus);
            frame.setVisible(true); // должен находится после добавления всех компонентов
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {  
                    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileLinkHelper2.getText()));
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    String userColumn = ChooserColumn.getText().toString().toUpperCase();
                    int userColumnIndex = CellReference.convertColStringToIndex(userColumn);
                    int userStartRow = Integer.parseInt(ChooserStartRow.getText())-1;
                    int userEndRow = Integer.parseInt(ChooserEndRow.getText())-1;
                    int userPercentage = Integer.parseInt(ChooserMultiplier.getText());
                    Row row = sheet.getRow(userStartRow);
                    Cell cell = row.getCell(userColumnIndex);
                     for(int i = userStartRow; i<=userEndRow; i++){
                        row = sheet.getRow(i);
                        if(row != null){
                            cell = row.getCell(userColumnIndex);
                            if(cell != null){
                                if(cell.getCellType().equals(CellType.FORMULA)||cell.getCellType().equals(CellType.NUMERIC)){
                                        double cellData = (cell.getNumericCellValue() / 100 * userPercentage) + cell.getNumericCellValue();
                                        cell.removeFormula();
                                        cell.setCellValue(cellData);
                                    } 
                                }
                    }
                } 
                                                                //Row row = sheet.getRow(11);       | выбирает ряд
                                                                //Cell cell = row.getCell(8);       | выбирает колонку и ячейку
                                                                //cell.removeFormula()              | удаляет формулу из ячейки
                                                                //cell.setCellValue("text2");       | создаёт значение без удаления формулы в ячейке

                    try(FileOutputStream outputStream = new FileOutputStream(fileLinkHelper2.getText().replace(".xlsx", "-" + userPercentage+"%.xlsx"))){ 
                        workbook.write(outputStream);
                    }
                    }
                    catch(FileNotFoundException fnfe){
                        fileLinkHelper2.setBackground(new Color(255,50,50));
                        completionStatus.setText("не гатова! :(\n" + "путь к файлу неверный");
                        throw new RuntimeException(fnfe);
                    }
                    catch(Exception exception){
                        completionStatus.setText("не гатова! :(\n" + exception.getLocalizedMessage());      // добавить потенциальные проблемы и отображать их цветом и текстом
                        throw new RuntimeException(exception);                                              // всё ещё осталась возможность прописывать значение первого ряда после последнего
                    }                                                                                       // если вводить неправильные значения, ошибка одна и та же, не знаю как ловить
                    
                    completionStatus.setText("ГАТОВА!");
            }
        });


} 
}
