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
package jdave.mock;

import jdave.Specification;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class DummyTest {
    private Specification<Void> specification = new Specification<Void>() {};
    
    @Test
    public void dummyMethodsAreIgnored() {
        Observable observable = new Observable(specification.dummy(Observer.class));
        observable.poke();
        specification.verifyMocks();
    }
    
    static class Observable {
        private final Observer observer;

        public Observable(Observer observer) {
            this.observer = observer;            
        }
        
        public void poke() {
            observer.changed();
        }
    }
    
    public static class Observer {
        public void changed() {
            throw new RuntimeException();
        }
    }
}
