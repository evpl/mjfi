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
 * Test case for {@link ByteConsumer}.
 */
final class ByteConsumerTest extends LambdaContractTest {

    ByteConsumerTest() {
        super(
                ByteConsumer.class,
                void.class, "accept", new Class[]{byte.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ByteConsumer lambda = (byte arg) -> {};
        lambda.accept((byte) 0);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final ByteConsumer operator = value -> {};
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotCatchException() {
        final ByteConsumer notThrowingConsumer = value -> {};
        final ByteConsumer throwingConsumer = value -> {throw new TestException();};
        final ByteConsumer andThenConsumerWithExceptionInBefore = throwingConsumer.andThen(notThrowingConsumer);
        final ByteConsumer andThenConsumerWithExceptionInAfter = notThrowingConsumer.andThen(throwingConsumer);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInBefore.accept((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInAfter.accept((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotInvokeOtherConsumerIfFirstThrowException() {
        final ByteConsumer throwingConsumer = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingConsumerInvoked = new AtomicBoolean();
        final ByteConsumer notThrowingConsumer = value -> {isNotThrowingConsumerInvoked.set(true);};
        final ByteConsumer andThenConsumer = throwingConsumer.andThen(notThrowingConsumer);
        assertThatCode(() -> {
            andThenConsumer.accept((byte) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingConsumerInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ByteConsumer beforeConsumer = value -> execSeq.add("before");
        final ByteConsumer afterConsumer = value -> execSeq.add("after");
        final ByteConsumer andThenConsumer = beforeConsumer.andThen(afterConsumer);
        andThenConsumer.accept((byte) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectCalculation() {
        final AtomicReference<Byte> beforeConsumerValue = new AtomicReference<>();
        final ByteConsumer beforeConsumer = beforeConsumerValue::set;
        final AtomicReference<Byte> afterConsumerValue = new AtomicReference<>();
        final ByteConsumer afterConsumer = afterConsumerValue::set;
        final ByteConsumer amdThenConsumer = beforeConsumer.andThen(afterConsumer);
        amdThenConsumer.accept((byte) 54);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(beforeConsumerValue.get())
                .isEqualTo(Byte.valueOf((byte) 54));
        assertions.assertThat(afterConsumerValue.get())
                .isEqualTo(Byte.valueOf((byte) 54));
        assertions.assertAll();
    }
}
