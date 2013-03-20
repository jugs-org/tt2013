package de.heinerkuecker.constraint;

/**
 * Interface f�r Objekte,
 * die sich in Java-Quelltext
 * umwandeln lassen.
 * 
 * TODO noch genauer spezifizieren Klasse; Methode/Konstruktor; Member; Statement; Expression
 * 
 * Achtung: f�r die Verwendung im {@link AbstractConstraintCodeGenerator}
 *          m�ssen die Methoden {@link Object#hashCode()} und {@link Object#equals(Object)}
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
