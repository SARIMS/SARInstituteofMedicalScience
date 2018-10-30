package com.example.shreyesh.sarinstituteofmedicalscience;

public class Report {

    private String title, staff, pid, reportid;

    public Report(String title, String staff) {
        this.title = title;
        this.staff = staff;
    }

    public Report(String title, String staff, String pid) {
        this.title = title;
        this.staff = staff;
        this.pid = pid;
    }

    public Report(String title, String staff, String pid, String reportid) {
        this.title = title;
        this.staff = staff;
        this.pid = pid;
        this.reportid = reportid;
    }

    public Report() {
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }
}
