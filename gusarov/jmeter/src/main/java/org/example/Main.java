package org.example;

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
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Start...");
        Main.runPT();
        System.out.println("Done!");
    }

    private static void runPT() throws IOException {
        String jmeterHome = System.getenv("JMETER_HOME");

        if(jmeterHome == null){
            throw new RuntimeException("Environment JMETER_HOME not found.");
        }

        JMeterUtils.loadJMeterProperties(Strings.concat(jmeterHome, "\\bin\\jmeter.properties"));
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();

        var jmeter = new StandardJMeterEngine();
        ThreadGroup threadGroup = getThreadGroup();
        TestPlan testPlan = getTestPlan(threadGroup);

        var testPlanTree = new ListedHashTree();
        var threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);

        for (int i = 0; i < 300; i++) {

            var sampler = getHttpSamplerProxy(UUID.randomUUID());
            threadGroupHashTree.add(sampler);

            HeaderManager manager = new HeaderManager();
            manager.add(new Header("Content-Type", "application/json"));
            manager.setName(JMeterUtils.getResString("header_manager_title"));
            manager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
            manager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

            HashTree httpRequestTree = new HashTree();
            httpRequestTree.add(sampler, manager);
            threadGroupHashTree.add(httpRequestTree);
        }
        SaveService.saveTree(testPlanTree, Files.newOutputStream(Paths.get("./user_service.jmx")));

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }
        var logger = new ResultCollector(summer);
        logger.setFilename("./user-logs.jtl");
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static HTTPSamplerProxy getHttpSamplerProxy(UUID uuid) {
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8081);
        httpSampler.setPath("/api/v1/users");
        httpSampler.setMethod("POST");
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);
        httpSampler.setPostBodyRaw(true);
        String body=String.format("{\"login\":\"%s\",\"password\":\"pass\"}", uuid);
        httpSampler.addNonEncodedArgument("", body, "");

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        httpSampler.setName("POST_USERS");
        return httpSampler;
    }

    private static ThreadGroup getThreadGroup() {

        var loopController = new LoopController();
        loopController.setLoops(1);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        var threadGroup = new ThreadGroup();
        threadGroup.setName("USER_TEST");
        threadGroup.setNumThreads(1);
//        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    private static TestPlan getTestPlan(ThreadGroup threadGroup) {
        var testPlan = new TestPlan("User Service Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.addThreadGroup(threadGroup);
        return testPlan;
    }
}