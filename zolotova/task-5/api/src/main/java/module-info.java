module api{
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires service;
    requires core;
    requires provider;
    requires entities;
    requires lombok;
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;

    exports edu.janeforjane.api;
    exports edu.janeforjane.api.api;

    opens edu.janeforjane.api to spring.core, spring.beans;
    opens edu.janeforjane.api.api to spring.core, spring.beans;
}