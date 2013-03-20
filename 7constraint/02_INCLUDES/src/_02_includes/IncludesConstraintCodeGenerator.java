package _02_includes;

import java.io.File;
import java.io.IOException;

import _02_includes.enums.Rolle;
import _02_includes.predicates.RolleEbeneFiliale;
import _02_includes.predicates.RolleEbeneNiederlassung;
import _02_includes.predicates.RolleEbeneZentrale;
import _02_includes.predicates.RolleFiliale;
import _02_includes.predicates.RolleFilialeNord;
import _02_includes.predicates.RolleNiederlassung;
import _02_includes.predicates.RolleNiederlassungNord;
import _02_includes.predicates.RolleRegionNord;
import _02_includes.predicates.RolleZentrale;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;

/**
 * Code-Generator für 
 * Constraint-Expression-Java-Klassen
 * zur Prüfung von Constraints
 * zur Compile-Zeit.
 *
 * Achtung Achtung Achtung:
 * die main-Methode dieser Klasse schreibt auf Deine Platte,
 * also sichere Deine Daten und schaue Dir die Pfade genau an !!!
 * 
 * @author Heiner K&uuml;cker
 */
public final class IncludesConstraintCodeGenerator
{
    /**
     * Start Generierung.
     * 
     * @param args unused
     * @throws IOException rethrown
     */
    public static void main(
            final String [] args )
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Rolle>(
                //cacheFolder
                null ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_02_includes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_02_includes.enums.*" ,
                    "_02_includes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                true ,
                //deleteUnusedConstraintJavaFiles
                true , 
                //exclude
                new Exclude<Rolle>() )
        {{
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleZentrale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneZentrale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassungNord() );
            
            add(
                    new RolleFiliale() );
            
            add(
                    new RolleEbeneFiliale() );
            
            add(
                    new RolleFilialeNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleRegionNord() );
            
            add(
                    or(
                            new RolleZentrale() ,
                            new RolleNiederlassung() ) );

            add(
                    xor(
                            new RolleZentrale() ,
                            new RolleNiederlassung() ) );

            add(
                    not(
                            new RolleFiliale() ) );

            add(
                    and(
                            new RolleNiederlassung() ,
                            not(
                                    new RolleFiliale() ) ) );

            add(
                    and(
                            new RolleZentrale() ,
                            new RolleNiederlassung() ,
                            not(
                                    new RolleFiliale() ) ) );
        }}.execute();
    }

}