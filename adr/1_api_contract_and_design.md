# API Contract and system design

## Status

Accepted

## Context

### API Contract
After having a quick glance at the problem statement, I thought of implementing an endpoint to simulate the swipe of the
Tiger Card by the commuter. Ideally, the commuters will touch/swipe the card everytime they exit the bus/train station.
This approach would have required the state management to store all the trips the commuter had in a day and week. However,
when I looked at sample input/output in the problem statement I came to know that the input should accept the collection
of the trips.

### Decision
Implemented an endpoint that will accept the list of trips and return the total calculated fare for all the trips. In order
to understand why a particular fare is applied to a particular trip, each trip in the response also has a `remark` that
shows which cap was applied for that trip.

HTTP Method: As per initial plan, I chose to use POST as I was planning to store the state of the trips at the server.
However, as the current implementation does not store the state and idempotent in nature, HTTP PUT is used.

HTTP Path: As this is the first version of it and exposed as suit of APIs, prefixed with `/api/v1`. As the operation is
about fare calculation for the input trips, the final path becomes `/api/v1/fare-calculations/trips`.

### System Design
The fare for a trip is calculated based on certain rules viz. daily cap and weekly cap. For each trip, we apply the daily
cap and then we apply the weekly cap, forming a chain of responsibility. As a trip goes through the chain of responsibility,
it gets decorated by newly calculated fare. One type of handler does not know about the other, making the model extensible.

The zones also have their specific configuration like what would be the fare to another zone, what should be the cap to
other zone, etc. Therefore, the zone should tell what is its config rather than someone asking them about it, following
 the `Tell, Don't ask` principle. The polymorphic behavior of the enum classes suits well for this kind of behavior. 

### Consequences

As the endpoint accepts the collection of all the trips in one go, the consumer of this API needs to manage the state of
all the trips for the commuter.
