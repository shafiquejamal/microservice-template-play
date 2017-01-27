package plumbing

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpecLike, ShouldMatchers, Suite, BeforeAndAfterAll}

class MessageBrokerMessageDispatcherUTest
 extends TestKit(ActorSystem("TestSystem"))
  with FlatSpecLike
  with ShouldMatchers
  with StopSystemAfterAll
  with MockFactory {
  
}

trait StopSystemAfterAll extends BeforeAndAfterAll {

  this: TestKit with Suite =>

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }

}