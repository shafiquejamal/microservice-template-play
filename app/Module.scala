import com.eigenroute.authentication._
import com.eigenroute.id.{UUIDProvider, UUIDProviderImpl}
import com.eigenroute.plumbing.PublisherSubscriber
import com.eigenroute.scalikejdbchelpers.{DBConfig, ScalikeJDBCDevProdDBConfig, ScalikeJDBCSessionProvider, ScalikeJDBCSessionProviderImpl}
import com.eigenroute.time.{TimeProvider, TimeProviderImpl}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import plumbing.messagebroker.MessageBrokerMessageSubscriber

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.

  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends AbstractModule with ScalaModule {

  override def configure() = {
    // bind[API].to[Facade]
    bind[TimeProvider].to[TimeProviderImpl]
    bind[UUIDProvider].to[UUIDProviderImpl]
    bind[JWTAlgorithmProvider].to[JWTAlgorithmProviderImpl]
    bind[JWTPublicKeyProvider].to[JWTPublicKeyProviderImpl]
    bind[JWTPrivateKeyProvider].to[JWTPrivateKeyProviderImpl]
    // bind[DAO].to[DAOImpl]
    bind[ScalikeJDBCSessionProvider].to[ScalikeJDBCSessionProviderImpl]
    bind[DBConfig].to[ScalikeJDBCDevProdDBConfig]
    bind[PublisherSubscriber].to[MessageBrokerMessageSubscriber]
  }

}
