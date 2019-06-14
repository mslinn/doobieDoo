import cats.free.Free
import doobie.free.connection

case class Foo(id: Int, value: String)

object DoobieDoobieDoo extends App {
  import doobie.quill.DoobieContext
  import io.getquill._
  import scala.language.postfixOps

  val dc = new DoobieContext.Postgres(Literal)
  import DoobieDoobieDoo.dc._

  val q0: dc.Quoted[dc.EntityQuery[Country]] = quote { query[Country].filter(_.code == "USA") }
  val x0: doobie.ConnectionIO[List[Country]] = run(q0)
  println(s"USA countries: ${ x0 }") // output is not right, how to obtain the value?

  val q1: dc.Quoted[dc.EntityQuery[Country]] = quote { query[Country].filter(_.code == "GBR") }
  val x: Free[connection.ConnectionOp, Option[Country]] = run(q1).map(_.headOption)
  println(s"GBR countries: ${ x }") // output is not right, how to obtain the value?

  val u1: dc.Quoted[dc.Update[Country]] = quote { query[Country].filter(_.name like "U%").update(_.name -> "foo") }
  val updateCount: doobie.ConnectionIO[Long] = run(u1)

  // A batch update.
  val u2 = quote {
    liftQuery(List("U%", "A%")).foreach { pat =>
      query[Country].filter(_.name like pat).update(_.name -> "foo")
    }
  }
  val batchUpdateCount: doobie.ConnectionIO[List[Long]] = run(u2)

  val u3: dc.Quoted[dc.ActionReturning[Foo, Index]] = quote {
    query[Foo].insert(lift(Foo(0, "Joe"))).returning(_.id)
  }
  val u3Index: doobie.ConnectionIO[dc.Index] = run(u3)

  val u4 = quote {
    liftQuery(List(Foo(0, "Joe"), Foo(0, "Bob"))).foreach { a =>
      query[Foo].insert(a).returning(_.id)
    }
  }
  val u4Index: doobie.ConnectionIO[List[Index]] = run(u4)

  // I could not find a code example in the docs for deleting records. This is my version using xaSynch:
  //sql"delete from country".update.run.transact(xaSynch).unsafeRunTimed(sqlTimeout)

  /** Delete a case class instance from the database */
//  def delete(country: Country): Option[Int] = xaAsynch.use { xa =>
//    sql"delete from country where code = '${ country.code }'".update.run.transact(xa)
//  }.unsafeRunTimed(sqlTimeout)

}
