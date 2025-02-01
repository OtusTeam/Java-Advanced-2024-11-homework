module core {
    requires spring.context;
    exports ru.otus.hw.task05.core to service, spring.beans;
    exports ru.otus.hw.task05.core.entity to service;
}