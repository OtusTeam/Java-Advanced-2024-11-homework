module provider{
    requires lombok;
    requires org.slf4j;
    requires entities;
    requires spring.context;
    requires com.fasterxml.jackson.databind;
    requires org.mapstruct;

    exports edu.janeforjane.provider.api;
    exports edu.janeforjane.provider.config;

    opens edu.janeforjane.provider.config to spring.core, spring.beans;
    opens edu.janeforjane.provider.internal.mapper to spring.core, org.mapstruct;
    opens edu.janeforjane.provider.internal.entities to com.fasterxml.jackson.databind;
}