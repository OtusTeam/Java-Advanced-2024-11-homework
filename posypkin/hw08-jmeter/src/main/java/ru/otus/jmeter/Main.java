package ru.otus.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
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
        var jmeterHome = System.getenv("JMETER_HOME");

        if (jmeterHome == null) {
            throw new RuntimeException("JMETER_HOME environment variable is not set.");
        }

        JMeterUtils.loadJMeterProperties(Strings.concat(jmeterHome, "\\bin\\jmeter.properties"));
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();

        var jmeter = new StandardJMeterEngine();

        var threadGroup = getThreadGroup();
        var testPlan = getTestPlan(threadGroup);

        var testPlanTree = new ListedHashTree();
        var threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        var sampler = getHttpSamplerProxy();
        threadGroupHashTree.add(sampler);
        var headerManager = getHeaderManager();
        threadGroupHashTree.add(headerManager);

        SaveService.saveTree(testPlanTree, Files.newOutputStream(Paths.get("./jmeter/script.jmx")));

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }
        var logger = new ResultCollector(summer);
        logger.setFilename("./jmeter/pt-logs.jtl");
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static HeaderManager getHeaderManager() {
        var headerManager = new HeaderManager();
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        headerManager.setName("header");
        headerManager.add(new Header("Content-Type", "application/json"));
        return headerManager;
    }

    private static HTTPSamplerProxy getHttpSamplerProxy() {
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/registration/register");
        httpSampler.setMethod("POST");
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);
        httpSampler.setPostBodyRaw(true);

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        httpSampler.setName("register");

        String jsonBody = """
                        {
                        	"login": "dasdasd",
                        	"password": "dasdasd"
                        }
                """;
        HTTPArgument argument = new HTTPArgument();
        argument.setValue(jsonBody);
        argument.setContentType("application/json"); // Указываем Content-Type для аргумента
        argument.setAlwaysEncoded(false);

        // Добавляем аргумент в HTTPSamplerProxy
        Arguments arguments = new Arguments();
        arguments.addArgument(argument);
        httpSampler.setArguments(arguments);

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
        threadGroup.setName("Hash Service");
        threadGroup.setNumThreads(1000);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    private static TestPlan getTestPlan(ThreadGroup threadGroup) {
        var testPlan = new TestPlan("Posypkin Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.addThreadGroup(threadGroup);
        return testPlan;
    }
}