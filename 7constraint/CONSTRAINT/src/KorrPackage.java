//package dein.package ;

import java.io.* ;
import java.util.*;

/**
 * Programm zum Korrigieren der package-Deklaration in Java-Klassen.<br/>
 * <br/>
 * Es durchläuft rekursiv das aktuelle Verzeichnis und alle Unterverzeichnisse und<br/>
 * prüft alle enthaltenen <b>*.java</b>-Dateien.<br/>
 * <br/>
 * Aus den <b>*.java</b>-Dateien wird die package-Deklaration gelesen und<br/>
 * mit dem Pfad der jeweiligen Datei verglichen.<br/>
 * <br/>
 * Dieses Programm sollte in dem Verzeichnis gestartet werden,<br/>
 * welches die Root der zu prüfenden Java-Dateien darstellt und<br/>
 * auf welches die CLASSPATH-Variable zeigt.<br/>
 * <br/>
 * <b>Author:</b> <a href="http://www.heinerkuecker.de" target="_blank">Heiner Kücker</a>
 * <br/>
 *
 * @author Heiner Kücker
 */
public class KorrPackage {

  /**
   * Merker für Exit-Status ( 0 - keine Fehler )
   */
  private static int iExitStatus = 0 ;

  /**
   * Main-Methode dieser Klasse. <br/>
   * Beginnt im aktuellen Verzeichnis mit der Suche nach <b>*.java</b>-Dateien.<br/>
   */
  public static void main( String[] args ) {
    String startDir = "./src" ;

    if ( args.length > 0 ) {
      startDir = args[ 0 ] ;
    }
    System.out.println( "CheckPackage Start in " + startDir ) ;
    scanDir( startDir , "" ) ;

    //Rückgabe Fehlerstatus und Programmende
    //Bei Start aus ANT im Tag javac muss mit dem Parameter fork="true" eine neue JVM gestartet werden
    //sonst System.exit(int) auskommentieren, weil System.exit(int) ANT komplett abbricht
    System.exit( iExitStatus ) ;
  }//end method main

  /**
   * Methode zum Durchlaufen des aktuellen Verzeichnisses und
   * aller Unterverzeichnisse. <br/>
   * Ruft für alle Dateien die Methode {@link #processFile} auf.
   *
   * @param dirPath Pfad des zu durchsuchenden Verzeichnisses
   */
  public static void scanDir(
          String dirPath ,
          final String expectedPackageName )
  {
    //System.out.println( "scanDir: " + dirPath ) ;

    //Ermittle Liste Dateien
    File[] fileArr = new File( dirPath ).listFiles() ;

    for ( int i = 0 ; i < fileArr.length ; i++ ) {
      if ( fileArr[i].isDirectory() ) {
        scanDir(
                fileArr[i].getParent() + File.separator + fileArr[i].getName() ,
                expectedPackageName.length() == 0
                    ? fileArr[i].getName()
                    : expectedPackageName + "." + fileArr[i].getName() ) ;
      }
      else { // ein File
        processFile(
                fileArr[i].getParent() + File.separator + fileArr[i].getName() ,
                expectedPackageName ) ;
      }
    } // end for
  }//end method scanDir

  /**
   * Methode zum Abarbeiten aller Dateien, ausser Verzeichnisse. <br/>
   * Diese Methode wird von {@link #scanDir} für jede Datei<br/>
   * aufgerufen.<br/>
   * Sie ruft für jede <b>*.java</b>-Dateien die Methode
   * {@link processJavaFile} auf.
   *
   * @param fileName Dateiname mit Pfad und Endung
   */
  public static void processFile( 
          final String fileName ,
          final String expectedPackageName )
  {
    //System.out.println( "processFile: " + fileName ) ;
    if ( fileName.endsWith( ".java" ) ) {
      processJavaFile(
              fileName ,
              expectedPackageName ) ;
    }//end if
  }//end method processFile

  /**
   * Methode zum Abarbeiten aller <b>*.java</b>-Dateien. <br/>
   * Diese Methode wird von {@link #processFile} für jede
   * <b>*.java</b>-Datei aufgerufen.<br/>
   * Sie benutzt die Methoden {@link getPackage} <br/>
   * und {@link isPackageValid}.
   *
   * @param fileName Dateiname mit Pfad und Endung
   */
  public static void processJavaFile( 
          final String fileName ,
          final String expectedPackageName )
  {
    //System.out.println( "processJavaFile: " + fileName ) ;

    //Datei-Inhalt in String-Array einziehen
    String[] fileLines = fileToStrArr( fileName ) ;

    //package-Angabe holen
    String packageName = getPackage( fileLines ) ;
    //System.out.println( packageName ) ;
    if ( empty( packageName ) ) {
      System.out.println( "package-Declaration in " + fileName + " not found" ) ;
    }//end if

    if ( ! isPackageValid( packageName , fileName ) ) {
      System.out.println( "Package " + packageName + " in File " + fileName + " invalid" ) ;
      if ( expectedPackageName.length() > 0 )
          // nicht im default-package
      {
          korrPackage(
                  fileLines ,
                  expectedPackageName ,
                  fileName );
      }
      
      iExitStatus = 1 ; // Rückgabe: nicht ok
    }//end if

  }//end method processJavaFile

  /**
   * Methode zum Auslesen der Package-Deklaration aus einem String-Array. <br/>
   * Das String-Array enthält den datei-Inhalt und wird mit der Methode
   * {@link fileToStrArr(String)} eingelesen.<br/>
   * Der Prefix package und das endende Semikolon werden abgeschnitten.<br/>
   *
   * @param Datei-Inhalt als String-Array
   */
  public static String getPackage( String[] strArr ) {
    for ( int i = 0 ; i < strArr.length ; i++ ) {
      String str = strArr[i].trim() ;
      if ( str.startsWith( "package " ) ) {
        str = str.substring( "package".length() ) ;
        str = str.trim() ;
        if ( str.endsWith( ";" ) ) {
          str = str.substring( 0 , str.length() - 1 ).trim() ;
        }//end if
        return str ;
      }// end if
    }//end for
    return "" ;
  }//end method getPackage

  public static void korrPackage( 
          final String[] strArr ,
          final String packageStr ,
          final String fileName )
  {
      for ( int i = 0 ; i < strArr.length ; i++ )
      {
        String str = strArr[i].trim() ;
        if ( str.startsWith( "package " ) )
        {
            strArr[i] = "package " + packageStr + ";";
            
            LineNumbers.strArr2file( strArr , fileName );
            
            System.out.println( "Package corrected to " + packageStr );
          return;
        }// end if
      }//end for
      return;
    }//end method korrPackage

  /**
   * Prüfen ob die package-Deklaration in Ordnung ist. <br/>
   * Der Dateiname mit Pfad wird noch aufbereitet:<br/>
   * &nbsp.Abschneiden .java<br/>
   * &nbsp.evtl. führenden Punkt abschneiden<br/>
   * &nbsp.führenden Separator abschneiden<br/>
   * &nbsp.File-Separator durch . ersetzen<br/>
   * &nbsp.Abschneiden Datei-Name<br/>
   * Danach wird geprüft, ob der aufbereitete Pfad mit der package-Deklaration endet.
   *
   * @param packageStr package-Deklaration ohne Prefix package und das endende Semikolon.<br/>
   *                   Ermittelt mit {@link getPackage}
   * @param fileName Dateiname mit Pfad und Endung
   * @return ja, wenn package-Deklaration in Ordnung
   */
  public static boolean isPackageValid( String packageStr , String fileName ) {
    // .java abschneiden
    fileName = fileName.substring( 0 , fileName.length() - 5 ) ;

    //führenden Punkt abschneiden
    while ( fileName.startsWith( "." ) ) {
      fileName = fileName.substring( 1 ) ;
    }//end while

    //führenden Separator abschneiden
    while ( fileName.startsWith( File.separator ) ) {
      fileName = fileName.substring( 1 ) ;
    }//end while

    //File-Separator durch . ersetzen
    //fileName = StringUtil.replace( fileName , File.separator , "." ) ;
    fileName = fileName.replace( File.separatorChar , '.' ) ;

    //Abschneiden Datei-Name
    //System.out.println( fileName ) ;
    if ( fileName.lastIndexOf( "." ) > 0 ) {
      fileName = fileName.substring( 0 , fileName.lastIndexOf( "." ) ) ;
    }//end if

    //System.out.println( "Vergleich " + fileName + " mit " + packageStr ) ;

    return fileName.endsWith( packageStr ) ;
  }// end method isPackageValid

  /**
   * String auf null, Länge 0 oder nur Spaces prüfen
   *
   * @param strValue zu prüfender String
   * @return ob String empty (ja/nein)
   */
  public static final boolean empty(String strValue)
  {
    boolean bReturn = true;

    if (strValue != null)
    {
      if (strValue.trim().length() > 0)
      {
        bReturn = false;
      }
    }

    return bReturn;
  } // end method empty

  /**
   * Textdatei zeilenweise in ein String-Array einziehen . <br/>
   * Jede Zeile bildet einen String im String-Array.<br/>
   *
   * @param fileName Dateiname
   * @return String-Array
   */
  public static String[] fileToStrArr(String fileName)
  {
    if (fileName == null)
    {
      return null;
    }

    return fileToStrArr(new File(fileName));
  } // end method fileToStrArr

  /**
   * Textdatei zeilenweise in ein String-Array einziehen . <br/>
   * Jede Zeile bildet einen String im String-Array.<br/>
   *
   * @param inFile File-Object
   * @return String-Array
   */
  public static String[] fileToStrArr(File inFile)
  {
    if (inFile == null)
    {
      return null;
    }

    try
    {
      // BufferedReader zum zeilenweisen Lesen
      FileReader      defFileReader = new FileReader(inFile);
      BufferedReader  defBufferedReader = new BufferedReader(defFileReader);
      ArrayList<String> strArrayList = new ArrayList<String>();
      String          readLine = null;

      while ((readLine = defBufferedReader.readLine()) != null) // bis zum Ende der Datei
      {
        strArrayList.add(readLine);
      } // end while

      String[]  retArr = arrayListToStrArr(strArrayList);

      return retArr;
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
      //Log.addLog( e ) ;
      //throw new RuntimeException(ExceptionUtil.getMessageAndStackTrace(e));
      throw new RuntimeException( "" + e ) ;
    }
    //return null ;
  } // end method fileToStrArr

  /**
   * ArrayList mit Strings in String-Array umwandeln . <br/>
   * Verwendet in {@link #fileToStrArr}.<br/>
   *
   * @param strArrayList ArrayList mit Strings
   * @return String[]
   * @exception ClassCastException wenn Element kein String
   */
  public static String[] arrayListToStrArr(
          final ArrayList<String> strArrayList )
  {
    if (strArrayList == null || strArrayList.isEmpty ())
    {
      return new String[0];
    }

    String[]  retArr = new String[strArrayList.size ()];

    for (int i = 0; i < strArrayList.size (); i++)
    {
      retArr[i] = (String) strArrayList.get (i);
    }

    return retArr;
  } // end method arrayListToStrArr

}//end class CheckPackage