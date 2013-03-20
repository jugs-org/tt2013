package de.heinerkuecker.constraint;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;

/**
 * Hilfsprädikat zum Ausrechnen
 * boolscher Ausdrücke.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class SolvePredicate<CT>
{
    /**
     * @see SolvePredicate#solve
     */
    public final boolean solve(
            //final Exclude<CT> exclude ,
            //final IncludeValidChecker<CT> includeValidChecker
            )
    {
//        final boolean noExcludeIncludeResult =
//                innerSolve();
//        
//        if ( noExcludeIncludeResult )
//        {
//            if ( isFalseForExclude( exclude ) )
//            {
//                return false;
//            }
//
//            if ( isFalseForInclude() )
//            {
//                return false;
//            }
//        }
//
//        return noExcludeIncludeResult;
    	//try{
            //final MutableBoundedExclude<CT> mutableBoundedExclude = new MutableBoundedExclude<CT>( exclude );
            
            //final HashSet<PrimitivPredicate<CT>> alreadyIncludeCheckedPredicateSet = new HashSet<PrimitivPredicate<CT>>();
            
            final boolean result =
            		this.innerSolve(
            				//mutableBoundedExclude ,
            				//includeValidChecker
            		        );
			
            return result;
        //}catch ( final SolveBreakException sbx ){
        //	return false;
        //}
    }

//    private boolean isFalseForExclude( 
//            final Exclude<CT> exclude )
//    {
//        final List<SolveSimple> satisfiedPredicateList =
//                new ArrayList<SolveSimple>();
//
//        collectSatisfiedPrimitiveSolvePredicates(
//                satisfiedPredicateList );
//
//        return isFalseForExclude( 
//                exclude , 
//                satisfiedPredicateList );
//    }

//    private boolean isFalseForExclude( 
//            final Exclude<CT> exclude ,
//            final List<SolveSimple<CT>> satisfiedPredicateList )
//    {
//        final MutableBoundedExclude mutableBoundedExclude =
//                new MutableBoundedExclude( 
//                        exclude );
//
//        for ( final SolveSimple solveSimple : satisfiedPredicateList )
//        {
//            if ( mutableBoundedExclude.isKontra( solveSimple.predicateToSolve ) )
//            {
//                return true;
//            }
//            mutableBoundedExclude.add( solveSimple.predicateToSolve );
//        }
//
//        return false;
//    }

//    private boolean isFalseForInclude()
//    {
//        final List<SolveSimple> satisfiedPredicateList =
//                new ArrayList<SolveSimple>();
//
//        collectSatisfiedPrimitiveSolvePredicates(
//                satisfiedPredicateList );
//
//        return isFalseForInclude( 
//                satisfiedPredicateList );
//    }

    /**
     * @param satisfiedPredicateList
     * @return
     */
//    private boolean isFalseForInclude( 
//            final List<SolveSimple<CT>> satisfiedPredicateList )
//    {
//        for ( final SolveSimple<CT> solveSimple : satisfiedPredicateList )
//        {
//        	if ( solveSimple.predicateToSolve.includePredicateSet != null )
//        	{
//        		for ( final Predicate<CT> includedPredicate : solveSimple.predicateToSolve.includePredicateSet )
//        		{
//        			if ( hasUnsatisfied( includedPredicate ) )
//        			{
//                        return true;
//        			}
//        		}
//        	}
//        }
//
//        return false;
//    }

    // TODO kann weg
    abstract protected boolean innerSolve(
    		//final MutableBoundedExclude<CT> mutableBoundedExclude ,
    		//final IncludeValidChecker<CT> includeValidChecker
    		)
    //throws SolveBreakException
    ;

    // TODO kann weg
    abstract protected void collectSatisfiedPrimitiveSolvePredicates(
            final List<SolveSimple<CT>> collectList );

    // TODO kann weg
    abstract protected void collectUnsatisfiedPrimitiveSolvePredicates(
            final List<SolveSimple<CT>> collectList );

    // TODO kann weg
    abstract protected void collectSatisfiedPrimitivePredicates(
            final List<PrimitivPredicate<CT>> collectList );

    abstract protected void collectSatisfiedPredicates(
            final List<Predicate<CT>> collectList );

    // TODO kann weg
    abstract protected void collectUnsatisfiedPrimitivePredicates(
            final List<Predicate<CT>> collectList );
    
    // TODO kann weg
    abstract protected boolean hasUnsatisfied(
    		Predicate<CT> predicateToCheck );
    
    abstract protected Iterable<SolveSimple<CT>> getPrimitiveSolvePredicates();
    
    /**
     * Nach der Konvertierung die inkludierten Prädikate vermerken
     */
    abstract /*default*/ void prepareIncludePredicates(
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet ,
            final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap );
    
    abstract /*default*/ boolean isValidForIncludes();
}
