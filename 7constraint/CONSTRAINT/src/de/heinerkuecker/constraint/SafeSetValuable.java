package de.heinerkuecker.constraint;

/**
 * Dieses Interface muss von
 * Prädikaten implementiert
 * werden, welche in einem
 * Constraint mit einem
 * {@link SafeSet} verwendet
 * werden.
 * <br/>
 * Es gibt einen Wert zurück,
 * welcher bei Erfüllung des
 * Prädikates erwartet wird.
 * 
 * @author Heiner K&uuml;cker
 */
public interface SafeSetValuable<T>
{
    public T safeSetValue();
}
