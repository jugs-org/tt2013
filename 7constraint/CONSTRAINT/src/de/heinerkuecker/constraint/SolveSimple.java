package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.XArrayList;

/**
 * Abstrakte Klasse zum Verknüpfen eines
 * einfachen Prädikates {@link PrimitivPredicate}
 * mit einer {@link BooleanSource}, welche
 * üblicherweise mit einem Zähler
 * {@link BinCounter} ge-backt ist,
 * um eine boolsche Expression für
 * unterschiedliche Belegungen (Model)
 * abzuarbeiten (zu evaluieren).
 * 
 * @author Heiner K&uuml;cker
 */
public final class SolveSimple<CT>
extends SolvePredicate<CT>
{
    final PrimitivPredicate<CT> predicateToSolve;

    final BooleanSource booleanSource;

    final List<SolveSimple<CT>> includePredicateList;

    /**
     * Konstruktor.
     *
     * @param predicateToSolve
     */
    public SolveSimple( 
            final PrimitivPredicate<CT> predicateToSolve ,
            final BooleanSource booleanSource )
    {
        this.predicateToSolve = predicateToSolve;
        this.booleanSource = booleanSource;
        
        //if ( predicateToSolve.includePredicateSet == null &&
        //        false == ( this.predicateToSolve instanceof ParametricalPredicate ) )
        //{
        //    this.includePredicateList = null;
        //}
        //else
        //{
            this.includePredicateList =
                    new ArrayList<SolveSimple<CT>>(
                            //predicateToSolve.includePredicateSet.size()
                            );
        //}
    }

//    /**
//     * @see SolvePredicate#solve
//     */
//    @Override
//    public boolean solve(
//            final Exclude<CT> exclude )
//    {
//        return innerSolve();
//    }

    /**
     * @return
     */
    protected boolean innerSolve()
    {
        // Sonderlocke TruePredicate
        if ( this.predicateToSolve instanceof TruePredicate )
        {
            return true;
        }

        // Sonderlocke FalsePredicate
        if ( this.predicateToSolve instanceof FalsePredicate )
        {
            return false;
        }

        return this.booleanSource.get();
    }

//    /**
//     * @see SolvePredicate#innerSolve
//     */
//    @Override
//    protected boolean innerSolve(
//    		//final MutableBoundedExclude<CT> mutableBoundedExclude ,
//    		//final IncludeValidChecker<CT> includeValidChecker
//    		)
//    //throws SolveBreakException
//    {
//        final boolean result = 
//        		//this.booleanSource.get()
//                // TruePredicate und FalsePredicate beachten
//                this.innerSolve();
//        
//        //if ( result && this.predicateToSolve.equals( new R2() ) )
//        //{
//        //    System.out.println( "debug break" );
//        //}
//        
//        if ( result == false )
//        {
//            // die Prüfungen auf Exclude und Include sind nur bei erfülltem Prädikat interessant
//            return result;
//        }
//        
//        //if ( mutableBoundedExclude.isKontraAndAdd( predicateToSolve ) )
//        //{
//        //	throw new SolveBreakException();
//        //}
//        mutableBoundedExclude.check( predicateToSolve );
//        
//        //if ( false == alreadyIncludeCheckedPredicateSet.contains( this.predicateToSolve ) )
//        //{
//        //  alreadyIncludeCheckedPredicateSet.add( this.predicateToSolve );
//        //}
//        includeValidChecker.check( 
//                predicateToSolve ,
//                mutableBoundedExclude );
//        
//		return result;
//    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectSatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        if ( this.innerSolve() )
        {
            collectList.add( this );
        }
    }

    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitiveSolvePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitiveSolvePredicates( 
            final List<SolveSimple<CT>> collectList )
    {
        if ( false == this.innerSolve() )
        {
            collectList.add( this );
        }
    }

    /**
     * @see SolvePredicate#collectSatisfiedPrimitivePredicates
     */
    @Override
    protected void collectSatisfiedPrimitivePredicates( 
            final List<PrimitivPredicate<CT>> collectList )
    {
        if ( this.innerSolve() )
        {
            collectList.add( this.predicateToSolve );
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
            collectList.add( this.predicateToSolve );
        }
    }
    
    /**
     * @see SolvePredicate#collectUnsatisfiedPrimitivePredicates
     */
    @Override
    protected void collectUnsatisfiedPrimitivePredicates( 
            final List<Predicate<CT>> collectList )
    {
        if ( false == this.innerSolve() )
        {
            collectList.add( this.predicateToSolve );
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                this.predicateToSolve.toString() + "[" + 
                //this.booleanSource.get()
                // TruePredicate und FalsePredicate beachten
                this.innerSolve() + 
                "]";
    }

	@Override
	protected boolean hasUnsatisfied(
			final Predicate<CT> predicateToCheck )
	{
		if ( this.predicateToSolve.equals( predicateToCheck ) )
		{
			return false == innerSolve();
		}
		return false;
	}

    /**
     * @see SolvePredicate#getPrimitiveSolvePredicates
     */
    @Override
    protected Iterable<SolveSimple<CT>> getPrimitiveSolvePredicates()
    {
        return new XArrayList<SolveSimple<CT>>( this );
    }

    /**
     * @see SolvePredicate#prepareIncludePredicates
     */
    @Override
    void prepareIncludePredicates(
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet ,
            final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap )
    {
        if ( this.predicateToSolve.includePredicateSet != null )
        {
            final HashSet<? extends PrimitivPredicate<CT>> includePredicateSet = predicateToSolve.includePredicateSet;

            for ( final PrimitivPredicate<CT> includedPredicate : includePredicateSet )
            {
                final SolveSimple<CT> includedSolvesimple =
                        primitivPredToSolvePredMap.get(
                                includedPredicate );

                if ( includedSolvesimple != null )
                {
                    this.includePredicateList.add(
                            includedSolvesimple );
                }
            }
        }

        // TODO folgender Block sollte Excludes sammeln, damit im IntRangeConstraintCodeGenerator nicht der default-Zweig im Switch generiert wird, klappt aber leider nicht, deshalb versuche ich es mit einem expliziten Exclude
        //for ( final PrimitivPredicate<CT> otherSimplePredicate : /*simplePredicateSet*/primitivPredToSolvePredMap.keySet() )
        //{
        //    if ( false == this.predicateToSolve.equals( otherSimplePredicate ) &&
        //            this.predicateToSolve instanceof ParametricalPredicate )
        //    {
        //        final ParametricalPredicate<CT> thisParametricalPredicateToSolve = (ParametricalPredicate<CT>) this.predicateToSolve;
        //
        //        if ( thisParametricalPredicateToSolve.isCompatibleTo( otherSimplePredicate ) )
        //        {
        //            final SolveSimple<CT> otherSolvePredicate =
        //                    primitivPredToSolvePredMap.get(
        //                    otherSimplePredicate );
        //
        //            this.includePredicateList.add(
        //                    otherSolvePredicate );
        //        }
        //    }
        //}
    }

    /**
     * @see SolvePredicate#isValidForIncludes
     */
    @Override
    boolean isValidForIncludes()
    {
        final boolean expectedResult = solve();
        
        if ( false == expectedResult )
        {
            return true;
        }
        
        if ( this.includePredicateList != null )
        {
            for ( final SolveSimple<CT> includedSolvesimple : this.includePredicateList )
            {
                if ( false == includedSolvesimple.solve() )
                {
                    return false;
                }
            }
        }
        return true;
    }

}
