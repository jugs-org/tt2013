package de.heinerkuecker.constraint;

import java.util.Iterator;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Diese Klasse wandelt primitive Pr�dikate
 * in Und-Verkn�pfungen ihrer inkludierten
 * Pr�dikate unter Beachtung der 
 * closed-world-Information aus 
 * {@link PredicateClosedIncludeChecker}
 * um.
 * 
 * @author Heiner K&uuml;cker
 */
public final class PredicateToFinalIncludedPredicateConverter
{
    /**
     * 
     * @param predicateToConvert
     * @param lineNr
     * @param cache
     * @return
     */
    /*default*/ static <CT> Predicate<CT> convert(
            final PrimitivPredicate<CT> predicateToConvert ,
            final int lineNr ,
            //final ReadOnlyMap<PrimitivPredicate<CT>, Boolean> predToIsIncludeClosedMap
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final ReadOnlySet<PrimitivPredicate<CT>> includePredicateSetRecursivInclusiveSelfSet = predicateToConvert.getIncludePredicateSetRecursivInclusiveSelf();

        final Set<PrimitivPredicate<CT>> tmpIncludePredicateSetRecursivInclusiveSelfSet =
                includePredicateSetRecursivInclusiveSelfSet.toJavaSet();

        for ( final Iterator<PrimitivPredicate<CT>> iterator = tmpIncludePredicateSetRecursivInclusiveSelfSet.iterator() ; iterator.hasNext() ; )
        {
            final PrimitivPredicate<CT> primitivPredicate = (PrimitivPredicate<CT>) iterator.next();
            
            if ( cache.predicateToIsIncludeClosedMap.get( primitivPredicate ) )
                // das aktuelle Pr�dikat kann durch eine Und-Verkn�pfung seiner inkludierten Pr�dikate vollst�ndig abgebildet werden
            {
                iterator.remove();
            }
        }

        return And.instanceOf(
                lineNr ,
                cache ,
                //andPredicateArr
                tmpIncludePredicateSetRecursivInclusiveSelfSet.toArray( new Predicate[ tmpIncludePredicateSetRecursivInclusiveSelfSet.size() ] ) );
    }

}
