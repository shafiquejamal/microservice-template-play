package plumbing.messagebroker

import akka.actor.{ActorSystem, Props}
import com.eigenroute.plumbing.{MessageBrokerMessageType, RabbitMQPublisherSubscriber}
import com.google.inject.Inject
import play.api.inject.ApplicationLifecycle

class MessageBrokerMessageSubscriber @Inject()(
    override val actorSystem: ActorSystem,
    override val lifecycle: ApplicationLifecycle
  )
  extends RabbitMQPublisherSubscriber {

  override val props: Props = MessageBrokerMessageDispatcher.props
  override val convert: (String) => Option[MessageBrokerMessageType] = ???

}
