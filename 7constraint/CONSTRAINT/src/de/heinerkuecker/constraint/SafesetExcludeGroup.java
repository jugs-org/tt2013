package de.heinerkuecker.constraint;

/**
 * {@link ExcludeGroup} for {@link SafeSet}.
 * 
 * @param <CT> Type-Parameter, Typ des Kontext-Objekts der Prädikate
 * @param <T> Type-Parameter, Typ des Objekts, welches den Java-Code-String für die Elemente des SafeSet liefert
 * @author Heiner K&uuml;cker
 */
public final class SafesetExcludeGroup<CT, T extends ToJavaStringable>
extends ExcludeGroup<CT>
{
    /**
     * Konstruktor.
     * 
     * @param name
     * @param predicateArr
     */
    public SafesetExcludeGroup( 
            final String name ,
            final boolean isClosedWorldAssumption ,
            final SafesetPrimitivPredicate<CT, T>... predicateArr )
    {
        super(
                name ,
                isClosedWorldAssumption ,
                predicateArr );
    }

}
