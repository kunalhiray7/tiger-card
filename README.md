# Tiger Card

Simulates a prepaid card that can be used for public transport.

### Building project and Running tests
* Run `./gradlew clean build` for building the project and running the unit test cases.

### How to run locally??
* Run the `tiger-card` by executing the script `./run.sh` or running manually by `./gradlew bootrun`.
* The server should be running on `http://localhost:8080`.

### Dockerization
* Run `./gradlew clean build`
* Build the image by `docker build . -t tiger-card`
* You should see the image `tiger-card` when executed `docker images`
* Run the docker image by `docker run -p 8080:8080 tiger-card`

### Sample Input/Output
* You can use [Postman](https://www.postman.com/) to test the API endpoint on local.
* Import the collection - `tiger-card.postman_collection.json` in the Postman and invoke the APIs.
* Adjust the inputs in the request for testing different scenarios.

### Running integration tests from CLI(Optional)
* Install newman using `sudo npm i -g newman`
* Run tests using `newman run tiger-card.postman_collection.json`
