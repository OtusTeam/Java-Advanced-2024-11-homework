## Эмулятор файлового кэша
Программа представляет собой демонстрацию работы файлового кэша на основе SoftReference.
После запуска программы пользователю будет предложено выполнить одно из доступных действий:
- Указать директорию, откуда будут загружаться файлы в кэш;
- Получить контент файла (если контент файла отсутствует в кэше, он будет предварительно загружен);
- Завершить работу программы.

### Архитектура программы
Программа состоит из трех классов:
1. Абстрактный класс SoftCache - позволяет хранить значения в кэше,
реализованном на основе SoftReference;
2. Класс FileCache - наследует класс SoftCache и имплементирует логику загрузки контента файла в кэш;
3. Класс Emulator - обеспечивает взаимодействие пользователя с файловым кэшем посредством командной строки.
 
### Запуск программы:

```console 
mvn clean package
```

```console
mvn exec:java
```
### Пример работы программы
```console
Enter command: 
1. Set cache directory
2. Get file content
3. Exit
1
Enter directory path:
C:\dev\workspace\learning\otus\Java-Advanced-11-homework\sivakova\task1\src\main\resources\files
File directory set to: C:\dev\workspace\learning\otus\Java-Advanced-11-homework\sivakova\task1\src\main\resources\files
Enter command: 
1. Set cache directory
2. Get file content
3. Exit
2
Enter file name:
file1.txt
Content of file1.txt:
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

Enter command: 
1. Set cache directory
2. Get file content
3. Exit
2
Enter file name:
file2.txt
Content of file2.txt:
Thomas Bailey Aldrich, Before The Rain

Before The Rain
We knew it would rain, for all the morn
A spirit on slender ropes of mist
Was lowering its golden buckets down
Into the vapory amethyst.
Of marshes and swamps and dismal fens�
Scooping the dew that lay in the flowers,
Dipping the jewels out of the sea,
To sprinkle them over the land in showers.
We knew it would rain, for the poplars showed
The white of their leaves, the amber grain
Shrunk in the wind - and the lightning now
Is tangled in tremulous skeins of rain!
Enter command: 
1. Set cache directory
2. Get file content
3. Exit
2
Enter file name:
fileThatDoNotExist.txt
Error getting file content: Can't put value to cache: File doesn't exist
Enter command: 
1. Set cache directory
2. Get file content
3. Exit

```