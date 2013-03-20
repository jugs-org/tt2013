package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * Hilfsklasse mit Methoden zum
 * Umwandeln von Prädikat-Expressions
 * für sichere Operationen
 * {@link TransformDestinationConstraintSafeOperation}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateTransformerForSafeOperation
{
    /**
     * 
     * @param predicateToTransformAndAdd
     * @param safeOperation
     * @param cache
     * @return
     */
    public static <CT> Predicate<CT> tranformAndAdd(
            final Predicate<CT> predicateToTransformAndAdd ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Predicate<CT> transformedPredicate =
                transform( 
                        predicateToTransformAndAdd , 
                        safeOperation , 
                        cache ,
                        predicateToTransformAndAdd );
        
        if ( transformedPredicate != null &&
                transformedPredicate.isContradiction( cache ) )
        {
            throw new IllegalStateException(
                    "destination constraint is contradiction(not satisfiable) " +
                    transformedPredicate + " " +
                    "LineNr " + transformedPredicate.lineNr + " " +
                    "for safe operation " +
                    safeOperation );
        }
        
        final Predicate<CT> transformedAndAddedPredicate =
                add( 
                        predicateToTransformAndAdd.lineNr ,
                        transformedPredicate , 
                        safeOperation , 
                        cache );
        
        return transformedAndAddedPredicate;
    }

    private static <CT> Predicate<CT> add(
            final int lineNr ,
            final Predicate<CT> predicateToAdd ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<? extends Predicate<CT>> predicateListToAdd =
                safeOperation.getPredicatesToAdd(
                        lineNr ,
                        cache );
        
        if ( predicateListToAdd == null ||
                predicateListToAdd.isEmpty() )
        {
            return predicateToAdd;
        }
        
        final ArrayList<Predicate<CT>> newAndPredicateList =
                new ArrayList<Predicate<CT>>();

        if ( predicateToAdd != null )
        {
            newAndPredicateList.add( predicateToAdd );
        }
        
        newAndPredicateList.addAll( predicateListToAdd );
        
        final Predicate newAnd = 
                And.instanceOf( 
                        predicateToAdd != null
                            ? predicateToAdd.lineNr
                            : 0 , 
                        cache ,
                        //cleanForImplicits
                        //true ,
                        newAndPredicateList.toArray( new Predicate[ newAndPredicateList.size() ] ) );

        return newAnd;
    }

    private static <CT> Predicate<CT> transform(
            final Predicate<CT> predicateToTransform ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> originalPredicate )
    {
        if ( predicateToTransform instanceof And )
        {
            return
                    transformAnd( 
                            (And<CT>) predicateToTransform ,
                            safeOperation ,
                            cache ,
                            originalPredicate );
        }
        else if ( predicateToTransform instanceof Or )
        {
            return
                    transformOr( 
                            (Or<CT>) predicateToTransform ,
                            safeOperation ,
                            cache ,
                            originalPredicate );
        }
        else if ( predicateToTransform instanceof Not )
        {
            return
                    transformNot( 
                            (Not<CT>) predicateToTransform ,
                            safeOperation ,
                            cache ,
                            originalPredicate );
        }
        else if ( predicateToTransform instanceof Xor )
        {
            return
                    transformXor( 
                            (Xor<CT>) predicateToTransform ,
                            safeOperation ,
                            cache ,
                            originalPredicate );
        }
        //else if ( predicateToTransform instanceof ParametricalPredicate )
        //    // parametrisiertes Prädikat
        //{
        //    final ParametricalPredicate<CT> parametricalPredicate = (ParametricalPredicate<CT>) predicateToTransform;
        //}
        else if ( predicateToTransform instanceof PrimitivPredicate )
            // elementares (atomares) Prädikat
        {
            //final PrimitivPredicate<CT> primitivSubPredicate = (PrimitivPredicate<CT>) predicateToTransform;
            //final Predicate<CT> transformedSubPredicate =
            // TODO diese Operation habe ich an das Prädikat verlegt, damit dieses in einem if-else-Verteiler über die dem Prädikat bekannten sicheren Operationen die Transformation ausführt und bei einer unbekannten sicheren Operation eine IllegalArgumentException wirft
            //        safeOperation.transformPredicate(
            //                originalPredicate ,
            //                primitivSubPredicate );
            if ( false == ( predicateToTransform instanceof TransformablePredicate ) )
            {
                throw new PredicateNotImplementsTransformablePredicateInterface(
                        "predicate have to implement interface " + TransformablePredicate.class.getName() + " " +
                        predicateToTransform + " " +
                        "LineNr " + originalPredicate.lineNr );
            }

            final Predicate<CT> transformedSubPredicate =
                    ( (TransformablePredicate<CT>) predicateToTransform ).transform(
                            originalPredicate ,
                            safeOperation );

            return transformedSubPredicate;
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateToTransform ) );
        }
    }

    private static <CT> Predicate<CT> transformAnd(
            final And<CT> andToTransform ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> originalPredicate )
    {
        final List<Predicate<CT>> transformedPredList =
                new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> subPredicate : andToTransform )
        {
            final Predicate<CT> transformedSubPredicate =
                    transform( 
                            subPredicate , 
                            safeOperation , 
                            cache ,
                            originalPredicate );

            if ( transformedSubPredicate != null )
            {
                transformedPredList.add( transformedSubPredicate );
            }
        }
        
        if ( transformedPredList.isEmpty() )
        {
            // zu transformierendes Prädikat ist völlig verschwunden
            return null;
        }
        
        return
                And.instanceOf( 
                        andToTransform.lineNr , 
                        cache ,
                        //cleanForImplicits
                        //true ,
                        transformedPredList.toArray( new Predicate[ transformedPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> transformOr(
            final Or<CT> orToTransform ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> originalPredicate )
    {
        final List<Predicate<CT>> transformedPredList =
                new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> subPredicate : orToTransform )
        {
            final Predicate<CT> transformedSubPredicate =
                    transform( 
                            subPredicate , 
                            safeOperation , 
                            cache ,
                            originalPredicate );

            if ( transformedSubPredicate != null )
            {
                transformedPredList.add( transformedSubPredicate );
            }
        }
        
        if ( transformedPredList.isEmpty() )
        {
            // zu transformierendes Prädikat ist völlig verschwunden
            return null;
        }
        
        return
                Or.instanceOf( 
                        orToTransform.lineNr , 
                        cache , 
                        transformedPredList.toArray( new Predicate[ transformedPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> transformNot(
            final Not<CT> notToTransform ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> originalPredicate )
    {
        final Predicate<CT> transformedSubPredicate =
                transform( 
                        notToTransform.predicateToNegate , 
                        safeOperation , 
                        cache ,
                        originalPredicate );

        if ( transformedSubPredicate == null )
        {
            // zu transformierendes Prädikat ist völlig verschwunden
            return null;
        }

        return
                Not.instanceOf( 
                        notToTransform.lineNr , 
                        transformedSubPredicate );
    }

    private static <CT> Predicate<CT> transformXor(
            final Xor<CT> xorToTransform ,
            final TransformDestinationConstraintSafeOperation<CT> safeOperation ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> originalPredicate )
    {
        final List<Predicate<CT>> transformedPredList =
                new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> subPredicate : xorToTransform )
        {
            final Predicate<CT> transformedSubPredicate =
                    transform( 
                            subPredicate , 
                            safeOperation , 
                            cache ,
                            originalPredicate );

            if ( transformedSubPredicate != null )
            {
                transformedPredList.add( transformedSubPredicate );
            }
        }
        
        if ( transformedPredList.isEmpty() )
        {
            // zu transformierendes Prädikat ist völlig verschwunden
            return null;
        }
        
        return
                new Xor<CT>( 
                        xorToTransform.lineNr , 
                        transformedPredList.toArray( new Predicate[ transformedPredList.size() ] ) );
    }

}
