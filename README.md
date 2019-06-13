This sample application exposes an endpoint '/character' supporting the following methods:

  * GET: application should respond with a json array of all Game of Thrones characters according to certain criteria, passed in the url parameter (see below).
  * POST: application should add a new character (encoded as Json) to the dummy SOR

You're asked to fork the current repo into your private area and complete the following tasks:

  1. As part of the GET response you need to add support to allow filtering the list of returned characters based on the following url parameters:

    * name (optional) - takes in a String representing the exact, full-name of the character to be retrieved
    * is_alive (optional) - takes in a boolean representing whether the character is alive (field “kill_by” can be used for this purpose)
    * kill_count_range (optional) - takes in a range in the format “[min,max]”, inclusive (field “killed” can be used for this purpose)

    Example:
      /character?is_alive=true,kill_count_range=[2,5]

    Should return a JSON array with all characters currently alive which have killed between 2 and 5 characters, inclusive

    If a filter is not specified, it should be ignored (ie. /character without get parameters returns the full list of characters)

  2. Write a new Java class called HitHard to test performance of your application endpoint for the POST method when under stress, i.e. multiple calls in parallel. Store the results of your performance tests in a text file.
  
  3. With the results found in #2, you'll find performance is not great given that we call a blocking operation. Optimise the current DummySOR::addCharacter function, but remember - you must call FakeDB::insert. Run your performance test again, and store the results in a text file.

  You'll earn extra 'points' if you complete the following tasks:
  
  1. Inject the StorageService class in SimpleRest using any dependency injection framework you're familiar with.
  2. Run the Main application in a docker container and your HitHard class in your IDE.
  3. Write unit tests for the StorageService, SimpleRest and DummySOR classes, using either mockito or spock.
  
  
Add as many comments as you find necessary to make it explanatory for the show n tell.

Enjoy!