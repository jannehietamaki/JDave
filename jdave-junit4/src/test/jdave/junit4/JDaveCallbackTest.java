package jdave.junit4;

import java.util.ArrayList;
import java.util.List;

import jdave.Specification;
import jdave.runner.Context;
import jdave.runner.SpecificationMethod;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

public class JDaveCallbackTest {
    private List<String> events = new ArrayList<String>();
    private JDaveCallback callback;

    @Before
    public void setUp() throws Exception {
        RunNotifier notifier = new RunNotifier() {
            @Override
            public void fireTestRunStarted(Description d) {
                events.add("fireTestRunStarted(" + d.getDisplayName()
                        + ")");
            }

            @Override
            public void fireTestStarted(Description d)
                    throws StoppedByUserException {
                events.add("fireTestStarted(" + d.getDisplayName()
                        + ")");
            }

            @Override
            public void fireTestFailure(Failure d) {
            }

            @Override
            public void fireTestFinished(Description d) {
                events.add("fireTestFinished(" + d.getDisplayName()
                        + ")");
            }

            @Override
            public void fireTestRunFinished(Result result) {
                events.add("fireTestRunFinished("
                        + result.getFailureCount() + ")");
            }
        };
        callback = new JDaveCallback(notifier);
    }

    @Test
    public void eventOrder() throws Exception {
        Context context = new Context(StackSpec.class,
                StackSpec.EmptyStack.class);
        callback.onContext(context);
        SpecificationMethod method = createSpecMethodByName(
                getClass(), "eventOrder");

        callback.onSpecMethod(method);
    }

    private SpecificationMethod createSpecMethodByName(
            final Class<?> target, String name)
            throws NoSuchMethodException {
        SpecificationMethod method = new SpecificationMethod(target.getDeclaredMethod(name)) {

            @Override
            protected void destroyContext(Object context) {
            }

            @Override
            protected Object newContext(Specification<?> arg0)
                    throws Exception {
                try {
                    return target.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected Specification<?> newSpecification()
                    throws Exception {
                return new StackSpec();
            }
        };
        return method;
    }
}