package com.reports;

import org.apache.poi.ss.usermodel.Workbook;

public interface Report {
    Workbook createWorkBook();
}
