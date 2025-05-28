package ru.otus.hw.task01.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandAvailability;
import org.springframework.shell.command.annotation.Option;
import ru.otus.hw.task01.service.ContextService;
import ru.otus.hw.task01.service.LoadingService;
import ru.otus.hw.task01.service.StatusService;

import java.util.Objects;

@Command(group = "App console commands")
@RequiredArgsConstructor
public class CacheCommands {

    private final ContextService contextService;

    private final StatusService statusService;

    private final LoadingService loadingService;

    @Command(description = "Setting files path", command = "init", alias = "i")
    public String init(@Option(longNames = "uri", shortNames = 'u') String uri) {
        contextService.setPath(uri);
        return "Path is set to " + uri;
    }

    @Command(description = "Current app state", command = "status", alias = "s")
    public String status() {
        return statusService.buildCurrentStatus();
    }

    @Command(description = "Load file", command = "load", alias = "l")
    @CommandAvailability(provider = "loadingFileCommandAvailabilityProvider")
    public String load(@Option(longNames = "file", shortNames = 'f') String fileName,
                       @Option(longNames = "type", shortNames = 't') String type) {
        if (!Objects.isNull(type) && !type.equals("weak") && !type.equals("soft")) {
            return "Type must be parameter, use -type, -t with one value of <weak or soft>";
        }
        return loadingService.load(fileName, type);
    }

    @Command(description = "Calling GC", command = "gc")
    public String gc() {
        System.gc();
        return "System.gc() was called";
    }
}
