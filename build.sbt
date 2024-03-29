assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("module-info.class")       => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

cancelable := true

crossScalaVersions := Seq("2.12.10", "2.13.1")

developers := List(
  Developer("mslinn",
            "Mike Slinn",
            "mslinn@micronauticsresearch.com",
            url("https://github.com/mslinn")
  )
)

// define the statements initially evaluated when entering 'console', 'console-quick', but not 'console-project'
initialCommands in console := """
                                |""".stripMargin

javacOptions ++= Seq(
  "-Xlint:deprecation",
  "-Xlint:unchecked",
  "-source", "1.8",
  "-target", "1.8",
  "-g:vars"
)

// I used extra dependencies to mirror another project which fails... seems these dependencies all play together fine
lazy val doobieVersion = "0.7.0"
libraryDependencies ++= Seq(
  "ch.qos.logback"             %  "logback-classic"  % "1.2.3"                withSources(),
  "com.fasterxml.jackson.core" %  "jackson-databind" % "2.9.9"                withSources(), // extra
  "com.github.scopt"           %% "scopt"            % "4.0.0-RC2"            withSources(), // extra
  "net.thisptr"                %  "jackson-jq"       % "0.0.10"               withSources(), // extra
  "org.eclipse.jgit"           %  "org.eclipse.jgit" % "5.3.1.201904271842-r" withSources(), // extra
  "org.tpolecat"               %% "doobie-core"      % doobieVersion          withSources(),
  "org.tpolecat"               %% "doobie-postgres"  % doobieVersion          withSources(),
  "org.tpolecat"               %% "doobie-hikari"    % doobieVersion          withSources(),
  "org.tpolecat"               %% "doobie-hikari"    % doobieVersion          withSources(),
  "org.tpolecat"               %% "doobie-quill"     % doobieVersion          withSources(),
  //
  "org.scalatest"              %% "scalatest"        % "3.1.0-SNAP9" % Test withSources(),
  "org.tpolecat"               %% "doobie-scalatest" % doobieVersion % Test withSources(),
  "junit"                      %  "junit"            % "4.12"        % Test
)

// If you want to apply a license, such as the Apache 2 license, uncomment the following:
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

logBuffered in Test := false

logLevel := Level.Warn

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Warn

// Level.INFO is needed to see detailed output when running tests
logLevel in test := Level.Info

name := "doobie-doo"

organization := "com.micronautics"

resolvers ++= Seq(
)

scalacOptions ++= Seq( // From https://tpolecat.github.io/2017/04/25/scalac-flags.html
  "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8",                // Specify character encoding used by source files.
  "-explaintypes",                     // Explain type errors in more detail.
  "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
  "-language:higherKinds",             // Allow higher-kinded types
  "-language:implicitConversions",     // Allow definition of implicit functions called views
  "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
  //"-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
  "-Xfuture",                          // Turn on future language features.
  "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
  "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
  "-Xlint:option-implicit",            // Option.apply used implicit view.
  "-Xlint:package-object-classes",     // Class or object defined in package object.
  "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match"               // Pattern match may not be typesafe.
)

scalacOptions ++=
  scalaVersion {
    case sv if sv.startsWith("2.13") => List(
    )

    case sv if sv.startsWith("2.12") => List(
      "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
      "-Ypartial-unification",             // Enable partial unification in type constructor inference
      //"-Ywarn-dead-code",                  // Warn when dead code is identified.
      "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
      "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
      "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
      "-Ywarn-numeric-widen"               // Warn when numerics are widened.
      //"-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
      //"-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
      //"-Ywarn-unused:locals",              // Warn if a local definition is unused.
      //"-Ywarn-unused:params",              // Warn if a value parameter is unused.
      //"-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
      //"-Ywarn-unused:privates",            // Warn if a private member is unused.
      //"-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
    )

    case _ => Nil
  }.value

// The REPL can’t cope with -Ywarn-unused:imports or -Xfatal-warnings so turn them off for the console
scalacOptions in (Compile, console) --= Seq("-Ywarn-unused:imports", "-Xfatal-warnings")

scalacOptions in (Compile, doc) ++= baseDirectory.map {
  bd: File => Seq[String](
     "-sourcepath", bd.getAbsolutePath,
     "-doc-source-url", "https://github.com/mslinn/doobie-doo/tree/master€{FILE_PATH}.scala"
  )
}.value

scalaVersion := "2.12.10"  // comment this line to use Scala 2.13
//scalaVersion := "2.13.1" // comment this line to use Scala 2.12

scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/mslinn/$name"),
    s"git@github.com:mslinn/$name.git"
  )
)

sublimeTransitive := true

version := "0.1.0"
