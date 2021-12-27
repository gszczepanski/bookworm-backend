Feature: person

  Background:
    * url personUrlBase

  Scenario: person endpoint test

    * url tokenUrlBase
    * form field grant_type = grantType
    * form field client_id = appId
    * form field client_secret = appSecret
    * form field username = testUserName
    * form field password = testUserPassword
    * method post
    * status 200
    * def accessToken = response.access_token

    #Add person should success
    Given url personUrlBase
    And request { lastName: 'Hood', middleName: 'John', firstName: 'Robin', idCardNumber: '1234567890', idCardType: 'NATIONAL_IDENTITY_CARD', type: 'CLIENT' }
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + accessToken
    And method POST
    Then status 201
    And match response == { id: #notnull, lastName: 'Hood', middleName: 'John', firstName: 'Robin', idCardNumber: '1234567890', idCardType: #present, type: #present }
    And def personId = response.id

    #Select person should success
    Given path personId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(personId), lastName: 'Hood', middleName: 'John', firstName: 'Robin', idCardNumber: '1234567890', idCardType: #present, type: #present }

    #Update person should success
    Given request { id: '#(personId)', lastName: 'Jacket', middleName: 'John', firstName: 'Robin', idCardNumber: '0987654321', idCardType: 'NATIONAL_IDENTITY_CARD', type: 'CLIENT' }
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + accessToken
    When method PUT
    Then status 200

    #Select person should success
    Given path personId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(personId), lastName: 'Jacket', middleName: 'John', firstName: 'Robin', idCardNumber: '0987654321', idCardType: #present, type: #present }

    #Delete person should success
    Given path personId
    And header Authorization = 'Bearer ' + accessToken
    When method DELETE
    Then status 200

    #Select person should fail
    Given path personId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404

    #Select many persons should fail
    Given params { offset: 1, pageNumber: 1, pageSize: 5 }
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404
