open module service {
    requires provider;
    requires core;
    requires spring.context;
    exports ru.otus.hw.task05.service to api, spring.beans;
    exports ru.otus.hw.task05.service.dto to api;
}