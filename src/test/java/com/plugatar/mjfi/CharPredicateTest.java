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
 * Test case for {@link CharPredicate}.
 */
final class CharPredicateTest extends LambdaContractTest {

    CharPredicateTest() {
        super(
                CharPredicate.class,
                boolean.class, "test", new Class[]{char.class}, new Class[]{}
        );
    }

    @Test
    void andMethodThrowNPEForNullArg() {
        final CharPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.and(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotCatchException() {
        final CharPredicate notThrowingPredicate = value -> true;
        final CharPredicate throwingPredicate = value -> {throw new TestException();};
        final CharPredicate andPredicateWithExceptionInBefore = throwingPredicate.and(notThrowingPredicate);
        final CharPredicate andPredicateWithExceptionInAfter = notThrowingPredicate.and(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInBefore.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            andPredicateWithExceptionInAfter.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final CharPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final CharPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final CharPredicate andPredicate = throwingPredicate.and(notThrowingPredicate);
        assertThatCode(() -> {
            andPredicate.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsFalse() {
        final CharPredicate falsePredicate = value -> false;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final CharPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final CharPredicate andPredicate = falsePredicate.and(otherPredicate);
        andPredicate.test((char) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void andMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final CharPredicate beforePredicate = value -> {
            execSeq.add("before");
            return true;
        };
        final CharPredicate afterPredicate = value -> {
            execSeq.add("after");
            return true;
        };
        final CharPredicate andPredicate = beforePredicate.and(afterPredicate);
        andPredicate.test((char) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void andMethodReturnsPredicateWithCorrectCalculation() {
        final CharPredicate trueFor0Predicate = value -> value == (char) 0;
        final CharPredicate falseFor0Predicate = value -> value != (char) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.and(trueFor0Predicate).test((char) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.and(falseFor0Predicate).test((char) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(trueFor0Predicate).test((char) 0))
                .isFalse();
        assertions.assertThat(falseFor0Predicate.and(falseFor0Predicate).test((char) 0))
                .isFalse();
        assertions.assertAll();
    }

    @Test
    void negateMethodReturnsCorrectPredicate() {
        final CharPredicate truePredicate = value -> true;
        final CharPredicate falsePredicate = value -> false;
        final CharPredicate negateTruePredicate = truePredicate.negate();
        final CharPredicate negateFalsePredicate = falsePredicate.negate();
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(negateTruePredicate.test((char) 0))
                .isFalse();
        assertions.assertThat(negateFalsePredicate.test((char) 0))
                .isTrue();
        assertions.assertAll();
    }

    @Test
    void orMethodThrowNPEForNullArg() {
        final CharPredicate predicate = value -> true;
        assertThatCode(() -> {
            predicate.or(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotCatchException() {
        final CharPredicate notThrowingPredicate = value -> false;
        final CharPredicate throwingPredicate = value -> {throw new TestException();};
        final CharPredicate orPredicateWithExceptionInBefore = throwingPredicate.or(notThrowingPredicate);
        final CharPredicate orPredicateWithExceptionInAfter = notThrowingPredicate.or(throwingPredicate);
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInBefore.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertThatCode(() -> {
            orPredicateWithExceptionInAfter.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertions.assertAll();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstThrowException() {
        final CharPredicate throwingPredicate = value -> {throw new TestException();};
        final AtomicBoolean isNotThrowingPredicateInvoked = new AtomicBoolean();
        final CharPredicate notThrowingPredicate = value -> {
            isNotThrowingPredicateInvoked.set(true);
            return true;
        };
        final CharPredicate orPredicate = throwingPredicate.or(notThrowingPredicate);
        assertThatCode(() -> {
            orPredicate.test((char) 0);
        }).isInstanceOf(TestException.class);
        assertThat(isNotThrowingPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateThatDoesNotInvokeOtherPredicateIfFirstReturnsTrue() {
        final CharPredicate truePredicate = value -> true;
        final AtomicBoolean isOtherPredicateInvoked = new AtomicBoolean();
        final CharPredicate otherPredicate = value -> {
            isOtherPredicateInvoked.set(true);
            return true;
        };
        final CharPredicate orPredicate = truePredicate.or(otherPredicate);
        orPredicate.test((char) 0);
        assertThat(isOtherPredicateInvoked.get())
                .isFalse();
    }

    @Test
    void orMethodReturnsPredicateWithCorrectExecutionOrder() {
        final List<String> execSeq = new ArrayList<>();
        final CharPredicate beforePredicate = value -> {
            execSeq.add("before");
            return false;
        };
        final CharPredicate afterPredicate = value -> {
            execSeq.add("after");
            return false;
        };
        final CharPredicate andPredicate = beforePredicate.or(afterPredicate);
        andPredicate.test((char) 0);
        assertThat(execSeq)
                .containsExactly("before", "after");
    }

    @Test
    void orMethodReturnsPredicateWithCorrectCalculation() {
        final CharPredicate trueFor0Predicate = value -> value == (char) 0;
        final CharPredicate falseFor0Predicate = value -> value != (char) 0;
        final SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(trueFor0Predicate.or(trueFor0Predicate).test((char) 0))
                .isTrue();
        assertions.assertThat(trueFor0Predicate.or(falseFor0Predicate).test((char) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(trueFor0Predicate).test((char) 0))
                .isTrue();
        assertions.assertThat(falseFor0Predicate.or(falseFor0Predicate).test((char) 0))
                .isFalse();
        assertions.assertAll();
    }
}
