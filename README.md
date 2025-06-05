# scoreboard

Architectural decisions:
1. JUnit was chosen as a widely known and simple testing library.

Concerns:
1. The task lacks information about the data format. Since there is no possibility of contacting the library consumer, they will have to adapt to the API.
2. The word *match* and *game* used in same context in the specification. For consistency, the term *match* is used through the library.
3. The results are presented as a sorted list rather than a description in the form of a string, because the exact expectations cannot be inferred from the specification.
