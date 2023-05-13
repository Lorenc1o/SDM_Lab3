package com.bdma;

import java.io.*;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class ABOX {

    private static final String BASE_URI = "http://www.bdma.com/";
    //    private static final String BASE_URI = "http://www.semanticweb.org/SDM_lab3_jose_abd/exercise_B/";
    private static final String RESOURCES_TBOX_OWL = "src/main/resources/tbox3.owl";
    private static final String PAPERS_DATA_FILE_PATH = "src/main/resources/cleaned_papers2.csv";
    private static final String PERSONS_DATA_FILE_PATH = "src/main/resources/cleaned_persons2.csv";
    private static final String PUBLICATIONS_DATA_FILE_PATH = "src/main/resources/cleaned_publications2.csv";
    private static final String VENUES_DATA_FILE_PATH = "src/main/resources/cleaned_venues2.csv";
    private static final String ABOX_MODEL_PATH = "./src/main/resources/abox3.nt";

    public static void createAndSaveABOX() {

        try {

            //===============================================
            // Reading & building Ontology model from TBOX
            //===============================================

            Model m = ModelFactory.createDefaultModel().read(new File(RESOURCES_TBOX_OWL).toURI().toString());
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);

            //===============================================
            // Getting all OntClasses (concepts) from TBOX
            //===============================================

            OntClass area = model.getOntClass(BASE_URI.concat("Area"));
            OntClass venue = model.getOntClass(BASE_URI.concat("Venue"));
            OntClass conference = model.getOntClass(BASE_URI.concat("Conference"));
            OntClass journal = model.getOntClass(BASE_URI.concat("Journal"));
            OntClass workshop = model.getOntClass(BASE_URI.concat("Workshop"));
            OntClass symposium = model.getOntClass(BASE_URI.concat("Symposium"));
            OntClass expertGroup = model.getOntClass(BASE_URI.concat("ExpertGroup"));
            OntClass regularConference = model.getOntClass(BASE_URI.concat("RegularConference"));
            OntClass person = model.getOntClass(BASE_URI.concat("Person"));
            OntClass author = model.getOntClass(BASE_URI.concat("Author"));
            OntClass chair = model.getOntClass(BASE_URI.concat("Chair"));
            OntClass editor = model.getOntClass(BASE_URI.concat("Editor"));
            OntClass reviewer = model.getOntClass(BASE_URI.concat("Reviewer"));
            OntClass publications = model.getOntClass(BASE_URI.concat("Publication"));
            OntClass paper = model.getOntClass(BASE_URI.concat("Paper"));
            OntClass fullPaper = model.getOntClass(BASE_URI.concat("FullPaper"));
            OntClass shortPaper = model.getOntClass(BASE_URI.concat("ShortPaper"));
            OntClass demoPaper = model.getOntClass(BASE_URI.concat("DemoPaper"));
            OntClass poster = model.getOntClass(BASE_URI.concat("Poster"));
            OntClass review = model.getOntClass(BASE_URI.concat("Review"));
            OntClass manager = model.getOntClass(BASE_URI.concat("Manager"));
            OntClass volume = model.getOntClass(BASE_URI.concat("Volume"));
            OntClass proceeding = model.getOntClass(BASE_URI.concat("Proceeding"));

            //===============================================
            // Getting all Ontology Properties from TBOX
            //===============================================

            OntProperty relatedTo = model.getOntProperty(BASE_URI.concat("relatedTo"));
            OntProperty isManagerOf = model.getOntProperty(BASE_URI.concat("isManagerOf"));
//            OntProperty isChairOf = model.getOntProperty(BASE_URI.concat("isChairOf"));
//            OntProperty isEditorOf = model.getOntProperty(BASE_URI.concat("isEditorOf"));
            OntProperty includedIn = model.getOntProperty(BASE_URI.concat("includedIn"));
//            OntProperty includedInJournal = model.getOntProperty(BASE_URI.concat("includedInJournal"));
//            OntProperty includedInConference = model.getOntProperty(BASE_URI.concat("includedInConference"));
            OntProperty hasAuthor = model.getOntProperty(BASE_URI.concat("hasAuthor"));
            OntProperty publishedAs = model.getOntProperty(BASE_URI.concat("publishedAs"));
            OntProperty submittedTo = model.getOntProperty(BASE_URI.concat("submittedTo"));
//            OntProperty submittedToConference = model.getOntProperty(BASE_URI.concat("submittedToConference"));
//            OntProperty submittedToJournal = model.getOntProperty(BASE_URI.concat("submittedToJournal"));
            OntProperty assignedBy = model.getOntProperty(BASE_URI.concat("assignedBy"));
            OntProperty assignedPaper = model.getOntProperty(BASE_URI.concat("assignedPaper"));
            OntProperty writtenBy = model.getOntProperty(BASE_URI.concat("writtenBy"));

            //===============================================
            // Read & Parse the csv file
            //===============================================

            HashMap<String, Paper> paperHashMap = readPapers();
            HashMap<String, Person> personHashMap = readPersons();
            HashMap<String, Venue> venueHashMap = readVenues();
            HashMap<String, Publications> publicationHashMap = readPublications();

            paperHashMap.forEach((key, value) -> {
                        OntClass __paper;
                        switch (value.getPaperType()) {
                            case "FullPaper":
                                __paper = fullPaper;
                                break;
                            case "ShortPaper":
                                __paper = shortPaper;
                                break;
                            case "DemoPaper":
                                __paper = demoPaper;
                                break;
                            default:
                                __paper = poster;
                        }

                        Individual _paper = __paper.createIndividual(BASE_URI.concat(value.paperTitle.replaceAll(" ", "_")));

                        String[] authors = value.getAuthorID().split(",");
                        for (String authorId : authors) {
                            Person __person = personHashMap.get(authorId);
                            if (__person != null) {
                            Individual _author = author.createIndividual(BASE_URI.concat(__person.getName().replaceAll(" ", "_")));
                            _paper.addProperty(hasAuthor, _author);
                            }
                        }

                        Individual _area = area.createIndividual(BASE_URI.concat(value.getArea().replaceAll(" ", "_")));
                        _paper.addProperty(relatedTo, _area);

                        Individual _review1 = review.createIndividual(BASE_URI.concat(value.getDecisions_1().replaceAll(" ", "_")));
                        _review1.addProperty(assignedPaper, _paper);
                        Individual _review2 = review.createIndividual(BASE_URI.concat(value.getDecisions_2().replaceAll(" ", "_")));
                        _review2.addProperty(assignedPaper, _paper);

                        Individual _reviewer1 = reviewer.createIndividual(BASE_URI.concat(value.getReviewer_1().replaceAll(" ", "_")));
                        _review1.addProperty(writtenBy, _reviewer1);
                        Individual _reviewer2 = reviewer.createIndividual(BASE_URI.concat(value.getReviewer_2().replaceAll(" ", "_")));
                        _review2.addProperty(writtenBy, _reviewer2);


                        if (value.getPublicationId() != null) {
                            Publications __publication = publicationHashMap.get(value.getPublicationId().replaceAll(" ", "_"));
                            if (__publication != null) {
                                Venue __venue = venueHashMap.get(__publication.getVenueId().replaceAll(" ", "_"));

                                if (__publication.getType().equals("Volume")) {
                                    Individual _volume = volume.createIndividual(BASE_URI.concat(__publication.getTitle().replaceAll(" ", "_")));
                                    _paper.addProperty(publishedAs, _volume);
                                    _volume.addProperty(relatedTo, _area);

                                    Individual _journal = journal.createIndividual(BASE_URI.concat(__venue.getName().replaceAll(" ", "_")));
                                    String __editor = __venue.getEditor();
                                    Individual _editor = editor.createIndividual(BASE_URI.concat(__editor.replaceAll(" ", "_")));
                                    _editor.addProperty(isManagerOf, _journal);
                                    _review1.addProperty(assignedBy, _editor);
                                    _review2.addProperty(assignedBy, _editor);

                                    _journal.addProperty(includedIn, _volume);
                                    _paper.addProperty(submittedTo, _journal);
                                } else {
                                    Individual _proceeding = proceeding.createIndividual(BASE_URI.concat(__publication.getTitle().replaceAll(" ", "_")));
                                    _paper.addProperty(publishedAs, _proceeding);
                                    _proceeding.addProperty(relatedTo, _area);

                                    switch (__venue.getConferenceType()) {
                                        case "ExpertGroup":
                                            Individual _expertGroup = expertGroup.createIndividual(BASE_URI.concat(__venue.getName().replaceAll(" ", "_")));
                                            String __chair = __venue.getChair();
                                            Individual _chair = chair.createIndividual(BASE_URI.concat(__chair.replaceAll(" ", "_")));
                                            _chair.addProperty(isManagerOf, _expertGroup);
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);

                                            _expertGroup.addProperty(includedIn, _proceeding);
                                            _paper.addProperty(submittedTo, _expertGroup);
                                            break;
                                        case "Symposium":
                                            Individual _symposium = symposium.createIndividual(BASE_URI.concat(__venue.getName().replaceAll(" ", "_")));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(__chair.replaceAll(" ", "_")));
                                            _chair.addProperty(isManagerOf, _symposium);
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);

                                            _symposium.addProperty(includedIn, _proceeding);
                                            _paper.addProperty(submittedTo, _symposium);
                                            break;
                                        case "Workshop":
                                            Individual _workshop = workshop.createIndividual(BASE_URI.concat(__venue.getName().replaceAll(" ", "_")));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(__chair.replaceAll(" ", "_")));
                                            _chair.addProperty(isManagerOf, _workshop);
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);

                                            _workshop.addProperty(includedIn, _proceeding);
                                            _paper.addProperty(submittedTo, _workshop);
                                            break;
                                        default:
                                            Individual _regularConference = regularConference.createIndividual(BASE_URI.concat(__venue.getName().replaceAll(" ", "_")));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(__chair.replaceAll(" ", "_")));
                                            _chair.addProperty(isManagerOf, _regularConference);
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);

                                            _regularConference.addProperty(includedIn, _proceeding);
                                            _paper.addProperty(submittedTo, _regularConference);

                                            break;
                                    }
                                }
                            }
                        }
                    }
            );

//            venueHashMap.forEach((key, value) -> {
//                        Venue __venue = venueHashMap.get(value.getId());
//                        Individual _venue = venue.createIndividual(BASE_URI.concat(__venue.getName()));
//                        _venue.addProperty(relatedTo, __venue.getArea());
//
//                        if (__venue.getVenueType().equals("Journal")) {
//                            Individual _journal = journal.createIndividual(BASE_URI.concat(__venue.getName()));
//                            Individual _editor = editor.createIndividual(BASE_URI.concat(__venue.getEditor()));
//                            _editor.addProperty(isManagerOf, _journal);
//                        } else {
//                            Individual _conference = conference.createIndividual(BASE_URI.concat(__venue.getName()));
//                            Individual _chair = chair.createIndividual(BASE_URI.concat(__venue.getChair()));
//                            _chair.addProperty(isManagerOf, _conference);
//                        }
//
//                    }
//            );
            FileOutputStream writerStream = new FileOutputStream(ABOX_MODEL_PATH);
            model.write(writerStream, "N-TRIPLE");
            writerStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, Paper> readPapers() {
        HashMap<String, Paper> paperHashMap = new HashMap<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(PAPERS_DATA_FILE_PATH));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(csvReader);

            for (CSVRecord record : parser) {
                String id = record.get("id");
                String paperTitle = record.get("paperTitle");
                String area = record.get("area");
                String authorID = record.get("authorID");
                String corrAuthorID = record.get("corrAuthorID");
                String publicationId = record.get("publicationID");
                String venueId = record.get("venueId");
                String reviewer_1 = record.get("reviewer_1");
                String reviewer_2 = record.get("reviewer_2");
                String review_1 = record.get("review_1");
                String review_2 = record.get("review_2");
                String paperType = record.get("paperType");
                String anAbstract = record.get("paperAbstract");
                String decisions_1 = record.get("decisions_1");
                String decisions_2 = record.get("decisions_2");
                String year = record.get("year");
                String url = record.get("url");

                paperHashMap.put(id, new Paper(id, paperTitle, area, authorID, corrAuthorID, publicationId, reviewer_1, reviewer_2, review_1, review_2, paperType, anAbstract, decisions_1, decisions_2, year, url, venueId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paperHashMap;
    }

    private static HashMap<String, Person> readPersons() {
        HashMap<String, Person> personsHashMap = new HashMap<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(PERSONS_DATA_FILE_PATH));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(csvReader);

            for (CSVRecord record : parser) {
                String id = record.get("id");
                String name = record.get("name");
                String dob = record.get("dob");

                personsHashMap.put(id, new Person(id, name, dob));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personsHashMap;
    }

    private static HashMap<String, Venue> readVenues() {
        HashMap<String, Venue> venueHashMap = new HashMap<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(VENUES_DATA_FILE_PATH));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(csvReader);

            for (CSVRecord record : parser) {
                String id = record.get("id");
                String venueType = record.get("venueType");
                String conferenceType = record.get("conferenceType");
                String name = record.get("name");
                String editor = record.get("editor");
                String chair = record.get("chair");
                String url = record.get("url");
                String publication = record.get("publication");
                String area = record.get("area");
                String issn = record.get("issn");
                String periodicity = record.get("periodicity");

                venueHashMap.put(id, new Venue(id, venueType, conferenceType, name, editor, chair, url, publication, area, issn, periodicity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return venueHashMap;
    }

    private static HashMap<String, Publications> readPublications() {
        HashMap<String, Publications> publicationHashMap = new HashMap<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(PUBLICATIONS_DATA_FILE_PATH));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(csvReader);

            for (CSVRecord record : parser) {
                String id = record.get("id");
                String title = record.get("title");
                String year = record.get("year");
                String publisher = record.get("publisher");
                String chair = record.get("chair");
                String city = record.get("city");
                String area = record.get("area");
                String type = record.get("type");
                String venueId = record.get("venueId");

                publicationHashMap.put(id, new Publications(id, title, year, publisher, chair, city, area, type, venueId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicationHashMap;
    }

    public static void main(String[] args) {
        createAndSaveABOX();
    }
}