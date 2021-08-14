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
 * Test case for {@link ShortUnaryOperator}.
 */
final class ShortUnaryOperatorTest extends LambdaContractTest {

    ShortUnaryOperatorTest() {
        super(
                ShortUnaryOperator.class,
                short.class, "applyAsShort", new Class[]{short.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ShortUnaryOperator lambda = (short arg) -> (short) 0;
        final short result = lambda.applyAsShort((short) 0);
    }

    @Test
    void composeMethodThrowNPEForNullArg() {
        final ShortUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.compose(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotCatchException() {
        final ShortUnaryOperator notThrowingOperator = value -> value;
        final ShortUnaryOperator throwingOperator = value -> {throw new TestException();};
        final ShortUnaryOperator composeOperatorWithExceptionInBefore = notThrowingOperator.compose(throwingOperator);
        final ShortUnaryOperator composeOperatorWithExceptionInAfter = throwingOperator.compose(notThrowingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInBefore.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            composeOperatorWithExceptionInAfter.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void composeMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final ShortUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final ShortUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final ShortUnaryOperator andOperator = notThrowingOperator.compose(throwingOperator);
        assertThatCode(() -> {
            andOperator.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ShortUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final ShortUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final ShortUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        composeOperator.applyAsShort((short) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void composeMethodReturnsOperatorWithCorrectCalculation() {
        final ShortUnaryOperator beforeOperator = value -> (short) (value + 17);
        final ShortUnaryOperator afterOperator = value -> (short) (value + 23);
        final ShortUnaryOperator composeOperator = afterOperator.compose(beforeOperator);
        assertThat(composeOperator.applyAsShort((short) 14))
                .isEqualTo((short) 54);
    }

    @Test
    void andThenMethodThrowNPEForNullArg() {
        final ShortUnaryOperator operator = value -> value;
        assertThatCode(() -> {
            operator.andThen(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotCatchException() {
        final ShortUnaryOperator notThrowingOperator = value -> value;
        final ShortUnaryOperator throwingOperator = value -> {throw new TestException();};
        final ShortUnaryOperator andThenOperatorWithExceptionInBefore = throwingOperator.andThen(notThrowingOperator);
        final ShortUnaryOperator andThenOperatorWithExceptionInAfter = notThrowingOperator.andThen(throwingOperator);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInBefore.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andThenOperatorWithExceptionInAfter.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andThenMethodReturnsOperatorThatDoesNotInvokeOtherOperatorIfFirstThrowException() {
        final ShortUnaryOperator throwingOperator = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingOperatorInvoked = new AtomicBoolean();
        final ShortUnaryOperator notThrowingOperator = value -> {
            isNotThrowingOperatorInvoked.set(true);
            return value;
        };
        final ShortUnaryOperator andOperator = throwingOperator.andThen(notThrowingOperator);
        assertThatCode(() -> {
            andOperator.applyAsShort((short) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingOperatorInvoked.get())
                .isFalse();
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ShortUnaryOperator beforeOperator = value -> {
            execSeq.add("before");
            return value;
        };
        final ShortUnaryOperator afterOperator = value -> {
            execSeq.add("after");
            return value;
        };
        final ShortUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        composeOperator.applyAsShort((short) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andThenMethodReturnsOperatorWithCorrectCalculation() {
        final ShortUnaryOperator beforeOperator = value -> (short) (value + 17);
        final ShortUnaryOperator afterOperator = value -> (short) (value + 23);
        final ShortUnaryOperator composeOperator = beforeOperator.andThen(afterOperator);
        assertThat(composeOperator.applyAsShort((short) 14))
                .isEqualTo((short) 54);
    }

    @Test
    void identityMethodReturnsCorrectOperator() {
        final ShortUnaryOperator identityOperator = ShortUnaryOperator.identity();
        assertThat(identityOperator.applyAsShort((short) 15))
                .isEqualTo((short) 15);
    }
}
