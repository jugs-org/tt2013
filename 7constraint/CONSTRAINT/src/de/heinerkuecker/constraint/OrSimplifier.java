package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;

/**
 * TODO
 * 
 * Testfall: ((Z2 and ZA2) or Z2)
 * sollte vereinfacht werden zu: (Z2 and ZA2)
 * 
 * @author Heiner K&uuml;cker
 */
public final class OrSimplifier
{
    /**
     * 
     * @param orToSimplify
     * @param cache
     * @return
     */
    public static <CT> Predicate<CT> simplify(
            final Or<CT> orToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        Predicate<CT> tmpSimplifiedPred = orToSimplify;
        
        while ( tmpSimplifiedPred instanceof Or )
        {
            final Predicate<CT> nextTmpSimplifiedPredicate;
            
            if ( hasAllPredicatesSameInclude(
                        orToSimplify ,
                        cache ) &&
                    hasAllPredicatesWithSameIncludeInOr(
                            orToSimplify ,
                            cache ) )
            {
                nextTmpSimplifiedPredicate =
                        removeAllSameIncludePredicates(
                                orToSimplify ,
                                cache );
            }
            else
            {
                nextTmpSimplifiedPredicate =
                        innerSimplify(
                        (Or<CT>) tmpSimplifiedPred ,
                        cache );
            }

            if ( tmpSimplifiedPred.equals( nextTmpSimplifiedPredicate ) )
                // keine weitere Verbesserung
            {
                return nextTmpSimplifiedPredicate;
            }
            tmpSimplifiedPred = nextTmpSimplifiedPredicate;
        }
        return tmpSimplifiedPred;
    }

    /**
     * 
     * @param orToSimplify
     * @param cache
     * @return
     */
    private static <CT> Predicate<CT> innerSimplify(
            final Or<CT> orToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> andSubPredList = new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> andSubPredicate : orToSimplify )
        {
            andSubPredList.add(
                    andSubPredicate );
        }
        
        boolean isChanged = false;

        while ( true )
        {
            boolean innerIsChanged = false;
            outerForLoop :
            for ( int simplifierIndex = 0 ; simplifierIndex < andSubPredList.size() ; simplifierIndex++ )
                // suchen nach Prädikaten, die Or-Prädikate vereinfachen können
            {
                final Predicate<CT> simplifyPredicate = andSubPredList.get( simplifierIndex );
                
                // das müsste auch bei nicht primitiven Prädikaten klappen: if ( simplifyPredicate.isPrimitive() )
                {
                    // vereinfachend wirkendes Prädikat
                    //final PrimitivPredicate<CT> simplifyPrimitivPredicate = (PrimitivPredicate<CT>) simplifyPredicate;
                    
                    // Suchen eines zu vereinfachenden Prädikates
                    for ( int toSimplifyIndex = 0 ; toSimplifyIndex < andSubPredList.size() ; toSimplifyIndex++ )
                    {
                        if ( simplifierIndex != toSimplifyIndex )
                        {
                            final Predicate<CT> toSimplifyPredicate = andSubPredList.get( toSimplifyIndex );

                            if (
                                    BruteForceCompatibleChecker.isCompatibleTo(
                                            cache ,
                                            //thisPredicate
                                            simplifyPredicate ,
                                            //otherPredicate
                                            toSimplifyPredicate ) )
                            {
                                // dieses Prädikat kann entfallen
                                andSubPredList.remove( toSimplifyIndex );
                                
                                isChanged = true;
                                innerIsChanged = true;
                                // wenn dieses break nicht aufgerufen wird, terminiert der Algorithmus
                                break outerForLoop;
                            }
                        }
                    }
                }
            }
            if ( false == innerIsChanged )
            {
                // while Abbrechen
                break;
            }
        }

        if ( false == isChanged )
        {
            return orToSimplify;
        }
        
        return Or.instanceOf(
                orToSimplify.lineNr ,
                cache ,
                //cleanForImplicits
                //false ,
                //andPredicateArr
                andSubPredList.toArray( new Predicate[ andSubPredList.size() ] ) );
    }

    /**
     * Ob im übergebenen Or Sub-Prädikate enthalten sind,
     * die mit dem übergebenen anderen Prädikat nicht
     * vereinbar sind.
     * 
     * @param orToSimplify
     * @param simplifyPredicate
     * @param cache
     * @return
     */
    private static <CT> boolean hasContradiction(
            final Or<CT> orToSimplify ,
            final Predicate<CT> simplifyPredicate ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        for ( final Predicate<CT> orSubPredicate : orToSimplify )
        {
            if ( BruteForceContradictionChecker.isContradiction(
                    cache ,
                    orSubPredicate ,
                    simplifyPredicate ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Aus dem übergebenen Or alle Sub-Prädikate entfernen,
     * die mit dem übergebenen anderen Prädikat nicht
     * vereinbar sind.
     * 
     * @param orToSimplify
     * @param simplifyPredicate
     * @param cache
     * @return
     */
    private static <CT> Predicate<CT> removeContradictions(
            final Or<CT> orToSimplify ,
            final Predicate<CT> simplifyPredicate ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> orSubPredResultList = new ArrayList<Predicate<CT>>();
        
        for ( final Predicate<CT> orSubPredicate : orToSimplify )
        {
            if ( false == BruteForceContradictionChecker.isContradiction(
                    cache ,
                    orSubPredicate ,
                    simplifyPredicate ) )
            {
                orSubPredResultList.add(
                        orSubPredicate );
            }
        }
        return Or.instanceOf(
                orToSimplify.lineNr ,
                cache ,
                orSubPredResultList.toArray( new Predicate[ orSubPredResultList.size() ] ) );
    }
    
    /**
     * Wenn alle enthaltenen Prädikate eines Or
     * die selben Prädikate inkludieren und 
     * alle includierenden Prädikate im Or
     * enthalten sind (closed world), können 
     * die includierenden Prädikate durch ihre
     * includierten Prädikate ersetzt werden.
     * 
     * @param orToSimplify
     * @return
     */
    /*default*/ static <CT> Predicate<CT> removeAllSameIncludePredicates(
            final Or<CT> orToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        
        return And.instanceOf(
                orToSimplify.lineNr ,
                cache ,
                ( (PrimitivPredicate<CT>) orToSimplify.iterator().next() ).includePredicateSet.toArray( new PrimitivPredicate[ 0 ] ) );
    }

    /**
     * Feststellen, ob alle Prädikate die gleichen
     * Prädikate inkludieren.
     * 
     * @param orToSimplify
     * @param cache
     * @return
     */
    /*default*/ static <CT> boolean hasAllPredicatesSameInclude(
            final Or<CT> orToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( orToSimplify.size() < 2 )
        {
            // dies sollte eigentlich nie passieren
            return false;
        }

        boolean isFirstLoop = true;
        
        HashSet<? extends PrimitivPredicate<CT>> tmpIncludePredicateSet = null;

        for ( final Predicate<CT> orSubPredicate : orToSimplify )
        {
            if ( orSubPredicate instanceof ParametricalPredicate )
            {
                return false;
            }

            if ( false == ( orSubPredicate instanceof PrimitivPredicate ) )
            {
                return false;
            }

            final PrimitivPredicate<CT> orSubPrimitivPredicate = (PrimitivPredicate<CT>) orSubPredicate;
            
            if ( orSubPrimitivPredicate.includePredicateSet == null ||
                    orSubPrimitivPredicate.includePredicateSet.isEmpty() )
            {
                return false;
            }
            
            if ( isFirstLoop )
            {
                tmpIncludePredicateSet = orSubPrimitivPredicate.includePredicateSet;

                isFirstLoop = false;
            }
            else
            {
                if ( false == ( tmpIncludePredicateSet.equals( orSubPrimitivPredicate.includePredicateSet ) ) )
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Feststellen, ob alle Prädikate, welche die gleichen
     * Prädikate inkludieren, im übergebenen Or-Prädikat
     * enthalten sind.
     * 
     * @param orToSimplify
     * @param cache
     * @return
     */
    /*default*/ static <CT> boolean hasAllPredicatesWithSameIncludeInOr(
            final Or<CT> orToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final HashsetWithReadonly<PrimitivPredicate<CT>> allIncludeePredSet =
                cache.includedPredSetToPredicateMap.get(
                        // TODO eventuell ist das einfache includePredicateSet nicht eindeutig, sondern es solte die Methode getIncludePredicateSetRecursiv verwendet werden
                        (HashSet<PrimitivPredicate<CT>>) ( (PrimitivPredicate<CT>) orToSimplify.iterator().next() ).includePredicateSet );
        
        final HashsetWithReadonly<PrimitivPredicate<CT>> orPredicateSet =
                new HashsetWithReadonly<PrimitivPredicate<CT>>();

        for ( final Predicate<CT> orSubPredicate : orToSimplify )
        {
            orPredicateSet.add(
                    (PrimitivPredicate<CT>) orSubPredicate );
        }

        return allIncludeePredSet.equalsByElements( orPredicateSet );
    }
    

}
