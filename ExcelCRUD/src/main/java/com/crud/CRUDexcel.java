package com.crud;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CRUDexcel {
    private final Logger logger = LoggerFactory.getLogger(CRUDexcel.class);
    private static final String FILE_PATH = "testWrite.xlsx";

    public void createExcel() {

        Workbook workbook = new XSSFWorkbook();
        logger.info("Creating file was starting...");

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(FILE_PATH));
            workbook.write(fileOutputStream);

        } catch (FileNotFoundException e) {
            logger.error("Path not found, check path and try again\n" + e);
        } catch (IOException e) {
            logger.error("Error with writing file\n" + e);
        } finally {
            try {

                workbook.close();

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    logger.info("Excel file was successful created!\n");
                }
            } catch (IOException e) {
                logger.error("Error with closing FileOutputStream\n" + e);
            }
        }
    }
}
