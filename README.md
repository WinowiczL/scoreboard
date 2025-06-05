# scoreboard

Architectural decisions:
1. JUnit was chosen as a widely known and simple testing library.
2. Commands were created to ensure clear validation of the data provided to the facade.
3. HashMap used as a simple key-value representation of a database.
4. The match creation date could be set at the time of saving to the database, but for the purposes of this task, the current approach was chosen.

Concerns:
1. The task lacks information about the data format. Since there is no possibility of contacting the library consumer, they will have to adapt to the API.
2. The word *match* and *game* used in same context in the specification. For consistency, the term *match* is used through the library.
3. The results are presented as a sorted list rather than a description in the form of a string, because the exact expectations cannot be inferred from the specification.
4. Application must be simple, so concurrency issues — such as updating points — were not handled.
5. For simplicity, a team consists of a `country`, which is a string and does not use ISO locale values.
