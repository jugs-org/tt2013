package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * @author Heiner K&uuml;cker
 */
/*default*/ final class XorConverter
{
    /**
     * Umwandeln XOR-Expressions
     * (A xor B) wird zu ((A and not(B)) or (not(A) or B))
     * 
     * @param <CT>
     * @param predicateToConvert
     * @return
     */
    /*default*/ static <CT> Predicate<CT> convertXor(
            final Predicate<CT> predicateToConvert ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Predicate<CT> convertedPredicate =
            innerConvertXor(
                predicateToConvert ,
                //exclude ,
                cache );

        return convertedPredicate;
    }

    /**
     * Innere rekursive Methode zum 
     * Umwandeln XOR-Expressions.
     * 
     * @param predicateToConvert
     * @return konvertierter Ausdruck
     */
    private static <CT> Predicate<CT> innerConvertXor(
            final Predicate<CT> predicateToConvert ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( predicateToConvert instanceof And )
        {
            return innerConvertXorAnd(
                    (And<CT>) predicateToConvert ,
                    cache );
        }
        else if ( predicateToConvert instanceof Or )
        {
            return innerConvertXorOr(
                    (Or<CT>) predicateToConvert ,
                    cache );
        }
        else if ( predicateToConvert instanceof Not )
        {
            return innerConvertXorNot( 
                    (Not<CT>) predicateToConvert ,
                    cache );
        }
        else if ( predicateToConvert instanceof Xor )
        {
            return innerConvertXorXor(
                    (Xor<CT>) predicateToConvert ,
                    cache );
        }
        else
        {
            return predicateToConvert;
        }
    }

    /**
     * Innere rekursive Methode zum 
     * Umwandeln XOR-Expressions.
     * 
     * @param andPredicateToConvert zu konvertierender And-Ausdruck
     * @return konvertierter Ausdruck
     */
    private static <CT> /*And*/Predicate<CT> innerConvertXorAnd(
            final And<CT> andPredicateToConvert ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        boolean isChanged = false;
        
        final List<Predicate<CT>> subPredList =
            new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> subPredicate : andPredicateToConvert )
        {
            final Predicate<CT> convertedSubPredicate =
                    convertXor( 
                            subPredicate ,
                            cache );

            if ( false == convertedSubPredicate.equals( subPredicate ) )
            {
                isChanged = true;
            }

            subPredList.add(
                    convertedSubPredicate );
        }
        
        if ( false == isChanged )
        {
            return andPredicateToConvert;
        }
        
        return 
                And.instanceOf(
                        andPredicateToConvert.lineNr ,
                        cache ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }
    
    /**
     * Innere rekursive Methode zum 
     * Umwandeln XOR-Expressions.
     * 
     * @param orPredicateToConvert zu konvertierender Or-Ausdruck
     * @return konvertierter Ausdruck
     */
    private static <CT> /*Or*/Predicate<CT> innerConvertXorOr(
            final Or<CT> orPredicateToConvert ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        boolean isChanged = false;
        
        final List<Predicate<CT>> subPredList =
            new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> subPredicate : orPredicateToConvert )
        {
            final Predicate<CT> convertedSubPredicate =
                    convertXor( 
                            subPredicate ,
                            //exclude ,
                            cache );

            if ( false == convertedSubPredicate.equals( subPredicate ) )
            {
                isChanged = true;
            }

            subPredList.add(
                    convertedSubPredicate );
        }
        
        if ( false == isChanged )
        {
            return orPredicateToConvert;
        }
        
        return 
                Or.instanceOf(
                        orPredicateToConvert.lineNr ,
                        cache ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }

    /**
     * Innere rekursive Methode zum 
     * Umwandeln XOR-Expressions.
     * 
     * @param notPredicateToNormalize zu konvertierender Not-Ausdruck
     * @return konvertierter Ausdruck
     */
    private static <CT> Predicate<CT> innerConvertXorNot(
            final Not<CT> notPredicateToNormalize ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Predicate<CT> convertSubPredicate =
                convertXor( 
                        notPredicateToNormalize.get() ,
                        cache );
        
        if ( convertSubPredicate.equals( notPredicateToNormalize.get() ) )
        {
            return notPredicateToNormalize;
        }

        return Not.instanceOf(
                notPredicateToNormalize.lineNr ,
                convertSubPredicate );
    }

    /**
     * Innere rekursive Methode zum 
     * Umwandeln XOR-Expressions.
     * 
     * @param xorPredicateToConvert zu konvertierender Xor-Ausdruck
     * @return konvertierter Ausdruck
     */
    private static <CT> /*Or*/Predicate<CT> innerConvertXorXor(
            final Xor<CT> xorPredicateToConvert ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> subPredList =
            new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> currPredicate : xorPredicateToConvert )
        {
            final List<Predicate<CT>> subSubPredList =
                new ArrayList<Predicate<CT>>();
            
            for ( Predicate<CT> predicate : xorPredicateToConvert )
            {
                if ( predicate != currPredicate )
                {
                    subSubPredList.add(
                            Not.instanceOf(
                                    xorPredicateToConvert.lineNr ,
                                    convertXor( 
                                    predicate ,
                                    cache ) ) );
                }
                else
                {
                    subSubPredList.add(
                            convertXor( 
                                    predicate ,
                                    cache ) );
                }
            }

            subPredList.add(
                    And.instanceOf(
                            xorPredicateToConvert.lineNr ,
                            cache ,
                            subSubPredList.toArray( 
                                    (Predicate<CT>[]) new Predicate[ subSubPredList.size() ] ) ) );
        }
        
        return 
                Or.instanceOf(
                        xorPredicateToConvert.lineNr ,
                        cache ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }

}
