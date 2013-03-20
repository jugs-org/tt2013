package de.heinerkuecker.constraint;

/**
 * Interface für Objekte,
 * die sich in Java-Quelltext
 * umwandeln lassen.
 * 
 * TODO noch genauer spezifizieren Klasse; Methode/Konstruktor; Member; Statement; Expression
 * 
 * Achtung: für die Verwendung im {@link AbstractConstraintCodeGenerator}
 *          müssen die Methoden {@link Object#hashCode()} und {@link Object#equals(Object)}
 *          korrekt implementiert werden
 * 
 * @author Heiner K&uuml;cker
 */
public interface ToJavaStringable
{
    /**
     * @return Java-Quelltext-String
     */
    public String toJavaString();

}
