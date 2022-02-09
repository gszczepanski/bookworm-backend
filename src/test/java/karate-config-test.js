// config with secrets used for tests
// set by mvn test -DargLine="-Dkarate.env=test"
// or ./gradlew test -Dkarate.env=test
function fn() {
  var config = { // test config JSON
    appId: 'bookworm_client',
    appSecret: 'a0f7590c-a779-4695-95cc-9d608142864b',
    tokenUrlBase: 'http://docker:8080/auth/realms/bookworm/protocol/openid-connect/token',
    authorUrlBase: 'http://docker:8000/authors',
    bookUrlBase: 'http://docker:8000/books',
    personUrlBase: 'http://docker:8000/persons',
    publisherUrlBase: 'http://docker:8000/publishers',
    grantType: 'password',
    testUserName: 'bookworm_john',
    testUserPassword: 'password'
  };
  // don't waste time waiting for a connection or if servers don't respond within 5 seconds
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
