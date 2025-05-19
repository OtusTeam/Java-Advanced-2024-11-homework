module provider {
    requires spring.context;
    requires org.apache.commons.lang3;
    exports ru.otus.hw.task05.provider to service, spring.beans;
}