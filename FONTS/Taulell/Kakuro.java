package Taulell;

import java.util.*;
import java.util.List;

import Celes.Cela;
import Celes.CelaBlanca;
import Celes.CelaNegra;

public class Kakuro {
    private int n,m;

    private Cela[][] celes;
    private int[][] kakuroResoltValors;
    private TiraCeles[][] tiraCelesH, tiraCelesV;
    private int contSolucions;

    private String kakuroResolt;

    static boolean[][][] calculCela;

    public Kakuro() {
        //1sol: matriu bool (true si negra), blanques i negres
        //2sol: matriu blanques, tiracel amb 1 negra i punters a blanques
        n = -1;
        m = -1;
        contSolucions = 0;
        kakuroResolt = "";
        calculCela = new boolean[9][45][9];
        ferCalcul();
    }

    public boolean llegirKakuro(){
        Scanner input = new Scanner(System.in);
        String[] mida = {};
        if(input.hasNext()) mida = input.nextLine().split(",");
        if(mida.length == 2) {
            n = Integer.parseInt(mida[0]);
            m = Integer.parseInt(mida[1]);

            celes = new Cela[n][m];
            tiraCelesH = new TiraCeles[n][m];
            tiraCelesV = new TiraCeles[n][m];

            for(int i = 0; i < n; ++i){
                String[] linia = {};
                if(input.hasNext()) linia = input.nextLine().split(",");
                for(int j = 0; j < linia.length; ++j){
                    String s = linia[j];
                    if(s.equals("?")){
                        celes[i][j] = new CelaBlanca();
                        celes[i][j].setValue(0);
                    }
                    else if(s.charAt(0) == 'C'){
                        String[] valors = s.split("F");
                        if(valors.length==1){
                            int valorV = Integer.parseInt(valors[0].replace("C", ""));

                            celes[i][j] = new CelaNegra();
                            celes[i][j].setMaxSumV(valorV);

                        }
                        else if(valors.length==2){
                            int valorV = Integer.parseInt(valors[0].replace("C", ""));
                            int valorH = Integer.parseInt(valors[1]);
                            celes[i][j] = new CelaNegra();
                            celes[i][j].setMaxSumH(valorH);
                            celes[i][j].setMaxSumV(valorV);
                        }
                        else {
                            System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                            return false;
                        }
                    }
                    else if(s.charAt(0) == 'F'){
                        int valorH = Integer.parseInt(s.replace("F", ""));
                        celes[i][j] = new CelaNegra();
                        celes[i][j].setMaxSumH(valorH);
                    }
                    else if (s.equals("*")){
                        celes[i][j] = new CelaNegra();
                    }
                    else if (s.charAt(0) == '-'){
                        System.out.println("Error, no s'accepten numeros negatius");
                        return false;
                    }
                    else if (s.equals("1") ||s.equals("2") ||s.equals("3") ||s.equals("4")||s.equals("5")||s.equals("6")||s.equals("7")||s.equals("8")||s.equals("9")){
                        celes[i][j] = new CelaBlanca();
                        celes[i][j].setValue(Integer.parseInt(s));
                    }
                    else{
                        System.out.println("Error, format incorrecte");
                        return false;
                    }
                }
            }
        }
        else{
            System.out.println("Error, se esperaba: n,m");
        }

        TiraCeles aux = null;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!celes[i][j].type()){
                    tiraCelesH[i][j] = new TiraCeles( true, celes[i][j]);
                    aux = tiraCelesH[i][j];
                }
                else{
                    tiraCelesH[i][j] = aux;
                }
            }
        }
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                if(!celes[i][j].type()){
                    tiraCelesV[i][j] = new TiraCeles(false, celes[i][j]);
                    aux = tiraCelesV[i][j];
                }
                else{
                    tiraCelesV[i][j] = aux;
                }
            }
        }
        for(int i=n-1;i>=0;i--){
            int cont = 0;
            for(int j=m-1; j>=0;j--){
                if(celes[i][j].type()) {
                    ++cont;
                    if(celes[i][j].getValue() != 0){
                        tiraCelesH[i][j].addCela(celes[i][j].getValue()-1);
                    }
                }
                else{
                    if(cont!=0) tiraCelesH[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        for(int j=m-1;j>=0;j--){
            int cont = 0;
            for(int i=n-1; i>=0;i--){
                if(celes[i][j].type()){
                    ++cont;
                    if(celes[i][j].getValue() != 0){
                        tiraCelesV[i][j].addCela( celes[i][j].getValue()-1);
                    }
                }
                else{
                    if(cont!=0) tiraCelesV[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        return true;
    }

    public boolean llegirKakuroText(String kakuroString){
        try{
            Scanner myReader = new Scanner(kakuroString);
            int i = -1;
            while (myReader.hasNextLine() && (i == -1 || i < n)) {
                String data = myReader.nextLine();
                if(i == -1){
                    String[] mida = data.split(",");
                    if(mida.length != 2) {
                        System.out.println("Error, se esperaba: n,m");
                        return false;
                    }
                    n = Integer.parseInt(mida[0]);
                    m = Integer.parseInt(mida[1]);

                    celes = new Cela[n][m];
                    tiraCelesH = new TiraCeles[n][m];
                    tiraCelesV = new TiraCeles[n][m];
                }
                else{
                    String[] linia = data.split(",");
                    for(int j = 0; j < linia.length; ++j){
                        String s = linia[j];
                        if(s.equals("?")){
                            celes[i][j] = new CelaBlanca();
                            celes[i][j].setValue(0);
                        }
                        else if(s.charAt(0) == 'C'){
                            String[] valors = s.split("F");
                            if(valors.length==1){
                                int valorV = -1;
                                try {
                                    valorV = Integer.parseInt(valors[0].replace("C", ""));
                                }
                                catch(NumberFormatException e){
                                    System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                                    return false;
                                }
                                celes[i][j] = new CelaNegra();
                                celes[i][j].setMaxSumV(valorV);

                            }
                            else if(valors.length==2){
                                int valorV = -1;
                                int valorH = -1;
                                try {
                                    valorV = Integer.parseInt(valors[0].replace("C", ""));
                                }
                                catch(NumberFormatException e){
                                    System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                                    return false;
                                }
                                try {
                                    valorH = Integer.parseInt(valors[1]);
                                }
                                catch(NumberFormatException e){
                                    System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                                    return false;
                                }
                                celes[i][j] = new CelaNegra();
                                celes[i][j].setMaxSumH(valorH);
                                celes[i][j].setMaxSumV(valorV);
                            }
                            else {
                                System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                                return false;
                            }
                        }
                        else if(s.charAt(0) == 'F'){
                            int valorH = -1;
                            try {
                                valorH = Integer.parseInt(s.replace("F", ""));
                            }
                            catch(NumberFormatException e){
                                System.out.println("Error, se esperaba el formato: CxxFxx o Cxx");
                                return false;
                            }
                            celes[i][j] = new CelaNegra();
                            celes[i][j].setMaxSumH(valorH);
                        }
                        else if (s.equals("*")){
                            celes[i][j] = new CelaNegra();
                        }
                        else if (s.equals("1") ||s.equals("2") ||s.equals("3") ||s.equals("4")||s.equals("5")||s.equals("6")||s.equals("7")||s.equals("8")||s.equals("9")){
                            celes[i][j] = new CelaBlanca();
                            celes[i][j].setValue(Integer.parseInt(s));
                        }
                        else{
                            System.out.println("Error, format incorrecte");
                            return false;
                        }
                    }
                }
                i++;
            }
            myReader.close();
        }
        catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }

        TiraCeles aux = null;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!celes[i][j].type()){
                    tiraCelesH[i][j] = new TiraCeles( true, celes[i][j]);
                    aux = tiraCelesH[i][j];
                }
                else{
                    tiraCelesH[i][j] = aux;
                }
            }
        }
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                if(!celes[i][j].type()){
                    tiraCelesV[i][j] = new TiraCeles(false, celes[i][j]);
                    aux = tiraCelesV[i][j];
                }
                else{
                    tiraCelesV[i][j] = aux;
                }
            }
        }
        for(int i=n-1;i>=0;i--){
            int cont = 0;
            for(int j=m-1; j>=0;j--){
                if(celes[i][j].type()) {
                    ++cont;
                    if(celes[i][j].getValue() != 0){
                        tiraCelesH[i][j].addCela(celes[i][j].getValue()-1);
                    }
                }
                else{
                    if(cont!=0) tiraCelesH[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        for(int j=m-1;j>=0;j--){
            int cont = 0;
            for(int i=n-1; i>=0;i--){
                if(celes[i][j].type()){
                    ++cont;
                    if(celes[i][j].getValue() != 0){
                        tiraCelesV[i][j].addCela( celes[i][j].getValue()-1);
                    }
                }
                else{
                    if(cont!=0) tiraCelesV[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        return true;
    }

    public int resoldreKakuro(){
        int numCelesBlanques = 0;
        for(int i = 0; i<n; ++i){
            for(int j= 0; j<m; ++j){
                if (celes[i][j].type() && celes[i][j].getValue() == 0) ++numCelesBlanques;
            }
        }
        contSolucions = 0;
        resoldreKakuroRec(numCelesBlanques);
        return contSolucions;
    }
    private void resoldreKakuroRec(int numBlanquesRestants) {
        //Busquem la cela amb menys possibles valors
        if (contSolucions == 2) return;
        if (numBlanquesRestants == 0){
            if(contSolucions == 0){
                crearKakuroResolt();
            }
            if(contSolucions <=1) contSolucions++;
            return;
        }
        int posX = 0;
        int posY = 0;
        int numMinPossibles = 10;
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                if (!celes[i][j].type()) continue;
                if (celes[i][j].getValue() != 0) continue;
                int numPossibles = 0;
                for (int k = 0; k < 9; ++k) {
                    if (tiraCelesV[i][j].celaUnica(k) && tiraCelesH[i][j].celaUnica(k) &&
                            calculCela[tiraCelesH[i][j].getLongitud() - tiraCelesH[i][j].getMida() - 1][tiraCelesH[i][j].getMaxSum() - tiraCelesH[i][j].getSuma() - 1][k] &&
                            calculCela[tiraCelesV[i][j].getLongitud() - tiraCelesV[i][j].getMida() - 1][tiraCelesV[i][j].getMaxSum() - tiraCelesV[i][j].getSuma() - 1][k]) {
                        ++numPossibles;
                    }
                }
                if (numPossibles < numMinPossibles) {
                    posY = i;
                    posX = j;
                    numMinPossibles = numPossibles;
                }
            }
        }
        if (numMinPossibles == 0) return;

        Set<Integer> numsPossibles = new HashSet();
        for (int k = 0; k < 9; ++k) {
            if (tiraCelesV[posY][posX].celaUnica(k) && tiraCelesH[posY][posX].celaUnica(k) &&
                    calculCela[tiraCelesH[posY][posX].getLongitud() - tiraCelesH[posY][posX].getMida() - 1][tiraCelesH[posY][posX].getMaxSum() - tiraCelesH[posY][posX].getSuma() - 1][k] &&
                    calculCela[tiraCelesV[posY][posX].getLongitud() - tiraCelesV[posY][posX].getMida() - 1][tiraCelesV[posY][posX].getMaxSum() - tiraCelesV[posY][posX].getSuma() - 1][k]) {
                numsPossibles.add(k);
            }
        }
        for(Integer value : numsPossibles){
            tiraCelesV[posY][posX].addCela(value);
            tiraCelesH[posY][posX].addCela(value);
            celes[posY][posX].setValue(value + 1);
            resoldreKakuroRec(numBlanquesRestants-1);
            celes[posY][posX].setValue(0);
            tiraCelesV[posY][posX].removeCela(value);
            tiraCelesH[posY][posX].removeCela(value);

        }

    }

    private void crearKakuroResolt() {
        kakuroResolt = getKakuroActual();
        kakuroResoltValors = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (celes[i][j].type()) kakuroResoltValors[i][j] = celes[i][j].getValue();
                else kakuroResoltValors[i][j] = -1;
            }
        }
    }

    private void ferCalcul(){
        for(int m = 0; m<9; ++m){
            for(int j = 0; j<45; ++j){
                boolean[] parcial = new boolean[9];
                for(int i = 0; i<9; ++i) parcial[i] = false;
                boolean[] total = new boolean[9];
                for(int i = 0; i<9; ++i) total[i] = false;
                for (int t = 0; t < 9; ++t) ferCalculAux(total, parcial, j+1, 45, m+1, t);
                for(int i = 0; i<9; ++i) calculCela[m][j][i] = total[i];
            }
        }
    }

    private void ferCalculAux(boolean[] total, boolean[] parcial, int sum, int total_sum, int m, int index){
        if (sum == 0 && m == 0){
            for(int i = 0; i<9; ++i) {
                if (parcial[i]) total[i] = parcial[i];
            }
            return;
        }
        else if (sum < (index+1)) return;
        else if (sum > total_sum) return;
        else if (sum > 0 && m > 0 && index < 9){
            parcial[index] = true;
            ferCalculAux( total, parcial, sum - (index + 1), total_sum-(index+1), m - 1, index + 1);
            parcial[index] = false;
            ferCalculAux( total, parcial, sum, total_sum-(index+1), m, index+1);
        }
    }

    public void generarKakuro(int n, int m, int nMinNegres,int nMaxNegres, int nSolucionades){

        this.n = n;
        this.m = m;
        celes = new Cela[n][m];
        tiraCelesH = new TiraCeles[n][m];
        tiraCelesV = new TiraCeles[n][m];

        //if((nNegres - n - m + 1)%2 != 0 && ((n%2==1) || (m%2==1) || (nNegres > n*m-2))) nNegres -= 1;
        int negresRestants;
        int nNegres = nMinNegres;
        int numMinBlanques = 1;
        boolean numerosPosats = false;
        boolean generat = false;
        boolean crearTaulell = true;
        int count = 0;
        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            celes[i][0] = new CelaNegra();
        }
        for (int j = 1; j < m; ++j) {
            celes[0][j] = new CelaNegra();
        }
        while(!generat) {
            if (count == 0) crearTaulell = true;
            if (count < 500) ++count;
            else count = 0;

            if (crearTaulell) {
                nNegres = (int)(Math.random()*(nMaxNegres-nMinNegres))+nMinNegres;
                if((nNegres - n - m + 1)%2 != 0 && ((n%2==1) || (m%2==1) || (nNegres > n*m-2))) nNegres += 1;
                if((nNegres - n - m + 1)%2 == 1 && n%2== 0 && m%2==0) nNegres += 1;
                boolean first = false;
                boolean second = false;
                boolean third = false;
                while (!first || !second || !third) {
                    negresRestants = nNegres - n - m + 1;
                    for (int i = 1; i < n; ++i) {
                        for (int j = 1; j < m; ++j) celes[i][j] = new CelaBlanca();
                    }
                    while (negresRestants > 0) {
                        int i = 1 + random.nextInt(n - 1);
                        int j = 1 + random.nextInt(m - 1);

                        if (!celes[i][j].type()) continue;
                        if (n % 2 == 0 && m % 2 == 0 && i == n / 2 && j == m / 2) continue;
                        celes[i][j] = new CelaNegra();
                        celes[n - i][m - j] = new CelaNegra();
                        negresRestants -= 2;
                    }
                    first = true;
                    for (int i = 0; i < n && first; ++i) {
                        int contBlanques = 0;
                        for (int j = 0; j < m && first; ++j) {
                            if (celes[i][j].type()) {
                                ++contBlanques;
                                if (contBlanques > 9) {
                                    first = false;
                                }
                            } else {
                                if (contBlanques > 0 && contBlanques <= numMinBlanques) {
                                    first = false;
                                }
                                contBlanques = 0;
                            }
                        }
                    }
                    if (!first) continue;
                    second = true;
                    for (int j = 0; j < m && second; ++j) {
                        int contBlanques = 0;
                        for (int i = 0; i < n && second; ++i) {
                            if (celes[i][j].type()) {
                                ++contBlanques;
                                if (contBlanques > 9) {
                                    second = false;
                                }
                            } else {
                                if (contBlanques > 0 && contBlanques <= numMinBlanques) {
                                    second = false;
                                }
                                contBlanques = 0;
                            }
                        }
                    }
                    if (!second) continue;
                    boolean[][] visited = new boolean[n][m];
                    int bY = 0;
                    int bX = 0;
                    for(int i = n-1; i>= 0; --i){
                        for(int j = m-1; j >= 0; --j){
                            visited[i][j] = false;
                            if (celes[i][j].type()){
                                bY = i;
                                bX = j;
                            }
                        }
                    }
                    LinkedList<Pair<Integer, Integer>> queue = new LinkedList<Pair<Integer, Integer>>();
                    visited[bY][bX] = true;
                    int numsBlanques = 1;
                    queue.add(new Pair(bY, bX));
                    while(queue.size() != 0){
                        Pair p = queue.poll();
                        int y = (int)p.l;
                        int x = (int)p.r;
                        if (y> 0 && !visited[y-1][x] && celes[y-1][x].type()){
                            visited[y-1][x] = true;
                            queue.add(new Pair(y-1, x));
                            ++numsBlanques;
                        }
                        if (x> 0 && !visited[y][x-1]&& celes[y][x-1].type()){
                            visited[y][x-1] = true;
                            queue.add(new Pair(y, x-1));
                            ++numsBlanques;
                        }
                        if (y < n-1 && !visited[y+1][x]&& celes[y+1][x].type()){
                            visited[y+1][x] = true;
                            queue.add(new Pair(y+1, x));
                            ++numsBlanques;
                        }
                        if (x < m-1 && !visited[y][x+1]&& celes[y][x+1].type()){
                            visited[y][x+1] = true;
                            queue.add(new Pair(y, x+1));
                            ++numsBlanques;
                        }
                    }
                    third = (numsBlanques == (n*m-nNegres));
                }
                crearTaulell = false;
            }
            int[][] valorsAux = new int[n][m];
            do {
                boolean[][][] celesUniques = new boolean[n][m][9];
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        for (int q = 0; q < 9; ++q) celesUniques[i][j][q] = true;
                    }
                }
                numerosPosats = true;
                //Omplim el taulell amb numeros
                List<Pair<Integer, Integer> > list = new ArrayList<>();
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j){
                        if (celes[i][j].type()){
                            list.add(new Pair(i, j));
                        }
                        valorsAux[i][j] = 0;
                    }
                }
                Collections.shuffle(list);
                int celesBlanquesRestants = n*m-nNegres;
                while(celesBlanquesRestants > 0){
                    /*
                    int y = random.nextInt(n-1)+1;
                    int x = random.nextInt(m-1)+1;
                    while(!celes[y][x].type() || celes[y][x].getValue() != 0){
                        y = random.nextInt(n-1)+1;
                        x = random.nextInt(m-1)+1;
                    }*/
                    Pair p = list.get(0);
                    int y = (int)p.l;
                    int x = (int)p.r;
                    list.remove(0);
                    if (valorsAux[y][x] != 0) continue;
                    int primerNumH, primerNumV;
                    primerNumH = primerNumV = 0;
                    int[] numsMin = {0, 1, 2, 3, 4, 5, 6, 7, 8};
                    int[] numsMax = {8, 7, 6, 5, 4, 3, 2, 1, 0};
                    while(celes[y-primerNumV][x].type() && y-primerNumV >= 0) ++primerNumV;
                    while(celes[y][x-primerNumH].type() && x-primerNumH >= 0) ++primerNumH;
                    boolean minNum = random.nextInt(2)== 0;
                    boolean orientacio = random.nextInt(2) == 0;
                    if (orientacio) {
                        int yAux = y;
                        y = y - primerNumV + 1;
                        while (y < n && celes[y][x].type()) {
                            if (valorsAux[y][x] == 0) {
                                int[] nums;
                                if (minNum) nums = numsMin;
                                else nums = numsMax;
                                int q = 0;
                                while (!celesUniques[y][x][nums[q]]) {
                                    ++q;
                                    if (q > 8) {
                                        numerosPosats = false;
                                        break;
                                    }
                                }
                                if (!numerosPosats) break;
                                int num = nums[q];
                                celesBlanquesRestants--;
                                valorsAux[y][x] = num + 1;
                                celesUniques[y][x][num] = false;
                                int t = 1;
                                while (x + t < m && celes[y][x + t].type()) {
                                    celesUniques[y][x + t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y + t < n && celes[y + t][x].type()) {
                                    celesUniques[y + t][x][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (x - t >= 1 && celes[y][x - t].type()) {
                                    celesUniques[y][x - t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y - t >= 1 && celes[y - t][x].type()) {
                                    celesUniques[y - t][x][num] = false;
                                    ++t;
                                }
                            }
                            y++;
                        }
                        if (!numerosPosats) break;
                        y = yAux;
                        x = x - primerNumH + 1;
                        while (x < m && celes[y][x].type()) {
                            if (valorsAux[y][x] == 0) {
                                int[] nums;
                                if (!minNum) nums = numsMin;
                                else nums = numsMax;
                                int q = 0;
                                while (!celesUniques[y][x][nums[q]]) {
                                    ++q;
                                    if (q > 8) {
                                        numerosPosats = false;
                                        break;
                                    }
                                }
                                if (!numerosPosats) break;
                                int num = nums[q];
                                celesBlanquesRestants--;
                                valorsAux[y][x] = num + 1;
                                celesUniques[y][x][num] = false;
                                int t = 1;
                                while (x + t < m && celes[y][x + t].type()) {
                                    celesUniques[y][x + t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y + t < n && celes[y + t][x].type()) {
                                    celesUniques[y + t][x][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (x - t >= 1 && celes[y][x - t].type()) {
                                    celesUniques[y][x - t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y - t >= 1 && celes[y - t][x].type()) {
                                    celesUniques[y - t][x][num] = false;
                                    ++t;
                                }
                            }
                            x++;
                        }
                    }
                    else{
                        int xAux = x;
                        x = x - primerNumH + 1;
                        while (x < m && celes[y][x].type()) {
                            if (valorsAux[y][x] == 0) {
                                int[] nums;
                                if (!minNum) nums = numsMin;
                                else nums = numsMax;
                                int q = 0;
                                while (!celesUniques[y][x][nums[q]]) {
                                    ++q;
                                    if (q > 8) {
                                        numerosPosats = false;
                                        break;
                                    }
                                }
                                if (!numerosPosats) break;
                                int num = nums[q];
                                celesBlanquesRestants--;
                                valorsAux[y][x] = num + 1;
                                celesUniques[y][x][num] = false;
                                int t = 1;
                                while (x + t < m && celes[y][x + t].type()) {
                                    celesUniques[y][x + t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y + t < n && celes[y + t][x].type()) {
                                    celesUniques[y + t][x][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (x - t >= 1 && celes[y][x - t].type()) {
                                    celesUniques[y][x - t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y - t >= 1 && celes[y - t][x].type()) {
                                    celesUniques[y - t][x][num] = false;
                                    ++t;
                                }
                            }
                            x++;
                        }
                        if (!numerosPosats) break;
                        x = xAux;
                        y = y - primerNumV + 1;
                        while (y < n && celes[y][x].type()) {
                            if (valorsAux[y][x] == 0) {
                                int[] nums;
                                if (minNum) nums = numsMin;
                                else nums = numsMax;
                                int q = 0;
                                while (!celesUniques[y][x][nums[q]]) {
                                    ++q;
                                    if (q > 8) {
                                        numerosPosats = false;
                                        break;
                                    }
                                }
                                if (!numerosPosats) break;
                                int num = nums[q];
                                celesBlanquesRestants--;
                                valorsAux[y][x] = num + 1;
                                celesUniques[y][x][num] = false;
                                int t = 1;
                                while (x + t < m && celes[y][x + t].type()) {
                                    celesUniques[y][x + t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y + t < n && celes[y + t][x].type()) {
                                    celesUniques[y + t][x][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (x - t >= 1 && celes[y][x - t].type()) {
                                    celesUniques[y][x - t][num] = false;
                                    ++t;
                                }
                                t = 1;
                                while (y - t >= 1 && celes[y - t][x].type()) {
                                    celesUniques[y - t][x][num] = false;
                                    ++t;
                                }
                            }
                            y++;
                        }
                    }
                    if (!numerosPosats) break;
                }
                /*
                int orientacio = random.nextInt(2);
                if (orientacio == 0) {
                    boolean minNum = (random.nextInt(2) == 0);
                    while (y < n) {
                        if (celes[y][x].type()) {
                            int[] numsMin = {0, 1, 2, 3, 4, 5, 6, 7, 8};
                            int[] numsMax = {8, 7, 6, 5, 4, 3, 2, 1, 0};
                            int[] nums;
                            if (minNum) nums = numsMin;
                            else nums = numsMax;
                            int q = 0;
                            while (!celesUniques[y][x][nums[q]]) {
                                ++q;
                                if (q > 8) {
                                    numerosPosats = false;
                                    break;
                                }
                            }
                            if (!numerosPosats) break;
                            int num = nums[q];
                            valorsAux[y][x] = num + 1;
                            celesUniques[y][x][num] = false;
                            int t = 1;
                            while (x + t < m && celes[y][x + t].type()) {
                                celesUniques[y][x + t][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (y + t < n && celes[y + t][x].type()) {
                                celesUniques[y + t][x][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (x - t >= 1 && celes[y][x - t].type()) {
                                celesUniques[y][x - t][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (y - t >= 1 && celes[y - t][x].type()) {
                                celesUniques[y - t][x][num] = false;
                                ++t;
                            }

                        } else minNum = (random.nextInt(2) == 0);
                        if (x == m - 1) {
                            y += 1;
                            x = 0;
                        } else ++x;
                    }
                }
                else{
                    boolean minNum = (random.nextInt(2) == 0);
                    while (x < m) {
                        if (celes[y][x].type()) {
                            int[] numsMin = {0, 1, 2, 3, 4, 5, 6, 7, 8};
                            int[] numsMax = {8, 7, 6, 5, 4, 3, 2, 1, 0};
                            int[] nums;
                            if (minNum) nums = numsMin;
                            else nums = numsMax;
                            int q = 0;
                            while (!celesUniques[y][x][nums[q]]) {
                                ++q;
                                if (q > 8) {
                                    numerosPosats = false;
                                    break;
                                }
                            }
                            if (!numerosPosats) break;
                            int num = nums[q];
                            valorsAux[y][x] = num + 1;
                            celesUniques[y][x][num] = false;
                            int t = 1;
                            while (x + t < m && celes[y][x + t].type()) {
                                celesUniques[y][x + t][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (y + t < n && celes[y + t][x].type()) {
                                celesUniques[y + t][x][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (x - t >= 1 && celes[y][x - t].type()) {
                                celesUniques[y][x - t][num] = false;
                                ++t;
                            }
                            t = 1;
                            while (y - t >= 1 && celes[y - t][x].type()) {
                                celesUniques[y - t][x][num] = false;
                                ++t;
                            }

                        } else minNum = (random.nextInt(2) == 0);
                        if (y == n - 1) {
                            x += 1;
                            y = 0;
                        } else ++y;
                    }
                }
                */
            }
            while (!numerosPosats);
            //System.out.println("Numeros posats");
            TiraCeles aux = null;
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    if(!celes[i][j].type()){
                        tiraCelesH[i][j] = new TiraCeles( true, celes[i][j]);
                        aux = tiraCelesH[i][j];
                    }
                    else{
                        tiraCelesH[i][j] = aux;
                    }
                }
            }
            for(int j=0;j<m;j++){
                for(int i=0;i<n;i++){
                    if(!celes[i][j].type()){
                        tiraCelesV[i][j] = new TiraCeles(false, celes[i][j]);
                        aux = tiraCelesV[i][j];
                    }
                    else{
                        tiraCelesV[i][j] = aux;
                    }
                }
            }
            for(int i=n-1;i>=0;i--){
                int cont = 0;
                int suma = 0;
                for(int j=m-1; j>=0;j--){
                    if(celes[i][j].type()) {
                        ++cont;
                        suma += valorsAux[i][j];
                    }
                    else{
                        if(cont!=0) tiraCelesH[i][j].setLongitud(cont);
                        if (suma != 0) celes[i][j].setMaxSumH(suma);
                        cont = 0;
                        suma = 0;
                    }
                }
            }
            for(int j=m-1;j>=0;j--){
                int cont = 0;
                int suma = 0;
                for(int i=n-1; i>=0;i--){
                    if(celes[i][j].type()){
                        ++cont;
                        suma += valorsAux[i][j];
                    }
                    else{
                        if(cont!=0) tiraCelesV[i][j].setLongitud(cont);
                        if (suma != 0) celes[i][j].setMaxSumV(suma);
                        cont = 0;
                        suma = 0;
                    }
                }
            }
            int numCelesBlanques = n * m - nNegres;

            while (numCelesBlanques > 0) {
                //Busquem la cela amb menys possibles valors
                boolean numPosat = false;
                for (int i = 1; i < n; ++i) {
                    for (int j = 1; j < m; ++j) {
                        if (!celes[i][j].type()) continue;
                        if (celes[i][j].getValue() != 0) continue;
                        int numPossibles = 0;
                        int valueAux = -1;
                        for (int k = 0; k < 9; ++k) {
                            if (tiraCelesV[i][j].celaUnica(k) && tiraCelesH[i][j].celaUnica(k) &&
                                    calculCela[tiraCelesH[i][j].getLongitud() - tiraCelesH[i][j].getMida() - 1][tiraCelesH[i][j].getMaxSum() - tiraCelesH[i][j].getSuma() - 1][k] &&
                                    calculCela[tiraCelesV[i][j].getLongitud() - tiraCelesV[i][j].getMida() - 1][tiraCelesV[i][j].getMaxSum() - tiraCelesV[i][j].getSuma() - 1][k]) {
                                ++numPossibles;
                                valueAux = k;
                            }
                        }
                        if (numPossibles == 1){
                            tiraCelesV[i][j].addCela(valueAux);
                            tiraCelesH[i][j].addCela(valueAux);
                            celes[i][j].setValue(valueAux + 1);
                            --numCelesBlanques;
                            numPosat = true;
                        }
                    }
                }
                if (!numPosat) break;
            }
            if (numCelesBlanques > 0) {
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        if (celes[i][j].type()) celes[i][j].setValue(0);
                        else{
                            celes[i][j].setMaxSumH(-1);
                            celes[i][j].setMaxSumV(-1);
                        }
                    }
                }
            }
            else generat = true;

        }
        crearKakuroResolt();
        int celesBuidesRestants = n*m - nNegres - nSolucionades;

        int posY,posX;

        while(celesBuidesRestants != 0){
            posY = random.nextInt(n);
            posX = random.nextInt(m);
            if(celes[posY][posX].type() && celes[posY][posX].getValue() > 0){
                celes[posY][posX].setValue(0);
                celesBuidesRestants--;
            }
        }
        TiraCeles aux = null;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!celes[i][j].type()){
                    tiraCelesH[i][j] = new TiraCeles( true, celes[i][j]);
                    aux = tiraCelesH[i][j];
                }
                else{
                    tiraCelesH[i][j] = aux;
                }
            }
        }
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                if(!celes[i][j].type()){
                    tiraCelesV[i][j] = new TiraCeles(false, celes[i][j]);
                    aux = tiraCelesV[i][j];
                }
                else{
                    tiraCelesV[i][j] = aux;
                }
            }
        }
        for(int i=n-1;i>=0;i--){
            int cont = 0;
            for(int j=m-1; j>=0;j--){
                if(celes[i][j].type()) {
                    ++cont;
                }
                else{
                    if(cont!=0) tiraCelesH[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        for(int j=m-1;j>=0;j--){
            int cont = 0;
            for(int i=n-1; i>=0;i--){
                if(celes[i][j].type()){
                    ++cont;
                }
                else{
                    if(cont!=0) tiraCelesV[i][j].setLongitud(cont);
                    cont = 0;
                }
            }
        }
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<m; ++j){
                if (celes[i][j].type() && celes[i][j].getValue() != 0){
                    tiraCelesV[i][j].addCela(celes[i][j].getValue()-1);
                    tiraCelesH[i][j].addCela(celes[i][j].getValue()-1);
                }
            }
        }
    }

    public void set_num(int i, int j, int value){
        if (celes[i][j].getValue() != 0){
            tiraCelesH[i][j].removeCela(celes[i][j].getValue()-1);
            tiraCelesV[i][j].removeCela(celes[i][j].getValue()-1);
        }
        if (value != 0) {
            tiraCelesH[i][j].addCela(value -1);
            tiraCelesV[i][j].addCela(value -1);
        }
        celes[i][j].setValue(value);
    }

    public String getKakuroActual(){
        String kakuro = "";
        kakuro = kakuro.concat(n+","+m+"\n");
        for(int i = 0; i<n; ++i){
            String linia = "";
            for(int j = 0; j < m; ++j){
                if(!celes[i][j].type()){
                    if (celes[i][j].getMaxSumH() != -1 && celes[i][j].getMaxSumV() != -1) linia += "C" + celes[i][j].getMaxSumV() + "F" +celes[i][j].getMaxSumH() + ",";
                    else if (celes[i][j].getMaxSumH() != -1) linia += "F" +celes[i][j].getMaxSumH() + ",";
                    else if (celes[i][j].getMaxSumV() != -1) linia += "C" + celes[i][j].getMaxSumV() + ",";
                    else linia += "*,";
                }
                else{
                    if (celes[i][j].getValue() == 0) linia += "?,";
                    else linia += celes[i][j].getValue() + ",";
                }
            }
            kakuro = kakuro.concat(linia.substring(0,linia.length()-1));
            if(i<n-1) kakuro = kakuro.concat("\n");
        }
        return kakuro;
    }

    public String getKakuroResolt(){
        return kakuroResolt;
    }

    public void setKakuroResolt(String kakuroResolt) {
        this.kakuroResolt = kakuroResolt;
        Cela[][] aux = celes.clone();
        TiraCeles[][] aux2 = tiraCelesH.clone();
        TiraCeles[][] aux3 = tiraCelesV.clone();
        llegirKakuroText(kakuroResolt);
        kakuroResoltValors = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (celes[i][j].type()) kakuroResoltValors[i][j] = celes[i][j].getValue();
                else kakuroResoltValors[i][j] = -1;
            }
        }
        celes = aux;
        tiraCelesH = aux2;
        tiraCelesV = aux3;
    }

    public void getPistaKakuro() {
        boolean pista = true;
        List<Pair<Integer,Integer>> pairList = new ArrayList<>();
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<m; ++j){
                if (celes[i][j].type() && celes[i][j].getValue() == 0){
                    pairList.add(new Pair(i,j));
                }
            }
        }
        Collections.shuffle(pairList);
        if (pairList.size() > 0) {
            Pair first = pairList.get(0);
            celes[(Integer)first.getL()][(Integer)first.getR()].setValue(kakuroResoltValors[(Integer)first.getL()][(Integer)first.getR()]);
            tiraCelesH[(Integer)first.getL()][(Integer)first.getR()].addCela(kakuroResoltValors[(Integer)first.getL()][(Integer)first.getR()]-1);
            tiraCelesV[(Integer)first.getL()][(Integer)first.getR()].addCela(kakuroResoltValors[(Integer)first.getL()][(Integer)first.getR()]-1);
        }

    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getNumBlanques() {
        int numBlanques = 0;
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<m; ++j){
                if (celes[i][j].type()) ++numBlanques;
            }
        }
        return numBlanques;
    }

    public boolean[] getValorsPossibles(Integer posY, Integer posX) {
        boolean[] valors = new boolean[9];
        for(int k = 0; k<9; ++k) valors[k] = false;
        for(int k = 0; k<9; ++k){
            if(tiraCelesV[posY][posX].celaUnica(k) && tiraCelesH[posY][posX].celaUnica(k) &&
                    tiraCelesV[posY][posX].getSuma()+(k+1) <= tiraCelesV[posY][posX].celaNegra.getMaxSumV() &&tiraCelesH[posY][posX].getSuma()+(k+1) <= tiraCelesH[posY][posX].celaNegra.getMaxSumH()){
                valors[k] = true;
            }
        }
        return valors;
    }

    public void editarCela(int celaY, int celaX, boolean esBlanc, String val1, String val2) {

        if (esBlanc){
            if (!celes[celaY][celaX].type()) celes[celaY][celaX] = new CelaBlanca();
            celes[celaY][celaX].setValue((val1.equals("")||val1.equals("?") ?0:Integer.parseInt(val1)));
        }
        else{
            celes[celaY][celaX] = new CelaNegra();
            celes[celaY][celaX].setMaxSumV((val1.equals("")||val1.equals("0") ?-1:Integer.parseInt(val1)));
            celes[celaY][celaX].setMaxSumH((val2.equals("")||val2.equals("0") ?-1:Integer.parseInt(val2)));
        }
    }
    public boolean verificarFormat(){
        boolean negraValor = false;
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<m; ++j){
                if (celes[i][j].type()){
                    if (!negraValor) return false;
                }
                else{
                    negraValor = (celes[i][j].getMaxSumH() != -1);
                }
            }
            negraValor = false;
        }
        negraValor = false;
        for(int j = 0; j<m; ++j){
            for(int i = 0; i<n; ++i){
                if (celes[i][j].type()){
                    if (!negraValor) return false;
                }
                else{
                    negraValor = (celes[i][j].getMaxSumV() != -1);
                }
            }
            negraValor = false;
        }
        llegirKakuroText(getKakuroActual());
        return true;
    }

    public boolean[] getValorsPossiblesPista(Integer posY, Integer posX) {

        boolean[] valors = new boolean[9];
        for(int k = 0; k<9; ++k) valors[k] = false;
        if (tiraCelesH[posY][posX].celesRestants() == 0 || tiraCelesV[posY][posX].celesRestants() == 0 || celes[posY][posX].getValue() != 0) return valors;
        for(int k = 0; k<9; ++k){
            if(tiraCelesV[posY][posX].celaUnica(k) && tiraCelesH[posY][posX].celaUnica(k) &&
                    calculCela[tiraCelesH[posY][posX].celesRestants()-1][tiraCelesH[posY][posX].sumaRestant()-1][k]
                    && calculCela[tiraCelesV[posY][posX].celesRestants()-1][tiraCelesV[posY][posX].sumaRestant()-1][k]){
                valors[k] = true;
            }

        }
        return valors;
    }
    private class Pair<L,R> {
        private L l;
        private R r;
        public Pair(L l, R r){
            this.l = l;
            this.r = r;
        }
        public L getL(){ return l; }
        public R getR(){ return r; }
        public void setL(L l){ this.l = l; }
        public void setR(R r){ this.r = r; }
    }
}
