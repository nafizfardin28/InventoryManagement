module com.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    //requires com.h2database;

    opens com.ecommerce to javafx.fxml;
    opens com.ecommerce.controller to javafx.fxml;
    opens com.ecommerce.model to javafx.base;
    opens com.ecommerce.service to javafx.base;
    opens com.ecommerce.util to javafx.fxml;

    exports com.ecommerce;
    exports com.ecommerce.controller;
    exports com.ecommerce.model;
    exports com.ecommerce.service;
}
