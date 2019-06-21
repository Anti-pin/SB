###### Assumptions
- the specification does mention thread safety so there are no thread safety measures taken
- the specification (deliberately?) does not include _BUY_ sample in the example for _live orders summary_, that makes it 
unclear whether _BUY_ and _SELL_ orders should be mixed and what side has priority. The decision is to combine both
sides in the same collection with _BUY_ orders having priority over _SELL_, alternative approach would be to maintain
separate collections for each side
- only core JDK with no additional dependencies is assumed
- although this is supposed to be distributed as a library I omit checking of incoming parameters for 
public methods for nulls and/or illegal values (e.g. negative price or quantity) as the requirements for such checks
should be clarified. I would potentially used IllegalArgumentException / asserts with @NotNull annotations once these 
requirements are clearer
- I deliberately do not assume any building or packaging tools (Maven, Gradle, SBT)
- BigDecimal is used instead of double because of potential rounding problems. May be a bit of overkill for a demo 
project
