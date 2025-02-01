module hw05.java.module.api {
    requires hw05.java.module.provider;
    requires hw05.java.module.service;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires modelmapper;
    requires static lombok;
    requires spring.web;
}