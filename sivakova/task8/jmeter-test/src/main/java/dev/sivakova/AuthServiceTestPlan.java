package dev.sivakova;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.modifiers.JSR223PreProcessor;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantThroughputTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.io.FileOutputStream;
import java.nio.file.Paths;

public class AuthServiceTestPlan {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("rps parameter is missing");
        }

        String jmeterHome = System.getenv("JMETER_HOME");
        if (jmeterHome == null) {
            throw new RuntimeException("JMETER_HOME environment variable is not set.");
        }

        JMeterUtils.loadJMeterProperties(Paths.get(jmeterHome, "bin", "jmeter.properties").toString());
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();

        int rps = Integer.parseInt(args[0]);
        double throughput = rps * 60;

        TestPlan testPlan = new TestPlan("Auth Service Test Plan");
        testPlan.setUserDefinedVariables(new Arguments());
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlan.class.getName());

        LoopController loopController = new LoopController();
        loopController.setLoops(-1);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Thread Group");
        threadGroup.setNumThreads(100);
        threadGroup.setScheduler(true);
        threadGroup.setDuration(60);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        HashTree testPlanTree = new ListedHashTree();
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);

        HeaderManager headerManager = new HeaderManager();
        headerManager.add(new Header("Content-Type", "application/json"));
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        threadGroupHashTree.add(headerManager);

        ConstantThroughputTimer timer = new ConstantThroughputTimer();
        timer.setName("RPS Limiter");
        timer.setThroughput(throughput);
        timer.setCalcMode(1);
        timer.setProperty(TestElement.TEST_CLASS, ConstantThroughputTimer.class.getName());
        timer.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        threadGroupHashTree.add(timer);

        JSR223PreProcessor preProcessor = new JSR223PreProcessor();
        preProcessor.setName("Dynamic User Generator");
        preProcessor.setProperty(TestElement.TEST_CLASS, JSR223PreProcessor.class.getName());
        preProcessor.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        preProcessor.setProperty("scriptLanguage", "groovy");
        String script = """
                    import groovy.json.JsonOutput
                
                    def id = UUID.randomUUID().toString().substring(0, 6)
                    def ts = System.currentTimeMillis()
                    def email = "user_${id}_${ts}@test.com"
                    def password = "Pass_${id}_${ts}"
                
                    def json = JsonOutput.toJson([name: email, password: password])
                    vars.put("json_body", json)
                """;
        preProcessor.setScript(script);
        preProcessor.setProperty("script", script);
        threadGroupHashTree.add(preProcessor);

        HTTPSamplerProxy sampler = new HTTPSamplerProxy();
        sampler.setName("Register User Request");
        sampler.setDomain("localhost");
        sampler.setPort(8080);
        sampler.setPath("/users");
        sampler.setMethod("POST");
        sampler.setPostBodyRaw(true);
        sampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        sampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        sampler.addNonEncodedArgument("", "${json_body}", "");
        sampler.setPostBodyRaw(true);
        threadGroupHashTree.add(sampler);

        Summariser summariser = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (!summariserName.isEmpty()) {
            summariser = new Summariser(summariserName);
        }
        ResultCollector logger = new ResultCollector(summariser);
        logger.setFilename("logs.jtl");
        threadGroupHashTree.add(logger);

        SaveService.saveTree(testPlanTree, new FileOutputStream("auth_service_generated_test_plan.jmx"));

        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}
