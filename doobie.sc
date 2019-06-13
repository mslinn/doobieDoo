// TODO optimize imports once this works
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect.{ContextShift, IO}
import cats.implicits._
import fs2.Stream
implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)
val xa = Transactor.fromDriverManager[IO](
  "org.postgresql.Driver",     // driver classname
  "jdbc:postgresql:world",     // connect URL (driver-specific)
  "postgres",                  // user
  "",                          // password
  ExecutionContexts.synchronous // just for testing
)
case class Country(code: String, name: String, pop: Int, gnp: Option[Double])
sql"select code, name, population, gnp from country".
  query[Country].
  to[List].
  transact(xa).
  unsafeRunSync
