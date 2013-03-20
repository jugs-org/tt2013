package de.heinerkuecker.constraint;

/**
 * Parametrisierbares Pr�dikat.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public interface ParametricalPredicate<CT>
{
    ///**
    // * Zu implementierende Methode zum Erzeugen
    // * eines Java-Source-Strings zum Ereugen
    // * eines parametrisierten Pr�dikat-Objekts
    // * mit dem korrekten Konstruktor.
    // * 
    // * @return Konstruktor-Aufruf-Java-Source-String
    // */
    //public String toJavaConstructorStr();
    
    /**
     * Zu implementierende Methode zum Erzeugen
     * eines Java-Source-Strings zum Ereugen
     * eines parametrisierten Pr�dikat-Objekts
     * mit dem korrekten Konstruktor
     * (bitte new davor setzen) oder
     * anhand einer instanceOf-Methode.
     * 
     * @return Konstruktor-oder-InstanceOf-Aufruf-Java-Source-String
     */
    public String toJavaInstanceOfStr();
    
    /**
     * Zu implementierende Methode zum Pr�fen,
     * ob dieses Pr�dikat zum �bergebenen
     * anderen Pr�dikat kompatibel ist<br/>
     * (das andere Pr�dikat die einfache 
     *  Implikation erf�llt)<br/>
     * this &rArr; other.
     * 
     * @param otherPredicateToCheck anderes Pr�dikat
     * @return ob kompatibel
     */
    public boolean isCompatibleTo(
            final PrimitivPredicate<CT> otherPredicateToCheck );

    /**
     * Zu implementierende Methode zum Pr�fen,
     * ob sich dieses Pr�dikat mit dem �bergebenen
     * anderen Pr�dikat auschliesst.
     * 
     * @param otherPredicateToCheck anderes Pr�dikat
     * @return ob exclude
     */
    public boolean isExcludeTo(
            final Predicate<CT> otherPredicateToCheck );

}
