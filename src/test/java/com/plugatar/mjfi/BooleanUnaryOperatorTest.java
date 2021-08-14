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
 * Test case for {@link BooleanUnaryOperator}.
 */
public final class BooleanUnaryOperatorTest extends LambdaContractTest {

    BooleanUnaryOperatorTest() {
        super(
                BooleanUnaryOperator.class,
                boolean.class, "applyAsBoolean", new Class[]{boolean.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final BooleanUnaryOperator lambda = (boolean arg) -> true;
        final boolean result = lambda.applyAsBoolean(true);
    }

    @Test
    void composeMethodThrowNPEForNullArg() {
        final BooleanUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.compose(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotCatchException() {
        final BooleanUnaryOperator notThrowingOperator = value -> value;
        final BooleanUnaryOperator throwingOperator = value -> {throw new TestException();};
        final BooleanUnaryOperator composeOperatorWithExceptionInBefore = notThrowingOperator.compose(throwingOperator);
        final BooleanUnaryOperator composeOperatorWithExceptionInAfter = throwingOperator.compose(notThrowingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInBefore.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInAfter.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final BooleanUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final BooleanUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final BooleanUnaryOperator andOperator = notThrowingOperator.compose(throwingOperator);
        assertThatCode(() -> {
            andOperator.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final BooleanUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final BooleanUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final BooleanUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        composeOperator.applyAsBoolean(true);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectCalculation() {
        final BooleanUnaryOperator identityOperator = value -> value;
        final BooleanUnaryOperator negativeOperator = value -> !value;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(identityOperator.compose(identityOperator).applyAsBoolean(true))
                .isTrue();
        assertions.assertThat(identityOperator.compose(identityOperator).applyAsBoolean(false))
                .isFalse();
        assertions.assertThat(identityOperator.compose(negativeOperator).applyAsBoolean(true))
                .isFalse();
        assertions.assertThat(negativeOperator.compose(identityOperator).applyAsBoolean(true))
                .isFalse();
        assertions.assertThat(negativeOperator.compose(negativeOperator).applyAsBoolean(true))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final BooleanUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotCatchException() {
        final BooleanUnaryOperator notThrowingOperator = value -> value;
        final BooleanUnaryOperator throwingOperator = value -> {throw new TestException();};
        final BooleanUnaryOperator andThenOperatorWithExceptionInBefore = throwingOperator.andThen(notThrowingOperator);
        final BooleanUnaryOperator andThenOperatorWithExceptionInAfter = notThrowingOperator.andThen(throwingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInBefore.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInAfter.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final BooleanUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final BooleanUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final BooleanUnaryOperator andOperator = throwingOperator.andThen(notThrowingOperator);
        assertThatCode(() -> {
            andOperator.applyAsBoolean(true);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final BooleanUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final BooleanUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final BooleanUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        composeOperator.applyAsBoolean(true);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectCalculation() {
        final BooleanUnaryOperator identityOperator = value -> value;
        final BooleanUnaryOperator negativeOperator = value -> !value;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(identityOperator.andThen(identityOperator).applyAsBoolean(true))
                .isTrue();
        assertions.assertThat(identityOperator.andThen(identityOperator).applyAsBoolean(false))
                .isFalse();
        assertions.assertThat(identityOperator.andThen(negativeOperator).applyAsBoolean(true))
                .isFalse();
        assertions.assertThat(negativeOperator.andThen(identityOperator).applyAsBoolean(true))
                .isFalse();
        assertions.assertThat(negativeOperator.andThen(negativeOperator).applyAsBoolean(true))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void identityMethodReturnsCorrectOperator() {
        final BooleanUnaryOperator identityOperator = BooleanUnaryOperator.identity();
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(identityOperator.applyAsBoolean(true))
                .isTrue();
        assertions.assertThat(identityOperator.applyAsBoolean(false))
                .isFalse();
        assertions.assertAll();
    }
}
