import org.scalatest._

class DoobieTest extends WordSpec with MustMatchers {
  "The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world".length === 11
    }
  }
}
