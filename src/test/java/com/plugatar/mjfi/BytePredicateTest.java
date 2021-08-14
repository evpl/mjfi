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
 * Test case for {@link BytePredicate}.
 */
final class BytePredicateTest extends LambdaContractTest {

    BytePredicateTest() {
        super(
                BytePredicate.class,
                boolean.class, "test", new Class[]{byte.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final BytePredicate lambda = (byte arg) -> true;
        final boolean result = lambda.test((byte) 0);
    }

    @Test
    void andMethodThrowNPEForNullArg() {
        final BytePredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.and(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotCatchException() {
        final BytePredicate notThrowingPredicate = value -> true;
        final BytePredicate throwingPredicate = value -> {throw new TestException();};
        final BytePredicate andPredicateWithExceptionInBefore = throwingPredicate.and(notThrowingPredicate);
        final BytePredicate andPredicateWithExceptionInAfter = notThrowingPredicate.and(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInBefore.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInAfter.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final BytePredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final BytePredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final BytePredicate andPredicate = throwingPredicate.and(notThrowingPredicate);
        assertThatCode(() -> {
            andPredicate.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsFalse() {
        final BytePredicate falsePredicate = value -> false;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final BytePredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final BytePredicate andPredicate = falsePredicate.and(otherPredicate);
        andPredicate.test((byte) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final BytePredicate beforePredicate = value -> {
            execSeq.add("before");
            return true;
        };
        final BytePredicate afterPredicate = value -> {
            execSeq.add("after");
            return true;
        };
        final BytePredicate andPredicate = beforePredicate.and(afterPredicate);
        andPredicate.test((byte) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andMethodReturnsPredicateWithCorrectCalculation() {
        final BytePredicate trueFor0Predicate = value -> value == (byte) 0;
        final BytePredicate falseFor0Predicate = value -> value != (byte) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.and(trueFor0Predicate).test((byte) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.and(falseFor0Predicate).test((byte) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(trueFor0Predicate).test((byte) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(falseFor0Predicate).test((byte) 0))
                .isFalse();
        assertions.assertAll();
    }

    @Test
    void negateMethodReturnsCorrectPredicate() {
        final BytePredicate truePredicate = value -> true;
        final BytePredicate falsePredicate = value -> false;
        final BytePredicate negateTruePredicate = truePredicate.negate();
        final BytePredicate negateFalsePredicate = falsePredicate.negate();
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(negateTruePredicate.test((byte) 0))
                .isFalse();
        assertions.assertThat(negateFalsePredicate.test((byte) 0))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void orMethodThrowNPEForNullArg() {
        final BytePredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.or(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotCatchException() {
        final BytePredicate notThrowingPredicate = value -> false;
        final BytePredicate throwingPredicate = value -> {throw new TestException();};
        final BytePredicate orPredicateWithExceptionInBefore = throwingPredicate.or(notThrowingPredicate);
        final BytePredicate orPredicateWithExceptionInAfter = notThrowingPredicate.or(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInBefore.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInAfter.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final BytePredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final BytePredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final BytePredicate orPredicate = throwingPredicate.or(notThrowingPredicate);
        assertThatCode(() -> {
            orPredicate.test((byte) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsTrue() {
        final BytePredicate truePredicate = value -> true;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final BytePredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final BytePredicate orPredicate = truePredicate.or(otherPredicate);
        orPredicate.test((byte) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final BytePredicate beforePredicate = value -> {
            execSeq.add("before");
            return false;
        };
        final BytePredicate afterPredicate = value -> {
            execSeq.add("after");
            return false;
        };
        final BytePredicate andPredicate = beforePredicate.or(afterPredicate);
        andPredicate.test((byte) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void orMethodReturnsPredicateWithCorrectCalculation() {
        final BytePredicate trueFor0Predicate = value -> value == (byte) 0;
        final BytePredicate falseFor0Predicate = value -> value != (byte) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.or(trueFor0Predicate).test((byte) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.or(falseFor0Predicate).test((byte) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(trueFor0Predicate).test((byte) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(falseFor0Predicate).test((byte) 0))
                .isFalse();
        assertions.assertAll();
    }
}
