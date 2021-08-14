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
 * Test case for {@link ShortPredicate}.
 */
final class ShortPredicateTest extends LambdaContractTest {

    ShortPredicateTest() {
        super(
                ShortPredicate.class,
                boolean.class, "test", new Class[]{short.class}, new Class[]{}
        );
    }

    @Test
    void asLambda() {
        final ShortPredicate lambda = (short arg) -> true;
        final boolean result = lambda.test((short) 0);
    }

    @Test
    void andMethodThrowNPEForNullArg() {
        final ShortPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.and(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotCatchException() {
        final ShortPredicate notThrowingPredicate = value -> true;
        final ShortPredicate throwingPredicate = value -> {throw new TestException();};
        final ShortPredicate andPredicateWithExceptionInBefore = throwingPredicate.and(notThrowingPredicate);
        final ShortPredicate andPredicateWithExceptionInAfter = notThrowingPredicate.and(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInBefore.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInAfter.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final ShortPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final ShortPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final ShortPredicate andPredicate = throwingPredicate.and(notThrowingPredicate);
        assertThatCode(() -> {
            andPredicate.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsFalse() {
        final ShortPredicate falsePredicate = value -> false;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final ShortPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final ShortPredicate andPredicate = falsePredicate.and(otherPredicate);
        andPredicate.test((short) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ShortPredicate beforePredicate = value -> {
            execSeq.add("before");
            return true;
        };
        final ShortPredicate afterPredicate = value -> {
            execSeq.add("after");
            return true;
        };
        final ShortPredicate andPredicate = beforePredicate.and(afterPredicate);
        andPredicate.test((short) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andMethodReturnsPredicateWithCorrectCalculation() {
        final ShortPredicate trueFor0Predicate = value -> value == (short) 0;
        final ShortPredicate falseFor0Predicate = value -> value != (short) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.and(trueFor0Predicate).test((short) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.and(falseFor0Predicate).test((short) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(trueFor0Predicate).test((short) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(falseFor0Predicate).test((short) 0))
                .isFalse();
        assertions.assertAll();
    }

    @Test
    void negateMethodReturnsCorrectPredicate() {
        final ShortPredicate truePredicate = value -> true;
        final ShortPredicate falsePredicate = value -> false;
        final ShortPredicate negateTruePredicate = truePredicate.negate();
        final ShortPredicate negateFalsePredicate = falsePredicate.negate();
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(negateTruePredicate.test((short) 0))
                .isFalse();
        assertions.assertThat(negateFalsePredicate.test((short) 0))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void orMethodThrowNPEForNullArg() {
        final ShortPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.or(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotCatchException() {
        final ShortPredicate notThrowingPredicate = value -> false;
        final ShortPredicate throwingPredicate = value -> {throw new TestException();};
        final ShortPredicate orPredicateWithExceptionInBefore = throwingPredicate.or(notThrowingPredicate);
        final ShortPredicate orPredicateWithExceptionInAfter = notThrowingPredicate.or(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInBefore.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInAfter.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final ShortPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final ShortPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final ShortPredicate orPredicate = throwingPredicate.or(notThrowingPredicate);
        assertThatCode(() -> {
            orPredicate.test((short) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsTrue() {
        final ShortPredicate truePredicate = value -> true;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final ShortPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final ShortPredicate orPredicate = truePredicate.or(otherPredicate);
        orPredicate.test((short) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final ShortPredicate beforePredicate = value -> {
            execSeq.add("before");
            return false;
        };
        final ShortPredicate afterPredicate = value -> {
            execSeq.add("after");
            return false;
        };
        final ShortPredicate andPredicate = beforePredicate.or(afterPredicate);
        andPredicate.test((short) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void orMethodReturnsPredicateWithCorrectCalculation() {
        final ShortPredicate trueFor0Predicate = value -> value == (short) 0;
        final ShortPredicate falseFor0Predicate = value -> value != (short) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.or(trueFor0Predicate).test((short) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.or(falseFor0Predicate).test((short) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(trueFor0Predicate).test((short) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(falseFor0Predicate).test((short) 0))
                .isFalse();
        assertions.assertAll();
    }
}
