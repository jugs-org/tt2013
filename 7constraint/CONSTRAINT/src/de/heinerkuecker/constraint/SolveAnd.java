package de.heinerkuecker.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.XArrayList;
import de.heinerkuecker.constraint.util.coll.XList;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * UND-Verknüpfung von Prädikaten
 * {@link Predicate}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SolveAnd<CT>
extends SolvePredicate<CT>
{
    /**
     * FirstInsertOrderHashset zum Absichern, dass ein identisches
     * Prädikat nur einmal aufgeführt wird und Absichern der
     * Erhaltung der Reihenfolge der Prädikate.
     */
    //private final FirstInsertOrderHashset<SolvePredicate> andPredicateSet;
    private final SolvePredicate<CT>[] andPredicateArr;
    
    final And<CT> and;
    
    /**
     * Konstruktor.
     * 
     * @param exclude Exclude-Objekt zum Ausfiltern negierter Sub-Prädikate, die sich mit einem der anderen Prädikate ausschliessen
     * @param andPredicateArr Array mit Prädikaten, welche 
     *                        UND-verknüpft werden sollen
     */
    public SolveAnd(
            And<CT> and ,
            //final Exclude<CT> exclude ,
            final SolvePredicate<CT>... andPredicateArr )
    {
        this.and = and;
        //this.andPredicateSet = 
        //    new FirstInsertOrderHashset<SolvePredicate>();
        //
        //this.andPredicateSet.addAll(
        //        Arrays.asList(
        //                andPredicateArr ) );
        this.andPredicateArr =
                andPredicateArr;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final SolveAnd<?> other = (SolveAnd<?>) obj;
        //return this.andPredicateSet.equals( other.andPredicateSet );
        return Arrays.equals( this.andPredicateArr , other.andPredicateArr );
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        //return this.andPredicateSet.hashCode();
        return Arrays.hashCode( this.andPredicateArr );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
//        return 
//            "And:" + 
//            this.andPredicateSet.toString();
        final SeparatedBuffer sepBuff = new SeparatedBuffer( " and " );
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            sepBuff.add( predicate.toString() );
        }
        
        return "(" + sepBuff + ")";
    }

    /**
     * @return Anzahl Unter-Prädikate
     */
    public int size()
    {
        //return this.andPredicateSet.size();
        return this.andPredicateArr.length;
    }

    /**
     * @throws SolveBreakException
     * @see SolvePredicate#innerSolve
     */
    @Override
    protected boolean innerSolve(
    		//final MutableBoundedExclude<CT> mutableBoundedExclude ,
    		//final IncludeValidChecker<CT> includeValidChecker
    		)
    //throws SolveBreakException
    {
        //boolean result = true;
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            if ( false ==
                    // hier ein Zeilenumbruch für einzelnes debuggen der aufgerufenen Methode innerSolve
                    predicate.innerSolve(
                            //mutableBoundedExclude ,
                            //includeValidChecker
                            ) )
                // mindestens ein Prädikats-Test schlägt fehl
            {
                return false;
                ////aufgrund des beim Lösen nebenbei mit ausgeführten Exclude-Check darf beim OR nicht mit short-cut-evaluation gearbeitet werden
                //result = false;
            }
        }

        return true;
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectSatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            predicate.collectSatisfiedPrimitiveSolvePredicates( 
                    collectList );
        }
    }

    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            predicate.collectUnsatisfiedPrimitiveSolvePredicates( 
                    collectList );
        }
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitivePredicates
     */
    @Override
    protected void collectSatisfiedPrimitivePredicates( 
            final List<PrimitivPredicate<CT>> collectList )
    {
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            predicate.collectSatisfiedPrimitivePredicates( 
                    collectList );
        }
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
            collectList.add( this.and );
        }

        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            predicate.collectSatisfiedPredicates( 
                    collectList );
        }
    }
    
    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitivePredicates( 
            final List<Predicate<CT>> collectList )
    {
        for ( final SolvePredicate<CT> predicate : this.andPredicateArr )
        {
            predicate.collectUnsatisfiedPrimitivePredicates( 
                    collectList );
        }
    }

	@Override
	protected boolean hasUnsatisfied(
			final Predicate<CT> predicateToCheck )
	{
        for ( final SolvePredicate<CT> andSubPredicate : this.andPredicateArr )
        {
            if ( andSubPredicate.hasUnsatisfied( predicateToCheck ) )
            {
            	return true;
            }
        }
        
        return false;
	}

    /**
     * @see SolvePredicate#getPrimitiveSolvePredicates
     */
    @Override
    protected Iterable<SolveSimple<CT>> getPrimitiveSolvePredicates()
    {
        final XList<SolveSimple<CT>> result = new XArrayList<SolveSimple<CT>>();

        for ( final SolvePredicate<CT> andSubPredicate : this.andPredicateArr )
        {
            result.addAllFromIterable(
                    andSubPredicate.getPrimitiveSolvePredicates() );
        }

        return result;
    }

    /**
     * @see SolvePredicate#prepareIncludePredicates
     */
    @Override
    void prepareIncludePredicates(
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet ,
            final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap )
    {
        for ( final SolvePredicate<CT> andSubPredicate : this.andPredicateArr )
        {
            andSubPredicate.prepareIncludePredicates(
                    simplePredicateSet ,
                    primitivPredToSolvePredMap );
        }
    }

    /**
     * @see SolvePredicate#isValidForIncludes
     */
    @Override
    boolean isValidForIncludes()
    {
        for ( final SolvePredicate<CT> andSubPredicate : this.andPredicateArr )
        {
            if ( false == andSubPredicate.isValidForIncludes() )
            {
                return false;
            }
        }
        return true;
    }

}
