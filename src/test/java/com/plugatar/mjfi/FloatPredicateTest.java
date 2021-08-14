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
 * Test case for {@link FloatPredicate}.
 */
final class FloatPredicateTest extends LambdaContractTest {

    FloatPredicateTest() {
        super(
                FloatPredicate.class,
                boolean.class, "test", new Class[]{float.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final FloatPredicate lambda = (float arg) -> true;
        final boolean result = lambda.test((float) 0);
    }

    @Test
    void andMethodThrowNPEForNullArg() {
        final FloatPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.and(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotCatchException() {
        final FloatPredicate notThrowingPredicate = value -> true;
        final FloatPredicate throwingPredicate = value -> {throw new TestException();};
        final FloatPredicate andPredicateWithExceptionInBefore = throwingPredicate.and(notThrowingPredicate);
        final FloatPredicate andPredicateWithExceptionInAfter = notThrowingPredicate.and(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInBefore.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInAfter.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final FloatPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final FloatPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final FloatPredicate andPredicate = throwingPredicate.and(notThrowingPredicate);
        assertThatCode(() -> {
            andPredicate.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsFalse() {
        final FloatPredicate falsePredicate = value -> false;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final FloatPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final FloatPredicate andPredicate = falsePredicate.and(otherPredicate);
        andPredicate.test((float) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final FloatPredicate beforePredicate = value -> {
            execSeq.add("before");
            return true;
        };
        final FloatPredicate afterPredicate = value -> {
            execSeq.add("after");
            return true;
        };
        final FloatPredicate andPredicate = beforePredicate.and(afterPredicate);
        andPredicate.test((float) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andMethodReturnsPredicateWithCorrectCalculation() {
        final FloatPredicate trueFor0Predicate = value -> value == (float) 0;
        final FloatPredicate falseFor0Predicate = value -> value != (float) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.and(trueFor0Predicate).test((float) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.and(falseFor0Predicate).test((float) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(trueFor0Predicate).test((float) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(falseFor0Predicate).test((float) 0))
                .isFalse();
        assertions.assertAll();
    }

    @Test
    void negateMethodReturnsCorrectPredicate() {
        final FloatPredicate truePredicate = value -> true;
        final FloatPredicate falsePredicate = value -> false;
        final FloatPredicate negateTruePredicate = truePredicate.negate();
        final FloatPredicate negateFalsePredicate = falsePredicate.negate();
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(negateTruePredicate.test((float) 0))
                .isFalse();
        assertions.assertThat(negateFalsePredicate.test((float) 0))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void orMethodThrowNPEForNullArg() {
        final FloatPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.or(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotCatchException() {
        final FloatPredicate notThrowingPredicate = value -> false;
        final FloatPredicate throwingPredicate = value -> {throw new TestException();};
        final FloatPredicate orPredicateWithExceptionInBefore = throwingPredicate.or(notThrowingPredicate);
        final FloatPredicate orPredicateWithExceptionInAfter = notThrowingPredicate.or(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInBefore.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInAfter.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final FloatPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final FloatPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final FloatPredicate orPredicate = throwingPredicate.or(notThrowingPredicate);
        assertThatCode(() -> {
            orPredicate.test((float) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsTrue() {
        final FloatPredicate truePredicate = value -> true;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final FloatPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final FloatPredicate orPredicate = truePredicate.or(otherPredicate);
        orPredicate.test((float) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final FloatPredicate beforePredicate = value -> {
            execSeq.add("before");
            return false;
        };
        final FloatPredicate afterPredicate = value -> {
            execSeq.add("after");
            return false;
        };
        final FloatPredicate andPredicate = beforePredicate.or(afterPredicate);
        andPredicate.test((float) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void orMethodReturnsPredicateWithCorrectCalculation() {
        final FloatPredicate trueFor0Predicate = value -> value == (float) 0;
        final FloatPredicate falseFor0Predicate = value -> value != (float) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.or(trueFor0Predicate).test((float) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.or(falseFor0Predicate).test((float) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(trueFor0Predicate).test((float) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(falseFor0Predicate).test((float) 0))
                .isFalse();
        assertions.assertAll();
    }
}
