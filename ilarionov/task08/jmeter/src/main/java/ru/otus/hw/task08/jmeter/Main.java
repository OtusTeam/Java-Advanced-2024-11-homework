package ru.otus.hw.task08.jmeter;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {

    public static void main(String[] args) throws IOException {
        Main.runPT();
    }

    private static void runPT() throws IOException {
        var jmeter = initJmeter();

        ListedHashTree testPlanTree = new ListedHashTree();
        var testPlan = getTestPlan();
        testPlanTree.add(testPlan);

        var loopController = getLoopController();
        var threadGroup = getThreadGroup(loopController);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);

        var headerManager = getHeaderManager();
        var sampler = getHttpSamplerProxy();
        ListedHashTree requestHashTree = new ListedHashTree();
        requestHashTree.add(sampler, headerManager);
        threadGroupHashTree.add(requestHashTree);
        SaveService.saveTree(testPlanTree, Files.newOutputStream(Paths.get("./jmeter/script.jmx")));

        ResultCollector resultCollector = getResultCollector();
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static StandardJMeterEngine initJmeter() {
        var jmeterHome = System.getenv("JMETER_HOME");

        if (jmeterHome == null) {
            throw new RuntimeException("JMETER_HOME environment variable is not set.");
        }
        JMeterUtils.loadJMeterProperties(Strings.concat(jmeterHome, "/bin/jmeter.properties"));
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();
        return new StandardJMeterEngine();
    }

    private static HeaderManager getHeaderManager() {
        HeaderManager manager = new HeaderManager();
        manager.add(new Header("Content-Type", "application/json"));
        manager.setName(JMeterUtils.getResString("header_manager_title"));
        manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        return manager;
    }

    private static HTTPSamplerProxy getHttpSamplerProxy() {
        var body = """
                {
                    "login": "jmeter_user",
                    "password": "jmeter_pass_323rwef1234fd@#$!#sdf"
                }
                """;
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/add");
        httpSampler.setMethod("POST");
        httpSampler.setName("request add user");
        httpSampler.addNonEncodedArgument("", body, "");

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        return httpSampler;
    }

    private static LoopController getLoopController() {
        var loopController = new LoopController();
        loopController.setLoops(2);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
        return loopController;
    }

    private static ThreadGroup getThreadGroup(LoopController loopController) {
        var threadGroup = new ThreadGroup();
        threadGroup.setName("Hash Service");
        threadGroup.setNumThreads(1000);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    private static TestPlan getTestPlan() {
        var testPlan = new TestPlan("Hash Service Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        return testPlan;
    }

    private static ResultCollector getResultCollector() {
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (!summariserName.isEmpty()) {
            summer = new Summariser(summariserName);
        }
        var logger = new ResultCollector(summer);
        logger.setFilename("./jmeter/pt-logs.jtl");
        return logger;
    }
}
