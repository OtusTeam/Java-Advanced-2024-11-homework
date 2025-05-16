# Отчёт по использованию keytool & jarsigner
Задание:

Выпустить самоподписанный сертификат, подписать им jar-файл(из одного класса с методом main()), верифицировать подпись

## Сборка простого приложения в jar 

Результат:

    keytool-jarsigner-app-1.0-SNAPSHOT.jar

## Создание самоподписанного сертификата

```commandline
keytool -genkeypair \
  -alias mykey \
  -keyalg RSA \
  -keysize 2048 \
  -validity 365 \
  -keystore mykeystore.jks \                           
  -storepass password123 \
  -keypass password123 \
  -dname "CN=Janeforjane, OU=Dev, O=Company, L=City, ST=State, C=RU"
```

Результат

    Создано хранилище ключей: mykeystore.jks


    Содержит:

        Приватный ключ с алиасом mykey

        Самоподписанный сертификат

        Срок действия: 1 год

## Проверка содержимого хранилища

```commandline
keytool -list -v -keystore mykeystore.jks -storepass password123
```

Результат

    Keystore type: PKCS12
    Keystore provider: SUN

    Your keystore contains 1 entry

    Alias name: mykey
    Creation date: Apr 14, 2025
    Entry type: PrivateKeyEntry
    Certificate chain length: 1
    Certificate[1]:
    Owner: CN=Janeforjane, OU=Dev, O=Company, L=City, ST=State, C=RU
    Issuer: CN=Janeforjane, OU=Dev, O=Company, L=City, ST=State, C=RU
    ...


## Подпись JAR-файла

```commandline
jarsigner \
  -keystore mykeystore.jks \                           
  -storepass password123 \
  -keypass password123 \
  -signedjar signed-keytool-jarsigner-app-1.0-SNAPSHOT.jar \                                  
  target/keytool-jarsigner-app-1.0-SNAPSHOT.jar \
  mykey 
```

Результат

    jar signed.

    Создан подписанный JAR-файл: 
    signed-keytool-jarsigner-app-1.0-SNAPSHOT.jar

    В JAR добавлены:
    META-INF/MANIFEST.MF (хеши файлов)
    META-INF/MYKEY.SF (файл подписи)
    META-INF/MYKEY.RSA (сертификат и подпись)

## Проверка подписи

```commandline
 jarsigner \
    -verify  \
    -keystore mykeystore.jks  \
    -storepass password123   \
    -verbose   \
    signed-keytool-jarsigner-app-1.0-SNAPSHOT.jar 
```

Результат

    Signed by "CN=Janeforjane, OU=Dev, O=Company, L=City, ST=State, C=RU"
    Digest algorithm: SHA-384
    Signature algorithm: SHA384withRSA, 2048-bit key

    jar verified.