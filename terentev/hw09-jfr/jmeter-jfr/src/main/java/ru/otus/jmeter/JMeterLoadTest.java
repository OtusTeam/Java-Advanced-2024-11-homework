package ru.otus.jmeter;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jmeter.threads.ThreadGroup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class JMeterLoadTest {

    public static void main(String[] args) throws IOException {
        runJMeterTest();
    }

    private static void runJMeterTest() throws IOException {
        String jmeterHome = System.getenv("JMETER_HOME");

        if (jmeterHome == null) {
            throw new RuntimeException("JMETER_HOME environment variable is not set.");
        }

        JMeterUtils.loadJMeterProperties(Paths.get(jmeterHome, "bin", "jmeter.properties").toString());
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();

        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        ListedHashTree testPlanTree = new ListedHashTree();

        TestPlan testPlan = new TestPlan("User Registration Load Test");
        testPlanTree.add(testPlan);

        ThreadGroup threadGroup = createThreadGroup();
        testPlanTree.add(testPlan, threadGroup);

        HTTPSamplerProxy sampler = createHttpSampler();
        testPlanTree.add(threadGroup, sampler);

        Summariser summariser = new Summariser("summary");
        ResultCollector resultCollector = new ResultCollector(summariser);
        resultCollector.setFilename("terentev/hw09-jfr/jmeter-results.jtl");
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);

        SaveService.saveTree(testPlanTree, new FileOutputStream("terentev/hw09-jfr/jmeter-script.jmx"));

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static ThreadGroup createThreadGroup() {
        LoopController loopController = new LoopController();
        loopController.setLoops(100);
        loopController.setFirst(true);
        loopController.initialize();

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("User Registration Load Test Group");
        threadGroup.setNumThreads(10);
        threadGroup.setRampUp(5);
        threadGroup.setSamplerController(loopController);

        return threadGroup;
    }

    private static HTTPSamplerProxy createHttpSampler() {
        var httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("/users/register");
        httpSampler.setMethod("POST");
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);
        httpSampler.addNonEncodedArgument("login", "user_" + System.currentTimeMillis(), "");
        httpSampler.addNonEncodedArgument("password", "password", "");

        return httpSampler;
    }
}
