Feature: publisher

  Background:
    * url publisherUrlBase

  Scenario: publisher endpoint test

    * url tokenUrlBase
    * form field grant_type = grantType
    * form field client_id = appId
    * form field client_secret = appSecret
    * form field username = testUserName
    * form field password = testUserPassword
    * method post
    * status 200
    * def accessToken = response.access_token

    #Add publisher should success
    Given url publisherUrlBase
    And request { name: 'Garden' }
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    And method POST
    Then status 201
    And match response == { id: #number, name: 'Garden' }
    And def publisherId = response.id

    #Add not unique publisher should fail
    Given request { name: 'Garden' }
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + accessToken
    And method POST
    Then status 500

    #Select publisher should success
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(publisherId), name: 'Garden' }

    #Update publisher should success
    Given request { id: '#(publisherId)', name: 'Market' }
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + accessToken
    When method PUT
    Then status 200

    #Select publisher should success
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(publisherId), name: 'Market' }

    #Delete publisher should success
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method DELETE
    Then status 200

    #Select publisher should fail
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404

    #Select many publishers should fail
    Given params { offset: 1, pageNumber: 1, pageSize: 5 }
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404
