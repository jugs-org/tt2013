package de.heinerkuecker.constraint;

import java.util.HashSet;

/**
 * {@link PrimitivPredicate} for {@link SafeSet}.
 * 
 * @author Heiner K&uuml;cker
 */
public abstract class SafesetPrimitivPredicate<CT, T extends ToJavaStringable>
extends PrimitivPredicate<CT>
implements SafeSetValuable<T>
{
    /**
     * Konstruktor.
     * @param lineNr
     * @param implicitsPredicateSet
     * @param safeMemberSet
     */
    public SafesetPrimitivPredicate( 
            final int lineNr ,
            final HashSet<? extends PrimitivPredicate<CT>> implicitsPredicateSet ,
            final HashSet<? extends SafeMember> safeMemberSet )
    {
        super(
                lineNr ,
                implicitsPredicateSet ,
                safeMemberSet );
    }

}
