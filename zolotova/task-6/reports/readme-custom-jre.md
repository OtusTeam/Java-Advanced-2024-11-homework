# Отчёт по созданию кастомной JRE с использованием jmod и jlink


## Структура проекта

Файл module-info.java:

```java
module my.module {
requires java.logging;
}
```


Файл Main.java:

```java
package edu.janeforjane;

import java.util.logging.Logger;

public class Main {
private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        log.info("I'm logger!");
        
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
        
        log.info("I finished!");
    }
}
```

## Компиляция модуля

Выполнена компиляция модуля с сохранением результатов в директорию target/classes:

```commandline
javac -d target/classes src/main/java/module-info.java src/main/java/edu/janeforjane/Main.java
```

Результат:

    Создана структура target/classes с файлами:

        module-info.class

        edu/janeforjane/Main.class

## Анализ зависимостей

Анализ зависимостей с помощью jdeps:
```commandline
jdeps --module-path target/classes --module my.module
```

Вывод анализа:

```commandline
my.module

requires mandated java.base (@21.0.6)
requires java.logging (@21.0.6)
my.module -> java.base
my.module -> java.logging
edu.janeforjane                                    -> java.io                                            java.base
edu.janeforjane                                    -> java.lang                                          java.base
edu.janeforjane                                    -> java.lang.invoke                                   java.base
edu.janeforjane                                    -> java.util.logging                                  java.logging

```

Выявленные зависимости:

    java.base (обязательный модуль)

    java.logging (для работы Logger)

## Сборка JRE

Создана кастомная JRE с минимальным набором модулей:

```commandline
jlink \
--module-path "target/classes:$JAVA_HOME/jmods" \
--add-modules my.module,java.logging \
--output my-custom-jre \
--strip-debug \
--compress=2 \
--no-header-files \
--no-man-pages
```

Параметры сборки:

    --strip-debug - удаление отладочной информации

    --compress=2 - сжатие ресурсов

    --no-header-files - исключение C-заголовочных файлов

    --no-man-pages - исключение документации


## Проверка работы

### Размер JRE
```commandline
du -sh my-custom-jre
------
39M     my-custom-jre
```



### Запуск приложения

```commandline
my-custom-jre/bin/java --module my.module/edu.janeforjane.Main
-------
Hello and welcome!
INFO: I'm logger!
i = 1
i = 2
i = 3
i = 4
i = 5
INFO: I finished!
```


### Список модулей в JRE

```commandline
my-custom-jre/bin/java --list-modules
------
java.base@21.0.6
java.logging@21.0.6
my.module
```
