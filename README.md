# Java Advanced Labs

## Table of Contents

1. [ForkJoin Labs](#forkjoin-labs)
2. [NIO Labs](#nio-labs)
3. [Reflections & Annotations Labs](#reflections--annotations-labs)
4. [Spark Labs](#spark-labs)

---

## 1. ForkJoin Labs

### Extra: Fibonacci

**Location:** `forkJoin/labs/extra`  
**Main Files:** `Fibonacci.java`, `FibonacciTask.java`

**Purpose:**

- Demonstrates the calculation of Fibonacci numbers using both a simple recursive method and the Fork/Join framework for parallel computation.

**Main Classes:**

- **Fibonacci.java**: Contains a static recursive method to compute the nth Fibonacci number and a `main` method to print the 20th Fibonacci number.
- **FibonacciTask.java**: Extends `RecursiveTask<Integer>` to compute Fibonacci numbers in parallel using the Fork/Join framework.

**How to Run:**

- Run either `Fibonacci.java` or `FibonacciTask.java` as a Java application.
- `FibonacciTask` uses a `ForkJoinPool` to compute `Fibonacci(20)` in parallel.

---

### Fibonacci (ForkJoin vs Recursive)

**Location:** `forkJoin/labs/fibonacci`  
**Main Files:** `Main.java`, `Fibonacci.java`, `RecursiveFibonacci.java`

**Purpose:**

- Compares the performance of calculating Fibonacci numbers using the Fork/Join framework versus a simple recursive approach.

**Main Classes:**

- **Main.java**: Runs both the Fork/Join and simple recursive Fibonacci calculations for `n=20`, timing each approach and printing results.
- **Fibonacci.java**: Implements the Fork/Join version by extending `RecursiveTask<Integer>`.
- **RecursiveFibonacci.java**: Implements the classic recursive Fibonacci calculation.

**How to Run:**

- Run `Main.java` as a Java application to see both methods in action and compare their performance.

---

### MatrixMultiplier

**Location:** `forkJoin/labs/matrixMultiplier`  
**Main Files:** `PerformanceComparison.java`, `MatrixMultiplier.java`, `SequentialMatrixMultiplier.java`

**Purpose:**

- Compares matrix multiplication performance between a sequential approach and a parallel Fork/Join approach.

**Main Classes:**

- **PerformanceComparison.java**: Entry point; runs tests on small and large matrices, prints timing and correctness results.
- **MatrixMultiplier.java**: Implements parallel matrix multiplication using divide-and-conquer with the Fork/Join framework.
- **SequentialMatrixMultiplier.java**: Implements standard sequential matrix multiplication.

**How to Run:**

- Run `PerformanceComparison.java` as a Java application to see performance and correctness comparisons.

---

### SumTask

**Location:** `forkJoin/labs/sumTask`  
**Main Files:** `Main.java`, `SumTask.java`, `SimpleSum.java`

**Purpose:**

- Compares summing a large array using the Fork/Join framework versus a simple loop.

**Main Classes:**

- **Main.java**: Initializes a large array, sums it using Fork/Join (`SumTask`), and (optionally) using a simple loop (`SimpleSum`).
- **SumTask.java**: Extends `RecursiveTask<Long>` to sum an array in parallel, splitting the task recursively.
- **SimpleSum.java**: Provides a static method to sum an array using a simple loop.

**How to Run:**

- Run `Main.java` as a Java application to see the Fork/Join sum in action. Uncomment the simple loop section to compare both methods.

---

## 2. NIO Labs

### NioFileCopier

**Location:** `nio/labs/nioFileCopier`  
**Main Files:** `Main.java`, `NioFileCopier.java`, `IoFileCopier.java`, `ThreadMonitor.java`

**Purpose:**

- Demonstrates file copying using both traditional blocking I/O and non-blocking asynchronous NIO, comparing their performance and behavior.

**Main Classes:**

- **Main.java**: Entry point. Creates a test file if needed, then copies it using both traditional I/O and NIO, timing each and printing a performance summary.
- **IoFileCopier.java**: Implements traditional blocking file copy using streams.
- **NioFileCopier.java**: Implements non-blocking asynchronous file copy using `AsynchronousFileChannel` and completion handlers.
- **ThreadMonitor.java**: (Not detailed here) Used to monitor thread activity during copy operations.

**How to Run:**

- Run `Main.java` as a Java application. It will create a 10MB test file if needed, then copy it using both methods and print timing and throughput results.

---

### NioWrite

**Location:** `nio/labs/nioWrite`  
**Main Files:** `Main.java`, `NioWrite.java`

**Purpose:**

- Demonstrates non-blocking file writing using Java NIO's `AsynchronousFileChannel`.

**Main Classes:**

- **Main.java**: Entry point. Calls `writeToNioFile` to write a string to a file asynchronously.
- **NioWrite.java**: Implements the asynchronous file write logic with a completion handler and a latch for synchronization.

**How to Run:**

- Run `Main.java` as a Java application to write a string to `labs/nioWrite/open.txt` using non-blocking NIO.

---

## 3. Reflections & Annotations Labs

### ClassLoader

**Location:** `reflectionsXAnnotations/labs/classloader`  
**Main Files:** `CustomClassLoaderTestMain.java`, `CustomClassLoader.java`, `LoadableClass.java`, `Loadable.java`

**Purpose:**

- Demonstrates creating and using a custom class loader to load classes at runtime, check for annotations, and invoke methods reflectively.

**Main Classes:**

- **CustomClassLoaderTestMain.java**: Entry point. Uses `CustomClassLoader` to load a class, checks for the `@Loadable` annotation, and invokes its `load` method if present.
- **CustomClassLoader.java**: Implements a custom class loader that loads class files from a specified path.
- **LoadableClass.java**: A sample class annotated with `@Loadable` and containing a `load` method.
- **Loadable.java**: Annotation used to mark classes as loadable.

**How to Run:**

- Run `CustomClassLoaderTestMain.java` as a Java application. Ensure the compiled class files are in the expected output directory.

---

### Logger (LogExecutionTime)

**Location:** `reflectionsXAnnotations/labs/logger`  
**Main Files:** `Main.java`, `LogExecutionTimeProxy.java`, `MyServiceImpl.java`, `MyService.java`, `LogExecutionTime.java`

**Purpose:**

- Demonstrates the use of dynamic proxies and custom annotations to log method execution time.

**Main Classes:**

- **Main.java**: Entry point. Creates a proxy for `MyServiceImpl` and calls its methods.
- **LogExecutionTimeProxy.java**: Implements an invocation handler that logs execution time for methods annotated with `@LogExecutionTime`.
- **MyServiceImpl.java**: Implements `MyService` with methods annotated for timing.
- **MyService.java**: Service interface.
- **LogExecutionTime.java**: Custom annotation for marking methods to be timed.

**How to Run:**

- Run `Main.java` as a Java application. The proxy will print execution times for annotated methods.

---

### Practice: Annotations

**Location:** `reflectionsXAnnotations/labs/practice/anotations`  
**Main Files:** `Main.java`, `Cat.java`, `RunImmediately.java`, `ImportantString.java`, `VeryImportant.java`

**Purpose:**

- Demonstrates custom annotations for marking important fields, classes, and methods to be run immediately, and using reflection to process them.

**Main Classes:**

- **Main.java**: Creates a `Cat` object, uses reflection to invoke methods annotated with `@RunImmediately` and print fields annotated with `@ImportantString`.
- **Cat.java**: Annotated with `@VeryImportant`, has a field with `@ImportantString`, and a method with `@RunImmediately`.
- **RunImmediately.java**: Annotation for methods to be run immediately, with parameters for times, array, and name.
- **ImportantString.java**: Annotation for marking important string fields.
- **VeryImportant.java**: Annotation for marking important classes, methods, or fields.

**How to Run:**

- Run `Main.java` as a Java application to see annotation processing in action.

---

### Practice: Reflections

**Location:** `reflectionsXAnnotations/labs/practice/reflections`  
**Main Files:** `Main.java`, `User.java`

**Purpose:**

- Demonstrates basic reflection: accessing and modifying fields, invoking methods, and printing class structure.

**Main Classes:**

- **Main.java**: Uses reflection to set a private field and invoke a private method on a `User` object.
- **User.java**: Simple class with private fields and a private method.

**How to Run:**

- Run `Main.java` as a Java application to see reflection in action.

---

## 4. Spark Labs

### Apache Spark (Java)

**Location:** `spark/labs/apacheSpark/sparks/src/main/java/com/apachesparks`  
**Main Files:** `App.java`

**Purpose:**

- Demonstrates a word count application using Apache Spark in Java.

**Main Classes:**

- **App.java**: Entry point. Reads a text file, splits lines into words, counts occurrences, and prints the result using Spark's RDD API.

**How to Run:**

- Submit the application to a Spark cluster with the input file path as an argument:
  ```
  spark-submit --class com.apachesparks.App --master spark://spark-master:7077 target/spark.jar <input_file>
  ```

---

### WordCount MapReduce (Java)

**Location:** `spark/labs/wordCountMapReduce`  
**Main Files:** `WordCountMapReduce.java`, `sample.txt`

**Purpose:**

- Demonstrates a simple MapReduce implementation in Java to count word frequencies in a text file.

**Main Classes:**

- **WordCountMapReduce.java**: Reads a file, maps lines to word counts, reduces them to a total count, and prints the result.

**How to Run:**

- Run `WordCountMapReduce.java` as a Java application. It will read `sample.txt` and print word counts.

---
