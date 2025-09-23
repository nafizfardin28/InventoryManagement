package com.ecommerce.report;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.util.List;

public interface ReportExporter {
    void export(Report report, List<Order> orders, List<OrderDetails> customerOrders,String filePath) throws Exception;

}
