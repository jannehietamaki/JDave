/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave;

import java.lang.reflect.Method;

import jdave.runner.DefaultSpecIntrospection;

/**
 * Default implementation of IContextFactory
 *
 * @author Janne Hietam&auml;ki
 */
public class DefaultContextObjectFactory<T> implements IContextObjectFactory<T> {
    @SuppressWarnings("unchecked")
    public T newContextObject(Object context) throws Exception {
        try {
            Method method = context.getClass().getMethod(DefaultSpecIntrospection.INITIALIZER_NAME);
            return (T) method.invoke(context);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
