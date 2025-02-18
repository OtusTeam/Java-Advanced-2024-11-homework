package ru.otus.hw.task06;

public class Main {

    //javac -d target ./src/main/java/module-info.java
    //javac -d target --module-path target ./src/main/java/ru/otus/hw/task06/Main.java
    //java --module-path target --module task_zero_six/ru.otus.hw.task06.Main

    //jdeps --module-path target -s --module task_zero_six

    //jlink --module-path "/Users/DmitryJDK/IdeaProjects/Java-Advanced-11-homework/ilarionov/task06/target" --add-modules task_zero_six --output custom_jre
    //jlink --launcher customjrelauncher=task_zero_six/ru.otus.hw.task06.Main --module-path "/Users/DmitryJDK/IdeaProjects/Java-Advanced-11-homework/ilarionov/task06/target" --add-modules task_zero_six --output custom_jre
    //./custom_jre/bin/customjrelauncher

    //keytool -genkeypair -alias cert1 -keypass pass123 -validity 365 -storepass stpass123 -keyalg RSA -keystore ./target/test_keystore
    //keytool -list -storepass stpass123 -keystore ./target/test_keystore
    //keytool -list -v -alias cert1 -storepass stpass123 -keystore ./target/test_keystore

    //cd target
    //copy manifest in target
    //jar cmvf ./META-INF/MANIFEST.MF ./Main.jar ./ru/otus/hw/task06/Main.class
    //jarsigner -verify ./Main.jar
    //jarsigner ./Main.jar -keystore ./test_keystore cert1
    //jarsigner -verify ./Main.jar -keystore ./test_keystore cert1
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
