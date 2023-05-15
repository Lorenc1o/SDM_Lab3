package com.bdma;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.EnumeratedClass;
import org.apache.jena.ontology.Individual;


public class TBOX {

    private static final String BASE_URI = "http://www.bdma.com/";
    public static final String RESOURCES_TBOX_OWL = "src/main/resources/tbox5.owl";

    public static void main(String[] args) {
        createTBOX();
    }

    public static void createTBOX() {

        OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RDFS_INF );

        // Ontology for Area
        OntClass area = model.createClass( BASE_URI.concat("Area") );

        createDatatypeProperty(model, area, XSD.xstring, "areaDescription", "description of the area");
        createDatatypeProperty(model, area, XSD.xstring, "areaName", "name of the area");

        // Ontology for Venue

        OntClass venue = model.createClass( BASE_URI.concat("Venue") );
        OntClass conference = model.createClass( BASE_URI.concat("Conference") );
        OntClass journal = model.createClass( BASE_URI.concat("Journal") );

        venue.addSubClass( conference );
        venue.addSubClass( journal );

        createProperty(model, venue, area, "relatedTo", "venue related to area");

        createDatatypeProperty(model, venue, XSD.xstring, "venueDescription", "description of the venue");
        createDatatypeProperty(model, venue, XSD.xstring, "venueName", "name of the venue");

        // Create periodicity property
        DatatypeProperty periodicity = model.createDatatypeProperty(BASE_URI.concat("periodicity"));
        periodicity.addDomain(conference);
        periodicity.addRange(XSD.xstring);

        // Create EnumeratedClass for periodicity
        EnumeratedClass periodicityEnum = model.createEnumeratedClass(null, null);

        // Add individuals to the enumerated class
        Individual monthly = model.createIndividual(BASE_URI.concat("Monthly"), null);
        Individual yearly = model.createIndividual(BASE_URI.concat("Yearly"), null);
        Individual weekly = model.createIndividual(BASE_URI.concat("Weekly"), null);

        periodicityEnum.addOneOf(monthly);
        periodicityEnum.addOneOf(yearly);
        periodicityEnum.addOneOf(weekly);

        // Set the range of periodicity property to be the enumerated class
        periodicity.setRange(periodicityEnum);

        // Create ISSN property
        DatatypeProperty issn = model.createDatatypeProperty(BASE_URI.concat("ISSN"));
        issn.addDomain(journal);
        issn.addRange(XSD.xstring);

        // Ontology for Conference

        OntClass workshop = model.createClass( BASE_URI.concat("Workshop") );
        OntClass symposium = model.createClass( BASE_URI.concat("Symposium") );
        OntClass expertGroup = model.createClass( BASE_URI.concat("ExpertGroup") );
        OntClass regularConference = model.createClass( BASE_URI.concat("RegularConference") );

        conference.addSubClass( workshop );
        conference.addSubClass( symposium );
        conference.addSubClass( expertGroup );
        conference.addSubClass( regularConference );

        createDatatypeProperty(model, workshop, XSD.dateTime, "date", "date of the workshop");
        createDatatypeProperty(model, symposium, XSD.xstring, "subject", "subject of the symposium");
        createDatatypeProperty(model, expertGroup, XSD.integer, "numberOfExperts", "number of expoerts in the group");

        // Ontology for Persons

        OntClass person = model.createClass( BASE_URI.concat("Person") );

        OntClass author = model.createClass( BASE_URI.concat("Author") );
        OntClass manager = model.createClass( BASE_URI.concat("Manager") );
        OntClass reviewer = model.createClass( BASE_URI.concat("Reviewer") );
        OntClass editor = model.createClass( BASE_URI.concat("Editor") );
        OntClass chair = model.createClass( BASE_URI.concat("Chair") );

        manager.addSubClass(editor);
        manager.addSubClass(chair);

        person.addSubClass( author );
        person.addSubClass( manager );
        person.addSubClass( reviewer );

        createDatatypeProperty(model, person, XSD.xstring, "name", "name of the person");
        createDatatypeProperty(model, author, XSD.xstring, "affiliation", "affiliation of the author");
        createDatatypeProperty(model, manager, XSD.xstring, "yearsOfExperience", "years of experience of the manager");
        createDatatypeProperty(model, reviewer, XSD.xstring, "specialization", "specialization of the reviewer");
        createDatatypeProperty(model, editor, XSD.dateTime, "editorUntil", "date until when the editor works");
        createDatatypeProperty(model, chair, XSD.dateTime, "chairUntil", "date until when the chair works");

        createProperty(model, manager, venue, "isManagerOf", "manager is managing venue");
        createPropertyWithSuperProperty(model, chair, conference, "isChairOf", "chair is managing conference", "isManagerOf");
        createPropertyWithSuperProperty(model, editor, journal, "isEditorOf", "editor is managing journal", "isManagerOf");

        // Ontology for Publications

        OntClass publication = model.createClass( BASE_URI.concat("Publication") );
        OntClass volume = model.createClass( BASE_URI.concat("Volume") );
        OntClass proceeding = model.createClass( BASE_URI.concat("Proceeding") );

        publication.addSubClass(volume);
        publication.addSubClass(proceeding);

        createProperty(model, publication, venue, "includedIn", "publication included in venue");
        createPropertyWithSuperProperty(model, volume, journal, "includedInJournal", "volume included in journal", "includedIn");
        createPropertyWithSuperProperty(model, proceeding, conference, "includedInConference", "proceeding included in conference", "includedIn");

        createProperty(model, publication, area, "relatedTo", "publication related to area");

        // Create title property
        DatatypeProperty title = model.createDatatypeProperty(BASE_URI.concat("title"));
        title.addDomain(publication);
        title.addRange(XSD.xstring);

        createDatatypeProperty(model, proceeding, XSD.xstring, "heldIn", "place where the proceeding was held");
        createDatatypeProperty(model, proceeding, XSD.integer, "numberOfPapersInProceeding", "number of papers in the proceeding");
        createDatatypeProperty(model, volume, XSD.integer, "numberOfPapersInVolume", "number of papers in the volume");
        createDatatypeProperty(model, publication, XSD.dateTime, "publicationDate", "date of the publication");
        createDatatypeProperty(model, publication, XSD.xstring, "publicationWebsite", "website of the publication");

        // Ontology for Papers

        OntClass paper = model.createClass( BASE_URI.concat("Paper") );

        createProperty(model, paper, author, "hasAuthor", "author of the paper");
        createProperty(model, paper, publication, "publishedAs", "paper published as publication");
        createProperty(model, paper, area, "relatedTo", "paper related to area");
        createProperty(model, paper, venue, "submittedTo", "paper submitted to venue");

        createPropertyWithSuperProperty(model, paper, conference, "submittedToConference", "paper is submitted To Conference", "submittedTo");

        OntClass fullPaper = model.createClass( BASE_URI.concat("FullPaper") );
        OntClass shortPaper = model.createClass( BASE_URI.concat("ShortPaper") );
        OntClass demoPaper = model.createClass( BASE_URI.concat("DemoPaper") );
        OntClass poster = model.createClass( BASE_URI.concat("Poster") );

        paper.addSubClass( fullPaper );
        paper.addSubClass( shortPaper );
        paper.addSubClass( demoPaper );
        paper.addSubClass( poster );

        createPropertyWithSuperProperty(model, fullPaper, journal, "submittedToJournal", "paper is submitted To Journal", "submittedTo");
        addDomainToProperty(model, shortPaper, "submittedToJournal");
        addDomainToProperty(model, demoPaper, "submittedToJournal");

        // Add paper to title property's domain
        title.addDomain(paper);

        createDatatypeProperty(model, paper, XSD.xstring, "abstract", "abstract of the paper");
        createDatatypeProperty(model, fullPaper, XSD.xstring, "citation", "citation of the full paper");
        createDatatypeProperty(model, shortPaper, XSD.xboolean, "isPurposive", "indicates whether the short paper is purposive or not");
        createDatatypeProperty(model, demoPaper, XSD.xstring, "urlToDemo", "url to the demo of the demo paper");
        createDatatypeProperty(model, poster, XSD.xstring, "purpose", "purpose of the poster");

        // Ontology for Review

        OntClass review = model.createClass( BASE_URI.concat("Review") );

        createProperty(model, review, manager, "assignedBy", "review assigned by manager");
        createProperty(model, review, paper, "assignedPaper", "review assigned paper");
        createProperty(model, review, reviewer, "writtenBy", "review written by reviewer");

        createDatatypeProperty(model, review, XSD.xstring, "reviewText", "text of the review");

        // Create decision property
        DatatypeProperty decision = model.createDatatypeProperty(BASE_URI.concat("decision"));
        decision.addDomain(review);
        decision.addRange(XSD.xstring);

        // Create EnumeratedClass for decision
        EnumeratedClass decisionEnum = model.createEnumeratedClass(null, null);

        // Add individuals to the enumerated class
        Individual accept = model.createIndividual(BASE_URI.concat("Accept"), null);
        Individual reject = model.createIndividual(BASE_URI.concat("Reject"), null);

        decisionEnum.addOneOf(accept);
        decisionEnum.addOneOf(reject);

        // Set the range of decision property to be the enumerated class
        decision.setRange(decisionEnum);

        writeTBOX(model);
    }

    private static void createProperty(OntModel model, OntClass domain, OntClass range, String urlProb, String label) {
        OntProperty submit = model.createOntProperty(BASE_URI.concat(urlProb));
        submit.addDomain(domain);
        submit.addRange(range);
        submit.addLabel(label, "en");
    }

    private static void createDatatypeProperty(OntModel model, OntClass domain, Resource range, String urlProp, String label) {
        DatatypeProperty newProperty = model.createDatatypeProperty(BASE_URI.concat(urlProp));
        newProperty.addDomain(domain);
        newProperty.addRange(range);
        newProperty.addLabel(label, "en");
    }

    private static void createPropertyWithSuperProperty(OntModel model, OntClass domain, OntClass range, String urlProb, String label, String superProp) {
        OntProperty newProperty = model.createOntProperty(BASE_URI.concat(urlProb));
        newProperty.addDomain(domain);
        newProperty.addRange(range);
        newProperty.addLabel(label, "en");

        OntProperty superProperty = model.getOntProperty(BASE_URI.concat(superProp));
        superProperty.addSubProperty(newProperty);
    }

    private static void addDomainToProperty(OntModel model, OntClass domain, String urlProb) {
        OntProperty newProperty = model.getOntProperty(BASE_URI.concat(urlProb));
        newProperty.addDomain(domain);
    }

    private static void writeTBOX(OntModel model) {
        try {
            FileOutputStream writerStream = new FileOutputStream(RESOURCES_TBOX_OWL);
            model.write(writerStream, "RDF/XML");
            writerStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
