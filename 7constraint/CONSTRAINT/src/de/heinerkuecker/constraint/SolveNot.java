package de.heinerkuecker.constraint;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;


/**
 * Negation eines Prädikats
 * {@link Predicate}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SolveNot<CT>
extends SolvePredicate<CT>
{
    /**
     * zu negierendes Prädikat
     */
    private final SolvePredicate<CT> predicateToNegate;
    
    private final Not<CT> not;
    
    /**
     * Konstruktor.
     *
     * @param predicateToNegate zu negierendes Prädikat
     */
    public SolveNot(
            final Not<CT> not ,
            final SolvePredicate<CT> predicateToNegate )
    {
        this.not = not;
        if ( predicateToNegate == null )
        {
            throw new IllegalArgumentException(
                    "predicateToNegate is null" );
        }

        this.predicateToNegate = predicateToNegate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "not(" + this.predicateToNegate + ")";
//        return "(not(" + this.predicateToNegate + "))";
    }

    /**
     * Zurückgeben negierten Unter-Prädikats.
     * 
     * @return negiertes Unter-Prädikate
     */
    public SolvePredicate<CT> get()
    {
        return this.predicateToNegate;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return this.predicateToNegate.hashCode() + 31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final SolveNot<?> other = (SolveNot<?>) obj;
        return this.predicateToNegate.equals( other.predicateToNegate );
    }

    /**
     * @see SolvePredicate#innerSolve
     */
    @Override
    protected boolean innerSolve(
    		//final MutableBoundedExclude<CT> mutableBoundedExclude ,
    		//final IncludeValidChecker<CT> includeValidChecker
    		)
    //throws SolveBreakException
    {
        //try{
            final boolean notNegatedResult =
                    this.predicateToNegate.innerSolve(
                            //mutableBoundedExclude ,
                            //includeValidChecker
                            );

            return ! notNegatedResult;
        //} catch ( SolveBreakException sbe ){
        //    return true;
        //}
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectSatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        this.predicateToNegate.collectSatisfiedPrimitiveSolvePredicates( 
                collectList );
    }

    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        this.predicateToNegate.collectUnsatisfiedPrimitiveSolvePredicates( 
                collectList );
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitivePredicates
     */
    @Override
    protected void collectSatisfiedPrimitivePredicates( 
            final List<PrimitivPredicate<CT>> collectList )
    {
        this.predicateToNegate.collectSatisfiedPrimitivePredicates( 
                collectList );
    }
    
    /**
     * @see SolvePredicate#collectSatisfiedPredicates
     */
    @Override
    protected void collectSatisfiedPredicates( 
            final List<Predicate<CT>> collectList )
    {
        if ( this.innerSolve() )
        {
            collectList.add( this.not );
        }
        
        this.predicateToNegate.collectSatisfiedPredicates( 
                collectList );
    }
    
    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitivePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitivePredicates( 
            final List<Predicate<CT>> collectList )
    {
        this.predicateToNegate.collectUnsatisfiedPrimitivePredicates( 
                collectList );
    }

	@Override
	protected boolean hasUnsatisfied(
			final Predicate<CT> predicateToCheck )
	{
		return this.predicateToNegate.hasUnsatisfied( predicateToCheck );
	}

    /**
     * @see SolvePredicate#getPrimitiveSolvePredicates
     */
    @Override
    protected Iterable<SolveSimple<CT>> getPrimitiveSolvePredicates()
    {
        return this.predicateToNegate.getPrimitiveSolvePredicates();
    }

    /**
     * @see SolvePredicate#prepareIncludePredicates
     */
    @Override
    void prepareIncludePredicates(
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet ,
            final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap )
    {
        this.predicateToNegate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );
    }

    /**
     * @see SolvePredicate#isValidForIncludes
     */
    @Override
    boolean isValidForIncludes()
    {
        return this.predicateToNegate.isValidForIncludes();
    }

}
