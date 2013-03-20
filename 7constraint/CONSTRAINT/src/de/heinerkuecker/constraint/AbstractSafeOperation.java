package de.heinerkuecker.constraint;

/**
 * Diese Klasse speichert die Definition
 * einer sicheren Operation, welche bei
 * einem bestimmten eingehaltenen
 * Constraint m�glich ist.
 * <br/>
 * Zur Generierungszeit wird gepr�ft, ob die
 * Operation sicher m�glich ist.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractSafeOperation<CT>
{
    /**
     * Zeilen-Nummer der definition dieser sicheren Operation.
     */
    public final int lineNr;

    /**
     * Konstruktor.
     * 
     * @param lineNr Zeilen-Nummer der definition dieser sicheren Operation
     */
    public AbstractSafeOperation(
            final int lineNr )
    {
        this.lineNr = lineNr;
    }

    /**
     * Falls eine �quivalente Constraint-Expression 
     * f�r das andere(rechte) Operanden-Constraint bereits 
     * definiert ist, wird dieses verwendet und hier vermerkt.
     * <br/>
     * Unter dem anderen(rechten) Operanden-Constraint
     * verstehe ich ein Constraint, welches eventuell
     * als Parameter der generierten Methode auftaucht:
     * <pre>
     *     Value/DestinationConstraint safeOperation(
     *             final OtherConstraint constraint ) {
     *         ...
     * </pre>
     * 
     * <br/>
     * Dies ist ein tempor�rer Zwischenspeicher f�r den Code-Generator
     */
    /*default*/ Predicate<CT> reusedOrCreatedOtherPredicate;
    
    /**
     * Zu implementierende Methode zum Zur�ckgeben
     * der Voraussetzung f�r die sichere Operation.
     * <br/>
     * Falls keine Voraussetzung erfoderlich ist,
     * darf <code>null</code> zur�ckgegeben werden.
     * 
     * @param cache Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verkn�pften Pr�dikaten
     * @return vorausgesetztes Pr�dikat bzw Constraint-Expression
     */
    abstract public Predicate<CT> getPremise(
            final ConstraintCodeGeneratorCache<CT> cache );

    /**
     * Zu implementierende Methode zum Zur�ckgeben
     * eines anderen(rechten) (Parameter-)Pr�dikats
     * f�r die sichere Operation.
     * <br/>
     * Falls kein anderes(rechtes) (Parameter-)Pr�dikat 
     * erfoderlich ist, darf <code>null</code> zur�ckgegeben
     * werden.
     * 
     * @param cache Hilfsparameter zum Anlegen von mit {@link And}, {@link Or} usw verkn�pften Pr�dikaten
     * @return anderes(rechtes) (Parameter-)Pr�dikat bzw Constraint-Expression
     */
    abstract public Predicate<CT> getOtherParameterPredicate(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<CT> cache );
}
