import cats.effect.{ContextShift, IO, Resource}
import doobie._
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor.Aux

/** Create a table called Country in a database called world according to the
  * [Doobie docs](https://tpolecat.github.io/doobie/docs/04-Selecting.html). */
case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

object DoobieDoo extends App {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)

  val xaSynch: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",     // driver classname
    "jdbc:postgresql:world",     // connect URL (driver-specific)
    "postgres",                  // user
    "",                          // password
    ExecutionContexts.synchronous // just for testing
  )

  /** Modified for Postgres from https://tpolecat.github.io/doobie/docs/14-Managing-Connections.html#using-a-hikaricp-connection-pool
    * Note that the type for xaFixed does not match xaSynch's type, and the following error message appears if it is used.
    * Error:(47, 14) type mismatch;
    * found   : cats.effect.Resource[cats.effect.IO,doobie.hikari.HikariTransactor[cats.effect.IO]]
    * (which expands to)  cats.effect.Resource[cats.effect.IO,doobie.util.transactor.Transactor[cats.effect.IO]{type A = com.zaxxer.hikari.HikariDataSource}]
    * required: doobie.util.transactor.Transactor[?]
    * transact(xaFixed). */
  val xaFixed: Resource[IO, HikariTransactor[IO]] =
    for {
      connectEC <- ExecutionContexts.fixedThreadPool[IO](32)
      transactionEC <- ExecutionContexts.cachedThreadPool[IO]
      xa <- HikariTransactor.newHikariTransactor[IO](
              "org.postgresql.Driver",                // driver classname
              "jdbc:postgresql:world",                // connect URL
              "sa",                                   // username
              "",                                     // password
              connectEC,                              // await connection here
              transactionEC                           // execute JDBC operations here
            )
    } yield xa

  // Does not do anything (table is not dropped)
  val drop = sql"""DROP TABLE IF EXISTS country""".update.run

  // Does not do anything (table is not created)
  // sample code did not have a large enough datatype to hold the US GNP so I increased it
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
         |  ${country.code}, ${country.name}, ${country.pop}, ${country.gnp}
         |)""".stripMargin.update

  // I could not find a code example in the docs for deleting records. This is my attempt:
  sql"delete from country".update.run.transact(xaSynch).unsafeRunSync

  // See https://www.worldometers.info/world-population/us-population/
  // See https://tradingeconomics.com/united-states/gross-national-product (this is for Q1/19)
  val usa = Country("USA", "United States", 328964220, Some(19132940000000.0))
  insert1(usa).run.transact(xaSynch).unsafeRunSync

  val result: Seq[Country] = sql"select code, name, population, gnp from country"
    .query[Country]
    .to[List]
    .transact(xaSynch)
    .unsafeRunSync

  println(result.mkString("\n"))
}
