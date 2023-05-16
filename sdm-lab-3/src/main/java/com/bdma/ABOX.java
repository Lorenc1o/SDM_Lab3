package com.bdma;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

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
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ABOX {

    private static final String BASE_URI = "http://www.bdma.com/";
    private static final String RESOURCES_TBOX_OWL = "src/main/resources/tbox.owl";
    private static final String PAPERS_DATA_FILE_PATH = "src/main/resources/cleaned_papers.csv";
    private static final String PERSONS_DATA_FILE_PATH = "src/main/resources/cleaned_persons.csv";
    private static final String PUBLICATIONS_DATA_FILE_PATH = "src/main/resources/cleaned_publications.csv";
    private static final String VENUES_DATA_FILE_PATH = "src/main/resources/cleaned_venues.csv";
    private static final String ABOX_MODEL_PATH = "./src/main/resources/abox.nt";

    public static void createABOX() {

        Random random = new Random();

        try {

           // Reading & building Ontology model from TBOX

            Model m = ModelFactory.createDefaultModel().read(new File(RESOURCES_TBOX_OWL).toURI().toString());
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);

            // Getting all OntClasses (concepts) from TBOX

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

            // Getting all Object Properties from TBOX

            OntProperty relatedTo = model.getOntProperty(BASE_URI.concat("relatedTo"));
            OntProperty isManagerOf = model.getOntProperty(BASE_URI.concat("isManagerOf"));
            OntProperty isChairOf = model.getOntProperty(BASE_URI.concat("isChairOf"));
            OntProperty isEditorOf = model.getOntProperty(BASE_URI.concat("isEditorOf"));
            OntProperty includedIn = model.getOntProperty(BASE_URI.concat("includedIn"));
            OntProperty includedInJournal = model.getOntProperty(BASE_URI.concat("includedInJournal"));
            OntProperty includedInConference = model.getOntProperty(BASE_URI.concat("includedInConference"));
            OntProperty hasAuthor = model.getOntProperty(BASE_URI.concat("hasAuthor"));
            OntProperty publishedAs = model.getOntProperty(BASE_URI.concat("publishedAs"));
            OntProperty submittedTo = model.getOntProperty(BASE_URI.concat("submittedTo"));
            OntProperty submittedToConference = model.getOntProperty(BASE_URI.concat("submittedToConference"));
            OntProperty submittedToJournal = model.getOntProperty(BASE_URI.concat("submittedToJournal"));
            OntProperty assignedBy = model.getOntProperty(BASE_URI.concat("assignedBy"));
            OntProperty assignedPaper = model.getOntProperty(BASE_URI.concat("assignedPaper"));
            OntProperty writtenBy = model.getOntProperty(BASE_URI.concat("writtenBy"));

            // Getting all Data Properties from TBOX

            OntProperty title = model.getOntProperty(BASE_URI.concat("title"));
            OntProperty _abstract = model.getOntProperty(BASE_URI.concat("abstract"));
            OntProperty affiliation = model.getOntProperty(BASE_URI.concat("affiliation"));
            OntProperty areaDesc = model.getOntProperty(BASE_URI.concat("areaDescription"));
            OntProperty additionalRemarks = model.getOntProperty(BASE_URI.concat("additionalRemarks"));
            OntProperty areaName = model.getOntProperty(BASE_URI.concat("areaName"));
            OntProperty chairUntil = model.getOntProperty(BASE_URI.concat("chairUntil"));
            OntProperty publicationDate = model.getOntProperty(BASE_URI.concat("publicationDate"));
            OntProperty date = model.getOntProperty(BASE_URI.concat("date"));
            OntProperty decision = model.getOntProperty(BASE_URI.concat("decision"));
            OntProperty editorUntil = model.getOntProperty(BASE_URI.concat("editorUntil"));
            OntProperty heldIn = model.getOntProperty(BASE_URI.concat("heldIn"));
            OntProperty isPurposive = model.getOntProperty(BASE_URI.concat("isPurposive"));
            OntProperty issn = model.getOntProperty(BASE_URI.concat("ISSN"));
            OntProperty name = model.getOntProperty(BASE_URI.concat("name"));
            OntProperty numberOfExperts = model.getOntProperty(BASE_URI.concat("numberOfExperts"));
            OntProperty numberOfPapersInProceeding = model.getOntProperty(BASE_URI.concat("numberOfPapersInProceeding"));
            OntProperty numberOfPapersInVolume = model.getOntProperty(BASE_URI.concat("numberOfPapersInVolume"));
            OntProperty venueDesc = model.getOntProperty(BASE_URI.concat("venueDescription"));
            OntProperty venueName = model.getOntProperty(BASE_URI.concat("venueName"));
            OntProperty periodicity = model.getOntProperty(BASE_URI.concat("periodicity"));
            OntProperty purpose = model.getOntProperty(BASE_URI.concat("purpose"));
            OntProperty specialization = model.getOntProperty(BASE_URI.concat("specialization"));
            OntProperty subject = model.getOntProperty(BASE_URI.concat("subject"));
            OntProperty reviewText = model.getOntProperty(BASE_URI.concat("reviewText"));
            OntProperty urlToDemo = model.getOntProperty(BASE_URI.concat("urlToDemo"));
            OntProperty publicationWebsite = model.getOntProperty(BASE_URI.concat("publicationWebsite"));
            OntProperty yearsOfExperience = model.getOntProperty(BASE_URI.concat("yearsOfExperience"));

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
                                break;
                        }

                        String paperTitle = value.paperTitle;
                        String paperAbstract = value.paperAbstract;

                        Individual _paper = __paper.createIndividual(BASE_URI.concat(replaceSpaces(value.paperTitle)));

                        switch (value.getPaperType()) {
                            case "FullPaper":
                                _paper.addProperty(additionalRemarks, model.createTypedLiteral("These are additional remarks."));
                                break;
                            case "ShortPaper":
                                _paper.addProperty(isPurposive, model.createTypedLiteral(random.nextInt(2) % 2 == 0));
                                break;
                            case "DemoPaper":
                                _paper.addProperty(urlToDemo, model.createTypedLiteral(value.getUrl()));
                                break;
                            case "Poster":
                                _paper.addProperty(purpose, model.createTypedLiteral("describe " + value.getArea()));
                                break;
                        }

                        String[] authors = value.getAuthorID().split(",");
                        for (String authorId : authors) {
                            Person __person = personHashMap.get(authorId);
                            if (__person != null) {
                                Individual _author = author.createIndividual(BASE_URI.concat(replaceSpaces(__person.getName())));
                                _paper.addProperty(hasAuthor, _author);
                                _author.addProperty(name, model.createTypedLiteral(__person.getName()));
                                _author.addProperty(affiliation, model.createTypedLiteral(__person.getAffiliation()));
                            }
                        }

                        _paper.addProperty(title, model.createTypedLiteral(paperTitle));
                        _paper.addProperty(_abstract, model.createTypedLiteral(paperAbstract == null ? "No abstract." : paperAbstract));

                        String areaNameInstance = value.getArea();

                        Individual _area = area.createIndividual(BASE_URI.concat(replaceSpaces(value.getArea())));
                        _area.addProperty(areaName, model.createTypedLiteral(areaNameInstance));
                        _area.addProperty(areaDesc, model.createTypedLiteral("This is the description of the area."));
                        _paper.addProperty(relatedTo, _area);

                        Individual _review1 = review.createIndividual(BASE_URI.concat(replaceSpaces(value.getDecisions_1())));
                        _review1.addProperty(assignedPaper, _paper);
                        _review1.addProperty(reviewText, model.createTypedLiteral(value.getDecisions_1()));
                        Individual _review2 = review.createIndividual(BASE_URI.concat(replaceSpaces(value.getDecisions_2())));
                        _review2.addProperty(assignedPaper, _paper);
                        _review2.addProperty(reviewText, model.createTypedLiteral(value.getDecisions_2()));


                        Individual _reviewer1 = reviewer.createIndividual(BASE_URI.concat(replaceSpaces(value.getReviewer_1())));
                        _review1.addProperty(writtenBy, _reviewer1);
                        _review1.addProperty(decision, model.createTypedLiteral(value.getDecisions_1()));
                        _reviewer1.addProperty(name, model.createTypedLiteral(value.getReviewer_1()));
                        _reviewer1.addProperty(specialization, value.getArea());
                        Individual _reviewer2 = reviewer.createIndividual(BASE_URI.concat(replaceSpaces(value.getReviewer_2())));
                        _review2.addProperty(writtenBy, _reviewer2);
                        _review2.addProperty(decision, model.createTypedLiteral(value.getDecisions_2()));
                        _reviewer2.addProperty(name, model.createTypedLiteral(value.getReviewer_2()));
                        _reviewer2.addProperty(specialization, value.getArea());

                        if (value.getPublicationId() != null) {
                            Publications __publication = publicationHashMap.get(replaceSpaces(value.getPublicationId()));
                            if (__publication != null) {
                                Venue __venue = venueHashMap.get(replaceSpaces(__publication.getVenueId()));

                                if (__publication.getType().equals("Volume")) {
                                    Individual _volume = volume.createIndividual(BASE_URI.concat(replaceSpaces(__publication.getTitle())));
                                    _paper.addProperty(publishedAs, _volume);
                                    _volume.addProperty(relatedTo, _area);
                                    _volume.addProperty(publicationWebsite, model.createTypedLiteral(__venue.getUrl() != null ? __venue.getUrl() : "this publication has no URL."));
                                    _volume.addProperty(publicationDate, model.createTypedLiteral(__publication.getYear(), XSDDatatype.XSDdate));
                                    _volume.addProperty(numberOfPapersInVolume, model.createTypedLiteral(random.nextInt(10) * 10));

                                    Individual _journal = journal.createIndividual(BASE_URI.concat(replaceSpaces(__venue.getName())));
                                    String __editor = __venue.getEditor();
                                    Individual _editor = editor.createIndividual(BASE_URI.concat(replaceSpaces(__editor)));
                                    _editor.addProperty(name, model.createTypedLiteral(__editor));
                                    _editor.addProperty(isEditorOf, _journal);
                                    _editor.addProperty(yearsOfExperience, model.createTypedLiteral(random.nextInt(15)));
                                    _review1.addProperty(assignedBy, _editor);
                                    _review2.addProperty(assignedBy, _editor);
                                    _journal.addProperty(issn, model.createTypedLiteral(__venue.getIssn() != null ? __venue.getIssn() : "no ISSN"));
                                    _journal.addProperty(relatedTo, _area);

                                    // Generate a random date in the future (within the next 5 years)
                                    LocalDate now = LocalDate.now();
                                    int randomDaysToAdd = new Random().nextInt(365 * 5); // up to 5 years
                                    LocalDate futureDate = now.plus(randomDaysToAdd, ChronoUnit.DAYS);

                                    // Convert the future date to an XSD date string
                                    String futureDateString = futureDate.toString();

                                    // Create a typed literal for the future date
                                    Literal futureDateLiteral = model.createTypedLiteral(futureDateString, XSDDatatype.XSDdate);

                                    // Add the future date property to the editor
                                    _editor.addProperty(editorUntil, futureDateLiteral);

                                    _journal.addProperty(venueName, model.createTypedLiteral(__venue.getName()));
                                    _journal.addProperty(venueDesc, model.createTypedLiteral(__venue.getName() + " about " + __venue.getArea()));

                                    _volume.addProperty(includedInJournal, _journal);
                                    _paper.addProperty(submittedToJournal, _journal);
                                } else {
                                    Individual _proceeding = proceeding.createIndividual(BASE_URI.concat(replaceSpaces(__publication.getTitle())));
                                    _paper.addProperty(publishedAs, _proceeding);
                                    _proceeding.addProperty(relatedTo, _area);
                                    _proceeding.addProperty(publicationWebsite, model.createTypedLiteral(__venue.getUrl() != null ? __venue.getUrl() : "this publication has no URL."));
                                    _proceeding.addProperty(numberOfPapersInProceeding, model.createTypedLiteral(random.nextInt(10) * 10));
                                    _proceeding.addProperty(heldIn, model.createTypedLiteral("Barcelona"));

                                    switch (__venue.getConferenceType()) {
                                        case "ExpertGroup":
                                            Individual _expertGroup = expertGroup.createIndividual(BASE_URI.concat(replaceSpaces(__venue.getName())));
                                            String __chair = __venue.getChair();
                                            Individual _chair = chair.createIndividual(BASE_URI.concat(replaceSpaces(__chair)));
                                            _chair.addProperty(name, model.createTypedLiteral(__chair));
                                            _chair.addProperty(isChairOf, _expertGroup);
                                            _chair.addProperty(yearsOfExperience, model.createTypedLiteral(random.nextInt(15)));
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);
                                            _expertGroup.addProperty(periodicity, model.createTypedLiteral(__venue.getPeriodicity()));
                                            _expertGroup.addProperty(venueName, model.createTypedLiteral(__venue.getName()));
                                            _expertGroup.addProperty(venueDesc, model.createTypedLiteral(__venue.getName() + " about " + __venue.getArea()));
                                            _expertGroup.addProperty(numberOfExperts, model.createTypedLiteral(random.nextInt(10) * 8));
                                            _expertGroup.addProperty(relatedTo, _area);

                                            // Generate a random date in the future (within the next 5 years)
                                            LocalDate now = LocalDate.now();
                                            int randomDaysToAdd = new Random().nextInt(365 * 5); // up to 5 years
                                            LocalDate futureDate = now.plus(randomDaysToAdd, ChronoUnit.DAYS);

                                            // Convert the future date to an XSD date string
                                            String futureDateString = futureDate.toString();

                                            // Create a typed literal for the future date
                                            Literal futureDateLiteral = model.createTypedLiteral(futureDateString, XSDDatatype.XSDdate);

                                            // Add the future date property to the chair
                                            _chair.addProperty(chairUntil, futureDateLiteral);

                                            _expertGroup.addProperty(includedInConference, _proceeding);
                                            _paper.addProperty(submittedToConference, _expertGroup);
                                            break;
                                        case "Symposium":
                                            Individual _symposium = symposium.createIndividual(BASE_URI.concat(replaceSpaces(__venue.getName())));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(replaceSpaces(__chair)));
                                            _chair.addProperty(name, model.createTypedLiteral(__chair));
                                            _chair.addProperty(isChairOf, _symposium);
                                            _chair.addProperty(yearsOfExperience, model.createTypedLiteral(random.nextInt(15)));
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);
                                            _symposium.addProperty(periodicity, model.createTypedLiteral(__venue.getPeriodicity()));
                                            _symposium.addProperty(venueName, model.createTypedLiteral(__venue.getName()));
                                            _symposium.addProperty(venueDesc, model.createTypedLiteral(__venue.getName() + " about " + __venue.getArea()));
                                            _symposium.addProperty(subject, value.getArea());
                                            _proceeding.addProperty(includedInConference, _symposium);
                                            _paper.addProperty(submittedToConference, _symposium);
                                            _symposium.addProperty(relatedTo, _area);

                                            // Generate a random date in the future (within the next 5 years)
                                            now = LocalDate.now();
                                            randomDaysToAdd = new Random().nextInt(365 * 5); // up to 5 years
                                            futureDate = now.plus(randomDaysToAdd, ChronoUnit.DAYS);

                                            // Convert the future date to an XSD date string
                                            futureDateString = futureDate.toString();

                                            // Create a typed literal for the future date
                                            futureDateLiteral = model.createTypedLiteral(futureDateString, XSDDatatype.XSDdate);

                                            // Add the future date property to the chair
                                            _chair.addProperty(chairUntil, futureDateLiteral);
                                            break;
                                        case "Workshop":
                                            Individual _workshop = workshop.createIndividual(BASE_URI.concat(replaceSpaces(__venue.getName())));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(replaceSpaces(__chair)));
                                            _chair.addProperty(name, model.createTypedLiteral(__chair));
                                            _chair.addProperty(isChairOf, _workshop);
                                            _chair.addProperty(yearsOfExperience, model.createTypedLiteral(random.nextInt(15)));
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);
                                            _workshop.addProperty(periodicity, model.createTypedLiteral(__venue.getPeriodicity()));
                                            _workshop.addProperty(venueName, model.createTypedLiteral(__venue.getName()));
                                            _workshop.addProperty(venueDesc, model.createTypedLiteral(__venue.getName() + " about " + __venue.getArea()));
                                            _proceeding.addProperty(includedInConference, _workshop);
                                            _workshop.addProperty(relatedTo, _area);

                                            // Generate a random date in the past (within the past 5 years)
                                            LocalDate now2 = LocalDate.now();
                                            int randomDaysToSubtract = new Random().nextInt(365 * 5); // up to 5 years
                                            LocalDate pastDate = now2.minus(randomDaysToSubtract, ChronoUnit.DAYS);

                                            // Convert the past date to an XSD date string
                                            String pastDateString = pastDate.toString();

                                            // Create a typed literal for the past date
                                            Literal pastDateLiteral = model.createTypedLiteral(pastDateString, XSDDatatype.XSDdate);

                                            // Add the past date property to the workshop
                                            _workshop.addProperty(date, pastDateLiteral);
                                            _paper.addProperty(submittedToConference, _workshop);

                                            // Generate a random date in the future (within the next 5 years)
                                            now = LocalDate.now();
                                            randomDaysToAdd = new Random().nextInt(365 * 5); // up to 5 years
                                            futureDate = now.plus(randomDaysToAdd, ChronoUnit.DAYS);

                                            // Convert the future date to an XSD date string
                                            futureDateString = futureDate.toString();

                                            // Create a typed literal for the future date
                                            futureDateLiteral = model.createTypedLiteral(futureDateString, XSDDatatype.XSDdate);

                                            // Add the future date property to the chair
                                            _chair.addProperty(chairUntil, futureDateLiteral);
                                            break;
                                        default:
                                            Individual _regularConference = regularConference.createIndividual(BASE_URI.concat(replaceSpaces(__venue.getName())));
                                            __chair = __venue.getChair();
                                            _chair = chair.createIndividual(BASE_URI.concat(replaceSpaces(__chair)));
                                            _chair.addProperty(name, model.createTypedLiteral(__chair));
                                            _chair.addProperty(isChairOf, _regularConference);
                                            _chair.addProperty(yearsOfExperience, model.createTypedLiteral(random.nextInt(15)));
                                            _review1.addProperty(assignedBy, _chair);
                                            _review2.addProperty(assignedBy, _chair);
                                            _regularConference.addProperty(periodicity, model.createTypedLiteral(__venue.getPeriodicity()));
                                            _regularConference.addProperty(venueName, model.createTypedLiteral(__venue.getName()));
                                            _regularConference.addProperty(venueDesc, model.createTypedLiteral(__venue.getName() + " about " + __venue.getArea()));
                                            _proceeding.addProperty(includedInConference, _regularConference);
                                            _paper.addProperty(submittedToConference, _regularConference);
                                            _regularConference.addProperty(relatedTo, _area);

                                            // Generate a random date in the future (within the next 5 years)
                                            now = LocalDate.now();
                                            randomDaysToAdd = new Random().nextInt(365 * 5); // up to 5 years
                                            futureDate = now.plus(randomDaysToAdd, ChronoUnit.DAYS);

                                            // Convert the future date to an XSD date string
                                            futureDateString = futureDate.toString();

                                            // Create a typed literal for the future date
                                            futureDateLiteral = model.createTypedLiteral(futureDateString, XSDDatatype.XSDdate);

                                            // Add the future date property to the chair
                                            _chair.addProperty(chairUntil, futureDateLiteral);

                                            break;
                                    }
                                }
                            }
                        }
                    }
            );

            saveABOXFile(model);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replaceSpaces(String name) {
        return name.replaceAll(" ", "_");
    }

    private static void saveABOXFile(OntModel model) throws IOException {
        FileOutputStream writerStream = new FileOutputStream(ABOX_MODEL_PATH);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
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
                String affiliation = record.get("affiliation");

                personsHashMap.put(id, new Person(id, name, dob, affiliation));
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

}