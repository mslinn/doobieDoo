import $ivy.`org.tpolecat::doobie-core:0.7.0`
import $ivy.`org.tpolecat::doobie-postgres:0.7.0`
import $ivy.`org.tpolecat::doobie-hikari:0.7.0`

import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats.effect.{ContextShift, IO, Resource}
import doobie.util.transactor.Transactor.Aux

implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)

val xaSynch: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",      // driver classname
    "jdbc:postgresql:world",      // connect URL (driver-specific)
    "postgres",                   // username
    "",                           // password
    ExecutionContexts.synchronous // just for testing
  )

val xaFixed: Resource[IO, HikariTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      te <- ExecutionContexts.cachedThreadPool[IO]    // our transaction EC
      xa <- HikariTransactor.newHikariTransactor[IO](
              "org.postgresql.Driver",                 // driver classname
              "jdbc:postgresql:world",                 // connect URL
              "sa",                                   // username
              "",                                     // password
              ce,                                     // await connection here
              te                                      // execute JDBC operations here
            )
    } yield xa

case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

sql"select code, name, population, gnp from country".
  query[Country].
  to[List].
  transact(xaSynch).
  unsafeRunSync
