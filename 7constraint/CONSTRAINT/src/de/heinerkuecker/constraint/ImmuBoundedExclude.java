package de.heinerkuecker.constraint;

import java.util.Collection;
import java.util.HashMap;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Exclude mit gebundenen Variablen
 * (Primitiv-Prädikate)
 * unveränderlich.
 * 
 * @author Heiner K&uuml;cker
 */
public class ImmuBoundedExclude<CT>
{
    final Exclude<CT> exclude;
    
    private final HashMap<Predicate<CT>, ReadOnlySet<ExcludeGroup<CT>>> boundedPredicateToExcludeGroupSetMap;

    /**
     * Konstruktor.
     *
     * @param exclude Exclude-Definition ohne spezielle Bindung
     */
    public ImmuBoundedExclude( 
            final Exclude<CT> exclude )
    {
        this.exclude = exclude;
        
        this.boundedPredicateToExcludeGroupSetMap =
                new HashMap<Predicate<CT>, ReadOnlySet<ExcludeGroup<CT>>>();
    }

    /**
     * Konstruktor.
     *
     * @param exclude
     * @param boundedPredicateToExcludeGroupMap
     */
    private ImmuBoundedExclude( 
            final Exclude<CT> exclude ,
            final HashMap<Predicate<CT>, ReadOnlySet<ExcludeGroup<CT>>> boundedPredicateToExcludeGroupSetMap )
    {
        this.exclude = exclude;
        this.boundedPredicateToExcludeGroupSetMap = boundedPredicateToExcludeGroupSetMap;
    }

    /**
     * Copy-Method for immutable Datenstruktur.
     * 
     * @param predicateToAdd
     * @return
     */
    public ImmuBoundedExclude<CT> add(
            final PrimitivPredicate<CT> predicateToAdd )
    {
        if ( this.boundedPredicateToExcludeGroupSetMap.containsKey( predicateToAdd ) )
            // Prädikat bereits vermerkt
        {
            return this;
        }
        
        if ( isKontra( predicateToAdd ) )
        {
            throw new RuntimeException(
                    "kontra " + predicateToAdd );
        }
        
        final ReadOnlySet<ExcludeGroup<CT>> excludeGroupSet =
                this.exclude.getExcludeGroupSet(
                        predicateToAdd );
        
        if ( excludeGroupSet == null ||
                excludeGroupSet.isEmpty() )
        {
            return this;
        }
        
        final HashMap<Predicate<CT>, ReadOnlySet<ExcludeGroup<CT>>> tmpBoundedPredicateToExcludeGroupSetMap =
                new HashMap<Predicate<CT>, ReadOnlySet<ExcludeGroup<CT>>>(
                        this.boundedPredicateToExcludeGroupSetMap );

        tmpBoundedPredicateToExcludeGroupSetMap.put( 
                predicateToAdd , 
                excludeGroupSet );
        
        return new ImmuBoundedExclude<CT>(
                exclude ,
                tmpBoundedPredicateToExcludeGroupSetMap );
    }
    
    public boolean isKontra(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        if ( this.boundedPredicateToExcludeGroupSetMap.containsKey( predicateToCheck ) )
            // Prädikat war als Schlüssel vermerkt
        {
            return false;
        }

        /* Prüfen, ob das Prädikat in einer der Exclude-Listen enthalten ist
         */
        final Collection<ReadOnlySet<ExcludeGroup<CT>>> boundedExcludeGroupSetColl =
                this.boundedPredicateToExcludeGroupSetMap.values();
        
        for ( final ReadOnlySet<ExcludeGroup<CT>> excludeGroupList : boundedExcludeGroupSetColl )
        {
            for ( ExcludeGroup<CT> excludeGroup : excludeGroupList )
            {
                if ( excludeGroup.predicateSet.contains( predicateToCheck ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

}
