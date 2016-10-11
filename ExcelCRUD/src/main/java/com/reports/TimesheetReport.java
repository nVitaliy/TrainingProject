package com.reports;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TimesheetReport implements Report {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimesheetReport.class);

    private static final String[] titles = {
            "Person", "ID", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun",
            "Total\nHrs", "Overtime\nHrs", "Regular\nHrs"
    };

    private static Object[][] sampleData = {
            {"Yegor Kozlov", "YK", 5.0, 8.0, 10.0, 5.0, 5.0, 7.0, 6.0},
            {"Gisella Bronzetti", "GB", 4.0, 3.0, 1.0, 3.5, null, null, 4.0},
    };

    @Override
    public Workbook createWorkBook() {

        Workbook workbook = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(workbook);

        createWorkSheet(workbook, styles);

        return workbook;
    }

    private void createWorkSheet(Workbook workbook, Map<String, CellStyle> styles) {
        Sheet sheet = workbook.createSheet("TimesheetReport");

        setupPrint(sheet);
        titleRow(styles, sheet);

        //header row
        int rownum = getRownum(styles, sheet);

        //row with totals below
        rowWithTotalsBelow(styles, sheet, rownum);

        //set sample data
        setSampleData(sheet);

        //finally set column widths, the width is measured in units of 1/256th of a character width
        setColumnWidth(sheet);
    }

    private void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 30 * 256); //30 characters wide
        for (int i = 2; i < 9; i++) {
            sheet.setColumnWidth(i, 6 * 256);  //6 characters wide
        }
        sheet.setColumnWidth(10, 10 * 256); //10 characters wide
    }

    private void setSampleData(Sheet sheet) {
        for (int i = 0; i < sampleData.length; i++) {
            Row row = sheet.getRow(2 + i);
            for (int j = 0; j < sampleData[i].length; j++) {
                if (sampleData[i][j] == null)
                    continue;
                if (sampleData[i][j] instanceof String) {
                    row.getCell(j).setCellValue((String) sampleData[i][j]);
                } else {
                    row.getCell(j).setCellValue((Double) sampleData[i][j]);
                }
            }
        }
    }

    private void rowWithTotalsBelow(Map<String, CellStyle> styles, Sheet sheet, int rownum) {
        int i;
        i = rownum++;
        Row sumRow = sheet.createRow(i);
        sumRow.setHeightInPoints(35);
        Cell cell;
        cell = sumRow.createCell(0);
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        cell.setCellValue("Total Hrs:");
        cell.setCellStyle(styles.get("formula"));

        for (int j = 2; j < 12; j++) {
            cell = sumRow.createCell(j);
            String ref = (char) ('A' + j) + "3:" + (char) ('A' + j) + "12";
            cell.setCellFormula("SUM(" + ref + ")");
            if (j >= 9) {
                cell.setCellStyle(styles.get("formula_2"));
            } else {
                cell.setCellStyle(styles.get("formula"));
            }
        }
        rownum++;
        sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(25);
        cell = sumRow.createCell(0);
        cell.setCellValue("Total Regular Hours");
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        cell.setCellFormula("L13");
        cell.setCellStyle(styles.get("formula_2"));
        sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(25);
        cell = sumRow.createCell(0);
        cell.setCellValue("Total Overtime Hours");
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        cell.setCellFormula("K13");
        cell.setCellStyle(styles.get("formula_2"));
    }

    private int getRownum(Map<String, CellStyle> styles, Sheet sheet) {
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < 10; i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                if (j == 9) {
                    //the 10th cell contains sum over week days, e.g. SUM(C3:I3)
                    String ref = "C" + rownum + ":I" + rownum;
                    cell.setCellFormula("SUM(" + ref + ")");
                    cell.setCellStyle(styles.get("formula"));
                } else if (j == 11) {
                    cell.setCellFormula("J" + rownum + "-K" + rownum);
                    cell.setCellStyle(styles.get("formula"));
                } else {
                    cell.setCellStyle(styles.get("cell"));
                }
            }
        }
        return rownum;
    }

    private void titleRow(Map<String, CellStyle> styles, Sheet sheet) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Weekly TimesheetReport");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));
    }

    private void setupPrint(Sheet sheet) {
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
    }

    private Map<String, CellStyle> createStyles(Workbook workbook) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBold(true);
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = workbook.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

}
