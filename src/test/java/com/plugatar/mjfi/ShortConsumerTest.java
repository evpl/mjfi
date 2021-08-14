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
 * Test case for {@link ShortConsumer}.
 */
final class ShortConsumerTest extends LambdaContractTest {

    ShortConsumerTest() {
        super(
                ShortConsumer.class,
                void.class, "accept", new Class[]{short.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ShortConsumer lambda = (short arg) -> {};
        lambda.accept((short) 0);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final ShortConsumer operator = value -> {};
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotCatchException() {
        final ShortConsumer notThrowingConsumer = value -> {};
        final ShortConsumer throwingConsumer = value -> {throw new TestException();};
        final ShortConsumer andThenConsumerWithExceptionInBefore = throwingConsumer.andThen(notThrowingConsumer);
        final ShortConsumer andThenConsumerWithExceptionInAfter = notThrowingConsumer.andThen(throwingConsumer);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInBefore.accept((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInAfter.accept((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotInvokeOtherConsumerIfFirstThrowException() {
        final ShortConsumer throwingConsumer = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingConsumerInvoked = new AtomicBoolean();
        final ShortConsumer notThrowingConsumer = value -> {isNotThrowingConsumerInvoked.set(true);};
        final ShortConsumer andThenConsumer = throwingConsumer.andThen(notThrowingConsumer);
        assertThatCode(() -> {
            andThenConsumer.accept((short) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingConsumerInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ShortConsumer beforeConsumer = value -> execSeq.add("before");
        final ShortConsumer afterConsumer = value -> execSeq.add("after");
        final ShortConsumer andThenConsumer = beforeConsumer.andThen(afterConsumer);
        andThenConsumer.accept((short) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectCalculation() {
        final AtomicReference<Short> beforeConsumerValue = new AtomicReference<>();
        final ShortConsumer beforeConsumer = beforeConsumerValue::set;
        final AtomicReference<Short> afterConsumerValue = new AtomicReference<>();
        final ShortConsumer afterConsumer = afterConsumerValue::set;
        final ShortConsumer amdThenConsumer = beforeConsumer.andThen(afterConsumer);
        amdThenConsumer.accept((short) 54);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(beforeConsumerValue.get())
                .isEqualTo(Short.valueOf((short) 54));
        assertions.assertThat(afterConsumerValue.get())
                .isEqualTo(Short.valueOf((short) 54));
        assertions.assertAll();
    }
}
