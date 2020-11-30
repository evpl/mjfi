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

import org.testng.annotations.Test;

/**
 * Test case for {@link ObjDoubleToDoubleFunction}.
 */
final class ObjDoubleToDoubleFunctionTest extends LambdaContractTest {

    ObjDoubleToDoubleFunctionTest() {
        super(
                ObjDoubleToDoubleFunction.class,
                double.class, "applyAsDouble", new Class[]{Object.class, double.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ObjDoubleToDoubleFunction<TypeA> lambda = (TypeA arg1, double arg2) -> (double) 0;
        final double result = lambda.applyAsDouble(new TypeA() {}, (double) 0);
    }
}
