# Assignment 1: Programming Principles, SNU 4190.210

## Restrictions

**WARNING: Please read the restrictions below carefully.** 

If you do not follow it, **your submission will not be graded.**

1. Do not use the keyword `var`. Use `val` and `def` instead.
2. Do not use any library functions or data structures like `List`, `Array`, `Range` (`1 to n`, `1 until n` ...), `fold`, `map`, `reduce` or etc.
   You can only use tuples, `scala.annotation.tailrec`, and `scala.util.control.TailCalls._` from the library.
3. Do not use Scala's iteration (loop) syntax (`for`, `while`, `do-while`, `yield`)

Again, your score will be zero if you do not follow these rules.

Note that these rules will be gradually relaxed through the next assignments.

## Grading 

For problem 1 and 3, 50% of the test cases will require tail call optimizations (i.e., large inputs)
and the other 50% will not (i.e., small inputs).

So, you will get at least 50% of the score if you submit a correct program without tail call optimization.

## How to run

### IntelliJ IDEA

1. Open `hw1` directory from IntelliJ IDEA
2. Mark `src` folder as **Source root** like below
![how_to_run.png](how_to_run.png)
3. Mark `test` folder as **Test source root** by the same way.
4. Run `object Test extends App` from `test/hw1/Test.scala` to test your implementation.

### Terminal

You mush check that `scalac` and `scala` are runnable from your terminal.

**Windows**

```shell
./compile.bat
./run.bat
```

**Mac, Linux**

```shell
bash ./compile.sh
bash ./run.sh
```

## Q&A

If you have any questions, please submit an issue to [pp202202 issue tracker](https://github.com/snu-sf-class/pp202202/issues).

## Submissions

We are constructing a submission server. We will let you know when the server is ready. 





