/*
 * Copyright (c) 2021 Evgenii Plugatar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plugatar.mjfi;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Test case for the contract of a lambda.
 */
abstract class LambdaContractTest {
    private final Class<?> functionalInterfaceType;
    private final Class<?> abstractMethodReturnedType;
    private final String abstractMethodName;
    private final Class<?>[] abstractMethodParameterTypes;
    private final Class<?>[] abstractMethodExceptionTypes;

    /**
     * Ctor.
     *
     * @param functionalInterfaceType      the functional interface
     * @param abstractMethodReturnedType   the returned type of the abstract method
     * @param abstractMethodName           the name of the abstract method
     * @param abstractMethodParameterTypes the array contains ordered parameters types of the abstract method
     * @param abstractMethodExceptionTypes the array contains ordered errors thrown by the abstract method
     */
    LambdaContractTest(final Class<?> functionalInterfaceType,
                       final Class<?> abstractMethodReturnedType,
                       final String abstractMethodName,
                       final Class<?>[] abstractMethodParameterTypes,
                       final Class<?>[] abstractMethodExceptionTypes) {
        this.functionalInterfaceType = functionalInterfaceType;
        this.abstractMethodReturnedType = abstractMethodReturnedType;
        this.abstractMethodName = abstractMethodName;
        this.abstractMethodParameterTypes = abstractMethodParameterTypes;
        this.abstractMethodExceptionTypes = abstractMethodExceptionTypes;
    }

    @Test
    final void lambdaContract() {
        final SoftAssertions interfaceAssertions = new SoftAssertions();
        interfaceAssertions
                .assertThat(this.functionalInterfaceType)
                .hasAnnotation(FunctionalInterface.class);
        interfaceAssertions
                .assertThat(this.functionalInterfaceType)
                .isPublic();
        interfaceAssertions
                .assertThat(this.functionalInterfaceType)
                .isInterface();
        final Method[] publicAbstractMethods = Arrays.stream(this.functionalInterfaceType.getMethods())
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .toArray(Method[]::new);
        interfaceAssertions
                .assertThat(publicAbstractMethods)
                .as("%s public abstract methods", this.functionalInterfaceType)
                .hasSize(1);
        interfaceAssertions
                .assertAll();

        final Method method = publicAbstractMethods[0];
        final SoftAssertions methodAssertions = new SoftAssertions();
        methodAssertions
                .assertThat(method.getReturnType())
                .as("%s abstract method returned type", method)
                .isEqualTo(this.abstractMethodReturnedType);
        methodAssertions
                .assertThat(method.getName())
                .as("%s abstract method name", method)
                .isEqualTo(this.abstractMethodName);
        methodAssertions
                .assertThat(method.getParameterTypes())
                .as("%s abstract method parameter types", method)
                .isEqualTo(this.abstractMethodParameterTypes);
        methodAssertions
                .assertThat(method.getExceptionTypes())
                .as("%s abstract method exception types", method)
                .isEqualTo(this.abstractMethodExceptionTypes);
        methodAssertions
                .assertAll();
    }
}
