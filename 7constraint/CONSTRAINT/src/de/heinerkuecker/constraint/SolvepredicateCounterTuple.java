package de.heinerkuecker.constraint;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;

/**
 * TODO
 * 
 * @author Heiner K&uuml;cker
 */
public final class SolvepredicateCounterTuple<CT>
{
    public final SolvePredicate solvePredicate;

    public final FirstInsertOrderHashset<Predicate<CT>> simplePredicateSet;

    public final BinCounterWithSkipListeners counter;

    /**
     * Konstruktor.
     *
     * @param solvePredicate
     * @param variableSet
     * @param counter
     */
    public SolvepredicateCounterTuple( 
            final SolvePredicate solvePredicate ,
            final FirstInsertOrderHashset<Predicate<CT>> simplePredicateSet , 
            final BinCounterWithSkipListeners counter )
    {
        this.solvePredicate = solvePredicate;
        this.simplePredicateSet = simplePredicateSet;
        this.counter = counter;
    }

}
