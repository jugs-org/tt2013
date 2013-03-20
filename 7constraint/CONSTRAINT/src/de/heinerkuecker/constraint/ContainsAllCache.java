package de.heinerkuecker.constraint;

import java.util.Set;

import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Cache-Klasse  zur Beschleunigung der
 * {@link set#containsAll}-Abfrage
 * in {@link ExcludeIncludeChecker}.
 * 
 * @author Heiner K&uuml;cker
 */
/*default*/ final class ContainsAllCache<CT>
{
    private final HashsetHashmap<Set<PrimitivPredicate<CT>>, ReadOnlySet<PrimitivPredicate<CT>>> trueCache =
            new HashsetHashmap<Set<PrimitivPredicate<CT>>, ReadOnlySet<PrimitivPredicate<CT>>>();

    private final HashsetHashmap<Set<PrimitivPredicate<CT>>, ReadOnlySet<PrimitivPredicate<CT>>> falseCache =
            new HashsetHashmap<Set<PrimitivPredicate<CT>>, ReadOnlySet<PrimitivPredicate<CT>>>();

    /*default*/ boolean containsAll(
            final Set<PrimitivPredicate<CT>> thisSetToCheck ,
            final ReadOnlySet<PrimitivPredicate<CT>> otherSetToCheck )
    {
        if ( trueCache.containsKeyValue(
                thisSetToCheck ,
                otherSetToCheck ) )
        {
            return true;
        }

        if ( falseCache.containsKeyValue(
                thisSetToCheck ,
                otherSetToCheck ) )
        {
            return false;
        }

        final boolean isContainsAll =
                thisSetToCheck.containsAll(
                        otherSetToCheck.toJavaSet() );

        if ( isContainsAll )
        {
            trueCache.put(
                    thisSetToCheck ,
                    otherSetToCheck );
        }
        else
        {
            falseCache.put(
                    thisSetToCheck ,
                    otherSetToCheck );
        }

        return isContainsAll;
    }
}
