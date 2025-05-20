module service {
    requires lombok;
    requires entities;
    requires provider;
    requires core;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires com.fasterxml.jackson.databind;
    requires spring.core;
    requires org.slf4j;
//    указывается только то что работает в рантайме, мапстракт например в рантайме не нужен

    exports edu.janeforjane.service.api;
    exports edu.janeforjane.service.api.exceptions;
    exports edu.janeforjane.service.config;

    opens edu.janeforjane.service.config to spring.core;
    opens edu.janeforjane.service.internal to spring.beans;
}