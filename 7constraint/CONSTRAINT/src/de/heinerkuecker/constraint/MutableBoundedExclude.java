package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Exclude mit gebundenen Variablen
 * (Primitiv-Prädikate)
 * veränderlich.
 * 
 * @author Heiner K&uuml;cker
 */
public class MutableBoundedExclude<CT>
{
    final Exclude<CT> exclude;
    
    private final HashMap<Predicate<CT>, ReadOnlySet<? extends AbstractExcludeGroup<CT>>> boundedPredicateToExcludeGroupSetMap;
    
    private final List<DynamicExcludeGroup<CT>> dynExclGrpList = new ArrayList<DynamicExcludeGroup<CT>>();

    /**
     * Konstruktor.
     *
     * @param exclude Exclude-Definition ohne spezielle Bindung
     */
    public MutableBoundedExclude( 
            final Exclude<CT> exclude )
    {
        this.exclude = exclude;
        
        this.boundedPredicateToExcludeGroupSetMap =
                new HashMap<Predicate<CT>, ReadOnlySet<? extends AbstractExcludeGroup<CT>>>();
    }

    /**
     * Add-Methode.
     * 
     * @param predicateToAdd
     * @return
     */
    private void add(
            final PrimitivPredicate<CT> predicateToAdd )
    {
        if ( this.boundedPredicateToExcludeGroupSetMap.containsKey( predicateToAdd ) )
            // check for develop: Prädikat bereits vermerkt
        {
            return;
        }

// Prüfung für bessere Performance rausgenommen        
//        if ( isKontra( predicateToAdd ) )
//        {
//            throw new RuntimeException(
//                    "kontra " + predicateToAdd );
//        }
        
        if ( predicateToAdd instanceof ParametricalPredicate )
        {
            final AbstractExcludeGroup<CT> excludeGroup =
                    new DynamicExcludeGroup<CT>( 
                            (ParametricalPredicate) predicateToAdd );

            final ReadOnlySet<? extends AbstractExcludeGroup<CT>> excludeGroupSet =
                    //Arrays.asList(
                    new ReadOnlySetAdaptor<AbstractExcludeGroup<CT>>(
                            excludeGroup );
            
            this.boundedPredicateToExcludeGroupSetMap.put( 
                    predicateToAdd , 
                    excludeGroupSet );
        }
        else
        {
            final ReadOnlySet<ExcludeGroup<CT>> excludeGroupSet =
                    this.exclude.getExcludeGroupSet(
                            predicateToAdd );
            
            if ( excludeGroupSet == null || 
                    excludeGroupSet.isEmpty() )
            {
                return;
            }
            
            this.boundedPredicateToExcludeGroupSetMap.put( 
                    predicateToAdd , 
                    excludeGroupSet );
        }

        // TODO nicht machen, dadurch werden inkludierte Prädikate ungeprüft addiert: jetzt noch die includierten Prädikate (einfache Implikation) mit vermerken
        //if ( predicateToAdd.includePredicateSet != null )
        //{
        //    for ( final PrimitivPredicate<CT> implicitPredicate : predicateToAdd.includePredicateSet )
        //    {
        //        add( implicitPredicate );
        //    }
        //}
    }
    
    private boolean isKontra(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        if ( this.boundedPredicateToExcludeGroupSetMap.containsKey( predicateToCheck ) )
            // Prädikat war bereits als Schlüssel vermerkt
            // TODO dies sollte nach der Umstellung der Prüfung der Excludes am BinCounter nicht mehr auftreten
        {
            return false;
        }

        /* Prüfen, ob das Prädikat in einer der Exclude-Group-Sets enthalten ist
         */
        final Collection<ReadOnlySet<? extends AbstractExcludeGroup<CT>>> boundedExcludeGroupSetColl =
                this.boundedPredicateToExcludeGroupSetMap.values();
        
        for ( final ReadOnlySet<? extends AbstractExcludeGroup<CT>> excludeGroupSet : boundedExcludeGroupSetColl )
        {
            for ( final AbstractExcludeGroup<CT> excludeGroup : excludeGroupSet )
            {
                if ( excludeGroup.contains(
                        predicateToCheck ) )
                {
                    return true;
                }
            }
        }
        
        // jetzt noch die dynamischen Excludes prüfen
        for ( final DynamicExcludeGroup<CT> dynExclGrp : this.dynExclGrpList )
        {
            if ( dynExclGrp.contains( predicateToCheck ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Methode zum Prüfen auf Verletzung Exclude und Vermerken
     * des Prädikates für nächste Prüfung.
     * 
     * @param predicateToCheck
     * @return
     */
    private boolean isKontraAndAdd(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        if ( isKontra(
                predicateToCheck ) )
        {
            return true;
        }

        add(
                predicateToCheck );

        return false;
    }
    
    /**
     * Methode zum Prüfen auf Verletzung Exclude und Vermerken
     * des Prädikates für nächste Prüfung.
     * 
     * @param predicateToCheck
     */
    public boolean isValid(
            final PrimitivPredicate<CT> predicateToCheck )
    //throws SolveBreakException
    {
        if ( this.isKontraAndAdd(
                predicateToCheck ) )
        {
            //throw new SolveBreakException();
            return false;
        }
        
        if ( predicateToCheck.includePredicateSet != null )
        {
            for ( final PrimitivPredicate<CT> includedPredicateToCheck : predicateToCheck.includePredicateSet )
            {
                if ( false == isValid(
                        includedPredicateToCheck ) )
                {
                    return false;
                }
            }
        }
        
        return true;
    }
}
