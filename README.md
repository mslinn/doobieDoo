# DoobieDoo

This project contains an [Ammonite script](doobie.sc) and [sample code](src/main/scala/DoobieDoo.scala) for 
[Doobie issue 913](https://github.com/tpolecat/doobie/issues/914).

## Running under Scala 2.12
To run under Scala 2.12, type:

    sbt run

## Running under Scala 2.13
To run under Scala 2.13, you should be able to type:

    sbt "; ++ 2.13.0; run"
    
However, errors appear instead:

```
[error] java.lang.ClassNotFoundException: $c1c0eff133217aa33cb7$
[error]         at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:471)
[error]         at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:588)
[error]         at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
[error]         at java.base/java.lang.Class.forName0(Native Method)
[error]         at java.base/java.lang.Class.forName(Class.java:398)
[error]         at sbt.compiler.Eval$.getModule(Eval.scala:584)
[error]         at sbt.compiler.EvalDefinitions.values(Eval.scala:58)
[error]         at sbt.internal.DefinedSbtValues.$anonfun$values$1(LoadedSbtFile.scala:50)
[error]         at scala.collection.TraversableLike.$anonfun$flatMap$1(TraversableLike.scala:240)
[error]         at scala.collection.immutable.List.foreach(List.scala:388)
[error]         at scala.collection.TraversableLike.flatMap(TraversableLike.scala:240)
[error]         at scala.collection.TraversableLike.flatMap$(TraversableLike.scala:237)
[error]         at scala.collection.immutable.List.flatMap(List.scala:351)
[error]         at sbt.internal.DefinedSbtValues.values(LoadedSbtFile.scala:50)
[error]         at sbt.internal.EvaluateConfigurations$.$anonfun$evaluateSbtFile$4(EvaluateConfigurations.scala:166)
[error]         at sbt.internal.Load$.loadSettingsFile$1(Load.scala:1137)
[error]         at sbt.internal.Load$.$anonfun$discoverProjects$2(Load.scala:1144)
[error]         at scala.collection.MapLike.getOrElse(MapLike.scala:127)
[error]         at scala.collection.MapLike.getOrElse$(MapLike.scala:125)
[error]         at scala.collection.AbstractMap.getOrElse(Map.scala:59)
[error]         at sbt.internal.Load$.memoLoadSettingsFile$1(Load.scala:1143)
[error]         at sbt.internal.Load$.$anonfun$discoverProjects$4(Load.scala:1151)
[error]         at scala.collection.TraversableLike.$anonfun$map$1(TraversableLike.scala:233)
[error]         at scala.collection.mutable.ResizableArray.foreach(ResizableArray.scala:58)
[error]         at scala.collection.mutable.ResizableArray.foreach$(ResizableArray.scala:51)
[error]         at scala.collection.mutable.ArrayBuffer.foreach(ArrayBuffer.scala:47)
[error]         at scala.collection.TraversableLike.map(TraversableLike.scala:233)
[error]         at scala.collection.TraversableLike.map$(TraversableLike.scala:226)
[error]         at scala.collection.AbstractTraversable.map(Traversable.scala:104)
[error]         at sbt.internal.Load$.loadFiles$1(Load.scala:1151)
[error]         at sbt.internal.Load$.discoverProjects(Load.scala:1165)
[error]         at sbt.internal.Load$.discover$1(Load.scala:862)
[error]         at sbt.internal.Load$.loadTransitive(Load.scala:937)
[error]         at sbt.internal.Load$.loadProjects$1(Load.scala:726)
[error]         at sbt.internal.Load$.$anonfun$loadUnit$11(Load.scala:729)
[error]         at sbt.internal.Load$.timed(Load.scala:1395)
[error]         at sbt.internal.Load$.$anonfun$loadUnit$1(Load.scala:729)
[error]         at sbt.internal.Load$.timed(Load.scala:1395)
[error]         at sbt.internal.Load$.loadUnit(Load.scala:688)
[error]         at sbt.internal.Load$.$anonfun$builtinLoader$4(Load.scala:484)
[error]         at sbt.internal.BuildLoader$.$anonfun$componentLoader$5(BuildLoader.scala:176)
[error]         at sbt.internal.BuildLoader.apply(BuildLoader.scala:241)
[error]         at sbt.internal.Load$.loadURI$1(Load.scala:546)
[error]         at sbt.internal.Load$.loadAll(Load.scala:562)
[error]         at sbt.internal.Load$.loadURI(Load.scala:492)
[error]         at sbt.internal.Load$.load(Load.scala:471)
[error]         at sbt.internal.Load$.$anonfun$apply$1(Load.scala:251)
[error]         at sbt.internal.Load$.timed(Load.scala:1395)
[error]         at sbt.internal.Load$.apply(Load.scala:251)
[error]         at sbt.internal.Load$.defaultLoad(Load.scala:69)
[error]         at sbt.BuiltinCommands$.liftedTree1$1(Main.scala:829)
[error]         at sbt.BuiltinCommands$.doLoadProject(Main.scala:829)
[error]         at sbt.BuiltinCommands$.$anonfun$loadProjectImpl$2(Main.scala:800)
[error]         at sbt.Command$.$anonfun$applyEffect$4(Command.scala:142)
[error]         at sbt.Command$.$anonfun$applyEffect$2(Command.scala:137)
[error]         at sbt.Command$.process(Command.scala:181)
[error]         at sbt.MainLoop$.processCommand(MainLoop.scala:151)
[error]         at sbt.MainLoop$.$anonfun$next$2(MainLoop.scala:139)
[error]         at sbt.State$$anon$1.runCmd$1(State.scala:246)
[error]         at sbt.State$$anon$1.process(State.scala:250)
[error]         at sbt.MainLoop$.$anonfun$next$1(MainLoop.scala:139)
[error]         at sbt.internal.util.ErrorHandling$.wideConvert(ErrorHandling.scala:16)
[error]         at sbt.MainLoop$.next(MainLoop.scala:139)
[error]         at sbt.MainLoop$.run(MainLoop.scala:132)
[error]         at sbt.MainLoop$.$anonfun$runWithNewLog$1(MainLoop.scala:110)
[error]         at sbt.io.Using.apply(Using.scala:22)
[error]         at sbt.MainLoop$.runWithNewLog(MainLoop.scala:104)
[error]         at sbt.MainLoop$.runAndClearLast(MainLoop.scala:59)
[error]         at sbt.MainLoop$.runLoggedLoop(MainLoop.scala:44)
[error]         at sbt.MainLoop$.runLogged(MainLoop.scala:35)
[error]         at sbt.StandardMain$.runManaged(Main.scala:138)
[error]         at sbt.xMain.run(Main.scala:89)
[error]         at xsbt.boot.Launch$$anonfun$run$1.apply(Launch.scala:109)
[error]         at xsbt.boot.Launch$.withContextLoader(Launch.scala:128)
[error]         at xsbt.boot.Launch$.run(Launch.scala:109)
[error]         at xsbt.boot.Launch$$anonfun$apply$1.apply(Launch.scala:35)
[error]         at xsbt.boot.Launch$.launch(Launch.scala:117)
[error]         at xsbt.boot.Launch$.apply(Launch.scala:18)
[error]         at xsbt.boot.Boot$.runImpl(Boot.scala:56)
[error]         at xsbt.boot.Boot$.main(Boot.scala:18)
[error]         at xsbt.boot.Boot.main(Boot.scala)
```

Editing `build.sbt` and changing these lines to read:

```
//scalaVersion := "2.12.8"   // comment this line to use Scala 2.13
scalaVersion := "2.13.0" // uncomment this line to use Scala 2.13
```

Yields this error:
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

## Running the Program as an Assembly
The `bin/run` Bash script assembles this project into a fat jar and runs it.
Sample usage, which runs the `Hello` entry point in `src/main/scala/Hello.scala`:

```
$ bin/run DoobieDoo
```

This error results:

```
[error] 1 error was encountered during merge
[error] java.lang.RuntimeException: deduplicate: different file contents found in the following:
[error] /home/mslinn/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jruby/jcodings/jcodings/1.0.43/jcodings-1.0.43.jar:module-info.class
[error] /home/mslinn/.cache/coursier/v1/https/repo1.maven.org/maven2/org/jruby/joni/joni/2.1.27/joni-2.1.27.jar:module-info.class
[error]         at sbtassembly.Assembly$.applyStrategies(Assembly.scala:143)
[error]         at sbtassembly.Assembly$.x$1$lzycompute$1(Assembly.scala:25)
[error]         at sbtassembly.Assembly$.x$1$1(Assembly.scala:23)
[error]         at sbtassembly.Assembly$.stratMapping$lzycompute$1(Assembly.scala:23)
[error]         at sbtassembly.Assembly$.stratMapping$1(Assembly.scala:23)
[error]         at sbtassembly.Assembly$.inputs$lzycompute$1(Assembly.scala:68)
[error]         at sbtassembly.Assembly$.inputs$1(Assembly.scala:58)
[error]         at sbtassembly.Assembly$.apply(Assembly.scala:85)
[error]         at sbtassembly.Assembly$.$anonfun$assemblyTask$1(Assembly.scala:244)
[error]         at scala.Function1.$anonfun$compose$1(Function1.scala:44)
[error]         at sbt.internal.util.$tilde$greater.$anonfun$$u2219$1(TypeFunctions.scala:40)
[error]         at sbt.std.Transform$$anon$4.work(System.scala:67)
[error]         at sbt.Execute.$anonfun$submit$2(Execute.scala:269)
[error]         at sbt.internal.util.ErrorHandling$.wideConvert(ErrorHandling.scala:16)
[error]         at sbt.Execute.work(Execute.scala:278)
[error]         at sbt.Execute.$anonfun$submit$1(Execute.scala:269)
[error]         at sbt.ConcurrentRestrictions$$anon$4.$anonfun$submitValid$1(ConcurrentRestrictions.scala:178)
[error]         at sbt.CompletionService$$anon$2.call(CompletionService.scala:37)
[error]         at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error]         at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
[error]         at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error]         at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[error]         at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[error]         at java.base/java.lang.Thread.run(Thread.java:834)
[error] (assembly) deduplicate: different file contents found in the following:
```

The `-j` option forces a rebuild of the fat jar. 
Use it after modifying the source code.

```
$ bin/run -j DoobieDoo
```
