package de.heinerkuecker.constraint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Map zum Vermerken von welchen Prädikaten
 * ein bestimmtes Prädikat includiert wird.
 * 
 * @param <CT> Type-Parameter, Typ des Context-Objekts der Prädikate und Constraint-Klassen
 * @author Heiner K&uuml;cker
 */
public final class IsIncludedByMap<CT>
{
    /**
     * innere Multi-Map
     */
    private final HashsetHashmap<PrimitivPredicate<CT>, PrimitivPredicate<CT>> innerMap =
            new HashsetHashmap<PrimitivPredicate<CT>, PrimitivPredicate<CT>>();

    /**
     * Add.
     * @param predToAdd
     */
    /*default*/ void add(
            final PrimitivPredicate<CT> predToAdd )
    {
        //if ( predToAdd instanceof ParametricalPredicate )
        //    // diese Prüfung offenbart, dass mit der Prädikat-Klassen-Hierarchie etwas nicht stimmt
        //{
        //    throw new IllegalArgumentException(
        //            String.valueOf( predToAdd ) );
        //}
        
        if ( predToAdd.hasIncludes() )
        {
            for ( final PrimitivPredicate<CT> includedPred : predToAdd.includePredicateSet )
            {
                this.innerMap.put(
                        includedPred ,
                        predToAdd );
                
                if ( includedPred.hasIncludes() )
                    // rekursiv
                {
                    add(
                            includedPred );
                }
            }
        }
    }

    /*default*/ void addAll(
            final Iterable<PrimitivPredicate<CT>> predIterableToAdd )
    {
        for ( final PrimitivPredicate<CT> predToAdd : predIterableToAdd )
        {
            add( predToAdd );
        }
    }

    /**
     * Cache for {@link #getIncludedBySet}.
     */
    private final Map<PrimitivPredicate<CT>, ReadOnlySet<PrimitivPredicate<CT>>> includedBySetCache =
            new HashMap<PrimitivPredicate<CT>, ReadOnlySet<PrimitivPredicate<CT>>>();

    /**
     * TODO
     * @param includedPred
     * @return
     */
    /*default*/ ReadOnlySet<PrimitivPredicate<CT>> getIncludedBySet(
            final Iterable<PrimitivPredicate<CT>> includedPredIbl )
    {
        final HashsetWithReadonly<PrimitivPredicate<CT>> result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

        for ( final PrimitivPredicate<CT> includedPredicate : includedPredIbl )
        {
            result.addAll(
                    getIncludedBySet( 
                            includedPredicate) );
        }
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>( result );
    }

    /**
     * TODO
     * @param includedPred
     * @return
     */
    /*default*/ ReadOnlySet<PrimitivPredicate<CT>> getIncludedBySet(
            final PrimitivPredicate<CT> includedPred )
    {
        ReadOnlySet<PrimitivPredicate<CT>> result =
                this.includedBySetCache.get(
                        includedPred );

        if ( result == null )
        {
            result =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            this.innerMap.getAllConnected(
                                    includedPred ) );

            this.includedBySetCache.put(
                    includedPred ,
                    result );
        }

        return result;
    }

    /**
     * Cache for {@link #getIncludedBySet}.
     */
    private final Map<PrimitivPredicate<CT>, ReadOnlySet<PrimitivPredicate<CT>>> includedByAndSelfSetCache =
            new HashMap<PrimitivPredicate<CT>, ReadOnlySet<PrimitivPredicate<CT>>>();

    /**
     * TODO
     * @param includedPred
     * @return
     */
    /*default*/ ReadOnlySet<PrimitivPredicate<CT>> getIncludedByAndSelfSet(
            final PrimitivPredicate<CT> includedPred )
    {
        ReadOnlySet<PrimitivPredicate<CT>> result =
                this.includedByAndSelfSetCache.get(
                        includedPred );

        if ( result == null )
        {
            final Set<PrimitivPredicate<CT>> tmpIncludedBySet =
                    this.getIncludedBySet(
                            includedPred ).toJavaSet();
            
            tmpIncludedBySet.add(
                    includedPred );

            result =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            tmpIncludedBySet );

            this.includedByAndSelfSetCache.put(
                    includedPred ,
                    result );
        }

        return result;
    }
    
    /**
     * TODO
     * @param includedPred
     * @return
     */
    /*default*/ ReadOnlySet<PrimitivPredicate<CT>> getIncludedByAndSelfSet(
            final Iterable<PrimitivPredicate<CT>> includedPred )
    {
        final HashsetWithReadonly<PrimitivPredicate<CT>> result = new HashsetWithReadonly<PrimitivPredicate<CT>>();
        
        for ( PrimitivPredicate<CT> primitivPredicate : includedPred )
        {
            result.addAll(
                    getIncludedByAndSelfSet(
                            primitivPredicate ) );
        }
        
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>( result );
    }

}
