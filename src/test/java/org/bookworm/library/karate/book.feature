Feature: book

  Background:
    * url bookUrlBase

  Scenario: book endpoint test

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
    Given request { name: 'Milano' }
    And header Authorization = 'Bearer ' + accessToken
    And url publisherUrlBase
    And method POST
    And header Accept = 'application/json'
    Then status 201
    And match response == { id: #number, name: 'Milano' }
    And def publisherId = response.id

    #Select publisher should success
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(publisherId), name: 'Milano' }
    And def publisherId = response.id

    #Add book should success
    Given request { registryNumber :1, title: 'Red Mars', placeOfOrigin: 'Warsaw', year: 2002, volume: '1', acquireDate: '2000-01-02', acquiringMethod: 'PURCHASED', acquiringEmployeeId: '28319c80-449d-11ec-81d3-0242ac130003', status:'AVAILABLE', publisherId: #(publisherId), language:'ENGLISH', jointPublication: false }
    And url bookUrlBase
    And header Authorization = 'Bearer ' + accessToken
    And method POST
    And header Accept = 'application/json'
    Then status 201
    And def bookId = response.id

    #Select book should success
    Given path bookId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
#    And match response == { id: #notnull, registryNumber:'1', title: 'Red Mars', placeOfOrigin:'Warsaw', year:'2002', volume:'1', acquireDate: '2000-01-02', acquiringMethod: #present, acquiringEmployeeId:'28319c80-449d-11ec-81d3-0242ac130003', status:#present, publisher:#present, language:#present}

    #Update book should success
    Given request { id: '#(bookId)', title: 'Green Mars' }
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    When method PATCH
    Then status 200

    #Delete book should success
    Given path bookId
    And url bookUrlBase
    And header Authorization = 'Bearer ' + accessToken
    When method DELETE
    Then status 200

    #Select many books should fail
    Given params { offset: 1, pageNumber: 1, pageSize: 5 }
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404

    #Delete publisher should success
    Given path publisherId
    And url publisherUrlBase
    And header Authorization = 'Bearer ' + accessToken
    When method DELETE
    Then status 200

    #Select publisher should fail
    Given path publisherId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404
