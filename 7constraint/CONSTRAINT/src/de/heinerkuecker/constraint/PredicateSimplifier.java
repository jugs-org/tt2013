package de.heinerkuecker.constraint;

import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * TODO
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateSimplifier
{
    /**
     * 
     * @param predicateToSimplify
     * @param cache
     * @return
     */
    public static <CT> Predicate<CT> simplify(
            final Predicate<CT> predicateToSimplify ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        Predicate<CT> tmpSimplifiedPred = predicateToSimplify;
        
        while ( true )
        {
            if ( tmpSimplifiedPred instanceof PrimitivPredicate )
            {
                return tmpSimplifiedPred;
            }
            
            final Predicate<CT> nextTmpSimplifiedPredicate;
            if ( tmpSimplifiedPred instanceof And )
            {
                nextTmpSimplifiedPredicate =
                        AndSimplifier.simplify(
                                (And<CT>) tmpSimplifiedPred ,
                                cache );
            }
            else if ( tmpSimplifiedPred instanceof Or )
            {
                nextTmpSimplifiedPredicate =
                        OrSimplifier.simplify(
                                (Or<CT>) tmpSimplifiedPred ,
                                cache );
            }
            else if  ( tmpSimplifiedPred instanceof Not )
            {
                nextTmpSimplifiedPredicate =
                        Not.instanceOf(
                                tmpSimplifiedPred.lineNr ,
                                simplify(
                                        ( (Not<CT>) tmpSimplifiedPred ).predicateToNegate ,
                                        cache ) );
            }
            else
            {
                throw new UnreachableCodeException(
                        String.valueOf( tmpSimplifiedPred ) );
            }
            
            if ( tmpSimplifiedPred.equals( nextTmpSimplifiedPredicate ) )
                // keine weitere Verbesserung
            {
                return nextTmpSimplifiedPredicate;
            }
            tmpSimplifiedPred = nextTmpSimplifiedPredicate;
        }
    }

}
