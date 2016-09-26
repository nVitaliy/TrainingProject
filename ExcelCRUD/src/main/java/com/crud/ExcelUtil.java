package com.crud;


import com.reports.Report;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
    private static final String FILE_PATH = "testWrite.xlsx";

    public void createExcel(Report report) {

        FileOutputStream fileOutputStream = null;
        Workbook workbook = report.createWorkBook();
        try {
            LOGGER.info("Creating file was starting...");
            fileOutputStream = new FileOutputStream(new File(FILE_PATH));

            workbook.write(fileOutputStream);

        } catch (FileNotFoundException e) {
            LOGGER.error("Path not found, check path and try again\n" + e);
        } catch (IOException e) {
            LOGGER.error("Error with writing file\n" + e);
        } finally {
            try {

                workbook.close();

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    LOGGER.info("Excel file was successful created!\n");
                }
            } catch (IOException e) {
                LOGGER.error("Error with closing FileOutputStream\n" + e);
            }
        }
    }
}
