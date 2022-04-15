package ALL;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;
import com.spire.xls.*;

public class XlsToCsv {

    private String pathFileXLSX;
    private String pathFileCSV;
    private char delimiter;

    public XlsToCsv(String pathFileXLSX, String pathFileCSV, char delimiter) {
        this.pathFileXLSX = pathFileXLSX;
        this.pathFileCSV = pathFileCSV;
        this.delimiter = delimiter;
    }

    public XlsToCsv(String pathFileXLSX, String pathFileCSV) {
        this(pathFileXLSX,pathFileCSV,'+'); // delimiter par défaut
    }

    /**
     * Ecrit le CSV depuis le XLSX
     */
    public void readXlsx() {
        Workbook workbook = new Workbook();
        workbook.loadFromFile(pathFileXLSX); // on charge le XLSX
        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.saveToFile(pathFileCSV, String.valueOf(delimiter), Charset.forName("UTF-8")); // on sauvegarde le XLSX en CSV
    }

    /**
     * Récupère le nom des colonnes
     * @param reader
     * @return le nom des colonnes
     * @throws Exception
     */
    public ArrayList<String> getCSVIndex(CSVReader reader) throws Exception{
        String [] firstLine = reader.readNext();
        ArrayList<String> firstTab = new ArrayList<>();
        for(String token : firstLine) {
            firstTab.add(token);
        }
        return firstTab;
    }

    public HashMap<String,Integer> getIndexOf(ArrayList<String> index, String[] line) throws Exception{
        HashMap<String,Integer> indexOf = new HashMap<>();

        /*int i = 0;
        for(String token : line) {
            if (index.contains(token)) {
                indexOf.put(token, i);
            }
            i++;
        }*/

        for (int i = 0; i < line.length; i++) {
            if (index.contains(line[i])) {
                indexOf.put(line[i], i);
            }
        }

        return indexOf;
    }

    public HashMap<String,Integer> getAllIndex(String[] line) throws Exception{
        HashMap<String,Integer> indexOf = new HashMap<>();
        for (int i = 0; i < line.length; i++) {
            indexOf.put(line[i], i);
        }
        return indexOf;
    }

    // GETTERS

    /**
     * Retourne le chemin vers le fichier XLSX
     * @return
     */
    public String getPathFileXLSX() {
        return pathFileXLSX;
    }

    /**
     * Retourne le chemin vers le fichier CSV
     * @return
     */
    public String getPathFileCSV() {
        return pathFileCSV;
    }

    /**
     * Retourne le chemin vers le fichier CSV
     * @return
     */
    public char getDelimiter() {
        return delimiter;
    }
}
