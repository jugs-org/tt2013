package de.heinerkuecker.constraint;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.constraint.util.CcgReflectionUtil;

/**
 * Zusammenfassung der Generierung unterschiedlicher
 * Constraints in einem Projekt mit jeweils verschiedenen
 * Kontext-Objekt-Typen.
 * <br/><br/>
 * Code-Generator für
 * Constraint-Expression-Java-Klassen
 * zur Prüfung von Constraints
 * zur Compile-Zeit.
 * <br/><br/>
 * Achtung Achtung Achtung:
 * die main-Methode dieser Klasse schreibt auf Deine Platte,
 * also sichere Deine Daten und schaue Dir die Pfade genau an !!!
 *
 * @author Heiner K&uuml;cker
 */
public final class ConstraintCodeGeneratorSuite
{
    /**
     * Brücken-Objekt zum Vermerken von einander
     * entsprechenden Prädikaten mit verschiedenen
     * Kontext-Objekt-Typen.
     */
    public final PredicateBridge bridge = new PredicateBridge();

    /**
     * Liste der Generatoren zur Erhaltung der Reihenfolge
     */
    private final List<AbstractConstraintCodeGenerator<?>> generatorList =
            new ArrayList<AbstractConstraintCodeGenerator<?>>();

    /**
     * Map vom Kontext-Objekt-Type zum jeweiligen Generator
     * zum Hinzufügen von Constraints für {@link BridgeSafeOperations}.
     */
    private final Map<Type, AbstractConstraintCodeGenerator<?>> ctxtypeToGeneratorMap =
            new HashMap<Type, AbstractConstraintCodeGenerator<?>>();

    /**
     * Code-Generator hinzufügen.
     * 
     * @param generatorToAdd hinzuzufügender Code-Generator
     */
    public void add(
            final AbstractConstraintCodeGenerator<?> generatorToAdd )
    {
        System.out.println( "add " + generatorToAdd.codegeneratorClassname.get() ); 

        this.generatorList.add(
                generatorToAdd );

        final Type gnrtCtxType =
                CcgReflectionUtil.typeParamArrOfObj(
                        generatorToAdd )[ 0 ];

        if ( this.ctxtypeToGeneratorMap.containsKey( gnrtCtxType ) )
        {
            throw new IllegalArgumentException(
                    "generator already added for context object type " +
                    gnrtCtxType );
        }

        this.ctxtypeToGeneratorMap.put(
                gnrtCtxType ,
                generatorToAdd );
    }

    /**
     * Generierung durchführen,
     * vor dem Schreiben der generierten Java-Dateien
     * erfolgt das gegenseitige Informieren der
     * verschiedenen Generatoren über benötigte
     * Constraints mit jeweils anderen Kontext-Objekt-Typen.
     * 
     * @throws IOException rethrown
     */
    public void execute()
    throws IOException
    {
        final long startTime = System.currentTimeMillis();

        for ( final AbstractConstraintCodeGenerator<?> generator : this.generatorList )
        {
            // Vorbereiten ohne SafeOperations
            generator.prepareBeforeSafeoperations();
        }
        
        for ( final AbstractConstraintCodeGenerator<?> generator : this.generatorList )
        {
            // Vorbereiten dynamische SafeOperations
            generator.addDynamicSafeOperations();
        }
        
        for ( final AbstractConstraintCodeGenerator<?> generator : this.generatorList )
        {
            // Vorbereiten SafeOperations in einem zweiten Schritt, weil bei SafeOperations mit anderem Ziel-Context-Objekt-Type jeeils die anderen Constraint-Code-Generatoren über zusätzlich erforderliche Constraints informiert werden
            generator.prepareSafeoperations(
                    this );
        }

        for ( final AbstractConstraintCodeGenerator<?> generator : this.generatorList )
        {
            generator.generate();
        }

        System.out.println( 
                "Duration " + 
                        ( ( System.currentTimeMillis() - startTime ) / 1000 ) +
                " seconds" );
    }

    /**
     * @param dstCtxObjType
     * @return
     */
    /*default*/ final Exclude<?> getExcludeForCtxobjtype(
            final Type dstCtxObjType )
    {
        return getCnstrcdgnrtForCtxobjtype( dstCtxObjType ).cache.exclude;
    }

    /**
     * @param dstCtxObjType
     * @return
     */
    /*default*/ final ConstraintCodeGeneratorCache<?> getCacheForCtxobjtype(
            final Type dstCtxObjType )
    {
        return getCnstrcdgnrtForCtxobjtype( dstCtxObjType ).cache;
    }

    /*default*/ AbstractConstraintCodeGenerator<?> getCnstrcdgnrtForCtxobjtype(
            final Type dstCtxObjType )
    {
        return ctxtypeToGeneratorMap.get( dstCtxObjType );
    }
}
