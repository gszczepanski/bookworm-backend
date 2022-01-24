// config with secrets used for tests on dev env
// set by mvn test -DargLine="-Dkarate.env=dev"
// or ./gradlew test -Dkarate.env=dev
function fn() {
  var config = { // base config JSON
    appId: 'bookworm_client',
    appSecret: '',//put value here according to var name
    tokenUrlBase: '',//put value here according to var name
    authorUrlBase: '',//put value here according to var name
    bookUrlBase: '',//put value here according to var name
    personUrlBase: '',//put value here according to var name
    publisherUrlBase: '',//put value here according to var name
    grantType: '',//put value here according to var name
    testUserName: '',//put value here according to var name
    testUserPassword: ''//put value here according to var name
  };
  // don't waste time waiting for a connection or if servers don't respond within 5 seconds
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
