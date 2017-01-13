package entrypoint

import com.eigenroute.authentication.{AuthenticatedActionCreator, JWTAlgorithmProvider, JWTPublicKeyProvider}
import com.eigenroute.plumbing.PublisherSubscriber
import com.eigenroute.time.TimeProvider
import com.google.inject.Inject
import play.api.mvc._
import domain.API

class SomeController @Inject()(
    subscriber: PublisherSubscriber,
    api: API,
    override val timeProvider: TimeProvider,
    override val jWTAlgorithmProvider: JWTAlgorithmProvider,
    override val jWTPublicKeyProvider: JWTPublicKeyProvider
  ) extends Controller with AuthenticatedActionCreator {

  def post = AuthenticatedAction(parse.json) { request =>
    Ok
  }

}
