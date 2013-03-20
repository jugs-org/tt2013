package _01_simple;

import java.io.File;
import java.io.IOException;

import _01_simple.enums.Rolle;
import _01_simple.predicates.RolleFiliale;
import _01_simple.predicates.RolleNiederlassung;
import _01_simple.predicates.RolleZentrale;
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
public final class SimpleConstraintCodeGenerator
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
                "_01_simple.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_01_simple.enums.*" ,
                    "_01_simple.predicates.*" } ,
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
            add(
                    new RolleZentrale() );
            
            add(
                    new RolleNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassungNord() );
            
            add(
                    new RolleFiliale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleFilialeNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    or(
//                            new RolleZentrale() ,
//                            new RolleNiederlassung() ) );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    and(
//                            new RolleNiederlassung() ,
//                            new RolleNiederlassungNord() ) );

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