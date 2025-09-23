package com.ecommerce.report;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.util.List;

public class CustomerReportBuilder implements ReportBuilder{
    private Report report = new Report();
    @Override
    public void setTitle(String title) {
        report.title = title;
    }
    @Override
    public void setSubTitle(String subTitle) {
        report.subTitle = null;
    }
    @Override
    public void setName(String name) {
        report.name = name;
    }
    @Override
    public void setPhone(String phone) {
        report.phoneNumber = phone;
    }
    @Override
    public void setId(String id) {
        report.id = id;
    }
    @Override
    public void setTotalOrders(int totalOrders) {
        report.totalOrders = totalOrders;
    }


    @Override
    public Report buildReport() {
        return report;
    }
}
