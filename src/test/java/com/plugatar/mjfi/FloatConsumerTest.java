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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test case for {@link FloatConsumer}.
 */
final class FloatConsumerTest extends LambdaContractTest {

    FloatConsumerTest() {
        super(
                FloatConsumer.class,
                void.class, "accept", new Class[]{float.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final FloatConsumer lambda = (float arg) -> {};
        lambda.accept((float) 0);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final FloatConsumer operator = value -> {};
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotCatchException() {
        final FloatConsumer notThrowingConsumer = value -> {};
        final FloatConsumer throwingConsumer = value -> {throw new TestException();};
        final FloatConsumer andThenConsumerWithExceptionInBefore = throwingConsumer.andThen(notThrowingConsumer);
        final FloatConsumer andThenConsumerWithExceptionInAfter = notThrowingConsumer.andThen(throwingConsumer);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInBefore.accept((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInAfter.accept((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotInvokeOtherConsumerIfFirstThrowException() {
        final FloatConsumer throwingConsumer = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingConsumerInvoked = new AtomicBoolean();
        final FloatConsumer notThrowingConsumer = value -> {isNotThrowingConsumerInvoked.set(true);};
        final FloatConsumer andThenConsumer = throwingConsumer.andThen(notThrowingConsumer);
        assertThatCode(() -> {
            andThenConsumer.accept((float) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingConsumerInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final FloatConsumer beforeConsumer = value -> execSeq.add("before");
        final FloatConsumer afterConsumer = value -> execSeq.add("after");
        final FloatConsumer andThenConsumer = beforeConsumer.andThen(afterConsumer);
        andThenConsumer.accept((float) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectCalculation() {
        final AtomicReference<Float> beforeConsumerValue = new AtomicReference<>();
        final FloatConsumer beforeConsumer = beforeConsumerValue::set;
        final AtomicReference<Float> afterConsumerValue = new AtomicReference<>();
        final FloatConsumer afterConsumer = afterConsumerValue::set;
        final FloatConsumer amdThenConsumer = beforeConsumer.andThen(afterConsumer);
        amdThenConsumer.accept((float) 54);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(beforeConsumerValue.get())
                .isEqualTo(Float.valueOf((float) 54));
        assertions.assertThat(afterConsumerValue.get())
                .isEqualTo(Float.valueOf((float) 54));
        assertions.assertAll();
    }
}
