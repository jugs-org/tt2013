package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Exclude mit gebundenen Variablen
 * (Primitiv-Pr�dikate)
 * ver�nderlich.
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
            // check for develop: Pr�dikat bereits vermerkt
        {
            return;
        }

// Pr�fung f�r bessere Performance rausgenommen        
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

        // TODO nicht machen, dadurch werden inkludierte Pr�dikate ungepr�ft addiert: jetzt noch die includierten Pr�dikate (einfache Implikation) mit vermerken
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
            // Pr�dikat war bereits als Schl�ssel vermerkt
            // TODO dies sollte nach der Umstellung der Pr�fung der Excludes am BinCounter nicht mehr auftreten
        {
            return false;
        }

        /* Pr�fen, ob das Pr�dikat in einer der Exclude-Group-Sets enthalten ist
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
        
        // jetzt noch die dynamischen Excludes pr�fen
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
     * Methode zum Pr�fen auf Verletzung Exclude und Vermerken
     * des Pr�dikates f�r n�chste Pr�fung.
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
     * Methode zum Pr�fen auf Verletzung Exclude und Vermerken
     * des Pr�dikates f�r n�chste Pr�fung.
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
