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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test case for {@link FloatUnaryOperator}.
 */
final class FloatUnaryOperatorTest extends LambdaContractTest {

    FloatUnaryOperatorTest() {
        super(
                FloatUnaryOperator.class,
                float.class, "applyAsFloat", new Class[]{float.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final FloatUnaryOperator lambda = (float arg) -> (float) 0;
        final float result = lambda.applyAsFloat((float) 0);
    }

    @Test
    void composeMethodThrowNPEForNullArg() {
        final FloatUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.compose(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotCatchException() {
        final FloatUnaryOperator notThrowingOperator = value -> value;
        final FloatUnaryOperator throwingOperator = value -> {throw new TestException();};
        final FloatUnaryOperator composeOperatorWithExceptionInBefore = notThrowingOperator.compose(throwingOperator);
        final FloatUnaryOperator composeOperatorWithExceptionInAfter = throwingOperator.compose(notThrowingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInBefore.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInAfter.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final FloatUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final FloatUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final FloatUnaryOperator andOperator = notThrowingOperator.compose(throwingOperator);
        assertThatCode(() -> {
            andOperator.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final FloatUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final FloatUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final FloatUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        composeOperator.applyAsFloat((float) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectCalculation() {
        final FloatUnaryOperator beforeOperator = value -> (float) (value + 17);
        final FloatUnaryOperator afterOperator = value -> (float) (value + 23);
        final FloatUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        assertThat(composeOperator.applyAsFloat((float) 14))
                .isEqualTo((float) 54);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final FloatUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotCatchException() {
        final FloatUnaryOperator notThrowingOperator = value -> value;
        final FloatUnaryOperator throwingOperator = value -> {throw new TestException();};
        final FloatUnaryOperator andThenOperatorWithExceptionInBefore = throwingOperator.andThen(notThrowingOperator);
        final FloatUnaryOperator andThenOperatorWithExceptionInAfter = notThrowingOperator.andThen(throwingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInBefore.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInAfter.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final FloatUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final FloatUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final FloatUnaryOperator andOperator = throwingOperator.andThen(notThrowingOperator);
        assertThatCode(() -> {
            andOperator.applyAsFloat((float) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final FloatUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final FloatUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final FloatUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        composeOperator.applyAsFloat((float) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectCalculation() {
        final FloatUnaryOperator beforeOperator = value -> (float) (value + 17);
        final FloatUnaryOperator afterOperator = value -> (float) (value + 23);
        final FloatUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        assertThat(composeOperator.applyAsFloat((float) 14))
                .isEqualTo((float) 54);
    }

    @Test
    void identityMethodReturnsCorrectOperator() {
        final FloatUnaryOperator identityOperator = FloatUnaryOperator.identity();
        assertThat(identityOperator.applyAsFloat((float) 15))
                .isEqualTo((float) 15);
    }
}
