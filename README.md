# DoobieDoo

This project contains an [Ammonite script](doobie.sc) and [sample code](src/main/scala/DoobieDoo.scala) for 
[Doobie issue 913](https://github.com/tpolecat/doobie/issues/914). 
The Ammonite script fails, don't know why, and I don't really care.

Issues demonstrated in the sample code:

 * There is no SQL delete example in the Doobie docs, so I show code to do that
 * Synchronous and asynchronous transactors are not drop-in replacements for each other, so I show how to use both
 * DDL to create and delete tables does nothing. I'd like suggestions on how to fix that.
 * The sample code always uses unbounded waits to execute transactions. I modified the code to time out after 30 seconds.
 * Serializing and deserializing case classes seems to require identical names of case class properties and sql table columns.
   Quill handles that well, and Doobie/Quill integration has no documentation.

## Running under Scala 2.12
To run under Scala 2.12, type:

    sbt run

You could also type:

    sbt "; ++2.12.8; run"

## Running under Scala 2.13

Editing `build.sbt` and changing these lines to read:

```
//scalaVersion := "2.12.8"   // comment this line to use Scala 2.13
scalaVersion := "2.13.0" // uncomment this line to use Scala 2.13
```

This error appears because Doobie has not yet released a version compatible with Scala 2.13:
```
[error] coursier.ResolutionException: Encountered 4 error(s) in dependency resolution:
[error]     org.tpolecat:doobie-hikari_2.13:0.7.0:
[error]         not found:
[error]             /home/mslinn/.ivy2/local/org.tpolecat/doobie-hikari_2.13/0.7.0/ivys/ivy.xml
[error]             https://repo1.maven.org/maven2/org/tpolecat/doobie-hikari_2.13/0.7.0/doobie-hikari_2.13-0.7.0.pom
[error]             https://dl.bintray.com/mslinn/maven/org/tpolecat/doobie-hikari_2.13/0.7.0/doobie-hikari_2.13-0.7.0.pom
[error]     org.tpolecat:doobie-scalatest_2.13:0.7.0:
[error]         not found:
[error]             /home/mslinn/.ivy2/local/org.tpolecat/doobie-scalatest_2.13/0.7.0/ivys/ivy.xml
[error]             https://repo1.maven.org/maven2/org/tpolecat/doobie-scalatest_2.13/0.7.0/doobie-scalatest_2.13-0.7.0.pom
[error]             https://dl.bintray.com/mslinn/maven/org/tpolecat/doobie-scalatest_2.13/0.7.0/doobie-scalatest_2.13-0.7.0.pom
[error]     org.tpolecat:doobie-postgres_2.13:0.7.0:
[error]         not found:
[error]             /home/mslinn/.ivy2/local/org.tpolecat/doobie-postgres_2.13/0.7.0/ivys/ivy.xml
[error]             https://repo1.maven.org/maven2/org/tpolecat/doobie-postgres_2.13/0.7.0/doobie-postgres_2.13-0.7.0.pom
[error]             https://dl.bintray.com/mslinn/maven/org/tpolecat/doobie-postgres_2.13/0.7.0/doobie-postgres_2.13-0.7.0.pom
[error]     org.tpolecat:doobie-core_2.13:0.7.0:
[error]         not found:
[error]             /home/mslinn/.ivy2/local/org.tpolecat/doobie-core_2.13/0.7.0/ivys/ivy.xml
[error]             https://repo1.maven.org/maven2/org/tpolecat/doobie-core_2.13/0.7.0/doobie-core_2.13-0.7.0.pom
[error]             https://dl.bintray.com/mslinn/maven/org/tpolecat/doobie-core_2.13/0.7.0/doobie-core_2.13-0.7.0.pom
```    

To run under Scala 2.13, you should be able to type:

    sbt "; ++2.13.0; run"
    
However, errors similar to the previously shown errors appear instead.


## Running the Program as an Assembly
The `bin/run` Bash script assembles this project into a fat jar and runs it.
Sample usage, which runs the `DoobieDoo` entry point in `src/main/scala/DoobieDoo.scala`:

```
$ bin/run
```

The `-j` option forces a rebuild of the fat jar. 
Use it after modifying the source code.

```
$ bin/run -j
```
