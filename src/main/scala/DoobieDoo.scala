
/** Create a table called Country in a database called world according to the
  * [Doobie docs](https://tpolecat.github.io/doobie/docs/04-Selecting.html). */
case class Country(code: String, name: String, population: Int, gnp: Option[Double])

object DoobieDoo extends App {
  import cats.effect.{ContextShift, IO, Resource}
  import doobie._
  import doobie.hikari.HikariTransactor
  import doobie.implicits._
  import doobie.util.ExecutionContexts
  import doobie.util.transactor.Transactor.Aux
  import scala.concurrent.duration._
  import scala.language.postfixOps

  /** Just used for non-blocking work.
    * See https://tpolecat.github.io/doobie/migration.html#transactors-and-threading */
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)

  val sqlTimeout: Duration = 30 seconds

  /** Single threaded, just for testing */
  val xaSynch: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",      // driver classname
    "jdbc:postgresql:world",      // connect URL (driver-specific)
    "postgres",                   // username
    "",                           // password
    ExecutionContexts.synchronous
  )

  /** Multi-threaded transactional resource. Modified for Postgres from
    * https://tpolecat.github.io/doobie/docs/14-Managing-Connections.html#using-a-hikaricp-connection-pool */
  val xaAsynch: Resource[IO, HikariTransactor[IO]] =
    for {
      connectEC     <- ExecutionContexts.fixedThreadPool[IO](32)
      transactionEC <- ExecutionContexts.cachedThreadPool[IO]
      xa            <- HikariTransactor.newHikariTransactor[IO](
        "org.postgresql.Driver",   // driver classname
        "jdbc:postgresql:world",   // connect URL (driver-specific)
        "postgres",                // username
        "",                        // password
        connectEC,                 // await connection here
        transactionEC              // execute JDBC operations here
      )
    } yield xa

  // FIXME Does not do anything (table is not dropped)
  val drop = sql"""DROP TABLE IF EXISTS country""".update.run

  // FIXME Does not do anything (table is not created)
  // The sample code in the Doobie docs did not have a large enough datatype to hold the US Q1 GNP so I increased it
  val create = sql"""CREATE TABLE country (
                    |  code       character(3)  NOT NULL,
                    |  name       text          NOT NULL,
                    |  population integer       NOT NULL,
                    |  gnp        numeric(16, 2)
                    |)
                    |""".stripMargin.update.run

  /** From https://tpolecat.github.io/doobie/docs/07-Updating.html#inserting */
  def insert1(country: Country): Update0 =
    sql"""insert into country (
         |  code, name, population, gnp
         |) values (
         |  ${country.code}, ${country.name}, ${country.population}, ${country.gnp}
         |)""".stripMargin.update

  // I could not find a code example in the docs for deleting records. This is my version using xaSynch:
  sql"delete from country".update.run.transact(xaSynch).unsafeRunTimed(sqlTimeout)

  /** Delete a case class instance from the database */
  def delete(country: Country): Option[Int] = xaAsynch.use { xa =>
    sql"delete from country where code = '${ country.code }'".update.run.transact(xa)
  }.unsafeRunTimed(sqlTimeout)

  // Data taken from https://www.worldometers.info/world-population/us-population/
  // Data taken from https://tradingeconomics.com/united-states/gross-national-product (for Q1/19)
  val usa = Country("USA", "United States", 328964220, Some(19132940000000.0))
  insert1(usa).run.transact(xaSynch).unsafeRunTimed(sqlTimeout)

  val result: List[Country] = xaAsynch.use { xa =>
    sql"select code, name, population, gnp from country"
    .query[Country]
    .to[List]
    .transact(xa)
  }.unsafeRunTimed(sqlTimeout).toList.flatten

  println(result.mkString("\n"))
}
