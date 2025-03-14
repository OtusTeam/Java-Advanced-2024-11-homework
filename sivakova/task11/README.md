# Эмулятор файлового хранилища в off-heap

Программа предоставляет собой два варианта реализации хранения файлов в памяти с возможностью многократного чтения:
1. **DirectByteBufferStorage** - использует `ByteBuffer.allocateDirect()` для загрузки файлов в память.
2. **MappedByteBufferStorage** - использует `MappedByteBuffer` для сопоставления файлов напрямую с памятью.
3. В отличие от DirectByteBufferStorage, эта реализация позволяет операционной системе оптимизировать доступ к файлу за счет работы с файловыми страницами (memory-mapped I/O). Это снижает накладные расходы на копирование данных и может улучшить производительность при работе с большими файлами.

Обе реализации позволяют загружать файлы размером до 2 ГБ и читать их содержимое с учетом указанного пользователем размера буфера.

## Возможности
- Загрузка файлов в память.
- Чтение содержимого файла в строковом формате.
- Поддержка выбора реализации хранения.
- Возможность повторного чтения файла без необходимости повторной загрузки.
- Консольное меню для взаимодействия с пользователем.

## Запуск программы:
```console 
mvn clean package
```

```console
mvn exec:java
```

## Как использовать

1. Запустите программу.
2. Выберите файл и задайте размер буфера.
3. Выберите способ хранения (DirectByteBuffer или MappedByteBuffer).
4. Прочитайте содержимое файла.
5. При необходимости сбросьте выбор файла или реализации хранения.

## Возможные ошибки и их решения
- **Ошибка: "File too large for DirectByteBuffer or exceeds heap size"**
    - Убедитесь, что размер файла не превышает 2 ГБ и соответствует указанному буферу.
- **Ошибка: "Invalid file path!"**
    - Проверьте корректность пути к файлу и его существование.
- **Ошибка при чтении файла**
    - Убедитесь, что файл не заблокирован другими процессами.

## Пример использования
````
"C:\Program Files\Java\jdk-21.0.2\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.1.1\lib\idea_rt.jar=65337:C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.1.1\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\dev\workspace\learning\otus\Java-Advanced-11-homework\sivakova\task11\target\classes dev.sivakova.Application
Choose an option:
1. Select File and Buffer Size
2. Choose Implementation (Direct or Mapped)
3. Read File Content
4. Reset File/Implementation
5. Exit
   1
   Enter file path: C:\dev\workspace\learning\otus\Java-Advanced-11-homework\sivakova\task1\src\main\resources\files\file1.txt
   Enter buffer size (max 2GB): 1000000
   File and buffer size selected.
   Choose an option:
1. Select File and Buffer Size
2. Choose Implementation (Direct or Mapped)
3. Read File Content
4. Reset File/Implementation
5. Exit
   2
   Choose implementation:
1. Direct ByteBuffer
2. Mapped ByteBuffer
   1
   DirectByteBufferStorage selected.
   Choose an option:
1. Select File and Buffer Size
2. Choose Implementation (Direct or Mapped)
3. Read File Content
4. Reset File/Implementation
5. Exit
   3
   File Content:
   Alone, Edgar Allan Poe

From childhood's hour I have not been
As others were; I have not seen
As others saw; I could not bring
My passions from a common spring.
From the same source I have not taken
My sorrow; I could not awaken
My heart to joy at the same tone;
And all I loved, I loved alone.
Then - in my childhood, in the dawn
Of a most stormy life - was drawn
From every depth of good and ill
The mystery which binds me still:
From the torrent, or the fountain,
From the red cliff of the mountain,
From the sun that round me rolled
In its autumn tint of gold,
From the lightning in the sky
As it passed me flying by,
From the thunder and the storm,
And the cloud that took the form
(When the rest of Heaven was blue)
Of a demon in my view.

Choose an option:
1. Select File and Buffer Size
2. Choose Implementation (Direct or Mapped)
3. Read File Content
4. Reset File/Implementation
5. Exit
   3
   File Content:
   Alone, Edgar Allan Poe

From childhood's hour I have not been
As others were; I have not seen
As others saw; I could not bring
My passions from a common spring.
From the same source I have not taken
My sorrow; I could not awaken
My heart to joy at the same tone;
And all I loved, I loved alone.
Then - in my childhood, in the dawn
Of a most stormy life - was drawn
From every depth of good and ill
The mystery which binds me still:
From the torrent, or the fountain,
From the red cliff of the mountain,
From the sun that round me rolled
In its autumn tint of gold,
From the lightning in the sky
As it passed me flying by,
From the thunder and the storm,
And the cloud that took the form
(When the rest of Heaven was blue)
Of a demon in my view.

Choose an option:
1. Select File and Buffer Size
2. Choose Implementation (Direct or Mapped)
3. Read File Content
4. Reset File/Implementation
5. Exit
````
