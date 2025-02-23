package task6.module.service;

import task6.module.core.HashDatabase;
import task6.module.provider.Task6Provider;

import java.util.UUID;

public class Task6Service {
    private final Task6Provider task6Provider;
    private final HashDatabase db;

    public  Task6Service (){
        task6Provider = new Task6Provider();
        db = new HashDatabase();
    }

    public String serviceData ()
    {
        String s = task6Provider.providerData();
        UUID saveId = db.save(s);
        return String.format("serviceData: provider return: '%s', db return id: '%s'", s, saveId);
    }
}
