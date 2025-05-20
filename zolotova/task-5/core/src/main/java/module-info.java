module core{
    requires lombok;
    requires org.slf4j;
    requires entities;
    requires spring.context;
    requires com.fasterxml.jackson.databind;
    requires org.mapstruct;
    requires spring.core;

    exports edu.janeforjane.core.api;
    exports edu.janeforjane.core.config;

    opens edu.janeforjane.core.config to spring.core, spring.beans;
    opens edu.janeforjane.core.internal.mapper to spring.core, org.mapstruct;
    opens edu.janeforjane.core.internal.entities to com.fasterxml.jackson.databind;
}