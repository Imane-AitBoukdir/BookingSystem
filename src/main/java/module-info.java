module com.example.homepage {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.data.jpa;
    requires spring.beans;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;  // Changed from hibernate.core


    opens com.example.homepage;
    opens com.ensam.hotelalrbadr;
    opens com.ensam.hotelalrbadr.api.model to spring.core, org.hibernate.orm.core;
    opens com.ensam.hotelalrbadr.api.controller;

    exports com.example.homepage;
    exports com.ensam.hotelalrbadr;
    exports com.ensam.hotelalrbadr.api.model;
    exports com.ensam.hotelalrbadr.api.controller;
}