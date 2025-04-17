    /* 
    *   Работающий прототип, единственная проблема это то, как написан код.
    *   В идеале его нужно сделать более читаемым, а также запретить пользователю писать неправильные форматы.
    *   Хотелось бы ещё драг н дроп сделать чтоб файл просто закинуть, но выглядит слишком тяжело.
    */




package com.exchelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.awt.event.*;  
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("УМНОЖАТОР"); // frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(480,500);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
        JTextArea mainHint = new JTextArea("Программа для УМНОЖЕНИЯ ДЕНЕГ в эксель таблице!\nВсё что нужно сделать это выбрать файл и значения!!"); // Text on top
            mainHint.setEditable(false);
            mainHint.setBounds(0,0,500,40);
            frame.add(mainHint);
        JFileChooser fileChooser = new JFileChooser(); // Dialog to choose a file
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx");
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("."));
        JTextField fileLinkHelper1 = new JTextField("Здесь нужно выбрать и проверить EXCEL файл");
            fileLinkHelper1.setEditable(false);
            fileLinkHelper1.setBounds(0,60,400,40);
            frame.add(fileLinkHelper1);
        JTextField fileLinkHelper2 = new JTextField(); // field for the file location 
            fileLinkHelper2.setEditable(false);
            fileLinkHelper2.setBounds(0,100,400,40);
            frame.add(fileLinkHelper2);
        JButton fileChooserButton = new JButton("..."); // button to choose a file
            fileChooserButton.setBounds(400,100,50,40);
            frame.add(fileChooserButton);
            fileChooserButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int fileChooserResult = fileChooser.showOpenDialog(null);
                    if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                        File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        fileLinkHelper2.setText(selectedFile.getAbsolutePath());;
                    }
                }
            });
        JTextArea ChooserColumnHint = new JTextArea("Теперь выбери колонку (A-XFD)");
            ChooserColumnHint.setBounds(0,140,200,20);
            ChooserColumnHint.setEditable(false);
            ChooserColumnHint.setOpaque(false);
            ChooserColumnHint.setFocusable(false);
            frame.add(ChooserColumnHint);
        JTextField ChooserColumn = new JTextField(""); // Column Field
            ChooserColumn.setBounds(0,160,50,40);
            ChooserColumn.setHorizontalAlignment(JTextField.CENTER);
            frame.add(ChooserColumn);
        JTextArea ChooserStartRowHint = new JTextArea("Начало ряда (1-1048576)");
            ChooserStartRowHint.setBounds(0,200,200,20);
            ChooserStartRowHint.setEditable(false);
            ChooserStartRowHint.setOpaque(false);
            ChooserStartRowHint.setFocusable(false);
            frame.add(ChooserStartRowHint);
        JTextField ChooserStartRow = new JTextField(""); // Start Row Field
            ChooserStartRow.setBounds(0,220,50,40);
            ChooserStartRow.setHorizontalAlignment(JTextField.CENTER);
            frame.add(ChooserStartRow);
        JTextArea ChooserEndRowHint = new JTextArea("Конец ряда (1-1048576)");
            ChooserEndRowHint.setBounds(0,260,200,20);
            ChooserEndRowHint.setEditable(false);
            ChooserEndRowHint.setOpaque(false);
            ChooserEndRowHint.setFocusable(false);
            frame.add(ChooserEndRowHint);
        JTextField ChooserEndRow = new JTextField(""); // End Row Field
            ChooserEndRow.setBounds(0,280,50,40);
            ChooserEndRow.setHorizontalAlignment(JTextField.CENTER);
            frame.add(ChooserEndRow);
        JTextArea ChooserMultiplierHint = new JTextArea("Наценка в процентах (25)");
            ChooserMultiplierHint.setBounds(0,320,200,20);
            ChooserMultiplierHint.setEditable(false);
            ChooserMultiplierHint.setOpaque(false);
            ChooserMultiplierHint.setFocusable(false);
            frame.add(ChooserMultiplierHint);
        JTextField ChooserMultiplier = new JTextField(""); // Multiplier Field
            ChooserMultiplier.setBounds(0,340,50,40);
            ChooserMultiplier.setHorizontalAlignment(JTextField.CENTER);
            frame.add(ChooserMultiplier);
        JButton button = new JButton("Умножировать");
            button.setBounds(11,394,130,50);
            frame.add(button);
            frame.setLayout(null);
            frame.setVisible(true);
        JTextArea completionStatus = new JTextArea();
            completionStatus.setBounds(160,394,90,50);
            completionStatus.setEditable(false);
            completionStatus.setOpaque(false);
            completionStatus.setFocusable(false);
            completionStatus.setVisible(false);
            completionStatus.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
            completionStatus.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
            frame.add(completionStatus);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {  
                    completionStatus.setVisible(true);
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
                        if(cell != null){
                        cell = row.getCell(userColumnIndex);}
                        if(cell != null){
                                if(cell.getCellType().equals(CellType.FORMULA)){
                                         double cellData = (cell.getNumericCellValue() / 100 * userPercentage) + cell.getNumericCellValue();
                                         cell.removeFormula();
                                         cell.setCellValue(cellData);
                                } 
                            }
                            else
                            System.out.println("NULL ALERT! SKIPPA SKIPPA!! ");
                    } 
                
                        
                    
                
                    //Row row = sheet.getRow(11);   // gets a row
                    //Cell cell = row.getCell(8);  // gets a column and sets a cell
                    //cell.setCellValue("text2");       // creates value
                
                
                
                
                
                    
                    try(FileOutputStream outputStream = new FileOutputStream(fileLinkHelper2.getText().replace(".xlsx", "-" + userPercentage+"%.xlsx"))){ // пока не понял как мне заменять а не создавать новую, если прожать команду дважды
                        workbook.write(outputStream);
                    }
                    catch(Exception eee){
                        throw new RuntimeException(eee);
                    } 
                    }
                    catch(Exception ee){
                        throw new RuntimeException(ee);
                    } 
                    completionStatus.setText("ГАТОВА!");
            }
        });


} 
}
