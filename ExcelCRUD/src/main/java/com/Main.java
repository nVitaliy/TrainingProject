package com;

import com.crud.ExcelUtil;
import com.reports.Timesheet;

public class Main {

    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil();
        Timesheet timesheetReport = new Timesheet();

        excelUtil.createExcel(timesheetReport);
    }
}
