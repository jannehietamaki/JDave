package jdave.junit4;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import jdave.Specification;
import jdave.runner.Context;

import org.junit.runner.Description;

/**
 * The DescriptionFactory's only purpose is to create the appropriate hierarchy
 * of Description objects for the surrounding JUnit 4 framework. Given a
 * <code>Specification</code> class, the DescriptionFactory generates a
 * three-level hierarchy of Descriptions as follows:
 * <ul>
 * <li>The Specification class itself</li>
 * <li>The Context objects defined within the Specification</li>
 * <li>The <i>specification methods</i> for a given context</li>
 * </ul>
 * 
 * @author lkoskela
 */
public class DescriptionFactory {

    public static Description create(
            Class<? extends Specification<?>> spec) {
        Description description = Description
                .createSuiteDescription(spec.getName());
        for (Class contextType : spec.getDeclaredClasses()) {
            Context context = new Context(spec, contextType);
            Description contextDescription = create(contextType,
                    context.getName());
            description.addChild(contextDescription);
        }
        return description;
    }

    // TODO: if Context would expose a "getType()", we could simply pass in the
    // Context itself, making the hierarchy of create(...) methods much nicer
    private static Description create(Class contextType, String name) {
        Description description = Description
                .createSuiteDescription(name);
        for (Method m : contextType.getDeclaredMethods()) {
            if (DescriptionFactory.looksLikeSpecMethod(m)) {
                description.addChild(create(m));
            }
        }
        return description;
    }

    private static Description create(Method m) {
        return Description.createSuiteDescription(m.getName());
    }

    private static boolean looksLikeSpecMethod(Method m) {
        boolean notCreateMethod = (m.getName().equals("create") == false);
        boolean isPublic = ((m.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC);
        return notCreateMethod && isPublic;
    }

}
