package com.model;

import java.util.Map;

public class TimeSheatInfo {
    private String nameEmployee;
    private String projectName;
    private Map<String,Integer> timeTracker;

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Map<String, Integer> getTimeTracker() {
        return timeTracker;
    }

    public void setTimeTracker(Map<String, Integer> timeTracker) {
        this.timeTracker = timeTracker;
    }
}
