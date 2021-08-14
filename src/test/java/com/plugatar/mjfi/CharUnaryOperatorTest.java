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
 * Test case for {@link CharUnaryOperator}.
 */
final class CharUnaryOperatorTest extends LambdaContractTest {

    CharUnaryOperatorTest() {
        super(
                CharUnaryOperator.class,
                char.class, "applyAsChar", new Class[]{char.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final CharUnaryOperator lambda = (char arg) -> (char) 0;
        final char result = lambda.applyAsChar((char) 0);
    }

    @Test
    void composeMethodThrowNPEForNullArg() {
        final CharUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.compose(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotCatchException() {
        final CharUnaryOperator notThrowingOperator = value -> value;
        final CharUnaryOperator throwingOperator = value -> {throw new TestException();};
        final CharUnaryOperator composeOperatorWithExceptionInBefore = notThrowingOperator.compose(throwingOperator);
        final CharUnaryOperator composeOperatorWithExceptionInAfter = throwingOperator.compose(notThrowingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInBefore.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInAfter.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final CharUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final CharUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final CharUnaryOperator andOperator = notThrowingOperator.compose(throwingOperator);
        assertThatCode(() -> {
            andOperator.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final CharUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final CharUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final CharUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        composeOperator.applyAsChar((char) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectCalculation() {
        final CharUnaryOperator beforeOperator = value -> (char) (value + 17);
        final CharUnaryOperator afterOperator = value -> (char) (value + 23);
        final CharUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        assertThat(composeOperator.applyAsChar((char) 14))
                .isEqualTo((char) 54);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final CharUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotCatchException() {
        final CharUnaryOperator notThrowingOperator = value -> value;
        final CharUnaryOperator throwingOperator = value -> {throw new TestException();};
        final CharUnaryOperator andThenOperatorWithExceptionInBefore = throwingOperator.andThen(notThrowingOperator);
        final CharUnaryOperator andThenOperatorWithExceptionInAfter = notThrowingOperator.andThen(throwingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInBefore.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInAfter.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final CharUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final CharUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final CharUnaryOperator andOperator = throwingOperator.andThen(notThrowingOperator);
        assertThatCode(() -> {
            andOperator.applyAsChar((char) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final CharUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final CharUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final CharUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        composeOperator.applyAsChar((char) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectCalculation() {
        final CharUnaryOperator beforeOperator = value -> (char) (value + 17);
        final CharUnaryOperator afterOperator = value -> (char) (value + 23);
        final CharUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        assertThat(composeOperator.applyAsChar((char) 14))
                .isEqualTo((char) 54);
    }

    @Test
    void identityMethodReturnsCorrectOperator() {
        final CharUnaryOperator identityOperator = CharUnaryOperator.identity();
        assertThat(identityOperator.applyAsChar((char) 15))
                .isEqualTo((char) 15);
    }
}
