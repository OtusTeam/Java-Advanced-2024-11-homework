package jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JmeterTest {

    public static void main(String[] args) throws IOException {
        runTest();
    }

    private static void runTest() throws IOException {
//        var jmeterHome = "/путь/к/apache-jmeter-5.6.3";
        var jmeterHome = System.getenv("JMETER_HOME");

        setInitialProps(jmeterHome);

        var jmeter = new StandardJMeterEngine();
        var threadGroup = getThreadGroup();
        var testPlan = getTestPlan(threadGroup);

        var testPlanTree = new ListedHashTree();
        var threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        var sampler = getHttpSamplerProxy();
        threadGroupHashTree.add(sampler);

        savePlan(testPlanTree);
        configureResults(testPlanTree);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static void setInitialProps(String jmeterHome) {
        JMeterUtils.loadJMeterProperties(Strings.concat(jmeterHome, "/bin/jmeter.properties"));
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();
    }

    private static void configureResults(ListedHashTree testPlanTree) {
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }
        var logger = new ResultCollector(summer);
        logger.setFilename("jmeter/logs.jtl");
        testPlanTree.add(testPlanTree.getArray()[0], logger);
    }

    private static void savePlan(ListedHashTree testPlanTree) throws IOException {
        SaveService.saveTree(testPlanTree, Files.newOutputStream(Paths.get("jmeter/test-plan-java.jmx")));
    }

    private static HTTPSamplerProxy getHttpSamplerProxy() {
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setProtocol("http");
        httpSampler.setPath("/api/users/register");
        httpSampler.setMethod("POST");
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);


        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        Arguments arguments = getArguments();
        httpSampler.setArguments(arguments);

        httpSampler.setName("new-user");
        return httpSampler;
    }

    private static Arguments getArguments() {
        Arguments arguments = new Arguments();

        HTTPArgument argument_login = new HTTPArgument("login", "${__UUID()}", "=");
        argument_login.setUseEquals(true);
        argument_login.setAlwaysEncoded(false);


        HTTPArgument argument_password = new HTTPArgument("password", "${__RandomString(8, abcd12345, password)}", "=");
        argument_password.setUseEquals(true);
        argument_password.setAlwaysEncoded(false);

        arguments.addArgument(argument_login);
        arguments.addArgument(argument_password);
        return arguments;
    }

    private static org.apache.jmeter.threads.ThreadGroup getThreadGroup() {

        var loopController = new LoopController();
        loopController.setLoops(10);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        var threadGroup = new org.apache.jmeter.threads.ThreadGroup();
        threadGroup.setName("Login Service");
        threadGroup.setNumThreads(10);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    private static TestPlan getTestPlan(org.apache.jmeter.threads.ThreadGroup threadGroup) {
        var testPlan = new TestPlan("Login Service Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.addThreadGroup(threadGroup);
        return testPlan;
    }
}
