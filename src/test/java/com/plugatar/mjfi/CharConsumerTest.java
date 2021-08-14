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
 * Test case for {@link CharConsumer}.
 */
final class CharConsumerTest extends LambdaContractTest {

    CharConsumerTest() {
        super(
                CharConsumer.class,
                void.class, "accept", new Class[]{char.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final CharConsumer lambda = (char arg) -> {};
        lambda.accept((char) 0);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final CharConsumer operator = value -> {};
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotCatchException() {
        final CharConsumer notThrowingConsumer = value -> {};
        final CharConsumer throwingConsumer = value -> {throw new TestException();};
        final CharConsumer andThenConsumerWithExceptionInBefore = throwingConsumer.andThen(notThrowingConsumer);
        final CharConsumer andThenConsumerWithExceptionInAfter = notThrowingConsumer.andThen(throwingConsumer);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInBefore.accept((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenConsumerWithExceptionInAfter.accept((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsConsumerThatDoesNotInvokeOtherConsumerIfFirstThrowException() {
        final CharConsumer throwingConsumer = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingConsumerInvoked = new AtomicBoolean();
        final CharConsumer notThrowingConsumer = value -> {isNotThrowingConsumerInvoked.set(true);};
        final CharConsumer andThenConsumer = throwingConsumer.andThen(notThrowingConsumer);
        assertThatCode(() -> {
            andThenConsumer.accept((char) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingConsumerInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final CharConsumer beforeConsumer = value -> execSeq.add("before");
        final CharConsumer afterConsumer = value -> execSeq.add("after");
        final CharConsumer andThenConsumer = beforeConsumer.andThen(afterConsumer);
        andThenConsumer.accept((char) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsConsumerWithCorrectCalculation() {
        final AtomicReference<Character> beforeConsumerValue = new AtomicReference<>();
        final CharConsumer beforeConsumer = beforeConsumerValue::set;
        final AtomicReference<Character> afterConsumerValue = new AtomicReference<>();
        final CharConsumer afterConsumer = afterConsumerValue::set;
        final CharConsumer amdThenConsumer = beforeConsumer.andThen(afterConsumer);
        amdThenConsumer.accept((char) 54);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(beforeConsumerValue.get())
                .isEqualTo(Character.valueOf((char) 54));
        assertions.assertThat(afterConsumerValue.get())
                .isEqualTo(Character.valueOf((char) 54));
        assertions.assertAll();
    }
}
