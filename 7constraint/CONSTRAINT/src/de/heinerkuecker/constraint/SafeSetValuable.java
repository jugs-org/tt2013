package de.heinerkuecker.constraint;

/**
 * Dieses Interface muss von
 * Pr�dikaten implementiert
 * werden, welche in einem
 * Constraint mit einem
 * {@link SafeSet} verwendet
 * werden.
 * <br/>
 * Es gibt einen Wert zur�ck,
 * welcher bei Erf�llung des
 * Pr�dikates erwartet wird.
 * 
 * @author Heiner K&uuml;cker
 */
public interface SafeSetValuable<T>
{
    public T safeSetValue();
}
