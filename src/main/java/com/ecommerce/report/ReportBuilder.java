package com.ecommerce.report;

import com.ecommerce.model.Order;

import java.util.List;

public interface ReportBuilder {
    void setTitle(String title);
    void setSubTitle(String subTitle);
    void setName(String name);
    void setPhone(String phone);
    void setId(String id);
    void setTotalOrders(int totalOrders);

    Report buildReport();
}
