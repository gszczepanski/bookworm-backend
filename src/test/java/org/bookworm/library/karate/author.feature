Feature: author

  Background:
    * url authorUrlBase

  Scenario: author endpoint test

    * url tokenUrlBase
    * form field grant_type = grantType
    * form field client_id = appId
    * form field client_secret = appSecret
    * form field username = testUserName
    * form field password = testUserPassword
    * method post
    * status 200
    * def accessToken = response.access_token

    #Add author should success
    Given url authorUrlBase
    And request { lastName: 'Clarke', firstName: 'Arthur', displayName: 'Arthur C. Clarke'}
    And header Authorization = 'Bearer ' + accessToken
    And method POST
    And header Accept = 'application/json'
    Then status 201
    And match response == { id: #notnull, lastName: 'Clarke', firstName: 'Arthur', displayName: 'Arthur C. Clarke', comment:null}
    And def authorId = response.id

    #Select author should success
    Given path authorId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(authorId), lastName: 'Clarke', firstName: 'Arthur', displayName: 'Arthur C. Clarke', comment:null}

    #Update author should success
    Given request { id: '#(authorId)', lastName: 'Heinlein', firstName: 'Robert', displayName: 'Robert A. Heinlein'}
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    When method PUT
    Then status 200

    #Select author should success
    Given path authorId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 200
    And match response == { id: #(authorId), lastName: 'Heinlein', firstName: 'Robert', displayName: 'Robert A. Heinlein', comment:null}

    #Delete author should success
    Given path authorId
    And header Authorization = 'Bearer ' + accessToken
    When method DELETE
    Then status 200

    #Select author should fail
    Given path authorId
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404

    #Select many authors should fail
    Given params { offset: 1, pageNumber: 1, pageSize: 5 }
    And header Authorization = 'Bearer ' + accessToken
    When method GET
    Then status 404
