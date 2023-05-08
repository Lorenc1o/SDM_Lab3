package com.bdma;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;


public class TBOX {

    private static final String BASE_URI = "http://www.bdma.com/#";
    public static final String RESOURCES_TBOX_OWL = "./src/main/resources/tbox.owl";

    public static void main(String[] args) {
        createTBOX();
    }

    public static void createTBOX() {

        OntModel model = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_RDFS_INF );

        // Ontology for Area

        OntClass area = model.createClass( BASE_URI.concat("Area") );

        // Ontology for Venue

        OntClass venue = model.createClass( BASE_URI.concat("Venue") );
        OntClass conference = model.createClass( BASE_URI.concat("Conference") );
        OntClass journal = model.createClass( BASE_URI.concat("Journal") );

        venue.addSubClass( conference );
        venue.addSubClass( journal );

        createProperty(model, venue, area, "related_to", "venue related to area");

        // Ontology for Conference

        OntClass workshop = model.createClass( BASE_URI.concat("Workshop") );
        OntClass symposium = model.createClass( BASE_URI.concat("Symposium") );
        OntClass expertGroup = model.createClass( BASE_URI.concat("Expert_Group") );
        OntClass regularConference = model.createClass( BASE_URI.concat("Regular_Conference") );

        conference.addSubClass( workshop );
        conference.addSubClass( symposium );
        conference.addSubClass( expertGroup );
        conference.addSubClass( regularConference );

        // Ontology for Persons

        OntClass person = model.createClass( BASE_URI.concat("Person") );

        OntClass author = model.createClass( BASE_URI.concat("Author") );
        OntClass manager = model.createClass( BASE_URI.concat("Manager") );
        OntClass reviewer = model.createClass( BASE_URI.concat("Reviewer") );
        OntClass editor = model.createClass( BASE_URI.concat("Editor") );
        OntClass chair = model.createClass( BASE_URI.concat("Chair") );

        person.addSubClass( author );
        person.addSubClass( manager );
        person.addSubClass( reviewer );

        // Ontology for Manager

        manager.addSubClass(editor);
        manager.addSubClass(chair);

        createProperty(model, manager, venue, "is_manager_of", "manager is managing venue");
        createPropertyWithSuperProperty(model, chair, conference, "is_chair_of", "chair is managing conference", "is_manager_of");
        createPropertyWithSuperProperty(model, editor, journal, "is_editor_of", "editor is managing journal", "is_manager_of");

        // Ontology for Publications

        OntClass publications = model.createClass( BASE_URI.concat("Publications") );

        createProperty(model, publications, venue, "included_in", "publication included in venue");
        createProperty(model, publications, area, "related_to", "publication related to area");

        // Ontology for Papers

        OntClass paper = model.createClass( BASE_URI.concat("Paper") );

        createProperty(model, paper, author, "has_author", "author of the paper");
        createProperty(model, paper, publications, "published_as", "paper published as publication");
        createProperty(model, paper, area, "related_to", "paper related to area");
        createProperty(model, paper, venue, "submitted_to", "paper submitted to venue");

        OntClass fullPaper = model.createClass( BASE_URI.concat("Full_Paper") );
        OntClass shortPaper = model.createClass( BASE_URI.concat("Short_Paper") );
        OntClass demoPaper = model.createClass( BASE_URI.concat("Demo_Paper") );
        OntClass poster = model.createClass( BASE_URI.concat("Poster") );

        paper.addSubClass( fullPaper );
        paper.addSubClass( shortPaper );
        paper.addSubClass( demoPaper );
        paper.addSubClass( poster );

        // Ontology for Review

        OntClass review = model.createClass( BASE_URI.concat("Review") );

        createProperty(model, review, manager, "assigned_by", "review assigned by manager");
        createProperty(model, review, paper, "assigned_paper", "review assigned paper");
        createProperty(model, review, reviewer, "written_by", "review written by reviewer");

        writeTBOX(model);
    }

    private static void createProperty(OntModel model, OntClass domain, OntClass range, String urlProb, String label) {
        OntProperty submit = model.createOntProperty(BASE_URI.concat(urlProb));
        submit.addDomain(domain);
        submit.addRange(range);
        submit.addLabel(label, "en");
    }

    private static void createPropertyWithSuperProperty(OntModel model, OntClass domain, OntClass range, String urlProb, String label, String superProp) {
        OntProperty submit = model.createOntProperty(BASE_URI.concat(urlProb));
        submit.addDomain(domain);
        submit.addRange(range);
        submit.addLabel(label, "en");
        submit.addSuperProperty(model.getOntProperty(BASE_URI.concat(superProp)));
    }

    private static void writeTBOX(OntModel model) {
        try {
            FileOutputStream writerStream = new FileOutputStream(RESOURCES_TBOX_OWL);
            model.write(writerStream, "TTL");
            writerStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
