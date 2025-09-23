package com.ecommerce.report;

public class TotalReportBuilder implements ReportBuilder {
    private Report report = new Report();
    @Override
    public void setTitle(String title) {
        report.title = title;
    }
    public void setSubTitle(String subTitle) {
        report.subTitle = subTitle;
    }
    @Override
    public void setName(String name) {
        report.name = null;
    }
    @Override
    public void setPhone(String phone) {
        report.phoneNumber = null;
    }
    @Override
    public void setId(String id) {
        report.id = null;
    }
    public void setTotalOrders(int totalOrders) {
        report.totalOrders = totalOrders;
    }
    @Override
    public Report buildReport() {
        return report;
    }
}
