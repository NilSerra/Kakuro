set CLASSPATH=%CLASSPATH%;junit-4.12.jar;hamcrest-core-1.3.jar;
javac -cp junit-4.12.jar;hamcrest-core-1.3.jar;. Drivers/KakuroTest.java Taulell/*.java GestorFitxers/*.java Celes/*.java
java org.junit.runner.JUnitCore Drivers.KakuroTest
del /s *.class
