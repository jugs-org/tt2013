package de.heinerkuecker.constraint;

import java.util.Set;

/**
 * Tupel für die Definition eines
 * SafeSet und die von de Methode
 * {@link BruteForceSolver#computeValidSafeset}
 * ermittelten Safeset-Elemente.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SafesetTojavastrbleTupel<CT, T extends ToJavaStringable>
{
    public final SafeSet<CT, T> safeSetDefinition;
    
    public final Set<T> tojavastrbleSet;

    /**
     * Konstruktor.
     * 
     * @param safeSetDefinition
     * @param tojavastrbleSet
     */
    public SafesetTojavastrbleTupel(
            final SafeSet<CT, T> safeSetDefinition ,
            final Set<T> tojavastrbleSet )
    {
        super();
        this.safeSetDefinition = safeSetDefinition;
        this.tojavastrbleSet = tojavastrbleSet;
    }

}
