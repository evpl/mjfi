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
 * Test case for {@link ByteUnaryOperator}.
 */
final class ByteUnaryOperatorTest extends LambdaContractTest {

    ByteUnaryOperatorTest() {
        super(
                ByteUnaryOperator.class,
                byte.class, "applyAsByte", new Class[]{byte.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ByteUnaryOperator lambda = (byte arg) -> (byte) 0;
        final byte result = lambda.applyAsByte((byte) 0);
    }

    @Test
    void composeMethodThrowNPEForNullArg() {
        final ByteUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.compose(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotCatchException() {
        final ByteUnaryOperator notThrowingOperator = value -> value;
        final ByteUnaryOperator throwingOperator = value -> {throw new TestException();};
        final ByteUnaryOperator composeOperatorWithExceptionInBefore = notThrowingOperator.compose(throwingOperator);
        final ByteUnaryOperator composeOperatorWithExceptionInAfter = throwingOperator.compose(notThrowingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInBefore.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInAfter.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final ByteUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final ByteUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final ByteUnaryOperator andOperator = notThrowingOperator.compose(throwingOperator);
        assertThatCode(() -> {
            andOperator.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ByteUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final ByteUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final ByteUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        composeOperator.applyAsByte((byte) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectCalculation() {
        final ByteUnaryOperator beforeOperator = value -> (byte) (value + 17);
        final ByteUnaryOperator afterOperator = value -> (byte) (value + 23);
        final ByteUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        assertThat(composeOperator.applyAsByte((byte) 14))
                .isEqualTo((byte) 54);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final ByteUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotCatchException() {
        final ByteUnaryOperator notThrowingOperator = value -> value;
        final ByteUnaryOperator throwingOperator = value -> {throw new TestException();};
        final ByteUnaryOperator andThenOperatorWithExceptionInBefore = throwingOperator.andThen(notThrowingOperator);
        final ByteUnaryOperator andThenOperatorWithExceptionInAfter = notThrowingOperator.andThen(throwingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInBefore.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInAfter.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final ByteUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final ByteUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final ByteUnaryOperator andOperator = throwingOperator.andThen(notThrowingOperator);
        assertThatCode(() -> {
            andOperator.applyAsByte((byte) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ByteUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final ByteUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final ByteUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        composeOperator.applyAsByte((byte) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectCalculation() {
        final ByteUnaryOperator beforeOperator = value -> (byte) (value + 17);
        final ByteUnaryOperator afterOperator = value -> (byte) (value + 23);
        final ByteUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        assertThat(composeOperator.applyAsByte((byte) 14))
                .isEqualTo((byte) 54);
    }

    @Test
    void identityMethodReturnsCorrectOperator() {
        final ByteUnaryOperator identityOperator = ByteUnaryOperator.identity();
        assertThat(identityOperator.applyAsByte((byte) 15))
                .isEqualTo((byte) 15);
    }
}
