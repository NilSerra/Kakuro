javac Drivers\driverCelaBlanca.java Celes\*.java
jar cmf MANIFEST\MANIFEST-DRIVERCELABLANCA.MF driverCelaBlanca.jar Drivers\driverCelaBlanca.class Celes\*.class

javac Drivers\driverCelaNegra.java Celes\*.java
jar cmf MANIFEST\MANIFEST-DRIVERCELANEGRA.MF driverCelaNegra.jar Drivers\driverCelaNegra.class Celes\*.class

javac Drivers\driverControladorDomini.java Controladors\*.java Celes\*.java Taulell\*.java
jar cmf MANIFEST\MANIFEST-DRIVERCONTROLADORDOMINI.MF driverControladorDomini.jar Drivers\driverControladorDomini.class Controladors\*.class Celes\*.class Taulell\*.class

javac Drivers\driverKakuro.java Celes\*.java Taulell\*.java
jar cmf MANIFEST\MANIFEST-DRIVERKAKURO.MF driverKakuro.jar Drivers\driverKakuro.class Celes\*.class Taulell\*.class

javac Drivers\driverTiraCeles.java Taulell\*.java Celes\*.java
jar cmf MANIFEST\MANIFEST-DRIVERTIRACELES.MF driverTiraCeles.jar Drivers\driverTiraCeles.class Taulell\*.class Celes\*.class

javac Main\Main.java Taulell\*.java Celes\*.java Controladors\*.java
jar cmf MANIFEST\MANIFEST-MAIN.MF main.jar Main\Main.class Taulell\*.class Celes\*.class Controladors\*.class
