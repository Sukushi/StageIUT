package ALL;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class FormRead {

    private ArrayList<String> index;
    private XlsToCsv convert;
    private CSVReader reader;

    public FormRead(String pathXLSX, String pathCSV, ArrayList<String> index) {
        this.index = index;
        this.convert = new XlsToCsv(pathXLSX,pathCSV);
        this.reader = null;
    }

    public FormRead(String pathXLSX, String pathCSV) {
        this(pathXLSX,pathCSV,null);
    }

    public ArrayList<String> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<String> index) {
        this.index = index;
    }

    public abstract void addInDB();

    public ArrayList<HashMap<String, String>> getData() {
        convert.readXlsx();
        ArrayList<HashMap<String,String>> data = new ArrayList<>(); // données de retour

        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(convert.getDelimiter()).build(); // notre parser pour choisir notre delimiter
            reader = new CSVReaderBuilder(new FileReader(convert.getPathFileCSV())).withCSVParser(parser).build(); // notre reader du fichier CSV
            String[] firstLine = reader.readNext(); // la première ligne avec les index

            HashMap<String,Integer> indexOf; // index des colonnes
            if(index == null) {
                indexOf = convert.getAllIndex(firstLine);
                this.setIndex(new ArrayList<>(Arrays.asList(firstLine)));
            } else {
                indexOf = convert.getIndexOf(index,firstLine);
                if (indexOf.size() != index.size()) { // on s'assure d'avoir récupéré tous les index demandés
                    throw new Exception("Tout les index n'ont pas été trouvé\n" + indexOf.toString());
                }
            }
            HashMap<String,String> currentData; // on déclare une fois seulement

            String [] nextLine; // notre variable de chaque ligne en tableau
            while ((nextLine = reader.readNext()) != null) {
                // on parcours chaque ligne
                if (!nextLine[1].equals("")) { // on s'assure que la ligne n'est pas vide
                    currentData = new HashMap<>(); // on initialise à chaque tour de boucle

                    for (int i : indexOf.values()) { // on parcours seulement les index où on sait qu'une donnée nous intéresse
                        currentData.put(firstLine[i],nextLine[i]); // on ajoute dans la HashMap (index de la donnée : la donnée)
                    }

                    data.add(currentData); // on ajoute la donnée courante dans la liste de données
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
