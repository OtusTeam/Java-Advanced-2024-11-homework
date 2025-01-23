## Description

Приложение, которое имеет проблемы с утечкой памяти

## Params:

-Xms128m -Xmx128m -XX:+HeapDumpOnOutOfMemoryError

## Before fix:

![before](./src/main/resources/with_map.png)

## After fix:

![after](./src/main/resources/with_cache.png)
