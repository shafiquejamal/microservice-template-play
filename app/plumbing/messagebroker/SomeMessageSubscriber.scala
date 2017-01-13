package plumbing.messagebroker

import akka.actor.{ActorRef, ActorSystem}
import com.eigenroute.plumbing.{MessageBrokerMessage, MessageBrokerMessageType, RabbitMQPublisherSubscriber}
import com.google.inject.Inject
import play.api.inject.ApplicationLifecycle

class SomeMessageSubscriber @Inject()(
    override val actorSystem: ActorSystem,
    override val lifecycle: ApplicationLifecycle
  )
  extends RabbitMQPublisherSubscriber[MessageBrokerMessage] {

  override val exchange: String = "some-exchange-name"
  override val queueName: String = "some-que-name"
  override val routingActor: ActorRef = actorSystem.actorOf(RoutingActor.props, "MessageRouter")
  override val convert: (String) => Option[MessageBrokerMessageType] = ???

}
