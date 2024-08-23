package com.itma.gestionProjet.seeders;

import com.github.javafaker.Faker;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class PersonneAffecteSeeder implements CommandLineRunner {

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override


    public void run(String... args) throws Exception {
        // Clear existing data
       // personneAffecteRepository.deleteAll();

        // Initialize Faker
        Faker faker = new Faker();
        Random random = new Random();
        Project defaultProject = projectRepository.findById(18L).orElseThrow(() -> new RuntimeException("Project not found"));

        // Set to store unique codePap values
        Set<String> uniqueCodePap = new HashSet<>();

        // Define possible codePap values
        String[] possibleCodePap = {"11111", "22222", "33333", "44444", "55555"};

        // Generate data
        for (int i = 0; i < 50; i++) {
            PersonneAffecte personneAffecte = new PersonneAffecte();

            // Ensure unique codePap
            String codePap;
            do {
                codePap = faker.options().option(possibleCodePap);
            } while (!uniqueCodePap.add(codePap));  // Keep generating until a unique codePap is found

            personneAffecte.setIdPap(String.valueOf(i + 1));
            personneAffecte.setNombreParcelle(random.nextInt(5) + 1);
            personneAffecte.setIdParcelle(String.valueOf(i + 101));
            personneAffecte.setCategorie(faker.options().option("A", "B", "C"));
            personneAffecte.setPrenom(faker.name().firstName());
            personneAffecte.setNom(faker.name().lastName());
            personneAffecte.setRegion("dakar");
            personneAffecte.setPrenomPere(faker.name().firstName());
            personneAffecte.setPrenom(faker.name().lastName());  // Correction du nom du père
            personneAffecte.setPays("Sénégal");
            personneAffecte.setSituationMatrimoniale(faker.options().option("Marié", "Célibataire", "Veuve", "Divorcé"));
            personneAffecte.setDateNaissance(faker.date().birthday());
            personneAffecte.setLieuNaissance(faker.address().city());
            personneAffecte.setSexe(faker.options().option("M", "F"));
            personneAffecte.setAge(random.nextInt(80) + 1);
            personneAffecte.setNationalite(faker.nation().nationality());
            personneAffecte.setDepartement(faker.address().state());
            personneAffecte.setTypeIdentification(faker.options().option("Carte d'identité", "Passport"));
            personneAffecte.setNumeroIdentification(faker.idNumber().valid());
            personneAffecte.setNumeroTelephone(faker.phoneNumber().phoneNumber());
            personneAffecte.setCodePap(codePap);  // Use the unique codePap
            personneAffecte.setLocaliteResidence(faker.address().fullAddress());
            personneAffecte.setStatutPap(faker.options().option("Actif", "Inactif"));
            personneAffecte.setStatutVulnerable(faker.options().option("Oui", "Non"));
            personneAffecte.setPrenomExploitant(faker.name().firstName());
            personneAffecte.setNomExploitant(faker.name().lastName());
            personneAffecte.setProject(defaultProject);
            personneAffecte.setSuperficieAffecte(random.nextDouble() * 10);
            personneAffecte.setTypeCulture(faker.options().option("Blé", "Maïs", "Riz", "Orge"));
            personneAffecte.setTypeEquipement(faker.options().option("Tracteur", "Moissonneuse", "Charrue"));
            personneAffecte.setSuperficieCultive(random.nextDouble() * 10);
            personneAffecte.setDescriptionBienAffecte(faker.lorem().sentence());

         //   personneAffecteRepository.save(personneAffecte);
        }
    }
}