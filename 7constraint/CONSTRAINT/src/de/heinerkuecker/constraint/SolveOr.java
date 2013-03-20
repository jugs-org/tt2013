package de.heinerkuecker.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.XArrayList;
import de.heinerkuecker.constraint.util.coll.XList;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * ODER-Verknüpfung von Prädikaten
 * {@link Predicate}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SolveOr<CT>
extends SolvePredicate<CT>
{
    /**
     * FirstInsertOrderHashset zum Absichern, dass ein identisches
     * Prädikat nur einmal aufgeführt wird und Absichern der
     * Erhaltung der Reihenfolge der Prädikate.
     */
    //private final FirstInsertOrderHashset<SolvePredicate> orPredicateSet;
    private final SolvePredicate<CT>[] orPredicateArr;
    
    private final Or<CT> or;
    
    /**
     * Konstruktor.
     *
     * @param orPredicateArr Array mit Prädikaten, welche 
     *                        ODER-verknüpft werden sollen
     */
    public SolveOr( 
            final Or<CT> or ,
            final SolvePredicate<CT>... orPredicateArr )
    {
        this.or = or;
        
        //this.orPredicateSet = 
        //    new FirstInsertOrderHashset<SolvePredicate>();
        //
        //this.orPredicateSet.addAll(
        //        Arrays.asList(
        //                orPredicateArr ) );
        this.orPredicateArr =
                orPredicateArr;
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
        final SolveOr<?> other = (SolveOr<?>) obj;
        return Arrays.equals( this.orPredicateArr , other.orPredicateArr );
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        //return this.orPredicateSet.hashCode();
        return Arrays.hashCode( this.orPredicateArr );
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
//        return 
//            "Or:" + 
//            this.orPredicateSet.toString();
        final SeparatedBuffer sepBuff = new SeparatedBuffer( " or " );
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
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
        //return this.orPredicateSet.size();
        return this.orPredicateArr.length;
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
        //boolean result = false;
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
        {
            if ( predicate.innerSolve(
            		//mutableBoundedExclude ,
            		//includeValidChecker
            		) )
                // mindestens ein Prädikat-Test ist erfolgreich
            {
                return true;
                //// aufgrund des beim Lösen nebenbei mit ausgeführten Exclude-Check darf beim OR nicht mit short-cut-evaluation gearbeitet werden
                //result = true;
            }
        }

        return false;
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectSatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
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
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
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
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
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
        if ( this.solve() )
        {
            collectList.add( this.or );
        }

        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
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
        for ( final SolvePredicate<CT> predicate : this.orPredicateArr )
        {
            predicate.collectUnsatisfiedPrimitivePredicates( 
                    collectList );
        }
    }

	@Override
	protected boolean hasUnsatisfied(
			final Predicate<CT> predicateToCheck )
	{
        for ( final SolvePredicate<CT> orSubPredicate : this.orPredicateArr )
        {
            if ( orSubPredicate.hasUnsatisfied( predicateToCheck ) )
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

        for ( final SolvePredicate<CT> orSubPredicate : this.orPredicateArr )
        {
            result.addAllFromIterable(
                    orSubPredicate.getPrimitiveSolvePredicates() );
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
        for ( final SolvePredicate<CT> orSubPredicate : this.orPredicateArr )
        {
            orSubPredicate.prepareIncludePredicates(
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
        for ( final SolvePredicate<CT> orSubPredicate : this.orPredicateArr )
        {
            if ( false == orSubPredicate.isValidForIncludes() )
            {
                return false;
            }
        }
        return true;
    }

}
