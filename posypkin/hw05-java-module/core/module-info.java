module hw05.java.module.core {
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    exports ru.otus.entity;
    exports ru.otus.repository;
}