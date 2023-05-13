package com.bdma;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class PreProcessing {

    static String[] paperTypes = {"FullPaper", "ShortPaper", "DemoPaper", "Poster"};
//    static String[] areas = {"Machine Learning", "Art", "AI", "Graphs", "Algorithms", "Databases"};
    static String[] conferenceTypes = {"Symposium", "Workshop", "RegularConference"};
    static String[] periodicities = {"Weekly", "Monthly", "Yearly"};

    public static void main(String[] args) {
        Random random = new Random();
        try {
            BufferedReader nodesReader = new BufferedReader(new FileReader("src/main/resources/nodes.csv"));
            CSVParser nodesParser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(nodesReader);

            HashMap<String, Paper> paperHashMap = new HashMap<>();
            HashMap<String, Person> personHashMap = new HashMap<>();
            HashMap<String, Venue> venueHashMap = new HashMap<>();
            HashMap<String, Publications> publicationHashMap = new HashMap<>();
            HashMap<String, String> citiesHashMap = new HashMap<>();
            HashMap<String, String> areasHashMap = new HashMap<>();

            for (CSVRecord record : nodesParser) {

                String _id = record.get("_id");
                String id = record.get("id");
                String label = record.get("_labels");
                String anAbstract = record.get("abstract");
                String title = record.get("title");
                String name = record.get("name");
                String dob = record.get("date_of_birth");
                String eic = record.get("editors_in_chief").split(",")[0];
                String pc = record.get("programme_chairs").split(",")[0];
                String publisher = record.get("publisher");
                String url = record.get("url");
                String year = record.get("year");
                String issn = record.get("issn");
                String value = record.get("value");

                if (!label.equals("")) {
                    switch (label) {
                        case ":Paper":
                            Paper paper = new Paper();
                            paper.setId(_id);
                            paper.setPaperAbstract(str_clean(anAbstract));
                            paper.setPaperTitle(str_clean(title));
//                            paper.setPaperType(paperTypes[random.nextInt(4)]);
                            paper.setYear(year);
                            paper.setUrl(url);
//                            paper.setArea(areas[random.nextInt(5)]);
                            paperHashMap.put(_id, paper);
                            break;
                        case ":Person":
                            Person person = new Person();
                            person.setDob(dob);
                            person.setId(_id);
                            person.setName(str_clean(name));
                            personHashMap.put(_id, person);
                            break;
                        case ":Journal": {
                            Venue venue = new Venue();
                            venue.setId(_id);
                            venue.setEditor(str_clean(eic));
                            venue.setName(str_clean(name));
                            venue.setUrl(url);
                            venue.setVenueType("Journal");
                            venue.setIssn(issn);
                            venue.setPeriodicity(periodicities[random.nextInt(3)]);
                            venueHashMap.put(_id, venue);
                            break;
                        }
                        case ":Event": {
                            Venue venue = new Venue();
                            venue.setId(_id);
                            venue.setName(str_clean(name));
                            venue.setUrl(url);
                            venue.setVenueType("Conference");
//                            venue.setChair(pc);
                            venue.setPeriodicity(periodicities[random.nextInt(3)]);
                            if (url.equals(""))
                                venue.setConferenceType(conferenceTypes[random.nextInt(3)]);
                            else
                                venue.setConferenceType("ExpertGroup");
                            venueHashMap.put(_id, venue);
                            break;
                        }
                        case ":Proceeding": {
                            Publications publications = new Publications();
                            publications.setId(_id);
                            publications.setTitle(str_clean(title));
                            publications.setChair(str_clean(pc));
                            publications.setPublisher(str_clean(publisher));
                            publications.setYear(year);
                            publications.setType("Proceeding");
                            publicationHashMap.put(_id, publications);
                            break;
                        }
                        case ":Volume": {
                            Publications publications = new Publications();
                            publications.setId(_id);
                            publications.setTitle(str_clean(id));
                            publications.setPublisher(str_clean(publisher));
                            publications.setYear(year);
                            publications.setType("Volume");
                            publicationHashMap.put(_id, publications);
                            break;
                        }
                        case ":City":
                            citiesHashMap.put(_id, name);
                            break;
                        case ":Keyword":
                            areasHashMap.put(_id, value);
                            break;
                    }
                }
            }

            BufferedReader relationsReader = new BufferedReader(new FileReader("src/main/resources/relations.csv"));
            CSVParser relationsParser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(relationsReader);

            for (CSVRecord record : relationsParser) {
                String _start = record.get("_start");
                String _end = record.get("_end");
                String _type = record.get("_type");
                String decision = record.get("decision");
                String review = record.get("review");

                switch (_type) {
                    case "relates": {
                        Paper paper = paperHashMap.get(_start);
                        String area = areasHashMap.get(_end);
                        paper.setArea(paper.getArea() == null ? area : paper.getArea() + "," + area);
                        paperHashMap.replace(_start, paper);
                        break;
                    }
                    case "authored": {
                        Paper paper = paperHashMap.get(_end);
                        paper.setAuthorID( paper.getAuthorID() == null ? _start : paper.getAuthorID() + "," +_start);
                        paperHashMap.replace(_end, paper);
                        break;
                    }
                    case "correspondingAuthor": {
                        Paper paper = paperHashMap.get(_end);
                        paper.setCorrAuthorID(_start);
                        paperHashMap.replace(_end, paper);
                        break;
                    }
                    case "reviewed": {
                        Paper paper = paperHashMap.get(_end);
                        Person person = personHashMap.get(_start);
                        if (paper.getReviewer_1() == null) {
                            paper.setReviewer_1(str_clean(person.getName()));
                            paper.setReview_1(str_clean(review));
                            paper.setDecisions_1(str_clean(decision));
                        } else {
                            paper.setReviewer_2(str_clean(person.getName()));
                            paper.setReview_2(str_clean(review));
                            paper.setDecisions_2(str_clean(decision));
                        }
                        paperHashMap.replace(_end, paper);
                        break;
                    }
                    case "published_in": {
//                            Venue venue = venueHashMap.get(_end);
//                            if (venue != null)
//                                if ( venue.getVenueType().equals("Journal")) {
//                                    paper.setVenue(venue.getName());
//                                    paper.setVenueType("Journal");
//                                    paper.setEditor(venue.getEditor());
//                                    paper.setManagerType("Editor");
//                                } else {
//                                    paper.setVenue(venue.getName());
//                                    paper.setConferenceType(venue.getConferenceType());
//                                    paper.setVenueType("Conference");
//                                    paper.setManager(venue.chair);
//                                    paper.setManagerType("Chair");
//                                }
                        Paper paper = paperHashMap.get(_start);
                        if (paper.getDecisions_1().equals("Yes") && paper.getDecisions_2().equals("Yes")) {
                            Publications publications = publicationHashMap.get(_end);
                            paper.setPublicationId(_end);
                            publications.setArea(paper.getArea());
                            publicationHashMap.replace(_end, publications);
                        }
//                        else {
                            String venueId = (String) venueHashMap.keySet().toArray()[random.nextInt(venueHashMap.size())];
                            Venue venue = venueHashMap.get(venueId);
                            paper.setPaperType(venue.getVenueType().equals("Journal") ? paperTypes[random.nextInt(3)] : paperTypes[random.nextInt(4)]);
                            paper.setVenueId(venueId);
                            venue.setArea(paper.getArea());
//                        }
                        paperHashMap.replace(_start, paper);
                        break;
                    }
                    case "part_of": {
                        Publications publications = publicationHashMap.get(_start);
                        Venue venue = venueHashMap.get(_end);
                        venue.setArea(publications.getArea());
                        if (publications.getType().equals("Volume"))
                            publications.setChair(venue.getEditor());
                        else
                            venue.setChair(publications.getChair());
//                        venue.setPublication(_start);
                        publications.setVenueId(publications.getVenueId() == null ? _end : publications.getVenueId() + "," + _end);
                        venueHashMap.replace(_end, venue);
                        publicationHashMap.replace(_start, publications);
                        break;
                    }
                    case "held_in": {
                        Publications publication = publicationHashMap.get(_start);
                        publication.setCity(citiesHashMap.get(_end));
                        publicationHashMap.replace(_start, publication);
                        break;
                    }
                }
            }

            String eol = System.getProperty("line.separator");

            Writer papersWriter = new FileWriter("src/main/resources/cleaned_papers2.csv");
            papersWriter.append("id;paperTitle;area;authorID;corrAuthorID;publicationID;venueId;reviewer_1;reviewer_2;review_1;review_2;paperType;paperAbstract;decisions_1;decisions_2;year;url").append(eol);
            for (Map.Entry<String, Paper> entry : paperHashMap.entrySet())
                papersWriter.append(entry.getValue().toString()).append(eol);
            papersWriter.close();

            Writer personsWriter = new FileWriter("src/main/resources/cleaned_persons2.csv");
            personsWriter.append("id;name;dob").append(eol);
            for (Map.Entry<String, Person> entry : personHashMap.entrySet())
                personsWriter.append(entry.getValue().toString()).append(eol);
            personsWriter.close();

            Writer venuesWriter = new FileWriter("src/main/resources/cleaned_venues2.csv");
            venuesWriter.append("id;area;publication;venueType;conferenceType;editor;url;name;chair;issn;periodicity").append(eol);
            for (Map.Entry<String, Venue> entry : venueHashMap.entrySet())
                venuesWriter.append(entry.getValue().toString()).append(eol);
            venuesWriter.close();

            Writer publicationsWriter = new FileWriter("src/main/resources/cleaned_publications2.csv");
            publicationsWriter.append("id;title;year;publisher;chair;city;area;type;venueId").append(eol);
            for (Map.Entry<String, Publications> entry : publicationHashMap.entrySet())
                publicationsWriter.append(entry.getValue().toString()).append(eol);
            publicationsWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String str_clean(String str) {
        return str
                .replace(".", "_")
                .replace(",", "_")
                .replace(": ", ":")
                .replace("'s", "_s")
                .replace(" ", "_")
                .replace("-", "_")
                .replace("’", "")
                .replace("©", "")
                .replace("›", "")
                .replace("‹", "")
                .replace("°", "")
                .replace("%", "")
                .replace("$", "")
                .replace("@", "")
                .replace("!", "")
                .replace("&", "")
                .replace("~", "")
                .replace("'", "")
                .replace("+", "")
                .replace("ν", "")
                .replace("η", "")
                .replace("?", "")
                .replace(">", "")
                .replace("<", "")
                .replace("μ", "")
                .replace("\"", "")
                .replace("\\", "")
                .replace("^", "")
                .replace("#", "")
                .replaceAll("\n", "")
                .replaceAll("[\\p{Ps}\\p{Pe}]", "") // To remove all opening & closing brackets (https://stackoverflow.com/a/25853119/6390175)
                .replaceAll("[^A-Za-z0-9] ","");
    }
}
