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
package io.microsphere.convert.multiple;

import io.microsphere.lang.Prioritized;
import io.microsphere.util.ClassLoaderUtils;

import java.util.Collection;
import java.util.ServiceLoader;

import static io.microsphere.reflect.TypeUtils.resolveActualTypeArgumentClass;
import static io.microsphere.util.ServiceLoaderUtils.loadServicesList;

/**
 * An interface to convert the source-typed value to multiple value, e.g , Java array, {@link Collection} or
 * sub-interfaces
 *
 * @param <S> The source type
 * @since 1.0.0
 */

public interface MultiValueConverter<S> extends Prioritized {

    /**
     * Accept the source type and target type or not
     *
     * @param sourceType     the source type
     * @param multiValueType the multi-value type
     * @return if accepted, return <code>true</code>, or <code>false</code>
     */
    boolean accept(Class<S> sourceType, Class<?> multiValueType);

    /**
     * Convert the source to be the multiple value
     *
     * @param source         the source-typed value
     * @param multiValueType the multi-value type
     * @param elementType    the element type
     * @return
     */
    Object convert(S source, Class<?> multiValueType, Class<?> elementType);

    /**
     * Get the source type
     *
     * @return non-null
     */
    default Class<S> getSourceType() {
        return resolveActualTypeArgumentClass(getClass(), MultiValueConverter.class, 0);
    }

    /**
     * Find the {@link MultiValueConverter} instance from {@link ServiceLoader} with the specified source and target type
     *
     * @param sourceType the source type
     * @param targetType the target type
     * @return <code>null</code> if not found
     */
    static MultiValueConverter<?> find(Class<?> sourceType, Class<?> targetType) {
        ClassLoader classLoader = ClassLoaderUtils.getDefaultClassLoader();
        return loadServicesList(MultiValueConverter.class, classLoader)
                .stream()
                .sorted()
                .filter(converter -> converter.accept(sourceType, targetType))
                .findFirst()
                .orElse(null);
    }

    static <T> T convertIfPossible(Object source, Class<?> multiValueType, Class<?> elementType) {
        Class<?> sourceType = source.getClass();
        MultiValueConverter converter = find(sourceType, multiValueType);
        if (converter != null) {
            return (T) converter.convert(source, multiValueType, elementType);
        }
        return null;
    }
}
