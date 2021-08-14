# MJFI - The Missing Java Functional Interfaces library

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.plugatar/mjfi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.plugatar/mjfi)
[![Javadoc](http://www.javadoc.io/badge/com.plugatar/mjfi.svg)](http://www.javadoc.io/doc/com.plugatar/mjfi)

---

This library extends `java.util.function` package with 130 functional interfaces for objects, primitive types
and operations between them.

## Roadmap
* only bug fixes

## How to use
The library has no dependencies. All you need is Java 8+ and this library.

Maven:
```xml
<dependency>
  <groupId>com.plugatar</groupId>
  <artifactId>mjfi</artifactId>
  <version>1.1.1</version>
</dependency>
```

Gradle:
```groovy
dependencies {
    compile 'com.plugatar:mjfi:1.1.1'
}
```

## List of interfaces
Include `java.util.function` package

| Input            | Output  | Functional interface                          |
| ---------------- | ------- | --------------------------------------------- |
| void             | T       | `java.util.function.Supplier`                 |
| T                | T       | `java.util.function.UnaryOperator`            |
| T                | R       | `java.util.function.Function`                 |
| T                | byte    | `com.plugatar.mjfi.ToByteFunction`            |
| T                | short   | `com.plugatar.mjfi.ToShortFunction`           |
| T                | int     | `java.util.function.ToIntFunction`            |
| T                | long    | `java.util.function.ToLongFunction`           |
| T                | float   | `com.plugatar.mjfi.ToFloatFunction`           |
| T                | double  | `java.util.function.ToDoubleFunction`         |
| T                | char    | `com.plugatar.mjfi.ToCharFunction`            |
| T                | boolean | `java.util.function.Predicate`                |
| T                | void    | `java.util.function.Consumer`                 |
| T, T             | T       | `java.util.function.BinaryOperator`           |
| T, U             | R       | `java.util.function.BiFunction`               |
| T, U             | byte    | `com.plugatar.mjfi.ToByteBiFunction`          |
| T, U             | short   | `com.plugatar.mjfi.ToShortBiFunction`         |
| T, U             | int     | `java.util.function.ToIntBiFunction`          |
| T, U             | long    | `java.util.function.ToLongBiFunction`         |
| T, U             | float   | `com.plugatar.mjfi.ToFloatBiFunction`         |
| T, U             | double  | `java.util.function.ToDoubleBiFunction`       |
| T, U             | char    | `com.plugatar.mjfi.ToCharBiFunction`          |
| T, U             | boolean | `java.util.function.BiPredicate`              |
| T, U             | void    | `java.util.function.BiConsumer`               |
| T, byte          | R       | `com.plugatar.mjfi.ObjByteFunction`           |
| T, byte          | byte    | `com.plugatar.mjfi.ObjByteToByteFunction`     |
| T, byte          | boolean | `com.plugatar.mjfi.ObjBytePredicate`          |
| T, byte          | void    | `com.plugatar.mjfi.ObjByteConsumer`           |
| T, short         | R       | `com.plugatar.mjfi.ObjShortFunction`          |
| T, short         | short   | `com.plugatar.mjfi.ObjShortToShortFunction`   |
| T, short         | boolean | `com.plugatar.mjfi.ObjShortPredicate`         |
| T, short         | void    | `com.plugatar.mjfi.ObjShortConsumer`          |
| T, int           | R       | `com.plugatar.mjfi.ObjIntFunction`            |
| T, int           | int     | `com.plugatar.mjfi.ObjIntToIntFunction`       |
| T, int           | boolean | `com.plugatar.mjfi.ObjIntPredicate`           |
| T, int           | void    | `java.util.function.ObjIntConsumer`           |
| T, long          | R       | `com.plugatar.mjfi.ObjLongFunction`           |
| T, long          | long    | `com.plugatar.mjfi.ObjLongToLongFunction`     |
| T, long          | boolean | `com.plugatar.mjfi.ObjLongPredicate`          |
| T, long          | void    | `java.util.function.ObjLongConsumer`          |
| T, float         | R       | `com.plugatar.mjfi.ObjFloatFunction`          |
| T, float         | float   | `com.plugatar.mjfi.ObjFloatToFloatFunction`   |
| T, float         | boolean | `com.plugatar.mjfi.ObjFloatPredicate`         |
| T, float         | void    | `com.plugatar.mjfi.ObjFloatConsumer`          |
| T, double        | R       | `com.plugatar.mjfi.ObjDoubleFunction`         |
| T, double        | double  | `com.plugatar.mjfi.ObjDoubleToDoubleFunction` |
| T, double        | boolean | `com.plugatar.mjfi.ObjDoublePredicate`        |
| T, double        | void    | `java.util.function.ObjDoubleConsumer`        |
| T, char          | R       | `com.plugatar.mjfi.ObjCharFunction`           |
| T, char          | char    | `com.plugatar.mjfi.ObjCharToCharFunction`     |
| T, char          | boolean | `com.plugatar.mjfi.ObjCharPredicate`          |
| T, char          | void    | `com.plugatar.mjfi.ObjCharConsumer`           |
| T, boolean       | R       | `com.plugatar.mjfi.ObjBooleanFunction`        |
| T, boolean       | boolean | `com.plugatar.mjfi.ObjBooleanPredicate`       |
| T, boolean       | void    | `com.plugatar.mjfi.ObjBooleanConsumer`        |
| void             | byte    | `com.plugatar.mjfi.ByteSupplier`              |
| byte             | R       | `com.plugatar.mjfi.ByteFunction`              |
| byte             | byte    | `com.plugatar.mjfi.ByteUnaryOperator`         |
| byte             | short   | `com.plugatar.mjfi.ByteToShortFunction`       |
| byte             | int     | `com.plugatar.mjfi.ByteToIntFunction`         |
| byte             | long    | `com.plugatar.mjfi.ByteToLongFunction`        |
| byte             | float   | `com.plugatar.mjfi.ByteToFloatFunction`       |
| byte             | double  | `com.plugatar.mjfi.ByteToDoubleFunction`      |
| byte             | char    | `com.plugatar.mjfi.ByteToCharFunction`        |
| byte             | boolean | `com.plugatar.mjfi.BytePredicate`             |
| byte             | void    | `com.plugatar.mjfi.ByteConsumer`              |
| byte, byte       | byte    | `com.plugatar.mjfi.ByteBinaryOperator`        |
| byte, byte       | boolean | `com.plugatar.mjfi.ByteBiPredicate`           |
| byte, byte       | R       | `com.plugatar.mjfi.ByteBiFunction`            |
| byte, byte       | void    | `com.plugatar.mjfi.ByteBiConsumer`            |
| void             | short   | `com.plugatar.mjfi.ShortSupplier`             |
| short            | R       | `com.plugatar.mjfi.ShortFunction`             |
| short            | byte    | `com.plugatar.mjfi.ShortToByteFunction`       |
| short            | short   | `com.plugatar.mjfi.ShortUnaryOperator`        |
| short            | int     | `com.plugatar.mjfi.ShortToIntFunction`        |
| short            | long    | `com.plugatar.mjfi.ShortToLongFunction`       |
| short            | float   | `com.plugatar.mjfi.ShortToFloatFunction`      |
| short            | double  | `com.plugatar.mjfi.ShortToDoubleFunction`     |
| short            | char    | `com.plugatar.mjfi.ShortToCharFunction`       |
| short            | boolean | `com.plugatar.mjfi.ShortPredicate`            |
| short            | void    | `com.plugatar.mjfi.ShortConsumer`             |
| short, short     | short   | `com.plugatar.mjfi.ShortBinaryOperator`       |
| short, short     | boolean | `com.plugatar.mjfi.ShortBiPredicate`          |
| short, short     | R       | `com.plugatar.mjfi.ShortBiFunction`           |
| short, short     | void    | `com.plugatar.mjfi.ShortBiConsumer`           |
| void             | int     | `java.util.function.IntSupplier`              |
| int              | R       | `java.util.function.IntFunction`              |
| int              | byte    | `com.plugatar.mjfi.IntToByteFunction`         |
| int              | short   | `com.plugatar.mjfi.IntToShortFunction`        |
| int              | int     | `java.util.function.IntUnaryOperator`         |
| int              | long    | `java.util.function.IntToLongFunction`        |
| int              | float   | `com.plugatar.mjfi.IntToFloatFunction`        |
| int              | double  | `java.util.function.IntToDoubleFunction`      |
| int              | char    | `com.plugatar.mjfi.IntToCharFunction`         |
| int              | boolean | `java.util.function.IntPredicate`             |
| int              | void    | `java.util.function.IntConsumer`              |
| int, int         | int     | `java.util.function.IntBinaryOperator`        |
| int, int         | boolean | `com.plugatar.mjfi.IntBiPredicate`            |
| int, int         | R       | `com.plugatar.mjfi.IntBiFunction`             |
| int, int         | void    | `com.plugatar.mjfi.IntBiConsumer`             |
| void             | long    | `java.util.function.LongSupplier`             |
| long             | R       | `java.util.function.LongFunction`             |
| long             | byte    | `com.plugatar.mjfi.LongToByteFunction`        |
| long             | short   | `com.plugatar.mjfi.LongToShortFunction`       |
| long             | int     | `java.util.function.LongToIntFunction`        |
| long             | long    | `java.util.function.LongUnaryOperator`        |
| long             | float   | `com.plugatar.mjfi.LongToFloatFunction`       |
| long             | double  | `java.util.function.LongToDoubleFunction`     |
| long             | char    | `com.plugatar.mjfi.LongToCharFunction`        |
| long             | boolean | `java.util.function.LongPredicate`            |
| long             | void    | `java.util.function.LongConsumer`             |
| long, long       | long    | `java.util.function.LongBinaryOperator`       |
| long, long       | boolean | `com.plugatar.mjfi.LongBiPredicate`           |
| long, long       | R       | `com.plugatar.mjfi.LongBiFunction`            |
| long, long       | void    | `com.plugatar.mjfi.LongBiConsumer`            |
| void             | float   | `com.plugatar.mjfi.FloatSupplier`             |
| float            | R       | `com.plugatar.mjfi.FloatFunction`             |
| float            | byte    | `com.plugatar.mjfi.FloatToByteFunction`       |
| float            | short   | `com.plugatar.mjfi.FloatToShortFunction`      |
| float            | int     | `com.plugatar.mjfi.FloatToIntFunction`        |
| float            | long    | `com.plugatar.mjfi.FloatToLongFunction`       |
| float            | float   | `com.plugatar.mjfi.FloatUnaryOperator`        |
| float            | double  | `com.plugatar.mjfi.FloatToDoubleFunction`     |
| float            | char    | `com.plugatar.mjfi.FloatToCharFunction`       |
| float            | boolean | `com.plugatar.mjfi.FloatPredicate`            |
| float            | void    | `com.plugatar.mjfi.FloatConsumer`             |
| float, float     | float   | `com.plugatar.mjfi.FloatBinaryOperator`       |
| float, float     | boolean | `com.plugatar.mjfi.FloatBiPredicate`          |
| float, float     | R       | `com.plugatar.mjfi.FloatBiFunction`           |
| float, float     | void    | `com.plugatar.mjfi.FloatBiConsumer`           |
| void             | double  | `java.util.function.DoubleSupplier`           |
| double           | R       | `java.util.function.DoubleFunction`           |
| double           | byte    | `com.plugatar.mjfi.DoubleToByteFunction`      |
| double           | short   | `com.plugatar.mjfi.DoubleToShortFunction`     |
| double           | int     | `java.util.function.DoubleToIntFunction`      |
| double           | long    | `java.util.function.DoubleToLongFunction`     |
| double           | float   | `com.plugatar.mjfi.DoubleToFloatFunction`     |
| double           | double  | `java.util.function.DoubleUnaryOperator`      |
| double           | char    | `com.plugatar.mjfi.DoubleToCharFunction`      |
| double           | boolean | `java.util.function.DoublePredicate`          |
| double           | void    | `java.util.function.DoubleConsumer`           |
| double, double   | double  | `java.util.function.DoubleBinaryOperator`     |
| double, double   | boolean | `com.plugatar.mjfi.DoubleBiPredicate`         |
| double, double   | R       | `com.plugatar.mjfi.DoubleBiFunction`          |
| double, double   | void    | `com.plugatar.mjfi.DoubleBiConsumer`          |
| void             | char    | `com.plugatar.mjfi.CharSupplier`              |
| char             | R       | `com.plugatar.mjfi.CharFunction`              |
| char             | byte    | `com.plugatar.mjfi.CharToByteFunction`        |
| char             | short   | `com.plugatar.mjfi.CharToShortFunction`       |
| char             | int     | `com.plugatar.mjfi.CharToIntFunction`         |
| char             | long    | `com.plugatar.mjfi.CharToLongFunction`        |
| char             | float   | `com.plugatar.mjfi.CharToFloatFunction`       |
| char             | double  | `com.plugatar.mjfi.CharToDoubleFunction`      |
| char             | char    | `com.plugatar.mjfi.CharUnaryOperator`         |
| char             | boolean | `com.plugatar.mjfi.CharPredicate`             |
| char             | void    | `com.plugatar.mjfi.CharConsumer`              |
| char, char       | char    | `com.plugatar.mjfi.CharBinaryOperator`        |
| char, char       | boolean | `com.plugatar.mjfi.CharBiPredicate`           |
| char, char       | R       | `com.plugatar.mjfi.CharBiFunction`            |
| char, char       | void    | `com.plugatar.mjfi.CharBiConsumer`            |
| void             | boolean | `java.util.function.BooleanSupplier`          |
| boolean          | R       | `com.plugatar.mjfi.BooleanFunction`           |
| boolean          | byte    | `com.plugatar.mjfi.BooleanToByteFunction`     |
| boolean          | short   | `com.plugatar.mjfi.BooleanToShortFunction`    |
| boolean          | int     | `com.plugatar.mjfi.BooleanToIntFunction`      |
| boolean          | long    | `com.plugatar.mjfi.BooleanToLongFunction`     |
| boolean          | float   | `com.plugatar.mjfi.BooleanToFloatFunction`    |
| boolean          | double  | `com.plugatar.mjfi.BooleanToDoubleFunction`   |
| boolean          | char    | `com.plugatar.mjfi.BooleanToCharFunction`     |
| boolean          | boolean | `com.plugatar.mjfi.BooleanUnaryOperator`      |
| boolean          | void    | `com.plugatar.mjfi.BooleanConsumer`           |
| boolean, boolean | boolean | `com.plugatar.mjfi.BooleanBinaryOperator`     |
| boolean, boolean | R       | `com.plugatar.mjfi.BooleanBiFunction`         |
| boolean, boolean | void    | `com.plugatar.mjfi.BooleanBiConsumer`         |
