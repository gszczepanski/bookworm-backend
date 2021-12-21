// config with secrets used for tests
function fn() {
  var env = karate.env; // get java system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev'; // a custom 'intelligent' default
  }
  var config = { // base config JSON
    appId: 'bookworm_client',
    appSecret: 'a0f7590c-a779-4695-95cc-9d608142864b',
    tokenUrlBase: 'http://localhost:9080/auth/realms/bookworm/protocol/openid-connect/token',
    authorUrlBase: 'http://localhost:8080/authors',
    bookUrlBase: 'http://localhost:8080/books',
    personUrlBase: 'http://localhost:8080/persons',
    publisherUrlBase: 'http://localhost:8080/publishers',
    grantType: 'password',
    testUserName: 'bookworm_john',
    testUserPassword: 'password'
  };
  if (env == 'prod') { // prod config JSON
    config.tokenUrlBase = 'http://bookworm-keycloak:9080/auth/realms/bookworm/protocol/openid-connect/token';
    config.authorUrlBase = 'http://bookworm-backend:8080/authors';
    config.bookUrlBase = 'http://bookworm-backend:8080/books';
    config.personUrlBase = 'http://bookworm-backend:8080/persons';
    config.publisherUrlBase = 'http://bookworm-backend:8080/publishers';
  }
  // don't waste time waiting for a connection or if servers don't respond within 5 seconds
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
