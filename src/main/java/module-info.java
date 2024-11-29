module com.example.homepage {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.beans;
    requires spring.web;
    requires spring.context;
    requires spring.core;
    requires java.desktop;
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires spring.boot.autoconfigure;
    requires spring.boot;


    opens com.example.homepage to javafx.fxml;
    exports com.example.homepage;
}