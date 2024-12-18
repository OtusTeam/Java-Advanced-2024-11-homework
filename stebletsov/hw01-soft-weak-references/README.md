# Домашнее задание №1

## Для запуска:

```console 
mvn clean package
```

```console
mvn exec:java
```

Пример работы:
```console
[INFO] --- exec:3.1.0:java (default-cli) @ hw01-soft-weak-references ---
Enter command: 
1. Set reference type (soft/weak)
2. Set cache directory
3. Load file into cache
4. Get file content
5. Exit
1
Enter reference type (soft/weak):
soft
Reference type set to: soft
Enter command: 
1. Set reference type (soft/weak)
2. Set cache directory
3. Load file into cache
4. Get file content
5. Exit
2
Enter directory:
C:\Users\stebl\IdeaProjects\Java-Advanced-11-homework\stebletsov\hw01-soft-weak-references\src\main\resources
Cache directory set to: C:\Users\stebl\IdeaProjects\Java-Advanced-11-homework\stebletsov\hw01-soft-weak-references\src\main\resources
Enter command: 
1. Set reference type (soft/weak)
2. Set cache directory
3. Load file into cache
4. Get file content
5. Exit
3
Enter file name:
Names.txt
File loaded into cache: Names.txt
Enter command: 
1. Set reference type (soft/weak)
2. Set cache directory
3. Load file into cache
4. Get file content
5. Exit
4
Enter file name:
Names.txt
Content of Names.txt:
Paul Atreides
Jessica Atreides
Duncan Idaho
Chani Kynes
Leto Atreides
Gurney Halleck
Baron Harkonnen
Feyd-Rautha Harkonnen
Vladimir Harkonnen
Thufir Hawat
Alia Atreides
Count Fenring
Rabban Harkonnen
Shaddam Corrino
Twilight Paul

Enter command: 
1. Set reference type (soft/weak)
2. Set cache directory
3. Load file into cache
4. Get file content
5. Exit
5
```

## Цель:

Написать собственную имплементацию кеша с использованием WeakReference & SoftReference

## Описание/Пошаговая инструкция выполнения домашнего задания:
1. Создать структуру данных типа кеш. Кеш должен быть абстрактный. То есть необходимо, чтобы можно было задать ключ получения объекта кеша, и, в случае если его нет в памяти, задать поведение загрузки этого объекта в кеш:


- указать кэшируемую директорию


- загрузить содержимое файла в кэш


- получить содержимое файла из кэша


2.Создать программу, эмулирующую поведение данного кэша. Программа должна считывать текстовые файлы из системы и выдавать текст при запросе имени файла. Если в кэше файла нет, кэш должен загрузить себе данные. По умолчанию в кеше нет ни одного файла. Текстовые файлы должны лежать в одной директории. Пример: Names.txt, Address.txt - файлы в системе. При запросе по ключу Names.txt - кеш должен вернуть содержимое файла Names.txt.


3.Создать в папке cache/menu класс Emulator для работы с пользователем. Предоставить пользователю возможности:


- указать кэшируемую директорию


- загрузить содержимое файла в кэш


- получить содержимое файла из кэша"



