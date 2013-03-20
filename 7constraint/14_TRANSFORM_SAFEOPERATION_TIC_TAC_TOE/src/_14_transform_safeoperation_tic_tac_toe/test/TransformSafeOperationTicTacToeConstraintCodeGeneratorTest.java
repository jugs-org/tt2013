package _14_transform_safeoperation_tic_tac_toe.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import _14_transform_safeoperation_tic_tac_toe.Gamer;
import _14_transform_safeoperation_tic_tac_toe.TicTacToe;
import _14_transform_safeoperation_tic_tac_toe.TransformSafeOperationTicTacToeConstraintCodeGenerator;
import _14_transform_safeoperation_tic_tac_toe.constraints.ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * JUnit4-Testcase für Constraints, welche
 * durch {@link TransformSafeOperationTicTacToeConstraintCodeGenerator}
 * generiert wurden.
 * 
 * @author Heiner K&uuml;cker
 */
public class TransformSafeOperationTicTacToeConstraintCodeGeneratorTest
extends AbstractTicTacToeConstraintTest
{
    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Play a winning game
     */
    @Test
    public void testPlayAWinningGame()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX11().
                        setO00().
                        setX02().
                        setO10().
                        takeMoveBackX().
                        setO22().
                        setX20().
                        game;
        
        System.out.println( "testPlayAWinningGame" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.winnerIs().equals(
                        Gamer.X.instance ) );
    }

    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Play a draw
     */
    @Test
    public void testPlayADraw()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX00().
                        setO01().
                        setX02().
                        setO10().
                        setX11().
                        setO20().
                        setX12().
                        setO22().
                        setX21().
                        game;
        
        System.out.println( "testPlayADraw" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.isDraw() );
    }
    
    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Erster Spieler (X) gewinnt, weil Spieler Zwei (O) in der ersten Runde einen Fehler macht 
     * (dieses Spiel ist von der Wikipedia-Site, 1. Beispiel)
     * <a href="http://de.wikipedia.org/wiki/Tic_Tac_Toe">Tic Tac Toe</a>
     */
    @Test
    public void testXGewinnt1()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX02().
                        setO00().
                        setX20().
                        setO11().
                        setX22().
                        setO12().
                        setX21().
                        game;

        System.out.println( "testXGewinnt1" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.winnerIs().equals(
                        Gamer.X.instance ) );
    }

    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Erster Spieler (X) gewinnt, weil Spieler Zwei (O) in der ersten Runde einen Fehler macht
     * (dieses Spiel ist von der Wikipedia-Site, 2. Beispiel)
     * <a href="http://de.wikipedia.org/wiki/Tic_Tac_Toe">Tic Tac Toe</a>
     */
    @Test
    public void testXGewinnt2()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX11().
                        setO01().
                        setX00().
                        setO22().
                        setX10().
                        setO20().
                        setX12().
                        game;

        System.out.println( "testXGewinnt2" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.winnerIs().equals(
                        Gamer.X.instance ) );
    }

    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Erster Spieler (X) verliert, weil er in der zweiten Runde einen Fehler macht 
     * (dieses Spiel ist von der Wikipedia-Site, 3. Beispiel)
     * <a href="http://de.wikipedia.org/wiki/Tic_Tac_Toe">Tic Tac Toe</a>
     */
    @Test
    public void testXVerliert()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX01().
                        setO11().
                        setX21().
                        setO20().
                        setX02().
                        setO00().
                        setX22().
                        setO10().
                        game;

        System.out.println( "testXVerliert" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.winnerIs().equals(
                        Gamer.O.instance ) );
    }
    
    /**
     * Testcase-Methode für verschiedene generierte sichere Operationen.
     * 
     * Testfall: Kein Spieler gewinnt, da beide fehlerfrei spielen
     * (dieses Spiel ist von der Wikipedia-Site, 4. Beispiel)
     * <a href="http://de.wikipedia.org/wiki/Tic_Tac_Toe">Tic Tac Toe</a>
     */
    @Test
    public void testUnentschieden()
    {
        final TicTacToe resultGame =
                new ANDB_GX_CEmpty00_CEmpty01_CEmpty02_CEmpty10_CEmpty11_CEmpty12_CEmpty20_CEmpty21_CEmpty22_ANDE(
                        TicTacToe.empty() ).
                        setX01().
                        setO11().
                        setX02().
                        setO00().
                        setX22().
                        setO12().
                        setX10().
                        setO20().
                        setX21().
                        game;

        System.out.println( "testUnentschieden" );
        System.out.println( resultGame );
        System.out.println();
        
        Assert.assertTrue(
                resultGame.isDraw() );
    }

}