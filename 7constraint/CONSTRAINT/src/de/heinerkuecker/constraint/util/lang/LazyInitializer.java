package de.heinerkuecker.constraint.util.lang;

/**
 * Klasse für einen bei Bedarf
 * (faul) zu initialisierenden
 * Wert.
 * 
 * @param <T> Type-Parameter, Typ des Wertes
 * @author Heiner K&uuml;cker
 */
public abstract class LazyInitializer<T>
{
    /**
     * Bei Bedarf (faul) zu 
     * initialisierender Wert.
     */
    private T t;

    /**
     * Merker, ob die Initialisierung
     * bereits erfolgte.
     * Das Prüfen der Initialisierung
     * durch Vergleich des vermerkten
     * Wertes mit <code>null</code> könnte 
     * inkorrekt sein, weil auch null ein 
     * erlaubter Wert ist.
     */
    private boolean isInitialized = false;
    
    /**
     * @return faul (lazy) initialisierter Wert
     */
    public final T get()
    {
        if ( false == this.isInitialized )
        {
            this.t = init();
            this.isInitialized = true;
        }
        return this.t;
    }
    
    /**
     * @return ob die Initialisierung erfolgte
     */
    public final boolean isInitialized()
    {
        return this.isInitialized;
    }

    /**
     * Zu implementierende Methode zum
     * Initialisieren des Wertes.
     * 
     * @return initialisierter Wert
     */
    abstract protected T init();

    /**
     * Setzen des Wertes unter Umgehung der
     * Backend-Logik.
     * 
     * @param value value to set
     */
    public final void set(
            final T value )
    {
        this.isInitialized = true;
        this.t = value;
    }
    
}
