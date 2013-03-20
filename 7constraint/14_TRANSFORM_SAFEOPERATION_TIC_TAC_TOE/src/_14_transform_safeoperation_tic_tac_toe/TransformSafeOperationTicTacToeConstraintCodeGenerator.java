package _14_transform_safeoperation_tic_tac_toe;

import java.io.File;

import _14_transform_safeoperation_tic_tac_toe.predicates.C;
import _14_transform_safeoperation_tic_tac_toe.predicates.G;
import _14_transform_safeoperation_tic_tac_toe.safeoperation.SetCell;
import _14_transform_safeoperation_tic_tac_toe.safeoperation.TakeMoveBack;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * Testfall zur Generierung von Constraint-Klassen
 * mit für ein <a href="http://de.wikipedia.org/wiki/Tic_Tac_Toe">Tic Tac Toe</a> Spiel.
 * <br/><br/>
 * Angeregt wurde dieses Beispiel von
 * Patrick Roemer auf der Usenet-Newsgroup
 * de.comp.lang.java.
 * <br/><br/>
 * Vorlage ist das Scala-Beispiel
 * <a href="http://blog.vasilrem.com/tic-tac-toe-api-with-phantom-types">Tic-Tac-Toe API with phantom types</a>.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TransformSafeOperationTicTacToeConstraintCodeGenerator
{
    /**
     * sich ausschliessende Prädikate
     * 
     * TODO dieser gegenseitige Ausschluss ist ebenfalls in G.isExcludeTo hinterlegt, sollte hier nicht notwendig sein
     */
    private static final Exclude<TicTacToe> exclude =
            new Exclude<TicTacToe>(
                    new ExcludeGroup<TicTacToe>(
                            "NextGamer" ,
                            //isClosedWorldAssumption
                            true ,
                            new G( Gamer.X.instance ) ,
                            new G( Gamer.O.instance ) 
                            ) );

    /**
     * Start Generierung.
     * 
     * @param args unused
     */
    public static void main(
            final String [] args )
    {
        try
        {
            new AbstractConstraintCodeGenerator<TicTacToe>(
                    //cacheFolder
                    //new File( "C:\\tmp" )
                    null ,
                    //srcDirPathStr
                    System.getProperty(
                            "user.dir" ) +
                    File.separator +
                    "src" , 
                    //packageName
                    "_14_transform_safeoperation_tic_tac_toe.constraints" ,
                    //importStrArr ,
                    new String[]{ 
                        "_14_transform_safeoperation_tic_tac_toe.*" ,
                        "_14_transform_safeoperation_tic_tac_toe.predicates.*" } ,
                    //contextObjClassName
                    "TicTacToe" , 
                    //contextObjName
                    "game" ,
                    //writeToDisc
                    true ,
                    //deleteUnusedConstraintJavaFiles
                    true , 
                    exclude )
            {{
                add(
                        // leeres Spielfeld
                        and(
                                new G( Gamer.X.instance ) ,
                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
                        new AbstractSafeOperation[]
                        {
// TODO Übungsaufgabe Stuttgarter Testtage 2013                            
//                            new SetCell( Gamer.X.instance , 0 , 0 ) ,
//                            new SetCell( Gamer.X.instance , 1 , 1 ) ,
//                            new SetCell( Gamer.X.instance , 0 , 1 ) ,
//                            new SetCell( Gamer.X.instance , 0 , 2 ) ,
                        } );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//                add(
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 0 , 0 ) ,
//                            new SetCell( Gamer.O.instance , 0 , 1 ) ,
//                        } );
//
//                add(
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 0 , 2 )
//                        } );
//
//                add(
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 0 ) ,
//                            new SetCell( Gamer.O.instance , 2 , 2 ) ,
//                        } );
//
//                add(
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new TakeMoveBack( Gamer.X.instance )
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CEmpty01_CX02_CO10_CX11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 2 ) ,
//                            
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CEmpty01_CX02_CO10_CX11_CEmpty12_CEmpty20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 0 ) ,
//                            
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CEmpty01_CEmpty02_CO10_CX11_CEmpty12_CEmpty20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 0 )
//                        } );
//
//                add(
//                        // ANDB_GO_CX00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 0 , 1 )
//                        } );
//                
//                add(
//                        // ANDB_GX_CX00_CO01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 0 , 2 )
//                        } );
//                
//                add(
//                        // ANDB_GO_CX00_CO01_CX02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 0 )
//                        } );
//                
//                add(
//                        // ANDB_GX_CX00_CO01_CX02_CO10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 1 , 1 )
//                        } );
//                
//                add(
//                        // ANDB_GO_CX00_CO01_CX02_CO10_CX11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 0 )
//                        } );
//                
//                add(
//                        // ANDB_GX_CX00_CO01_CX02_CO10_CX11_CEmpty12_CO20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 1 , 2 )
//                        } );
//                
//                add(
//                        // ANDB_GO_CX00_CO01_CX02_CO10_CX11_CX12_CO20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 2 )
//                        } );
//                
//                add(
//                        // ANDB_GX_CX00_CO01_CX02_CO10_CX11_CX12_CO20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 1 )
//                        } );
//                
//                add(
//                        // ANDB_GO_CEmpty00_CX01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 1 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CEmpty00_CX01_CEmpty02_CEmpty10_CO11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 1 ) ,
//                            new SetCell( Gamer.X.instance , 0 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CEmpty00_CX01_CEmpty02_CEmpty10_CO11_CEmpty12_CEmpty20_CX21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CEmpty00_CX01_CEmpty02_CEmpty10_CO11_CEmpty12_CO20_CX21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 0 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CEmpty00_CX01_CX02_CEmpty10_CO11_CEmpty12_CO20_CX21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 0 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CX01_CX02_CEmpty10_CO11_CEmpty12_CO20_CX21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CX01_CX02_CEmpty10_CO11_CEmpty12_CO20_CX21_CX22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CEmpty00_CEmpty01_CX02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 0 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CEmpty01_CX02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CEmpty01_CX02_CEmpty10_CEmpty11_CEmpty12_CX20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 1 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CEmpty01_CX02_CEmpty10_CO11_CEmpty12_CX20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CEmpty01_CX02_CEmpty10_CO11_CEmpty12_CX20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CEmpty01_CX02_CEmpty10_CO11_CO12_CX20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 1 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CEmpty00_CO01_CEmpty02_CEmpty10_CX11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 0 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CX00_CO01_CEmpty02_CEmpty10_CX11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CX00_CO01_CEmpty02_CEmpty10_CX11_CEmpty12_CEmpty20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 1 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CX00_CO01_CEmpty02_CX10_CX11_CEmpty12_CEmpty20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CX00_CO01_CEmpty02_CX10_CX11_CEmpty12_CO20_CEmpty21_CO22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 1 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CEmpty00_CX01_CX02_CEmpty10_CO11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.Empty.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 0 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CX01_CX02_CEmpty10_CO11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CX01_CX02_CEmpty10_CO11_CEmpty12_CEmpty20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 1 , 2 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CX01_CX02_CEmpty10_CO11_CO12_CEmpty20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 1 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GO_CO00_CX01_CX02_CX10_CO11_CO12_CEmpty20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.O.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.O.instance , 2 , 0 ) ,
//                        } );
//
//                add(
//                        // ANDB_GX_CO00_CX01_CX02_CX10_CO11_CO12_CO20_CEmpty21_CX22_ANDE
//                        and(
//                                new G( Gamer.X.instance ) ,
//                                C.instanceOf( Cell.O.instance , 0 , 0 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 0 , 2 ) ,
//                                C.instanceOf( Cell.X.instance , 1 , 0 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 1 ) ,
//                                C.instanceOf( Cell.O.instance , 1 , 2 ) ,
//                                C.instanceOf( Cell.O.instance , 2 , 0 ) ,
//                                C.instanceOf( Cell.Empty.instance , 2 , 1 ) ,
//                                C.instanceOf( Cell.X.instance , 2 , 2 ) ) ,
//                        new AbstractSafeOperation[]
//                        {
//                            new SetCell( Gamer.X.instance , 2 , 1 ) ,
//                        } );

        }}.execute();
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}