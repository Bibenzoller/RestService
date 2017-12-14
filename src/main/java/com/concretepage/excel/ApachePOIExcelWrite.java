package com.concretepage.excel;

import com.concretepage.jewelry.entity.Jewelry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ApachePOIExcelWrite {

    private static final String FILE_NAME = "C:/Users/gimba/Pictures/test.xls";
    private List<Jewelry> jewelryList;

    public ApachePOIExcelWrite(List<Jewelry> jewelryList) {
        this.jewelryList = jewelryList;

    }


    public XSSFWorkbook containFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Заказ");

        int rowNum = 1;int colNum;
        System.out.println("Creating excel file");
        createForm(sheet);

        for (Jewelry j : jewelryList) {
            colNum = 0;
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(colNum++);
            cell.setCellValue((String) j.getBarCode());
            cell = row.createCell(colNum++);
            cell.setCellValue((String) j.getArticle());
            cell = row.createCell(colNum++);
            cell.setCellValue((String) j.getCategory());
            cell = row.createCell(colNum++);
            cell.setCellValue((String) j.getDescription());
            cell = row.createCell(colNum++);
            cell.setCellValue((Integer) j.getCost());
        }
        return workbook;

    }
    private void writeFile(XSSFWorkbook xssfWorkbook){

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    private void createForm(XSSFSheet sheet){
        int rowNum = 0; int colNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(colNum++);
        cell.setCellValue("Штрих код");
        cell = row.createCell(colNum++);
        cell.setCellValue("Артикль");
        cell = row.createCell(colNum++);
        cell.setCellValue("Категория");
        cell = row.createCell(colNum++);
        cell.setCellValue("Описание");

    }
}
