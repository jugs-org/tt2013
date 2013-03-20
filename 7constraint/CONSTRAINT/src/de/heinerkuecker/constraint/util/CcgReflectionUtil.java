package de.heinerkuecker.constraint.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Hilfsmethoden.
 * 
 * @author Heiner K&uuml;cker
 */
public final class CcgReflectionUtil
{
    /**
     * Konstruktor privat,
     * Instanziieren verboten.
     */
    private CcgReflectionUtil()
    {
        super();
    }

    /**
     * Ermtittlung Typ-Parameter per Reflection
     * 
     * <a href="http://openbook.galileocomputing.de/javainsel/javainsel_09_006.html#dodtpd9bd7d67-99c3-4603-bc32-f30b8add83a9">Galileo Computing / &lt;openbook&gt; / Java ist auch eine Insel  9 Generics, 9.6 Konsequenzen der Typlöschung: Typ-Token, Arrays und Brücken, 9.6.2 Super-Type-Token</a>
     * 
     * @param obj Object, dessen Typ-Parameter zurückgegeben werden sollen
     * @return Typ-Parameter des übergebenen Objekts
     */
    public static Type[] typeParamArrOfObj(
            final Object obj )
    {
        return typeParamArrOfClass( obj.getClass() );
    }

    /**
     * Ermtittlung Typ-Parameter per Reflection
     * 
     * <a href="http://openbook.galileocomputing.de/javainsel/javainsel_09_006.html#dodtpd9bd7d67-99c3-4603-bc32-f30b8add83a9">Galileo Computing / &lt;openbook&gt; / Java ist auch eine Insel  9 Generics, 9.6 Konsequenzen der Typlöschung: Typ-Token, Arrays und Brücken, 9.6.2 Super-Type-Token</a>
     * 
     * @param classToReflect Klasse, deren Typ-Parameter zurückgegeben werden sollen
     * @return Typ-Parameter des übergebenen Objekts
     */
    public static Type[] typeParamArrOfClass(
            final Class<?> classToReflect )
    {
        return ( (ParameterizedType) classToReflect.getGenericSuperclass() ).getActualTypeArguments();
    }

}
