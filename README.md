# fetch-coding-challenge
Fetch Rewards Coding Exercise - Backend Software Engineering


## Interpretation of Assignment
Adding points allows for positive and negative points to be submitted. To achieve the result of DANNON having a balance of 100 after spending 5000 points, the -200 added points must be subtracted from the 300 DANNON transaction. Therefore, all negative points are treated as credits and maintained in a separate data structure. Whenever a positive number of points is submitted, a credit is potentially subtracted. There is no requirement that a payer must have supplied a positive amount of points prior to submitting a negative amount.

## Running the code
- Requirements: Java >= 1.8 (tested using 18 but the pom.xml is configured for 1.8), Maven (tested using 3.8.6), bash
- mvn install
- java -jar ./target/coding-challenge-1.0-SNAPSHOT.jar
- bash ./client.sh


## Data Models and Algorithms
Storing the positive point transactions can be done by specifying a natural sort order on the objects (timestamp). By default, a `TreeSet` will sort decending, enabling iteration to happend over the oldest timestamps first. Deducting spent points is done by iterating over the elements in the TreeSet and determines whether to spend all the payer's points or a subset such that no payer will have a negative balance.

The balances and credits are maintained each by a `HashMap` which enables O(1) lookup time.

## Code Layout
Java was chosen to implement this project because of the easy use of SpringBoot, data typing, native data structures, and unit testing framework. The code is logically organized into java packages:
- **com.fetchrewards.points.api**: contains only the rest controller
- **com.fetchrewards.points.database**: contains the database proxy class `Database` and the database models `DataModel`
- **com.fetchrewards.points.models**: contains only presentation models used for json representation
- **com.fetchrewards.points**: contains only the `App` having the main method

The `App` contains only the main method and a dummy config for SpringBoot. No Spring beans or autowired objects are needed.

All presentation models use Lombok to elimiate boilerplate code. Use of `javax.validation.constraints` along with the `@Valid` annotation provides some basic validation, for example spending non negative points.

The `DataModel` is implemented as a class that cannot be extended nor instanciated. Rather it contains the data structures needed to store and fetch raw data.

The `Database` class is also not extendable or instanciable. Ideally this should be a thread-safe singleton. 

The `PointsController` class is also not extendable or instanciable. Ideally this should be a thread-safe singleton. It uses `org.springframework.http` dependency to define the endpoints, objects consumed and produced, and json conversion from java data types. There is no logic in this class, all of which is defered to the `Database` class.

## Testing ##
All tests are within the **com.fetchrewards.points.database** package, as verifying the data structures are correct is the most important unit tests. Any other tests should be put in an appropriate package to potentially unit test package-private methods.

## API Design
- **POST** endpoint `/add` which consumes a json transaction object and returns 201 when successful
- **POST** endpoint `/spend` which consumes a json transaction object and returns a 200 with a json response object
- **GET** endpoint `/balance` which consumes nothing and responds with a json reponse object

All calls to the Database from within the controller class are wrapped in a try-catch, whereby a 500 is returned in the event that an exception happens.

## Edge Cases Not Addressed
There are no requirements for the following cases:

- A negative add points operation happens for a payer who has not yet submitted a positive number of points
- Requesting to spend more points than are accumulated
- The Database class is not thread safe nor are the backend data structures
