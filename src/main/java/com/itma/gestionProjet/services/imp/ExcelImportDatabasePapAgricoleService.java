package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.repositories.DatabasePapAgricoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
@Service
public class ExcelImportDatabasePapAgricoleService {
    @Autowired
    private DatabasePapAgricoleRepository repository;

    // Noms attendus des colonnes
    private static final List<String> REQUIRED_COLUMNS = Arrays.asList("code_pap", "code_parcelle", "prenom", "nom");
    private static final List<String> ALL_COLUMNS = Arrays.asList(
            "code_pap", "code_parcelle", "prenom", "nom", "caracteristique_parcelle",
            "evaluation_perte", "commune", "departement", "nombre_parcelle", "surnom",
            "sexe", "existe_pni", "type_pni", "numero_pni", "numero_telephone",
            "photo_pap", "point_geometriques", "superficie", "nationalite",
            "ethnie", "langue_parlee", "situation_matrimoniale", "niveau_etude",
            "religion", "membre_foyer", "membre_foyer_handicape", "informations_etendues"
    );

    public ByteArrayInputStream importExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        // Lire les en-têtes dynamiquement
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnIndexes = getColumnIndexes(headerRow);

        // Vérifier que toutes les colonnes obligatoires sont présentes
        List<String> missingColumns = validateRequiredColumns(columnIndexes);
        if (!missingColumns.isEmpty()) {
            throw new IllegalArgumentException("Les colonnes suivantes sont manquantes : " + String.join(", ", missingColumns));
        }

        List<DatabasePapAgricole> validEntries = new ArrayList<>();
        List<Row> invalidRows = new ArrayList<>();

        // Traiter chaque ligne
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isRowValid(row, columnIndexes)) {
                validEntries.add(convertRowToEntity(row, columnIndexes));
            } else {
                invalidRows.add(row);
            }
        }

        // Sauvegarder les données valides
        repository.saveAll(validEntries);

        // Créer un fichier Excel des erreurs
        return generateErrorFile(headerRow, invalidRows);
    }

    private Map<String, Integer> getColumnIndexes(Row headerRow) {
        Map<String, Integer> columnIndexes = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String columnName = headerRow.getCell(i).getStringCellValue().toLowerCase().trim();
            columnIndexes.put(columnName, i);
        }
        return columnIndexes;
    }

    private List<String> validateRequiredColumns(Map<String, Integer> columnIndexes) {
        List<String> missingColumns = new ArrayList<>();
        for (String requiredColumn : REQUIRED_COLUMNS) {
            if (!columnIndexes.containsKey(requiredColumn)) {
                missingColumns.add(requiredColumn);
            }
        }
        return missingColumns;
    }

    private boolean isRowValid(Row row, Map<String, Integer> columnIndexes) {
        for (String requiredColumn : REQUIRED_COLUMNS) {
            Integer colIndex = columnIndexes.get(requiredColumn);
            if (colIndex == null || row.getCell(colIndex) == null || row.getCell(colIndex).getStringCellValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private DatabasePapAgricole convertRowToEntity(Row row, Map<String, Integer> columnIndexes) {
        DatabasePapAgricole entity = new DatabasePapAgricole();
        entity.setCodePap(getCellValue(row, columnIndexes.get("code_pap")));
        entity.setCodeParcelle(getCellValue(row, columnIndexes.get("code_parcelle")));
        entity.setPrenom(getCellValue(row, columnIndexes.get("prenom")));
        entity.setNom(getCellValue(row, columnIndexes.get("nom")));
        entity.setCaracteristiqueParcelle(getCellValue(row, columnIndexes.get("caracteristique_parcelle")));
        entity.setEvaluationPerte(getCellValue(row, columnIndexes.get("evaluation_perte")));
        entity.setCommune(getCellValue(row, columnIndexes.get("commune")));
        entity.setDepartement(getCellValue(row, columnIndexes.get("departement")));
        entity.setNombreParcelle(getCellValue(row, columnIndexes.get("nombre_parcelle")));
        entity.setSurnom(getCellValue(row, columnIndexes.get("surnom")));
        entity.setSexe(getCellValue(row, columnIndexes.get("sexe")));
        entity.setExistePni(getCellValue(row, columnIndexes.get("existe_pni")));
        entity.setTypePni(getCellValue(row, columnIndexes.get("type_pni")));
        entity.setNumeroPni(getCellValue(row, columnIndexes.get("numero_pni")));
        entity.setNumeroTelephone(getCellValue(row, columnIndexes.get("numero_telephone")));
        entity.setPhotoPap(getCellValue(row, columnIndexes.get("photo_pap")));
        entity.setPointGeometriques(getCellValue(row, columnIndexes.get("point_geometriques")));
        entity.setSuperficie(getCellValue(row, columnIndexes.get("superficie")));
        entity.setNationalite(getCellValue(row, columnIndexes.get("nationalite")));
        entity.setEthnie(getCellValue(row, columnIndexes.get("ethnie")));
        entity.setLangueParlee(getCellValue(row, columnIndexes.get("langue_parlee")));
        entity.setSituationMatrimoniale(getCellValue(row, columnIndexes.get("situation_matrimoniale")));
        entity.setNiveauEtude(getCellValue(row, columnIndexes.get("niveau_etude")));
        entity.setReligion(getCellValue(row, columnIndexes.get("religion")));
        entity.setMembreFoyer(getCellValue(row, columnIndexes.get("membre_foyer")));
        entity.setMembreFoyerHandicape(getCellValue(row, columnIndexes.get("membre_foyer_handicape")));
        entity.setInformationsEtendues(getCellValue(row, columnIndexes.get("informations_etendues")));

        return entity;
    }


    private String getCellValue(Row row, Integer colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return ""; // Retourne une chaîne vide si la cellule est null
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue()).trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            default:
                return "";
        }
    }


    private ByteArrayInputStream generateErrorFile(Row headerRow, List<Row> invalidRows) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Erreurs");

        // Copier les en-têtes
        Row headerCopy = sheet.createRow(0);
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerCopy.createCell(i).setCellValue(headerRow.getCell(i).getStringCellValue());
        }

        // Copier les lignes invalides
        for (int i = 0; i < invalidRows.size(); i++) {
            Row sourceRow = invalidRows.get(i);
            Row targetRow = sheet.createRow(i + 1);
            for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
                targetRow.createCell(j).setCellValue(sourceRow.getCell(j).toString());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
