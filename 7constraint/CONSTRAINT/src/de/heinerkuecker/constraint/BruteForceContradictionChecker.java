package de.heinerkuecker.constraint;

import java.util.HashMap;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * @author Heiner K&uuml;cker
 */
public final class BruteForceContradictionChecker
{
    /**
     * Nie gültig, Kontradiktion, wenn die beiden übergebenen Prädikate Und-Verknüpft werden.
     *
     * @param exclude
     * @param predicateToCheck1
     * @param predicateToCheck2 
     * @return ob Kontradiktion
     */
    /*default*/ static <CT> boolean isContradiction(
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> predicateToCheck1 ,
            final Predicate<CT> predicateToCheck2 )
    {
        if ( predicateToCheck1.equals( predicateToCheck2 ) )
        {
            // literale Übereinstimmung
            return false;
        }

        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceContradictionChecker.java> $LineNr: 35: isContradiction " + predicateToCheck1 + " to " + predicateToCheck2 );

        if ( ( predicateToCheck1.isPrimitive() ||
                ( predicateToCheck1 instanceof And &&
                  ( (And<CT>) predicateToCheck1 ).isAllSubPredicatesPrimitivAndNotParametrized() ) ) &&
                  ( predicateToCheck2.isPrimitive() ||
                  ( predicateToCheck2 instanceof And &&
                  ( (And<CT>) predicateToCheck2 ).isAllSubPredicatesPrimitivAndNotParametrized() ) ) )
        {
            final ReadOnlySet<PrimitivPredicate<CT>> includePredSet1;
            
            if ( predicateToCheck1.isPrimitive() )
            {
                final PrimitivPredicate<CT> primitivPredicate1 = (PrimitivPredicate<CT>) predicateToCheck1;

                includePredSet1 = primitivPredicate1.getIncludePredicateSetRecursivInclusiveSelf();
            }
            else
            {
                final And<CT> andPredicate1 = (And<CT>) predicateToCheck1;
                
                includePredSet1 = andPredicate1.getIncludePredicateSetRecursivInclusiveSelf();
            }
            

            final ReadOnlySet<PrimitivPredicate<CT>> includePredSet2;
            
            if ( predicateToCheck2.isPrimitive() )
            {
                final PrimitivPredicate<CT> primitivPredicate2 = (PrimitivPredicate<CT>) predicateToCheck2;

                includePredSet2 = primitivPredicate2.getIncludePredicateSetRecursivInclusiveSelf();
            }
            else
            {
                final And<CT> andPredicate2 = (And<CT>) predicateToCheck2;
                
                includePredSet2 = andPredicate2.getIncludePredicateSetRecursivInclusiveSelf();
            }
            

            final HashsetWithReadonly<PrimitivPredicate<CT>> jointIncludePredSet =
                    new HashsetWithReadonly<PrimitivPredicate<CT>>();
            
            jointIncludePredSet.addAll( includePredSet1 );
            jointIncludePredSet.addAll( includePredSet2 );
            
            final MutableBoundedExclude<CT> mutBndExc = new MutableBoundedExclude<CT>( cache.exclude );
            
            for ( final PrimitivPredicate<CT> jointIncludePredicate : jointIncludePredSet )
            {
                if ( false == mutBndExc.isValid( jointIncludePredicate ) )
                {
                    cache.incompatibleCache.put(
                            predicateToCheck1 ,
                            predicateToCheck2 );

                    cache.incompatibleCache.put(
                            predicateToCheck2 ,
                            predicateToCheck1 );

                    cache.contradictionPredicateCache.add(
                            predicateToCheck1 ,
                            predicateToCheck2 );

                    return true;
                }
            }
        }
        
        // nichts hilft, also brute force
        final Predicate<CT> checkAndPredicate =
                And.instanceOf(
                        //lineNr
                        0 ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //false ,
                        //andPredicateArr
                        new Predicate[]
                        {
                            predicateToCheck1 ,
                            predicateToCheck2
                        } );

        final boolean isContradiction =
                isContradiction(
                cache ,
                checkAndPredicate );

        if ( isContradiction )
        {
            //Inkompatibilität in Cache vermerken
            cache.incompatibleCache.put(
                    predicateToCheck1 ,
                    predicateToCheck2 );

            cache.incompatibleCache.put(
                    predicateToCheck2 ,
                    predicateToCheck1 );
        }

        return isContradiction;
    }

    /**
     * Nie gültig, Kontradiktion
     *
     * @param exclude
     * @param predicateToCheck
     * @return ob Kontradiktion
     */
    /*default*/ static <CT> boolean isContradiction(
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> predicateToCheck )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceContradictionChecker.java> $LineNr: 152: isContradiction " + predicateToCheck );

        final Predicate<CT> noXorPredicateToCheck =
                XorConverter.convertXor(
                        predicateToCheck ,
                        cache );

        final Predicate<CT> simplifiedForNotPredicateToCheck =
                Normalizer.simplifyNot(
                        noXorPredicateToCheck ,
                        cache );

        //final Predicate<CT> singleNotReplacedWithExcludePredicateToCheck =
        //        Normalizer.replaceSingleNotWithExclude(
        //                simplifiedForNotPredicateToCheck ,
        //                cache );

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        BruteForceSolver.collectSimplePredicates(
                //singleNotReplacedWithExcludePredicateToCheck
                simplifiedForNotPredicateToCheck ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        //singleNotReplacedWithExcludePredicateToCheck
                        simplifiedForNotPredicateToCheck ,
                        counter ,
                        simplePredicateSet );

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
//        final Map<ExcludeGroup<CT>, ExcludeGroupWithCounterListener<CT>> excludeGroupWithCounterListenerMap =
//                ExcludeGroupWithCounterListener.createGetOrCreateHashmap();

        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );

//            ExcludeGroupWithCounterListener.saveExcludeGroupsWithCounterListener(
//                    cache ,
//                    simplePredicateSet ,
//                    counter ,
//                    excludeGroupWithCounterListenerMap ,
//                    solvePred );
        }

        // jetzt noch die includierten Prädikate vermerken
        convertedSolvePredicate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            final boolean result =
                    convertedSolvePredicate.solve();

            if ( result )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        cache.exclude ,
                        //simplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache
                        //excludeGroupWithCounterListenerMap.values().toArray( new ExcludeGroupWithCounterListener[ excludeGroupWithCounterListenerMap.values().size() ] )
                        ).isValid() )
                    // dieser Check über alle primitiven Prädikate ist notwendig, weil kleinere Ausdrücke erfüllbar sein können aber grössere aufgrund des exclude unerfüllbar sein können
                {
                    // keine Kontradiktion
                    return false;
                }
            }
            counter.skip();
        }

        // Kontradiktion
        return true;
    }

}