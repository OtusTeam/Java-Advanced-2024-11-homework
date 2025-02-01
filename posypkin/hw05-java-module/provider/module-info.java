module hw05.java.module.provider {
    requires hw05.java.module.core;
    requires static lombok;
    requires modelmapper;
    requires spring.context;
    exports ru.otus.model;
    exports ru.otus.provider;
}