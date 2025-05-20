module hw05.java.module.provider {
    requires static lombok;
    requires modelmapper;
    requires spring.context;
    exports ru.otus.model;
    exports ru.otus.provider;
}