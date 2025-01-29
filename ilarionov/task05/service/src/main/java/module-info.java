module service {
    requires provider;
    requires spring.context;
    exports ru.otus.hw.task05.service to api, spring.beans;
}