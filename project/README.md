# Project: Programming Principles, SNU 4190.210

Refer to the [manual](pp-proj.pdf), make complete four scripts below.

- [ExprInterpreter](src/main/scala/pp202202/project/impl/ExprInterpreter.scala)
- [LazyOpsImpl](src/main/scala/pp202202/project/impl/LazyOpsImpl.scala)
- [MapEnvImpl](src/main/scala/pp202202/project/impl/MapEnvImpl.scala)
- [BrainFX](src/main/scala/pp202202/project/impl/BrainFX.scala)

You cannot fix the other codes except those codes.

## IMPORTANT: Submissions

You should zip **`impl`** directory from `src/main/scala/pp202202/project` and submit it. That means, you have to submit
only four scripts.

The structure of the zip file must look like this.

```
impl
  - ExprInterpreter.scala
  - MapEnvImpl.scala
  - LazyOpsImpl.scala
  - BrainFX.scala
```

Submission page will be opened next week.

## How to run and test

This project uses [SBT](https://www.scala-sbt.org/) to build and test Scala codes.

IntelliJ IDEA has a builtin support for SBT, so you should just open this directory from IntelliJ.

If you want to run this project manually, please install SBT manually and run below.

```bash
# compile scripts
sbt compile

# run 'src/main/scala/pp202202/project/Main.scala`
sbt run

# run test suites by scalatest
# you can find some tests from 'src/test/scala'
sbt test
```

If you have any troubles related with SBT or execution, please submit an issue
to [issue tracker](https://github.com/snu-sf-class/pp202202/issues).

## Restrictions

- Do not use keyword `var` (except in `EVLazyVal`). Use `val` and `def` instead.
    - To implement laziness, you should exploit `var evaluated` from `LVLazy`
- You cannot use mutable data structures like `Java.Array`
- You cannot use type casting methods like `asInstanceOf` or `isInstanceOf`

## Q&A

If you have any questions, please submit an issue
to [pp202202 issue tracker](https://github.com/snu-sf-class/pp202202/issues).
