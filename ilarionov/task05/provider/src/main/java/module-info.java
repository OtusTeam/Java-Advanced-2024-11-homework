module provider {
    requires core;
    requires spring.context;
    exports ru.otus.hw.task05.provider to service, spring.beans;
}