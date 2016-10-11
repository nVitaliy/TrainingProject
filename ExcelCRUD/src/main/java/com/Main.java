package com;

import com.reports.TimesheetReport;
import com.util.ExcelUtil;

public class Main {

    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil();
        TimesheetReport timesheetReportReport = new TimesheetReport();

        excelUtil.createExcel(timesheetReportReport);
    }
}
