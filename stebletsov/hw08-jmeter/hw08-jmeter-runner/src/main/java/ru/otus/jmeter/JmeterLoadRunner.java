package ru.otus.jmeter;

import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.logging.log4j.util.Strings;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JmeterLoadRunner {

    public static void main(String[] args) {
        JmeterLoadRunner.runJMeter();
    }

    private static void runJMeter() {
        var jmeterHome = System.getenv("JMETER_HOME");

        if (jmeterHome == null) {
            throw new RuntimeException("JMETER_HOME environment variable is not set.");
        }

        JMeterUtils.loadJMeterProperties(Strings.concat(jmeterHome, "\\bin\\jmeter.properties"));
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();

        var jmeter = new StandardJMeterEngine();
        var threadGroup = getThreadGroup();
        var testPlanTree = getTestPlan(threadGroup);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static HTTPSamplerProxy getHttpSamplerProxy() {
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/api/register");
        httpSampler.setMethod("POST");
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        httpSampler.setPostBodyRaw(true);

        var jsonData = "{\"login\":\"${login}\",\"password\":\"complexPassword123\"}";
        httpSampler.addNonEncodedArgument("", jsonData, "");

        httpSampler.setName("hash");
        return httpSampler;
    }

    private static CSVDataSet getCSVDataSet() {
        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setProperty("filename", "./logins.csv");
        csvDataSet.setProperty("variableNames", "login");
        csvDataSet.setProperty("delimiter", ",");
        csvDataSet.setProperty("recycle", "true");
        csvDataSet.setProperty("stopThread", "false");
        return csvDataSet;
    }

    private static ThreadGroup getThreadGroup() {
        var loopController = new LoopController();
        loopController.setLoops(10);
        loopController.setFirst(true);

        var threadGroup = new ThreadGroup();
        threadGroup.setName("Hash Service");
        threadGroup.setNumThreads(100);
        threadGroup.setRampUp(5);
        threadGroup.setSamplerController(loopController);
        threadGroup.addTestElement(getHttpSamplerProxy());

        loopController.initialize();
        return threadGroup;
    }

    private static ListedHashTree getTestPlan(ThreadGroup threadGroup) {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setName("HTTP Header Manager");
        headerManager.add(new Header("Content-Type", "application/json"));
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

        var testPlan = new TestPlan("Hash Service Test Plan");
        ListedHashTree testPlanTree = new ListedHashTree();
        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        HashTree httpSamplerHashTree = new HashTree();
        httpSamplerHashTree.add(getHttpSamplerProxy(), headerManager);
        threadGroupHashTree.add(httpSamplerHashTree);
        var csvDataSet = getCSVDataSet();
        threadGroupHashTree.add(csvDataSet);

        try {
            SaveService.saveTree(testPlanTree,
                    Files.newOutputStream(Paths.get("./script.jmx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var summariser = new Summariser("summary");
        var resultCollector = new ResultCollector(summariser);
        resultCollector.setFilename("./jmeter/pt-logs.jtl");
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);

        var requestCollector = new ResultCollector() {
            @Override
            public void sampleOccurred(SampleEvent event) {
                SampleResult result = event.getResult();
                System.out.println("Request URL: " + result.getURL());
                System.out.println("Request Headers: " + result.getRequestHeaders());
                System.out.println("Request Body: " + result.getSamplerData());
                System.out.println("Response Code: " + result.getResponseCode());
                System.out.println("Response Message: " + result.getResponseMessage());
                System.out.println("Response Body: " + result.getResponseDataAsString());
            }
        };
        testPlanTree.add(testPlanTree.getArray()[0], requestCollector);

        return testPlanTree;
    }
}