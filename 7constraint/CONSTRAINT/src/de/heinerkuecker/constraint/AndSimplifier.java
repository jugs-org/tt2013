package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * Testfall: ((Z2 or ZA2 or R2 or Z3 or ZA3 or R3) and (not(EbeneZe) and IfbRechtskreisSgb2))
 * sollte vereinfacht werden zu: R2
 * 
 * @author Heiner K&uuml;cker
 */
public final class AndSimplifier
{
    /**
     * 
     * @param andToSimplify
     * @param cache
     * @return
     */
    public static <CT> Predicate<CT> simplify(
            final And<CT> andToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        Predicate<CT> tmpSimplifiedPred = andToSimplify;
        
        while ( tmpSimplifiedPred instanceof And )
        {
            final Predicate<CT> nextTmpSimplifiedPredicate =
                    innerSimplify(
                            (And<CT>) tmpSimplifiedPred ,
                            cache );
            
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
     * @param andToSimplify
     * @param cache
     * @return
     */
    private static <CT> Predicate<CT> innerSimplify(
            final And<CT> andToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> andSubPredList = new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> andSubPredicate : andToSimplify )
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
                // suchen nach Pr�dikaten, die Or-Pr�dikate vereinfachen k�nnen
            {
                final Predicate<CT> simplifyPredicate = andSubPredList.get( simplifierIndex );
                
                // das m�sste auch bei nicht primitiven Pr�dikaten klappen: if ( simplifyPredicate.isPrimitive() )
                {
                    // vereinfachend wirkendes Pr�dikat
                    //final PrimitivPredicate<CT> simplifyPrimitivPredicate = (PrimitivPredicate<CT>) simplifyPredicate;
                    
                    // Suchen eines zu vereinfachenden Pr�dikates
                    for ( int toSimplifyIndex = 0 ; toSimplifyIndex < andSubPredList.size() ; toSimplifyIndex++ )
                    {
                        if ( simplifierIndex != toSimplifyIndex )
                        {
                            final Predicate<CT> toSimplifyPredicate = andSubPredList.get( toSimplifyIndex );

                            if (
                                    //isAllCompatible(
                                    //        toSimplifyOrPredicate ,
                                    //        //simplifyPrimitivPredicate
                                    //        simplifyPredicate ,
                                    //        cache ) )
                                    // alle Sub-Pr�dikate im Or enthalten bereits das andere Pr�dikat
                                    BruteForceCompatibleChecker.isCompatibleTo(
                                            cache ,
                                            //thisPredicate
                                            toSimplifyPredicate ,
                                            //otherPredicate
                                            simplifyPredicate ) )
                            {
                                // das andere Pr�dikat kann entfallen
                                andSubPredList.remove( simplifierIndex );
                                
                                isChanged = true;
                                innerIsChanged = true;
                                // wenn dieses break nicht aufgerufen wird, terminiert der Algorithmus
                                break outerForLoop;
                            }
                            else if ( toSimplifyPredicate instanceof Or )
                                // die Aufgabe dieses Zweiges kann der OrSimplifier nicht �bernehmen, da dieser nur das Or-Pr�dikat sieht, nicht ein aussen liegendes And
                            {
                                // zu vereinfachendes Pr�dikat
                                final Or<CT> toSimplifyOrPredicate = (Or<CT>) toSimplifyPredicate;
                                
                                if ( hasContradiction(
                                        toSimplifyOrPredicate ,
                                        //simplifyPrimitivPredicate
                                        simplifyPredicate ,
                                        cache ) )
                                    // im Or stecken Pr�dikate, die aufgrund Kontradiktion entfallen k�nnen
                                {
                                    final Predicate<CT> simplifiedOrPredicate =
                                            removeContradictions(
                                                    toSimplifyOrPredicate ,
                                                    //simplifyPrimitivPredicate
                                                    simplifyPredicate ,
                                                    cache );

                                    andSubPredList.set(
                                            toSimplifyIndex ,
                                            simplifiedOrPredicate );
                                    
                                    isChanged = true;
                                    innerIsChanged = true;
                                    // wenn dieses break nicht aufgerufen wird, terminiert der Algorithmus
                                    break outerForLoop;
                                }
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
            return andToSimplify;
        }
        
        return And.instanceOf(
                andToSimplify.lineNr ,
                cache ,
                //cleanForImplicits
                //false ,
                //andPredicateArr
                (Predicate<CT>[]) andSubPredList.toArray( new Predicate[ andSubPredList.size() ] ) );
    }

    /**
     * Ob im �bergebenen Or Sub-Pr�dikate enthalten sind,
     * die mit dem �bergebenen anderen Pr�dikat nicht
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
     * Aus dem �bergebenen Or alle Sub-Pr�dikate entfernen,
     * die mit dem �bergebenen anderen Pr�dikat nicht
     * vereinbar sind.
     * 
     * @param orToSimplify
     * @param simplifyPredicate
     * @param cache
     * @return vereinfachtes Or-Pr�dikat
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
                (Predicate<CT>[]) orSubPredResultList.toArray( new Predicate[ orSubPredResultList.size() ] ) );
    }

    /**
     * Pr�fen, ob alle im Or enthaltenen Sub-Pr�dikate
     * mit dem anderen Pr�dikat kompatibel sind, so
     * dass das andere Pr�dikat entfallen kann.
     * 
     * @param simplifyOrPred
     * @param predicateToRemove
     * @param cache
     * @return
     */
    private static <CT> boolean isAllCompatible(
            final Or<CT> simplifyOrPred ,
            final Predicate<CT> predicateToRemove ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        for ( final Predicate<CT> orSubPredicate : simplifyOrPred )
        {
            if ( false == BruteForceCompatibleChecker.isCompatibleTo(
                    cache ,
                    orSubPredicate ,
                    predicateToRemove ) )
            {
                return false;
            }
        }
        return true;
    }

}
