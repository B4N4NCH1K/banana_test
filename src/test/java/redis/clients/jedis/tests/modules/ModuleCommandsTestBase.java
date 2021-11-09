package redis.clients.jedis.tests.modules;

import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import redis.clients.jedis.Connection;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.providers.PooledJedisConnectionProvider;

public abstract class ModuleCommandsTestBase {

  protected static final HostAndPort hnp = new HostAndPort(Protocol.DEFAULT_HOST, 6479);

  private static final PooledJedisConnectionProvider provider = new PooledJedisConnectionProvider(hnp);
  protected UnifiedJedis client;

  public ModuleCommandsTestBase() {
    super();
  }

  @BeforeClass
  public static void prepare() throws Exception {
    try (Connection connection = new Connection(hnp)) {
      assumeTrue("No Redis running on 6479 port. Ignoring modules tests.", connection.ping());
    } catch (JedisConnectionException jce) {
      assumeTrue(false);
    }
  }

  @Before
  public void setUp() throws Exception {
    try (Jedis jedis = createJedis()) {
      jedis.flushAll();
    }
    client = new UnifiedJedis(provider);
  }
//
//  @After
//  public void tearDown() throws Exception {
//    client.close();
//  }

  protected static Jedis createJedis() {
    return new Jedis(hnp);
  }
}
