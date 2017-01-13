package entrypoint

import java.util.UUID

import com.eigenroute.authentication.{JWTAlgorithmProviderImpl, JWTPrivateKeyProviderImpl}
import com.eigenroute.id.{TestUUIDProviderImpl, UUIDProvider}
import com.eigenroute.scalikejdbchelpers.DBConfig
import com.eigenroute.scalikejdbctesthelpers.{InitialMigration, OneAppPerTestWithOverrides, ScalikeJDBCTestDBConfig}
import com.eigenroute.time.{TestTimeProviderImpl, TimeProvider}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import pdi.jwt.JwtJson
import persistence.db.Fixture
import play.api.inject._
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import scalikejdbc.NamedAutoSession

class SomeControllerATest
  extends FlatSpec
  with ShouldMatchers
  with OneAppPerTestWithOverrides
  with BeforeAndAfterEach
  with InitialMigration
  with Fixture {

  val timeProvider = new TestTimeProviderImpl()
  val jWTAlgorithmProvider = new JWTAlgorithmProviderImpl()
  val jWTPrivateKeyProvider = new JWTPrivateKeyProviderImpl()
  val claim = Json.obj("userId" -> UUID.fromString("00000000-0000-0000-0000-000000000001"), "iat" -> timeProvider.now())
  val jWT = JwtJson.encode(claim, jWTPrivateKeyProvider.privateKey, jWTAlgorithmProvider.algorithm)

  val validPost = ???

  override def overrideModules = Seq(
    bind[UUIDProvider].to[TestUUIDProviderImpl],
    bind[TimeProvider].to[TestTimeProviderImpl],
    bind[DBConfig].to[ScalikeJDBCTestDBConfig]
  )

  val dBConfig = new ScalikeJDBCTestDBConfig()

  override def beforeEach(): Unit = {
    implicit val session = NamedAutoSession(Symbol(dBConfig.dBName))
    dBConfig.setUpAllDB()
    migrate(dBConfig)
    sqlToExecute.foreach(_.update.apply())
    super.beforeEach()
  }

  val routeEndpoint = "/some-route-endpoint"

  override def afterEach() {}

  "Posting an ???" should "succeed if posted with a properly encoded token and valid content" in {
    val content =
      contentAsJson(route(app, FakeRequest(POST, routeEndpoint)
        .withHeaders(("Authorization", "Bearer " + jWT))
        .withJsonBody(validPost)
      ).get)

    (content \ "status").asOpt[String] should contain("success")
  }

  it should "fail if the token cannot be decoded to yield a valid user UUID" in {

    import java.security.spec.ECPrivateKeySpec
    import java.security.{KeyFactory, Security}

    import org.bouncycastle.jce.ECNamedCurveTable
    import org.bouncycastle.jce.spec.ECNamedCurveSpec

    val wrongPrivateKey = {
      val sRaw: String = "123"
      val S = BigInt(sRaw, 16)
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider())
      val curveParams = ECNamedCurveTable.getParameterSpec("P-521")
      val curveSpec =
        new ECNamedCurveSpec("P-521", curveParams.getCurve, curveParams.getG, curveParams.getN, curveParams.getH)
      val privateSpec = new ECPrivateKeySpec(S.underlying(), curveSpec)
      KeyFactory.getInstance("ECDSA", "BC").generatePrivate(privateSpec)
    }
    val badJWT = JwtJson.encode(claim, wrongPrivateKey, jWTAlgorithmProvider.algorithm)
    val result =
      route(app, FakeRequest(POST, routeEndpoint)
        .withHeaders(("Authorization", "Bearer " + badJWT))
        .withJsonBody(validPost)
      ).get

    status(result) shouldBe UNAUTHORIZED
  }

  it should "fail it the token is proper but the content is not valid" in {
    val invalidPost = ???
    val result =
      route(app, FakeRequest(POST, routeEndpoint)
        .withHeaders(("Authorization", "Bearer " + jWT))
        .withJsonBody(invalidPost)
      ).get

    status(result) shouldBe BAD_REQUEST
  }
}
