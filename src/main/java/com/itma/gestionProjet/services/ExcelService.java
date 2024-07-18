package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    public void importPersonneAffecteData(MultipartFile file) throws IOException {
        List<PersonneAffecte> personneAffecteList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                throw new IOException("The file is empty or the sheet is missing.");
            }

            Row headerRow = rows.next();
            Map<String, Integer> columnMap = getColumnMap(headerRow);

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                PersonneAffecte personneAffecte = new PersonneAffecte();

                personneAffecte.setIdPap(getCellValueAsString(currentRow, columnMap.get("idPap")));
                personneAffecte.setNombreParcelle((int) getCellValueAsNumber(currentRow, columnMap.get("nombreParcelle")));
                personneAffecte.setIdParcelle(getCellValueAsString(currentRow, columnMap.get("idParcelle")));
                personneAffecte.setCategorie(getCellValueAsString(currentRow, columnMap.get("categorie")));
                personneAffecte.setPrenom(getCellValueAsString(currentRow, columnMap.get("prenom")));
                personneAffecte.setNom(getCellValueAsString(currentRow, columnMap.get("nom")));
                personneAffecte.setDateNaissance(currentRow.getCell(columnMap.get("dateNaissance")).getDateCellValue());
                personneAffecte.setLieuNaissance(getCellValueAsString(currentRow, columnMap.get("lieuNaissance")));
                personneAffecte.setSexe(getCellValueAsString(currentRow, columnMap.get("sexe")));
                personneAffecte.setAge((int) getCellValueAsNumber(currentRow, columnMap.get("age")));
                personneAffecte.setNationalite(getCellValueAsString(currentRow, columnMap.get("nationalite")));
                personneAffecte.setDepartement(getCellValueAsString(currentRow, columnMap.get("departement")));
                personneAffecte.setNumeroIdentification(getCellValueAsString(currentRow, columnMap.get("numeroIdentification")));
                personneAffecte.setNumeroTelephone(getCellValueAsString(currentRow, columnMap.get("numeroTelephone")));
                personneAffecte.setLocaliteResidence(getCellValueAsString(currentRow, columnMap.get("localiteResidence")));
                personneAffecte.setStatutPap(getCellValueAsString(currentRow, columnMap.get("statutPap")));
                personneAffecte.setStatutVulnerable(getCellValueAsString(currentRow, columnMap.get("statutVulnerable")));
                personneAffecte.setPrenomExploitant(getCellValueAsString(currentRow, columnMap.get("prenomExploitant")));
                personneAffecte.setNomExploitant(getCellValueAsString(currentRow, columnMap.get("nomExploitant")));
                personneAffecte.setSuperficieAffecte(getCellValueAsNumber(currentRow, columnMap.get("superficieAffecte")));
                personneAffecte.setTypeCulture(getCellValueAsString(currentRow, columnMap.get("typeCulture")));
                personneAffecte.setTypeEquipement(getCellValueAsString(currentRow, columnMap.get("typeEquipement")));
                personneAffecte.setSuperficieCultive(getCellValueAsNumber(currentRow, columnMap.get("superficieCultive")));
                personneAffecte.setDescriptionBienAffecte(getCellValueAsString(currentRow, columnMap.get("descriptionBienAffecte")));

                personneAffecteList.add(personneAffecte);
            }
        }

        personneAffecteRepository.saveAll(personneAffecteList);
    }

    private Map<String, Integer> getColumnMap(Row headerRow) {
        Map<String, Integer> columnMap = new HashMap<>();
        for (Cell cell : headerRow) {
            if (cell instanceof Cell) { // Vérifie si cell est une instance de Cell
                columnMap.put(cell.getStringCellValue(), cell.getColumnIndex());
            } else {
                throw new IllegalArgumentException("Une cellule n'est pas une instance de Cell");
            }
        }
        return columnMap;
    }


    private String getCellValueAsString(Row row, Integer colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return ""; // Retourne une chaîne vide si la cellule est nulle
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Nettoyer la chaîne
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                System.err.println("Type de cellule inattendu: " + cell.getCellType()); // Log l'erreur
                return ""; // Retourne une chaîne vide pour les types inconnus
        }
    }


    private double getCellValueAsNumber(Row row, Integer colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                    try {
                        return Double.parseDouble(cell.getStringCellValue());
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de conversion de la valeur de la cellule: " + cell.getStringCellValue());
                        return 0; // Retourne 0 par défaut si la conversion échoue
                    }
                default:
                    throw new IllegalStateException("Type de cellule inattendu: " + cell.getCellType());
            }
        }
        return 0;
    }
}