/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.reflect;

import org.junit.Test;

import static io.microsphere.reflect.ProxyUtils.isProxyable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link ProxyUtils} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class ProxyUtilsTest {

    @Test
    public void testIsProxyable() {
        assertTrue(isProxyable(getClass()));
        assertFalse(isProxyable(int.class));
        assertFalse(isProxyable(int[].class));
        assertFalse(isProxyable(String.class));
        assertFalse(isProxyable(A.class));
        assertFalse(isProxyable(B.class));
    }

    static class A {

        A(Object... args) {

        }
    }

    static class B {
        public final String toString() {
            return "B";
        }
    }
}
