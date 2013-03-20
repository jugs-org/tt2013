package de.heinerkuecker.constraint;

/**
 * Anhand der Informationen,
 * welche in dieser Klasse
 * codiert sind, wird eine
 * unver�nderliche Set-Member
 * (Menge) generiert, welche
 * alle m�glichen Auspr�gungen
 * der 
 * {@link SafeSetValuable}-Werte
 * enth�lt.
 * <br/><br/>
 * Dieses Set kann zum Filtern
 * einer Collection oder zum
 * Erzeugen einer SQL-WHERE-IN-Klausel
 * verwendet werden, um g�ltige
 * (das Constraint erf�llende)
 * Objekte/Datens�tze zu filtern.
 * 
 * <pre>
 * Beispiel:
 * 
 * Es wird von einer Firmen-Hierarchie
 * aus landes�bergreifender Zentrale und 
 * landesspezifischer Niederlassung
 * ausgegangen.
 * 
 * Pr�dikate:
 * 
 * Z: Zentrale
 *      Include: Ebene Zentrale
 * 
 * ND: Niederlassung Deutschland
 *      Include: Ebene Niederlassung
 *               Land  Deutschland
 * 
 * 
 * NP: Niederlassung Polen
 *      Include: Ebene Niederlassung
 *               Land  Polen
 * 
 * 
 * NF: Niederlassung Frankreich
 *      Include: Ebene Niederlassung
 *               Land  Frankreich
 * 
 * 
 * Constraint: ND or NP or NF
 *      SafeSet: { ND , NP , NF }
 * 
 * Constraint: Z or ND
 *      SafeSet: keins
 * 
 * </pre>
 * 
 * @param <CT> Type-Parameter, Typ des Kontext-Objektes, auf welches das Pr�dikat angewendet wird
 * @param <T> Type-Parameter, Typ des erwarteten Wertes
 * @author Heiner K&uuml;cker
 */
public final class SafeSet<CT, T extends ToJavaStringable>
{
    /**
     * Name der Set-Member
     */
    public final String name;

    ///**
    // * Java-Klasse der Elemente des Set
    // */
    //public final Class<T> safeSetElementClass;
    /**
     * Java-Klassen-Name der Elemente des Set
     */
    public final String safeSetElementClassName;

    /**
     * Nach meiner Meinung ist es nur sinnvoll,
     * ein SafeSet auf Pr�dikate zu beziehen,
     * die sich gegenseitig ausschliessen,
     * weil sich die Member eines Set
     * unterscheiden m�ssen.
     */
    public final SafesetExcludeGroup<CT, T> excludeGroup;

    /**
     * Konstruktor.
     * 
     * @param name
     * @param safeSetElementClass
     */
    public SafeSet( 
            final String name , 
            //final Class<T> safeSetElementClass
            final String safeSetElementClassName ,
            final SafesetExcludeGroup<CT, T> excludeGroup )
    {
        if ( name == null )
        {
            throw new IllegalArgumentException(
                    "name is null" );
        }
        this.name = name;
        
        //this.safeSetElementClass = safeSetElementClass;

        if ( safeSetElementClassName == null )
        {
            throw new IllegalArgumentException(
                    "safeSetElementClassName is null" );
        }
        this.safeSetElementClassName = safeSetElementClassName;

        if ( excludeGroup == null )
        {
            throw new IllegalArgumentException(
                    "excludeGroup is null" );
        }
        this.excludeGroup = excludeGroup;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
                "SafeSet[" +
                "name=" + this.name + ", " +
                "safeSetElementClassName=" + this.safeSetElementClassName + ", " +
                "excludeGroup=" + this.excludeGroup +
                "]";
    }

}
