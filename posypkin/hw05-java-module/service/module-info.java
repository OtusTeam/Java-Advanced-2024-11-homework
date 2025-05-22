module hw05.java.module.service {
    requires hw05.java.module.core;
    requires hw05.java.module.provider;
    requires spring.context;
    requires static lombok;
    requires modelmapper;
    exports ru.otus.service;
}