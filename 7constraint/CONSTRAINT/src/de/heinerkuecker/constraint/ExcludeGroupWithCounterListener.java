package de.heinerkuecker.constraint;

import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.GetOrCreateHashmap;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * TODO zur Beschleunigung BruteforceSolver.
 * 
 * @author Heiner K&uuml;cker
 */
public final class ExcludeGroupWithCounterListener<CT>
implements BinCounterSkipListener
{
    public final ExcludeGroup<CT> excludeGroup;
    
    /*default*/ int satisfiedPredicateCount;

    /**
     * Konstruktor.
     * @param excludeGroup
     */
    public ExcludeGroupWithCounterListener( 
            final ExcludeGroup<CT> excludeGroup )
    {
        this.excludeGroup = excludeGroup;
    }

    /**
     * @see BinCounterSkipListener#notifyListener
     */
    @Override
    public void notifyListener(
            final boolean newValue )
    {
        if ( newValue )
        {
            this.satisfiedPredicateCount++;
        }
        else
        {
            this.satisfiedPredicateCount--;
        }
    }
    
    /**
     * Hilfsmethode zum Erzeugen Hilfs-Map.
     * 
     * @return
     */
    /*default*/ static <CT> GetOrCreateHashmap<ExcludeGroup<CT>, ExcludeGroupWithCounterListener<CT>> createGetOrCreateHashmap()
    {
        return new GetOrCreateHashmap<ExcludeGroup<CT>, ExcludeGroupWithCounterListener<CT>>()
        {
            @Override
            public ExcludeGroupWithCounterListener<CT> create(
                    final ExcludeGroup<CT> excludeGroup )
            {
                return
                        new ExcludeGroupWithCounterListener<CT>(
                                excludeGroup );
            }
        };
    }

    /**
     * Vermerken ExcludeGroupsWithCounterListener.
     * 
     * @param cache
     * @param simplePredicateSet
     * @param counter
     * @param excludeGroupWithCounterListenerMap
     * @param solvePred
     */
    /*default*/ static <CT> void saveExcludeGroupsWithCounterListener(
            final ConstraintCodeGeneratorCache<CT> cache ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet ,
            final BinCounterWithSkipListeners counter ,
            final Map<ExcludeGroup<CT>, ExcludeGroupWithCounterListener<CT>> excludeGroupWithCounterListenerMap ,
            final SolveSimple<CT> solvePred )
    {
        for ( final PrimitivPredicate<CT> includedPrimitivPredicate : solvePred.predicateToSolve.getIncludePredicateSetRecursivInclusiveSelf() )
        {
            final ReadOnlySet<ExcludeGroup<CT>> excludeGroupSet = cache.exclude.getExcludeGroupSet( includedPrimitivPredicate );
            if ( excludeGroupSet != null )
            {
                for ( final ExcludeGroup<CT> excludeGroup : excludeGroupSet )
                {
                    if ( simplePredicateSet.contains( includedPrimitivPredicate ) )
                    {
                        final ExcludeGroupWithCounterListener<CT> excludeGroupWithCounterListener = excludeGroupWithCounterListenerMap.get( excludeGroup );

                        counter.addListener(
                                simplePredicateSet.indexOf( includedPrimitivPredicate ) ,
                                excludeGroupWithCounterListener );
                    }
                }
            }
        }
    }

}
