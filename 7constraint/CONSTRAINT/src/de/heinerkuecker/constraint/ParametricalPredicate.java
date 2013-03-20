package de.heinerkuecker.constraint;

/**
 * Parametrisierbares Prädikat.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public interface ParametricalPredicate<CT>
{
    ///**
    // * Zu implementierende Methode zum Erzeugen
    // * eines Java-Source-Strings zum Ereugen
    // * eines parametrisierten Prädikat-Objekts
    // * mit dem korrekten Konstruktor.
    // * 
    // * @return Konstruktor-Aufruf-Java-Source-String
    // */
    //public String toJavaConstructorStr();
    
    /**
     * Zu implementierende Methode zum Erzeugen
     * eines Java-Source-Strings zum Ereugen
     * eines parametrisierten Prädikat-Objekts
     * mit dem korrekten Konstruktor
     * (bitte new davor setzen) oder
     * anhand einer instanceOf-Methode.
     * 
     * @return Konstruktor-oder-InstanceOf-Aufruf-Java-Source-String
     */
    public String toJavaInstanceOfStr();
    
    /**
     * Zu implementierende Methode zum Prüfen,
     * ob dieses Prädikat zum übergebenen
     * anderen Prädikat kompatibel ist<br/>
     * (das andere Prädikat die einfache 
     *  Implikation erfüllt)<br/>
     * this &rArr; other.
     * 
     * @param otherPredicateToCheck anderes Prädikat
     * @return ob kompatibel
     */
    public boolean isCompatibleTo(
            final PrimitivPredicate<CT> otherPredicateToCheck );

    /**
     * Zu implementierende Methode zum Prüfen,
     * ob sich dieses Prädikat mit dem übergebenen
     * anderen Prädikat auschliesst.
     * 
     * @param otherPredicateToCheck anderes Prädikat
     * @return ob exclude
     */
    public boolean isExcludeTo(
            final Predicate<CT> otherPredicateToCheck );

}
